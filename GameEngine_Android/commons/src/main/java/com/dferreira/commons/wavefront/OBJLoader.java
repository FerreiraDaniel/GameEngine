package com.dferreira.commons.wavefront;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.shapes.WfObject;
import com.dferreira.commons.utils.ResourcesCache;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Parses files in wavefront format
 */
public class OBJLoader extends GenericLoader {

    /* Character for split material and group */
    private final static String MAT_GROUP_SPLIT = "@";

    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_VERTEX = 3;
    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_TEXTURE = 2;
    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_NORMAL = 3;
    private final static String TAG = "OBJLoader";


    /**
     * @param face reference to a Polygonal face
     * @return A key to use in the dictionary of indices
     */
    private static String getKey(PolygonalFace face) {
        String materialName = (face.getMaterialName() == null) ? "" : face.getMaterialName();
        String groupName = (face.getGroupName() == null) ? "" : face.getGroupName();
        return materialName + MAT_GROUP_SPLIT + groupName;
    }

    /**
     * Uses the hash map to create a list of objects
     *
     * @param verticesArray   Array of vertices
     * @param normalsArray    Array of normals
     * @param texturesArray   Array of textures positions
     * @param indicesArrayMap Dictionary with indices that make the faces
     * @param materials       HashMap of materials that make part of the object (if any)
     * @return List of objects created
     */
    private static List<IShape> buildShapesLst(float[] verticesArray, float[] normalsArray, float[] texturesArray,
                                               HashMap<String, List<Integer>> indicesArrayMap, HashMap<String, IExternalMaterial> materials) {
        List<IShape> wfObjectList = new ArrayList<>();
        for (String key : indicesArrayMap.keySet()) {
            List<Integer> indicesList = indicesArrayMap.get(key);
            String materialName = key.split(MAT_GROUP_SPLIT)[0];
            String groupName = key.split(MAT_GROUP_SPLIT)[1];

            int[] indicesArray = new int[indicesList.size()];
            for (int i = 0; i < indicesList.size(); i++) {
                indicesArray[i] = indicesList.get(i);
            }
            IExternalMaterial material = null;

            if ((materials != null) && (materials.containsKey(materialName))) {
                material = materials.get(materialName);
            }

            IShape shape = new WfObject(verticesArray, texturesArray, normalsArray, indicesArray, groupName, material);
            wfObjectList.add(shape);
        }
        return wfObjectList;
    }

    /**
     * Uses the list of elements of the waveFront file to create one shape
     *
     * @param vertices  List of vertices from the waveFront shape
     * @param normals   List of normals from the waveFront shape
     * @param textures  List of texture coordinates from the waveFront shape
     * @param facesLst  List of face from the waveFront shape
     * @param materials HashMap of materials that make part of the object (if any)
     * @return The waveFront element as an IShape
     */
    private static List<IShape> createShapes(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> textures,
                                             ArrayList<PolygonalFace> facesLst, HashMap<String, IExternalMaterial> materials) {
        // This are the format of data accepted by the loader
        // We setup the arrays now that we know the size of them
        float[] verticesArray = new float[vertices.size() * COORDINATES_BY_VERTEX];
        float[] normalsArray = new float[vertices.size() * COORDINATES_BY_NORMAL];
        float[] texturesArray = new float[vertices.size() * COORDINATES_BY_TEXTURE];
        HashMap<String, List<Integer>> indicesArrayMap = new HashMap<>();

        for (int j = 0; j < facesLst.size(); j++) {
            PolygonalFace face = facesLst.get(j);

            int vertexIndex = face.getVertexIndex();
            int normalIndex = face.getNormalIndex().intValue();
            int textureIndex = face.getTextureIndex().intValue();

            Vector3f vertex = vertices.get(vertexIndex);
            Vector3f currentNorm = normals.get(normalIndex);
            Vector2f currentTexture = textures.get(textureIndex);

            // Build index lists
            if (indicesArrayMap.containsKey(getKey(face))) {
                indicesArrayMap.get(getKey(face)).add(vertexIndex);
            } else {
                List<Integer> indicesArray = new ArrayList<>();
                indicesArray.add(vertexIndex);
                indicesArrayMap.put(getKey(face), indicesArray);
            }

            // Uses the (faces and vertices list to build the final vertices
            // array
            verticesArray[vertexIndex * COORDINATES_BY_VERTEX] = vertex.x;
            verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 1] = vertex.y;
            verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 2] = vertex.z;

