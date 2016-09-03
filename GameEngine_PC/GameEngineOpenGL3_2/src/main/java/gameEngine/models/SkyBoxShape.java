package gameEngine.models;

import com.dferreira.commons.shapes.IShape;

/**
 * Represents the sky box in the 3D world
 */
public class SkyBoxShape implements IShape {
	private static final float SIZE = 500f;

	/**
	 * Vertices of the sky box
	 */
	private static final float[] vertices = { -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
			-SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,

			-SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE,
			-SIZE, SIZE,

			SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE,
			-SIZE,

			-SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE,
			SIZE,

			-SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
			-SIZE,

			-SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE,
			-SIZE, SIZE };

	/**
	 * Constructor of the sky box shape
	 */
	public SkyBoxShape() {
	}

	/**
	 * The vertices of the sky box
	 */
	@Override
	public float[] getVertices() {
		return vertices;
	}

	@Override
	public float[] getTextureCoords() {
		return null;
	}

	@Override
	public float[] getNormals() {
		return null;
	}

	@Override
	public int[] getIndices() {
		return null;
	}

}
