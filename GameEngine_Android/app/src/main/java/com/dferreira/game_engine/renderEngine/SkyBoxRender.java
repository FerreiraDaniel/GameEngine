package com.dferreira.game_engine.renderEngine;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.shaders.skyBox.SkyBoxShaderManager;
import com.dferreira.game_engine.shaders.skyBox.TSkyBoxAttribute;

/**
 * Class responsible to render the sky box in the screen
 */
@SuppressWarnings("WeakerAccess")
public class SkyBoxRender extends GenericRender {

    /**
     * Reference to the shader manager
     */
    private final SkyBoxShaderManager sbShader;

    /**
     * Constructor of the skyBox render
     *
     * @param sbManager        Shader manager
     * @param projectionMatrix The projection matrix
     * @param frameRenderAPI   Reference to the API responsible for render the frame
     */
    public SkyBoxRender(SkyBoxShaderManager sbManager, GLTransformation projectionMatrix, IFrameRenderAPI frameRenderAPI) {
        super(frameRenderAPI);
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
        IRawModel model = skyBox.getModel();

        // bind several textures of the sky box
        bindTextures(skyBox);
        this.frameRenderAPI.prepare3DModel(model, TSkyBoxAttribute.position);
    }

    /**
     * Bind the cube texture of the skyBox
     */
    private void bindTextures(SkyBox skyBox) {
        this.frameRenderAPI.activeAndBindCubeTexture(skyBox.getTextureId());
    }


    /**
     * Call the render of the triangles to the skyBox itself
     *
     * @param skyBox The sky box to be render
     */
    private void render(SkyBox skyBox) {
        IRawModel model = skyBox.getModel();
        this.frameRenderAPI.drawTrianglesVertex(model);
    }

    /**
     * UnBind the previous bound elements
     */
    private void unbindTexture() {
        this.frameRenderAPI.unPrepareModel(TSkyBoxAttribute.position);
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        sbShader.cleanUp();
    }
}
