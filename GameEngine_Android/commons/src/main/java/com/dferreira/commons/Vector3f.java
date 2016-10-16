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
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param z
     *            z-coordinate
     */
    public Vector3f(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    /**
     * @return A string describing the vector3f
     */
    @Override
    public String toString() {
        return "Vector3f{" +
                "x=" + x +
                "y=" + y +
                "z=" + z +
                '}';
    }
}
