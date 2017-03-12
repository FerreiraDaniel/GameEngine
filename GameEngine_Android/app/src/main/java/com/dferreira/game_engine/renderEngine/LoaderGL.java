package com.dferreira.game_engine.renderEngine;

import android.content.Context;
import android.opengl.GLES20;
import android.text.TextUtils;

import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.utils.LoadUtils;

import java.util.HashMap;

/**
 * Loader for parts that are specific to openGL
 */
public class LoaderGL {

    /**
     * Identifiers of the textures bound to OpenGL
     */
    private final HashMap<String,Integer> textureIdentifiers;

    public LoaderGL() {
        this.textureIdentifiers = new HashMap<>();
    }

    /**
     * When loads one texture defines that by default should zoom in/out it
     *
     * @param target the target of the filter
     */
    private void defineTextureFunctionFilters(int target) {
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(target, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
    }


    /**
     * Load texture from resource
     *
     * @param context    Context where this method will be called
     * @param resourceId id of the resource where the texture exists
     * @return Id from the texture that was bounded in openGL
     */
    @SuppressWarnings("SameParameterValue")
    public int loadTexture(Context context, int resourceId) {
        TextureData textureData = LoadUtils.decodeTextureFile(context, resourceId);

        int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, textureData.getBuffer());


        defineTextureFunctionFilters(GLES20.GL_TEXTURE_2D);

        return textureId[0];
    }

    /**
     * Load texture from resource located in the mipmap folder
     *
     * @param context         Context where this method will be called
     * @param textureFileName The name of the texture where the texture exists
     * @return Id from the texture that was bounded in openGL
     */
    public int loadTexture(Context context, String textureFileName) {

        if (TextUtils.isEmpty(textureFileName)) {
            return 0;
        } else {
            String resourceName = textureFileName.split("\\.png")[0];
            int resourceId = context.getResources().getIdentifier(resourceName, "mipmap", context.getPackageName());
            if (resourceId == 0) {
                return 0;
            } else {
                return loadTexture(context, resourceId);
            }
        }
    }

    /**
     * Loads a cubic texture
     *
     * @param context     Context where this method will be called
     * @param resourceIds The resources where should get the images of the cube
     * @return Identifier of the texture cubic texture loaded
     */
    @SuppressWarnings("SameParameterValue")
    public Integer loadTCubeMap(Context context, int[] resourceIds) {
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
        defineTextureFunctionFilters(GLES20.GL_TEXTURE_CUBE_MAP);
        return textureId[0];
    }
}
