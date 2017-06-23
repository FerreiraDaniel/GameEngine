package com.dferreira.commons.gl_render;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.RenderAttributeEnum;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * * Represents one raw model of one entity of the OpenGL ES V2
 */
public class GLRawModel implements IRawModel {

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
     * List of attributes associated with the model
     */
    private final HashMap<RenderAttributeEnum, IEnum> attributes;

    /**
     * Constructor of a raw model
     *
     * @param vertexBuffer   Buffer that describes the position of the vertices
     * @param indexBuffer    Buffer that describes which vertices will be used
     * @param normalBuffer   Buffer that describes which normal will be user
     * @param texCoordinates Coordinates of the textures in the model
     * @param attributes     List of attributes associated with the model
     */
    public GLRawModel(FloatBuffer vertexBuffer, IntBuffer indexBuffer, int numOfIndexes, FloatBuffer normalBuffer, FloatBuffer texCoordinates, HashMap<RenderAttributeEnum, IEnum> attributes) {
        super();
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = 0;
        this.indexBuffer = indexBuffer;
        this.texCoordinates = texCoordinates;
        this.normalBuffer = normalBuffer;
        this.numOfIndexes = numOfIndexes;
        this.attributes = attributes;
    }

    /**
     * Constructor of a raw model
     *
     * @param vertexBuffer Buffer that describes the position of the vertices
     * @param vertexCount  Number of vertices that compose the raw model
     */
    public GLRawModel(FloatBuffer vertexBuffer, int vertexCount, HashMap<RenderAttributeEnum, IEnum> attributes) {
        this.vertexBuffer = vertexBuffer;
        this.vertexCount = vertexCount;
        this.indexBuffer = null;
        this.normalBuffer = null;
        this.texCoordinates = null;
        this.numOfIndexes = 0;
        this.attributes = attributes;
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
     * @return the number of indexes
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
     * @return the number of vertex
     */
    public int getVertexCount() {
        return vertexCount;
    }


    /**
     * Takes the model and the render attribute and returns the corresponding IEnum
     *
     * @param renderAttribute The attribute to fetch
     * @return Enum of the render attribute
     */
    public IEnum getAttribute(RenderAttributeEnum renderAttribute) {
        return this.attributes.get(renderAttribute);
    }

    /**
     * Clean the memory used by the model
     */
    @Override
    public void dispose() {
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
}
