package com.dferreira.commons;

/**
 * Represents a 3D Vector with their x,y,z coordinates
 */
public class Vector3f extends Vector2f {

	/* The coordinates them selves */
	public float z;

	/**
	 * The constructor of the vector
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @param z
	 *            coordinate
	 */
	public Vector3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}
}
