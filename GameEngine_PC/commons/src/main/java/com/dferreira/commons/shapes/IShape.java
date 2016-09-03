package com.dferreira.commons.shapes;

/**
 * Interface that should be implemented for the different shapes available
 */
public interface IShape {
	
	/**
	 * @return the vertices of the shape
	 */
	public float[] getVertices();
	
	/**
	 * @return the Coordinates of the textures of the shape
	 */
	public float[] getTextureCoords();

	/**
	 * 
	 * @return the normal vectors that make the shape
	 */
	public float[] getNormals();
	
	/**
	 * @return The indices of the vertices that make the shape
	 */
	public int[] getIndices();


}
