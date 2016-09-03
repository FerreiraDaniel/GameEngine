package com.dferreira.game_engine.shapes;

/**
 * Interface that should be implemented for the different shapes available
 */
public interface IShape {
	
	/**
	 * @return the vertices of the shape
	 */
	float[] getVertices();
	
	/**
	 * @return the Coordinates of the textures of the shape
	 */
	float[] getTextureCoords();

	/**
	 *
	 * @return the normal vectors that make the shape
	 */
	float[] getNormals();

	/**
	 * @return The indices of the vertices that make the shape
	 */
	int[] getIndices();
}
