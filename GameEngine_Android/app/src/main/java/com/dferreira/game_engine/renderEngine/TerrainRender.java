package com.dferreira.game_engine.renderEngine;

import android.opengl.GLES20;
import android.util.SparseIntArray;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.shaders.terrains.TTerrainAttribute;
import com.dferreira.game_engine.shaders.terrains.TerrainShaderManager;
import com.dferreira.game_engine.textures.TerrainTexturesPack;

import java.util.List;

/**
 * Class responsible to render the terrain of the scene
 */
@SuppressWarnings("WeakerAccess")
public class TerrainRender {
    /**
     * Reference to the shader manager
     */
    private final TerrainShaderManager tShader;


    /**
     * Constructor of the entity render
     *
     * @param sManager Shader manager
     */
    public TerrainRender(TerrainShaderManager sManager, GLTransformation projectionMatrix) {
        this.tShader = sManager;

        sManager.start();
        sManager.loadProjectionMatrix(projectionMatrix);
        sManager.connectTextureUnits();
        sManager.stop();
    }

    /**
     * Get the transformation matrix of one entity
     *
     * @param entity Entity for which is to create the transformation matrix
     * @return The transformation matrix that put the entity in its right
     * position
     */
    private GLTransformation getTransformationMatrix(Terrain entity) {
        GLTransformation matrix = new GLTransformation();
        matrix.glLoadIdentity();
        matrix.glTranslate(entity.getX(), entity.getY(), entity.getZ());
        float terrainRotation = 0.0f;
        matrix.glRotate(terrainRotation, 1.0f, 0.0f, 0.0f);
        matrix.glRotate(terrainRotation, 0.0f, 1.0f, 0.0f);
        matrix.glRotate(terrainRotation, 0.0f, 0.0f, 1.0f);

        return matrix;
    }


    /**
     * Render the terrains in the scene
     *
     * @param skyColor   Color of the sky
     * @param sun        The source of light of the scene
     * @param viewMatrix View matrix to render the scene
     * @param terrains   List of terrains of the scene
     */
    public void render(Vector3f skyColor, Light sun, GLTransformation viewMatrix, List<Terrain> terrains) {
        tShader.start();
        tShader.loadSkyColor(skyColor);
        tShader.loadLight(sun);
        tShader.loadViewMatrix(viewMatrix);

        this.render(terrains);
        tShader.stop();
    }

    /**
     * Render one list of terrains
     *
     * @param terrains List of Terrains to render
     */
    private void render(List<Terrain> terrains) {
        if ((terrains != null) && (!terrains.isEmpty())) {
            for (Terrain terrain : terrains) {
                prepareTerrain(terrain);
                prepareInstance(terrain);
                render(terrain);
                unbindTexturedModel();
            }
        }
    }

    /**
     * When loads one texture defines that by default should zoom in/out it
     */
    private void defineTextureFunctionFilters() {
        //The texture minify function is used whenever the pixel being textured maps to an area greater than one texture element
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

        //The texture magnification function is used when the pixel being textured maps to an area less than or equal to one texture element
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //Sets the wrap parameter for texture coordinate s
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);

        //Sets the wrap parameter for texture coordinate t
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
    }

    /**
     * Bind the several textures of the terrain
     */
    private void bindTextures(Terrain terrain) {
        TerrainTexturesPack texturesPackage = terrain.getTexturePack();
        SparseIntArray texturesMatching = new SparseIntArray(5);
        texturesMatching.put(GLES20.GL_TEXTURE0, texturesPackage.getBackgroundTextureId());
        texturesMatching.put(GLES20.GL_TEXTURE1, texturesPackage.getMudTextureId());
        texturesMatching.put(GLES20.GL_TEXTURE2, texturesPackage.getGrassTextureId());
        texturesMatching.put(GLES20.GL_TEXTURE3, texturesPackage.getPathTextureId());
        texturesMatching.put(GLES20.GL_TEXTURE4, texturesPackage.getWeightMapTextureId());

        for (int i = 0; i < texturesMatching.size(); i++) {
            int key = texturesMatching.keyAt(i);
            int textureId = texturesMatching.get(key);

            GLES20.glActiveTexture(key);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

            // Set filtering of the texture
            defineTextureFunctionFilters();
        }
    }

    /**
     * Bind the attributes of terrain with openGL
     *
     * @param terrain The terrain that have the properties to bind
     */
    private void prepareTerrain(Terrain terrain) {
        RawModel model = terrain.getModel();

        //Enable the attributes to bind
        GLES20.glEnableVertexAttribArray(TTerrainAttribute.position.getValue());
        GLES20.glEnableVertexAttribArray(TTerrainAttribute.textureCoords.getValue());
        GLES20.glEnableVertexAttribArray(TTerrainAttribute.normal.getValue());

        // bind several textures of the terrain
        bindTextures(terrain);


        //Load the light properties
        tShader.loadShineVariables(1.0f, 0.0f);

        // Load the vertex data
        GLES20.glVertexAttribPointer(TTerrainAttribute.position.getValue(), RenderConstants.VERTEX_SIZE, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, model.getVertexBuffer());

        // Load the texture coordinate
        GLES20.glVertexAttribPointer(TTerrainAttribute.textureCoords.getValue(), RenderConstants.NUMBER_COMPONENTS_PER_VERTEX_ATTR, GLES20.GL_FLOAT,
                RenderConstants.VERTEX_NORMALIZED,
                RenderConstants.STRIDE,
                model.getTexCoordinates());


        // Load the normals data
        GLES20.glVertexAttribPointer(TTerrainAttribute.normal.getValue(), RenderConstants.NUMBER_COMPONENTS_PER_NORMAL_ATTR, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE,
                model.getNormalBuffer());
    }

    /**
     * Render the terrain itself
     *
     * @param terrain the terrain to render
     */
    private void prepareInstance(Terrain terrain) {
        // Load the transformation matrix
        tShader.loadTransformationMatrix(getTransformationMatrix(terrain));
    }

    /**
     * Call the render of the triangles to the terrain itself
     *
     * @param terrain A reference to the terrain to get render
     */
    private void render(Terrain terrain) {
        RawModel model = terrain.getModel();

        // Specify the indexes
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getNumOfIndexes(),
                GLES20.GL_UNSIGNED_INT, model.getIndexBuffer());
    }

    /**
     * UnBind the previous bound elements
     */
    private void unbindTexturedModel() {
        GLES20.glDisableVertexAttribArray(TTerrainAttribute.position.getValue());
        GLES20.glDisableVertexAttribArray(TTerrainAttribute.textureCoords.getValue());
        GLES20.glDisableVertexAttribArray(TTerrainAttribute.normal.getValue());
    }


    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        tShader.cleanUp();
    }
}
