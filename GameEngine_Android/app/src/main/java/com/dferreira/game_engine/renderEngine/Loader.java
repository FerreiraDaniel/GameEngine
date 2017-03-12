package com.dferreira.game_engine.renderEngine;

import android.content.Context;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.utils.LoadUtils;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.complexEntities.Material;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Load the data to render the scene
 */
public class Loader {

    @SuppressWarnings("FieldCanBeLocal")
    private final int INT_IN_BYTES = 4;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FLOAT_IN_BYTES = 4;

    /**
     * Load to a new vertex array object
     *
     * @param positions          Positions to load
     * @param textureCoordinates Texture coordinates to load
     * @param normals            Normals of the model to load
     * @param indices            Indices to be load
     * @return A row model with information loaded
     */
    public RawModel loadToRawModel(float[] positions, float[] textureCoordinates, float[] normals, int[] indices) {

        FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
        IntBuffer indexBuffer = storeDataInIntBuffer(indices);
        FloatBuffer normalBuffer = storeDataInFloatBuffer(normals);
        FloatBuffer texCoordinatesBuffer = storeDataInFloatBuffer(textureCoordinates);

        return new RawModel(vertexBuffer, indexBuffer, indices.length, normalBuffer, texCoordinatesBuffer);
    }

    /**
     * @param externalMaterial A reference to an external material with all the information
     *                         needed to create a material
     * @return The material loaded
     */
    public Material loadMaterial(IExternalMaterial externalMaterial) {
        if (externalMaterial == null) {
            return null;
        } else {
            float textureWeight;
            ColorRGBA diffuseColor;

            if ((externalMaterial.getDiffuseTextureFileName() == null)
                    || ("".equals(externalMaterial.getDiffuseTextureFileName().trim()))) {
                textureWeight = 0.0f;
                diffuseColor = (externalMaterial.getDiffuseColor() == null) ? new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f) : new ColorRGBA(externalMaterial.getDiffuseColor());
            } else {
                textureWeight = 1.0f;
                diffuseColor = new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f);
            }
            Material material = new Material();

            material.setTextureWeight(textureWeight);
            material.setDiffuseColor(diffuseColor);

            return material;
        }
    }

    /**
     * Load to a new vertex array object
     *
     * @param positions  Positions to load
     * @param dimensions Dimensions of the positions to load
     */
    private RawModel loadPositionsToRawModel(float[] positions, int dimensions) {
        FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
        return new RawModel(vertexBuffer, positions.length / dimensions);
    }

    /**
     * Load a list of 2D positions to RawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    public RawModel load2DPositionsToRawModel(float[] positions) {
        int dimensions = 2;
        return loadPositionsToRawModel(positions, dimensions);
    }

    /**
     * Load a list of 3D positions to RawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    public RawModel load3DPositionsToRawModel(float[] positions) {
        int dimensions = 3;
        return loadPositionsToRawModel(positions, dimensions);
    }

    /**
     * Loads the data of a texture without bind
     *
     * @param context    Context where this method will be called
     * @param resourceId id of the resource where the texture exists
     * @return The texture read from the file without any openGL bind
     */
    @SuppressWarnings({"SameParameterValue", "UnnecessaryLocalVariable"})
    public TextureData getTextureData(Context context, int resourceId) {
        TextureData textureData = LoadUtils.decodeTextureFile(context, resourceId);
        return textureData;
    }

    /**
     * Convert on array of Integers in a buffer of Integers that can be used in
     * openGL
     *
     * @param data array with data to put in the Integer Buffer the integer
     *             buffer created
     * @return The integer buffer created
     */
    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer sBuffer = ByteBuffer.allocateDirect(data.length * INT_IN_BYTES)
                .order(ByteOrder.nativeOrder()).asIntBuffer();
        sBuffer.put(data).position(0);
        return sBuffer;
    }


    /**
     * Convert on array of Floats in a buffer of Floats that can be used in
     * openGL
     *
     * @param data array with data to put in the Float Buffer the float buffer
     *             created
     * @return The integer buffer created
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data) {

        // Allocate a direct block of memory on the native heap,
        // size in bytes is equal to data.length * BYTES_PER_FLOAT.
        // BYTES_PER_FLOAT is equal to 4, since a float is 32-bits, or 4 bytes.
        FloatBuffer fBuffer = ByteBuffer.allocateDirect(data.length * FLOAT_IN_BYTES)
                // Floats can be in big-endian or little-endian order.
                // We want the same as the native platform.
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        fBuffer
                // Copy data from the Java heap to the native heap.
                .put(data)
                // Reset the buffer position to the beginning of the buffer.
                .position(0);

        return fBuffer;
    }
}
