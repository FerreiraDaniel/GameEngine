package gameEngine.models;

import com.dferreira.commons.shapes.IShape;

/**
 * Represents one terrain in the 3D world
 */
public class TerrainShape implements IShape {
	/**/
	public static final float SIZE = 800;
	
	/* Number of vertices in each side of the terrain */
	public static final int VERTEX_COUNT = 128;
	
	/**
	 * Vertices of the terrain
	 */
	private float[] vertices;

	/**
	 * Normals of the terrain
	 */
	private float[] normals;

	/**
	 * Coordinates of the textures
	 */
	private float[] textureCoords;

	/**
	 * The indices of the terrain
	 */
	private int[] indices;

	
	
	
	/**
	 * Generates a completely flat terrain
	 * 
	 */
	private void generateTerrain() {
		int count = VERTEX_COUNT * VERTEX_COUNT;
		this.vertices = new float[count * 3];
		this.normals = new float[count * 3];
		this.textureCoords = new float[count * 2];
		this.indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = 0;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer * 3] = 0;
				normals[vertexPointer * 3 + 1] = 1;
				normals[vertexPointer * 3 + 2] = 0;
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
	}

	/**
	 * Constructor of the terrain shape
	 */
	public TerrainShape(){
		generateTerrain();
	}
	

	/**
	 * The vertices of the terrain
	 */
	public float[] getVertices() {
		return this.vertices;
	}

	/**
	 * Get the texture coordinates of the terrain
	 */
	public float[] getTextureCoords() {
		return this.textureCoords;
	}

	/**
	 * Get the normal of the terrain entity
	 */
	public float[] getNormals() {
		return this.normals;
	}

	/**
	 * Get the indices of the terrain entity
	 */
	public int[] getIndices() {
		return this.indices;
	}


	
}
