package com.dferreira.game_engine.wave_front;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.utils.ResourcesCache;
import com.dferreira.commons.wavefront.PolygonalFace;
import com.dferreira.game_engine.shapes.IShape;
import com.dferreira.game_engine.shapes.WfObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses files in wavefront format
 */
public class OBJLoader {

    private final static String TAG = "OBJLoader";

    /**
     * Vertices positions in the model
     */
    private static final String VERTEX_PREFIX = "v";

    /**
     * Normal vertices
     */
    private static final String NORMAL_PREFIX = "vn";

    /**
     * Vertices texture coordinates in our model
     */
    private static final String TEXTURE_PREFIX = "vt";

    /**
     * Face each face represents one triangle
     */
    private static final String FACE_PREFIX = "f";

    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_VERTEX = 3;
    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_TEXTURE = 2;
    @SuppressWarnings("WeakerAccess")
    public final static int COORDINATES_BY_NORMAL = 3;

    /**
     * Parse two strings and return the equivalent vector2f
     *
     * @param xStr X component
     * @param yStr Y component
     * @return The parsed vector
     */
    private static Vector2f parseVector2f(String xStr, String yStr) {
        float x = Float.parseFloat(xStr);
        float y = Float.parseFloat(yStr);

        return new Vector2f(x, y);
    }

    /**
     * Parse two strings and return the equivalent vector2f
     *
     * @param xStr X component
     * @param yStr Y component
     * @param zStr Z component
     * @return The parsed vector
     */
    private static Vector3f parseVector3f(String xStr, String yStr, String zStr) {
        float x = Float.parseFloat(xStr);
        float y = Float.parseFloat(yStr);
        float z = Float.parseFloat(zStr);

        return new Vector3f(x, y, z);
    }

    /**
     * Uses the list of elements of the waveFront file to create one shape
     *
     * @param vertices	List of vertices from the waveFront shape
     * @param normals	List of normals from the waveFront shape
     * @param textures	List of texture coordinates from the waveFront shape
     * @param facesLst 	List of face from the waveFront shape

     * @return The waveFront element as an IShape
     */
    private static IShape createShape(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> textures,
                                      ArrayList<PolygonalFace> facesLst) {
        // This are the format of data accepted by the loader
        // We setup the arrays now that we know the size of them
        float[] verticesArray = new float[vertices.size() * COORDINATES_BY_VERTEX];
        float[] normalsArray = new float[vertices.size() * COORDINATES_BY_NORMAL];
        float[] texturesArray = new float[vertices.size() * COORDINATES_BY_TEXTURE];
        int[] indicesArray = new int[facesLst.size()];

        for (int j = 0; j < facesLst.size(); j++) {
            PolygonalFace face = facesLst.get(j);

            int vertexIndex = face.getVertexIndex();
            int normalIndex = face.getNormalIndex().intValue();
            int textureIndex = face.getTextureIndex().intValue();

            // Point to loaded structure
            Vector3f vertex = vertices.get(vertexIndex);
            Vector3f currentNorm = normals.get(normalIndex);
            Vector2f currentTexture = textures.get(textureIndex);

            // Build index lists
            indicesArray[j] = vertexIndex;

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
                texturesArray[vertexIndex * COORDINATES_BY_TEXTURE + 1] = currentTexture.y;
            }

        }

        return new WfObject(verticesArray, texturesArray, normalsArray, indicesArray);
    }

    /**
     * Parses one waveFront file
     *
     * @param resourceId id of the resource where the waveFront file exists
     * @return Wavefront object
     */
    @SuppressWarnings("SameParameterValue")
    private static IShape pLoadObjModel(Context context, int resourceId) {
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
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        ArrayList<PolygonalFace> facesLst = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                String[] currentLine = line.split(" ");
                switch (currentLine[0]) {
                    // Parses the vertices
                    case VERTEX_PREFIX:
                        Vector3f vertex = parseVector3f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y], currentLine[CoordinatePositions.z]);
                        vertices.add(vertex);
                        break;
                    // Parses the texture coordinates
                    case TEXTURE_PREFIX:
                        Vector2f texture = parseVector2f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y]);
                        textures.add(texture);
                        break;
                    // Parses the normals
                    case NORMAL_PREFIX:
                        Vector3f normal = parseVector3f(currentLine[CoordinatePositions.x],
                                currentLine[CoordinatePositions.y], currentLine[CoordinatePositions.z]);
                        normals.add(normal);
                        break;
                    // Parses the faces
                    case FACE_PREFIX:
                        for (int i = 1; i < 4; i++) {
                            fVertexStr = currentLine[i].split("/");

                            facesLst.add(processVertex(fVertexStr));
                        }

                        break;
                }
            }
            return createShape(vertices, normals, textures, facesLst);

        } catch (Exception e) {
            Log.e(TAG, "loadObj failed", e);
            return null;
        }
    }


    /**
     * Parses one waveFront file (Using cache mechanism if possible)
     *
     * @param resourceId id of the resource where the waveFront file exists
     * @return Wavefront object
     */
    @SuppressWarnings("SameParameterValue")
    public static IShape loadObjModel(Context context, int resourceId) {
        ResourcesCache cache = ResourcesCache.getInstance();
        IShape shape = (IShape) cache.get(resourceId);
        if (shape == null) {
            IShape newShape = pLoadObjModel(context, resourceId);
            cache.put(resourceId, newShape);
            return newShape;
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
     */
    private static PolygonalFace processVertex(String[] vertexData) {
        Integer vertexIndex = parseIntComponent(vertexData[0]);
        Long textureIndex = parseLongComponent(vertexData[1]);
        Long normalIndex = parseLongComponent(vertexData[2]);

        return new PolygonalFace(vertexIndex, textureIndex, normalIndex);
    }
}
