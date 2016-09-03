package com.dferreira.game_engine.models;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Represents one raw model of one entity
 */
public class RawModel {

    /**
     * Number of vertices of the raw model
     */
    private final int vertexCount;


    /**
     * Buffer that supports the vertices of the object
     */
    private final FloatBuffer vertexBuffer;


    /**
     * Buffer that supports the indexes of the object
     */
    private final IntBuffer indexBuffer;

    /**
     * Buffer that supports the coordinates of the textures of the object
     */
    private final FloatBuffer texCoordinates;

    /**
     * Buffer that supports the normals of the object
     */
    private final FloatBuffer normalBuffer;

    /**
     * Number of indices of the row model
     */
    private final int numOfIndexes;

    /**
     * Constructor of a raw model
     *
     * @param vertexBuffer   Buffer that describes the position of the vertices
     * @param indexBuffer    Buffer that describes which vertices will be used
     * @param normalBuffer   Buffer that describes which normal will be user
     * @param texCoordinates Coordinates of the textures in the model
     */
    public RawModel(FloatBuffer vertexBuffer, IntBuffer indexBuffer, int numOfIndexes, FloatBuffer normalBuffer, FloatBuffer texCoordinates) {
        super();
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = 0;
        this.indexBuffer = indexBuffer;
        this.texCoordinates = texCoordinates;
        this.normalBuffer = normalBuffer;
        this.numOfIndexes = numOfIndexes;
    }

    /**
     * Constructor of a raw model
     *
     * @param vertexBuffer Buffer that describes the position of the vertices
     * @param vertexCount  Number of vertices that compose the raw model
     */
    public RawModel(FloatBuffer vertexBuffer, int vertexCount) {
        super();
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = vertexCount;
        this.indexBuffer = null;
        this.normalBuffer = null;
        this.texCoordinates = null;
        this.numOfIndexes = 0;
    }

    /**
     * @return The buffer that describes the positions of the vertices
     */
    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    /**
     * @return The buffer that describes the vertices that are going to be used to render the
     * model
     */
    public IntBuffer getIndexBuffer() {
        return indexBuffer;
    }

    /**
     * @return
     */
    public int getNumOfIndexes() {
        return numOfIndexes;
    }

    /**
     * @return The Coordinates of the textures
     */
    public FloatBuffer getTexCoordinates() {
        return texCoordinates;
    }

    /**
     * @return The normals of the entity
     */
    public FloatBuffer getNormalBuffer() {
        return normalBuffer;
    }

    /**
     * Clean the memory used by the model
     */
    public void cleanUp() {
        if (vertexBuffer != null) {
            vertexBuffer.clear();
        }
        if (indexBuffer != null) {
            indexBuffer.clear();
        }
        if (texCoordinates != null) {
            texCoordinates.clear();
        }


    }

    /**
     * @return the number of vertex
     */
    public int getVertexCount() {
        return vertexCount;
    }
}
