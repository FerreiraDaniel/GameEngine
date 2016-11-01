package com.dferreira.commons;

/**
 * Custom mathematics methods
 */
public class Maths {

	/**
	 * In geometry, the barycentric coordinate system is a coordinate system in
	 * which the location of a point of a triangle is specified as the center of
	 * mass, or barycenter, of usually unequal masses placed at its vertices
	 * 
	 * @param v1
	 *            First of the vertices of the triangle
	 * @param v2
	 *            Second of the vertices of the triangle
	 * @param v3
	 *            Third of the vertices of the triangle
	 * @param pos
	 *            Position the is to determine
	 * 
	 * @return The location determined
	 */
	public static float barryCentric(Vector3f v1, Vector3f v2, Vector3f v3, Vector2f pos) {
		double det = (v2.z - v3.z) * (v1.x - v3.x) + (v3.x - v2.x) * (v1.z - v3.z);
		// Compute dot products
		double l1 = (((v2.z - v3.z) * (pos.x - v3.x) + (v3.x - v2.x) * (pos.y - v3.z)) / det);
		double l2 = (((v3.z - v1.z) * (pos.x - v3.x) + (v1.x - v3.x) * (pos.y - v3.z)) / det);
		double l3 = (1.0f - l1 - l2);

		float yPos = (float) (l1 * v1.y + l2 * v2.y + l3 * v3.y);

		return yPos;
	}

}
