package com.dferreira.game_engine.renderEngine;


import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.shaders.entities.EntityShaderManager;
import com.dferreira.game_engine.shaders.skyBox.SkyBoxShaderManager;

/**
 * Class responsible to render the entities in the screen
 */
public class SkyBoxRender {

    /**
     * Reference to the shader manager
     */
    private SkyBoxShaderManager sbShader;

    /**
     * Constructor of the skyBox render
     *
     * @param sbManager        Shader manager
     * @param projectionMatrix The projection matrix
     */
    public SkyBoxRender(SkyBoxShaderManager sbManager, GLTransformation projectionMatrix) {
        this.sbShader = sbManager;

        sbManager.start();
        sbManager.loadProjectionMatrix(projectionMatrix);
        sbManager.stop();
    }

    /**
     * Render the sky box of the scene
     *
     * @param viewMatrix View matrix to render the scene
     * @param skyBox     The sky box to render
     */
    public void render(GLTransformation viewMatrix, SkyBox skyBox) {
        sbShader.start();
        sbShader.loadViewMatrix(viewMatrix);
        prepareSkyBox(skyBox);
        render(skyBox);
        unbindTexture();
        sbShader.stop();
    }

    /**
     * Bind the attributes of openGL
     *
     * @param skyBox The sky box to bind the attributes
     */
    private void prepareSkyBox(SkyBox skyBox) {
        RawModel model = skyBox.getModel();

        //Enable the attributes to bind
        GLES20.glEnableVertexAttribArray(SkyBoxShaderManager.LOCATION_ATTR_ID);

        // bind several textures of the sky box
        bindTextures(skyBox);

        //Load from buffers
        // Load the vertex data
        GLES20.glVertexAttribPointer(EntityShaderManager.LOCATION_ATTR_ID, RenderConstants.VERTEX_SIZE, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, model.getVertexBuffer());


    }

    /**
     * Bind the cube texture of the skyBox
     */
    private void bindTextures(SkyBox skyBox) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, skyBox.getTextureId());
    }


    /**
     * Call the render of the triangles to the skyBox itself
     *
     * @param skyBox
     */
    private void render(SkyBox skyBox) {
        RawModel model = skyBox.getModel();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, model.getVertexCount());
    }

    /**
     * UnBind the previous binded elements
     */
    private void unbindTexture() {
        GLES20.glDisableVertexAttribArray(SkyBoxShaderManager.LOCATION_ATTR_ID);
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        sbShader.cleanUp();
    }
}
