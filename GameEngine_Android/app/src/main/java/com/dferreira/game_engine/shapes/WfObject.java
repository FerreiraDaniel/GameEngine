package com.dferreira.game_engine.shapes;

/**
 * Represents one entity with a shape
 * defined by one waveFront file
 */
public class WfObject implements IShape {

    private final float[] vertices;
    private final float[] textureCoords;
    private final float[] normals;
    private final int[] indices;


    /**
     * Constructor of the waveFront shape define
     *
     * @param vertices      Vertices of the shape
     * @param textureCoords Coordinate of the textures of the shape
     * @param normals       The normal vectors of the shape
     * @param indices       Indices of the shape
     */
    public WfObject(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
        super();
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
    }

    /**
     * @return the vertices of the file
     */
    @Override
    public float[] getVertices() {
        return vertices;
    }

    /**
     * @return the coordinates of the textures of the file
     */
    @Override
    public float[] getTextureCoords() {
        return textureCoords;
    }

    /**
     * @return the indices the make the file render possible
     */
    @Override
    public int[] getIndices() {
        return indices;
    }

    /**
     * @return the normal vectors that make the shape
     */
    @Override
    public float[] getNormals() {
        return normals;
    }
}
