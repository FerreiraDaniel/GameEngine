package gameEngine.models;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;

/**
 * Represents one terrain in the 3D world
 */
public class TerrainShape implements IShape {
	/* Size of block that composes the terrain */
	public static final float SIZE = 500.0f;

	/* Number of blocks in each side of the terrain */
	public static final int VERTEX_COUNT = 128;

	/* Minimum height that the terrain has */
	private static final float MIN_HEIGHT = -40.0f;

	/* Maximum height that the terrain has */
	private static final float MAX_HEIGHT = 40.0f;

	/**
	 * Vertices of the terrain
	 */
	private float[] vertices;

	/**
	 * Heights of the vertices that make the terrain
	 */
	private float[][] heights;

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
	 * Get the height of the terrain in the specified coordinate
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * 
	 * @return the height of the terrain in the specified position
	 */
	private float getCurrentHeight(int x, int y) {
		if ((x < 0) || (x >= VERTEX_COUNT) || (y < 0) || (y >= VERTEX_COUNT)) {
			return 0.0f;
		} else {
			return heights[x][y];
		}
	}

	/**
	 * Generates a terrain
	 * 
	 * @param heightMap
	 *            Texture with different heights in the terrain
	 */
	private void generateTerrain(TextureData heightMap) {
		int count = VERTEX_COUNT * VERTEX_COUNT;
		this.vertices = new float[count * 3];
		this.normals = new float[count * 3];
		this.textureCoords = new float[count * 2];
		this.indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		this.heights = new float[VERTEX_COUNT][VERTEX_COUNT];

		int vertexPointer = 0;
		// Generates the positions of the terrain
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				this.heights[j][i] = getHeight(j, i, heightMap);
				vertices[vertexPointer * 3 + 1] = this.heights[j][i];
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}

		vertexPointer = 0;
		// Generates the normals of the terrain
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				Vector3f normal = calculateNormal(j, i);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
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
	 * Computes the normal of a vertice using for that the neighbor points
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * 
	 * @return The normal of the point
	 */
	private Vector3f calculateNormal(int x, int y) {
		float heightL = getCurrentHeight(x - 1, y);
		float heightR = getCurrentHeight(x + 1, y);
		float heightD = getCurrentHeight(x, y - 1);
		float heightU = getCurrentHeight(x, y + 1);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

	/*
	 * Get the height that was specified in the height map image
	 * 
	 * @param x x-coordinate
	 * 
	 * @param y y-coordinate
	 * 
	 * @param heightMap Texture with different heights in the terrain
	 * 
	 * @return the height of the terrain in the specified position
	 */
	private float getHeight(int x, int y, TextureData heightMap) {

		int rgb = heightMap.getRGB(x, y);
		float heightNormal = (rgb / TextureData.MAX_PIXEL_COLOR);
		float finalHeight = (heightNormal * (MAX_HEIGHT - MIN_HEIGHT)) + MIN_HEIGHT;

		return finalHeight;
	}

	/**
	 * Constructor of the terrain shape
	 *
	 * @param heightMap
	 *            Texture with different heights in the terrain
	 */
	public TerrainShape(TextureData heightMap) {
		generateTerrain(heightMap);
	}

	/**
	 * The vertices of the terrain
	 */
	@Override
	public float[] getVertices() {
		return this.vertices;
	}

	/**
	 * Get the texture coordinates of the terrain
	 */
	@Override
	public float[] getTextureCoords() {
		return this.textureCoords;
	}

	/**
	 * Get the normal of the terrain entity
	 */
	@Override
	public float[] getNormals() {
		return this.normals;
	}

	/**
	 * Get the indices of the terrain entity
	 */
	@Override
	public int[] getIndices() {
		return this.indices;
	}

	/**
	 * 
	 * @return The heights of the vertices of the terrain
	 */
	public float[][] getHeights() {
		return this.heights;
	}

	/**
	 * @return The group that the terrain shape belongs (if any)
	 */
	@Override
	public String getGroupName() {
		return null;
	}

	/**
	 * @return The reference to the material of the terrain shape
	 */
	@Override
	public IExternalMaterial getMaterial() {
		return null;
	}
}
