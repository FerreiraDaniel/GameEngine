package com.dferreira.commons.waveFront;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dferreira.commons.TextUtils;
import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.shapes.WfObject;

/**
 * Parses files formated in wavefront format
 */
public class OBJLoader {

	private final static Logger logger = LogManager.getLogger(OBJLoader.class);
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

	public final static int COORDINATES_BY_VERTEX = 3;
	public final static int COORDINATES_BY_TEXTURE = 2;
	public final static int COORDINATES_BY_NORMAL = 3;

	/**
	 * Parse two strings and return the equivalent vector2f
	 * 
	 * @param xStr
	 *            X component
	 * @param yStr
	 *            Y component
	 * 
	 * @return The parsed vector
	 */
	private static Vector2f parseVector2f(String xStr, String yStr) {
		float x = Float.parseFloat(xStr);
		float y = Float.parseFloat(yStr);

		return new Vector2f(x, y);
	}

	/**
	 * Parse two strings and return the equivalent vector23
	 * 
	 * @param xStr
	 *            X component
	 * @param yStr
	 *            Y component
	 * @param zStr
	 *            Z component
	 * 
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

			int vertexIndex = face.getVertexIndex().intValue();
			int normalIndex = face.getNormalIndex().intValue();
			int textureIndex = face.getTextureIndex().intValue();
			
			Vector3f vertice = vertices.get(vertexIndex);
			Vector3f currentNorm = normals.get(normalIndex);
			Vector2f currentTexture = textures.get(textureIndex);

			// Build index lists
			indicesArray[j] = vertexIndex;

			// Uses the (faces and vertices list to build the final vertices
			// array
			verticesArray[vertexIndex * COORDINATES_BY_VERTEX] = vertice.x;
			verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 1] = vertice.y;
			verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 2] = vertice.z;

			
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
	 * @param fileName
	 *            Name of the file without extension
	 * 
	 * @return The shape with information about the waveFront file read
	 */
	public static IShape loadObjModel(String fileName) {
		FileReader fr = null;

		try {
			File file = new File("res/" + fileName + ".obj");
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("Could not load file!");
			e.printStackTrace();
			logger.error(TAG, e);
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		String[] fVertexStr;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<PolygonalFace> facesLst = new ArrayList<PolygonalFace>();
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
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Parse a component in string format and return its long value
	 *
	 * @param strComponent
	 *            Component to parse
	 * @return Long value or null if it is the case
	 */
	private static Long ParseLongComponent(String strComponent) {
		if (TextUtils.isEmpty(strComponent)) {
			return null;
		} else {
			return Long.parseLong(strComponent) - 1;
		}
	}

	/**
	 * Process one vertex
	 */
	private static PolygonalFace processVertex(String[] vertexData) {
		Long vertexIndex = ParseLongComponent(vertexData[0]);
		Long textureIndex = ParseLongComponent(vertexData[1]);
		Long normalIndex = ParseLongComponent(vertexData[2]);

		return new PolygonalFace(vertexIndex, textureIndex, normalIndex);
	}
}