            // Build the normals list
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL] = currentNorm.x;
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 1] = currentNorm.y;
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 2] = currentNorm.z;

            // Build the texture coordinates list
            if (currentTexture != null) {
                texturesArray[vertexIndex * COORDINATES_BY_TEXTURE] = currentTexture.x;
                texturesArray[vertexIndex * COORDINATES_BY_TEXTURE + 1] = 1.0f - currentTexture.y;
            }
        }

        return buildShapesLst(verticesArray, normalsArray, texturesArray, indicesArrayMap, materials);
    }


    /**
     * Parses one waveFront file
     *
     * @param context    Context where the method is called
     * @param resourceId id of the resource where the waveFront file exists
     * @return The shapes with information about the waveFront file read
     */
    @SuppressWarnings("SameParameterValue")
    private static List<IShape> pLoadObjModel(Context context, int resourceId) {
        InputStream inputStream;


        try {
            inputStream = context.getResources().openRawResource(resourceId);
        } catch (Exception e) {
            Log.e(TAG, "Could not load file!", e);
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        String line;
        String[] fVertexStr;
        HashMap<String, IExternalMaterial> materials = null;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        ArrayList<PolygonalFace> facesLst = new ArrayList<>();
        String currentGroupName = "";
        String currentMaterialName = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] currentLine = line.split(SPLIT_TOKEN);
                switch (currentLine[0]) {
                    // The comments in the OBJ do nothing
                    case OBJPrefix.COMMENT:
                        break;
                    // Definition of the list of materials of the model
                    case OBJPrefix.MATERIALS:
                        String materialsFileName = parseStringComponent(line, currentLine);
                        materials = MtlLoader.loadObjModel(context, materialsFileName);
                        break;
                    // Parses the information that is to update the current material
                    // used
                    case OBJPrefix.USE_MATERIAL:
                        currentMaterialName = parseStringComponent(line, currentLine);
                        break;
                    /// Define the name of the object or the group
                    case OBJPrefix.OBJECT_NAME:
                    case OBJPrefix.GROUP:
                        currentGroupName = parseStringComponent(line, currentLine);
                        break;
                    /// Defines the smooth shading (Not done yet)
                    case OBJPrefix.SMOOTH_SHADING:
                        break;
                    // Parses the vertices
                    case OBJPrefix.VERTEX:
                        Vector3f vertex = parseVector3f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y], currentLine[CoordinatePositions.z]);
                        vertices.add(vertex);
                        break;
                    // Parses the texture coordinates
                    case OBJPrefix.TEXTURE:
                        Vector2f texture = parseVector2f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y]);
                        textures.add(texture);
                        break;
                    // Parses the normals
                    case OBJPrefix.NORMAL:
                        Vector3f normal = parseVector3f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y], currentLine[CoordinatePositions.z]);
                        normals.add(normal);
                        break;
                    // Parses the faces
                    case OBJPrefix.FACE:
                        for (int i = 1; i < 4; i++) {
                            fVertexStr = currentLine[i].split("/");

                            facesLst.add(processVertex(fVertexStr, currentGroupName, currentMaterialName));
                        }
                        break;
                    default:
                        System.err.println("Impossible to parse:" + line);
                        break;
                }
            }
            return createShapes(vertices, normals, textures, facesLst, materials);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Parses one waveFront file (Using cache mechanism if possible)
     *
     * @param resourceId id of the resource where the waveFront file exists
     * @return Wavefront object
     */
    @SuppressWarnings({"SameParameterValue", "unchecked"})
    public static List<IShape> loadObjModel(Context context, int resourceId) {
        ResourcesCache cache = ResourcesCache.getInstance();
        List<IShape> shape = (List<IShape>) cache.get(resourceId);
        if (shape == null) {
            List<IShape> newShapes = pLoadObjModel(context, resourceId);
            if (newShapes != null) {
                cache.put(resourceId, newShapes);
            }
            return newShapes;
        } else {
            return shape;
        }
    }

    /**
     * Parse a component in string format and return its long value
     *
     * @param strComponent Component to parse
     * @return Long value or null if it is the case
     */
    private static Long parseLongComponent(String strComponent) {
        if (TextUtils.isEmpty(strComponent)) {
            return null;
        } else {
            return Long.parseLong(strComponent) - 1;
        }
    }

    /**
     * Parse a component in string format and return its short value
     *
     * @param strComponent Component to parse
     * @return Short value or null if it is the case
     */
    private static Integer parseIntComponent(String strComponent) {
        if (TextUtils.isEmpty(strComponent)) {
            return null;
        } else {
            return (Integer.parseInt(strComponent) - 1);
        }
    }

    /**
     * Process one vertex
     *
     * @param vertexData   All the indexes about a vertex (vertexIndex, textureIndex,
     *                     normalIndex)
     * @param groupName    Name of the group that the face belongs
     * @param materialName Material that the material has if any
     * @return A face
     */
    private static PolygonalFace processVertex(String[] vertexData, String groupName, String materialName) {
        Integer vertexIndex = parseIntComponent(vertexData[0]);
        Long textureIndex = parseLongComponent(vertexData[1]);
        Long normalIndex = parseLongComponent(vertexData[2]);

        return new PolygonalFace(vertexIndex, textureIndex, normalIndex, groupName, materialName);
    }
}
