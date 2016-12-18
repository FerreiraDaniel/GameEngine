package com.dferreira.commons.shapes;

/**
 * Represents one entity with a shape defined by one waveFront file
 */
public class WfObject implements IShape {

    private final float[] vertices;
    private final float[] textureCoords;
    private final float[] normals;
    private final int[] indices;
    private final String groupName;
    private final IExternalMaterial material;

    /**
     * Constructor of the waveFront shape define
     *
     * @param vertices      Vertices of the shape
     * @param textureCoords Coordinate of the textures of the shape
     * @param normals       The normal vectors of the shape
     * @param indices       Indices of the shape
     * @param groupName     Name of the group wish belongs
     * @param material      Material of the Wavefront object if any
     */
    public WfObject(float[] vertices, float[] textureCoords, float[] normals, int[] indices, String groupName,
                    IExternalMaterial material) {
        super();
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.groupName = groupName;
        this.material = material;
    }

    /**
     * @return the vertices of the file
     */
    public float[] getVertices() {
        return vertices;
    }

    /**
     * @return the coordinates of the textures of the file
     */
    public float[] getTextureCoords() {
        return textureCoords;
    }

    /**
     * @return the indices the make the file render possible
     */
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

    /**
     * @return the groupName Name of the group wish belongs
     */
    @Override
    public String getGroupName() {
        return groupName;
    }

    /**
     * @return The material associated with shape
     */
    @Override
    public IExternalMaterial getMaterial() {
        return material;
    }

}