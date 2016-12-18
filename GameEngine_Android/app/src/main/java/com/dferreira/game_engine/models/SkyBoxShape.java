package com.dferreira.game_engine.models;


import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;

/**
 * Represents the sky box in the 3D world
 */
public class SkyBoxShape implements IShape {
    private static final float SIZE = 500f;

    /**
     * Vertices of the sky box
     */
    private static final float[] vertices = {-SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
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
            -SIZE, SIZE};

    /**
     * Constructor of the sky box shape
     */
    public SkyBoxShape() {
    }

    /**
     * @return The vertices of the sky box
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
     * @return The group that the sky box shape belongs (if any)
     */
    @Override
    public String getGroupName() {
        return null;
    }

    /**
     * @return The reference to the material of the sky box shape
     */
    @Override
    public IExternalMaterial getMaterial() {
        return null;
    }
}
