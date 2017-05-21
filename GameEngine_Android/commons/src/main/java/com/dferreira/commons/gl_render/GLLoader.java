package com.dferreira.commons.gl_render;

import android.content.Context;
import android.opengl.GLES20;
import android.text.TextUtils;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.LoadUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

/**
 * Loader for parts that are specific to openGL
 */
public class GLLoader implements ILoaderRenderAPI {

    @SuppressWarnings("FieldCanBeLocal")
    private final int INT_IN_BYTES = 4;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FLOAT_IN_BYTES = 4;

    /**
     * Context that some methods need to load resources
     */
    private final Context context;

    /**
     * Identifiers of the textures bound to OpenGL
     */
    private final HashMap<String, Integer> textureIdentifiers;

    /**
     * Constructor of the loader GL
     *
     * @param context Context that some methods need to load resources
     */
    public GLLoader(Context context) {
        this.context = context;
        this.textureIdentifiers = new HashMap<>();
    }

    /**
     * When loads one texture defines that by default should zoom in/out it
     *
     * @param target    The target of the filter
     * @param wrapParam Parameter used in the wrap filters
     */
    private void defineTextureFunctionFilters(int target, int wrapParam) {

        //The texture minify function is used whenever the pixel being textured maps to an area greater than one texture element
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

        //The texture magnification function is used when the pixel being textured maps to an area less than or equal to one texture element
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //Sets the wrap parameter for texture coordinate s
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_WRAP_S, wrapParam);

        //Sets the wrap parameter for texture coordinate t
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_WRAP_T, wrapParam);
    }


    /**
     * @param repeat Boolean that indicates if is to repeat or not the texture
     * @return The value that should put in the filter of the texture
     */
    private int getWrapFilters(boolean repeat) {
        return repeat ? GLES20.GL_REPEAT : GLES20.GL_CLAMP_TO_EDGE;
    }

    /**
     * Load texture from resource
     *
     * @param resourceId id of the resource where the texture exists
     * @param repeat     Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Id from the texture that was bounded in openGL
     */
    @Override
    public Integer loadTexture(int resourceId, boolean repeat) {
        TextureData textureData = LoadUtils.decodeTextureFile(context, resourceId);

        int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, textureData.getBuffer());


        defineTextureFunctionFilters(GLES20.GL_TEXTURE_2D, getWrapFilters(repeat));

        return textureId[0];
    }

    /**
     * Load texture from resource located in the mipmap folder
     *
     * @param textureFileName The name of the texture where the texture exists
     * @param repeat          Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Id from the texture that was bounded in openGL
     */
    @Override
    public Integer loadTexture(String textureFileName, boolean repeat) {

        if (TextUtils.isEmpty(textureFileName)) {
            return 0;
        } else {
            String resourceName = textureFileName.split("\\.png")[0];
            int resourceId = context.getResources().getIdentifier(resourceName, "mipmap", context.getPackageName());
            if (resourceId == 0) {
                return 0;
            } else {
                int textureId = loadTexture(resourceId, repeat);
                textureIdentifiers.put(textureFileName, textureId);
                return textureId;
            }
        }
    }

    /**
     * Loads a cubic texture
     *
     * @param resourceIds The resources where should get the images of the cube
     * @param repeat      Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Identifier of the texture cubic texture loaded
     */
    @Override
    public Integer loadTCubeMap(int[] resourceIds, boolean repeat) {
        if (resourceIds == null) {
            return null;
        }
        int[] cubicTextureTargets = new int[]{GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
                GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
                GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
                GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z};

        int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId[0]);

        for (int i = 0; i < cubicTextureTargets.length; i++) {
            int resourceId = resourceIds[i];

            TextureData textureData = LoadUtils.decodeTextureFile(context, resourceId);
            if (textureData == null) {
                return null;
            } else {
                int target = cubicTextureTargets[i];
                GLES20.glTexImage2D(target, 0, GLES20.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
                        GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, textureData.getBuffer());
            }
        }
        defineTextureFunctionFilters(GLES20.GL_TEXTURE_CUBE_MAP, getWrapFilters(repeat));
        return textureId[0];
    }


    /**
     * @param fileName The identifier of texture associated with file
     * @return The identifier of the texture that much with specified file name
     */
    public int getTextureId(String fileName) {
        if (textureIdentifiers.containsKey(fileName)) {
            return this.textureIdentifiers.get(fileName);
        } else {
            return 0;
        }
    }

    /**
     * Load from a shape to one equivalent IRawModel
     *
     * @param shape The shape to load
     * @return A row model with information loaded
     */
    @Override
    public IRawModel loadToRawModel(IShape shape) {
        float[] positions = shape.getVertices();
        float[] textureCoordinates = shape.getTextureCoords();
        float[] normals = shape.getNormals();
        int[] indices = shape.getIndices();

        FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
        IntBuffer indexBuffer = storeDataInIntBuffer(indices);
        FloatBuffer normalBuffer = storeDataInFloatBuffer(normals);
        FloatBuffer texCoordinatesBuffer = storeDataInFloatBuffer(textureCoordinates);

        return new GLRawModel(vertexBuffer, indexBuffer, indices.length, normalBuffer, texCoordinatesBuffer);
    }

    /**
     * Load to a new vertex array object
     *
     * @param positions  Positions to load
     * @param dimensions Dimensions of the positions to load
     */
    private IRawModel loadPositionsToRawModel(float[] positions, int dimensions) {
        FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
        return new GLRawModel(vertexBuffer, positions.length / dimensions);
    }

    /**
     * Load a list of 2D positions to GLRawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    @Override
    public IRawModel load2DPositionsToRawModel(float[] positions) {
        int dimensions = 2;
        return loadPositionsToRawModel(positions, dimensions);
    }

    /**
     * Load a list of 3D positions to GLRawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    @Override
    public IRawModel load3DPositionsToRawModel(float[] positions) {
        int dimensions = 3;
        return loadPositionsToRawModel(positions, dimensions);
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
