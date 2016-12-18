package gameEngine.models;

import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;

/**
 * Represents the 2D GUI
 */
public class GuiShape implements IShape {
	private static final float SIZE = 1f;

	/**
	 * Vertices of the GUI quad
	 */
	private static final float[] vertices = { -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, SIZE, -SIZE };

	/**
	 * Constructor of the GUI shape
	 */
	public GuiShape() {
	}

	/**
	 * 
	 * @return The vertices of the quad
	 */
	@Override
	public float[] getVertices() {
		return vertices;
	}

	/**
	 * @return the Coordinates of the textures of the shape
	 */
	@Override
	public float[] getTextureCoords() {
		return null;
	}

	/**
	 * @return the normal vectors that make the shape
	 */
	@Override
	public float[] getNormals() {
		return null;
	}

	/**
	 * @return The indices of the vertices that make the shape
	 */
	@Override
	public int[] getIndices() {
		return null;
	}

    /**
     * @return The group that the gui shape belongs (if any)
     */
	@Override
	public String getGroupName() {
		return null;
	}

	@Override
	public IExternalMaterial getMaterial() {
		return null;
	}

}
