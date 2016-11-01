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

	/**
	 * Transforms a 3F vector into a string
	 */
	@Override
	public String toString() {
		return String.format("(%f,%f,%f)", x, y, z);
	}

	/**
	 * Normalizes the components of the vector
	 */
	public void normalise() {
		double magnitude = Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
		this.x = (float) (x / magnitude);
		this.y = (float) (y / magnitude);
		this.z = (float) (z / magnitude);
	}
}
