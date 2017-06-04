package com.dferreira.gameEngine.renderEngine;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.shaders.guis.GuiShaderManager;
import com.dferreira.gameEngine.shaders.guis.TGuiAttribute;

import java.util.List;

/**
 * Class responsible to render the GUIs in the screen
 */
@SuppressWarnings("WeakerAccess")
public class GuiRender extends GenericRender {

    /**
     * Reference to the shader manager
     */
    private final GuiShaderManager gShader;

    /**
     * Constructor of the gui render
     *
     * @param gManager       Shader manager
     * @param frameRenderAPI Reference to the API responsible for render the frame
     */
    public GuiRender(GuiShaderManager gManager, IFrameRenderAPI frameRenderAPI) {
        super(frameRenderAPI);
        this.gShader = gManager;
    }

    /**
     * Get the transformation matrix of one entity
     *
     * @param gui GUI for which is to create the transformation matrix
     * @return The transformation matrix that put the entity in its right
     * position
     */
    private GLTransformation getTransformationMatrix(GuiTexture gui) {
        GLTransformation matrix = new GLTransformation();
        matrix.loadIdentity();
        matrix.translate(gui.getPosition().x, gui.getPosition().y, 0.0f);

        matrix.scale(gui.getScale().x, gui.getScale().y, 1.0f);
        return matrix;
    }

    /**
     * Render the GUIs in the scene
     *
     * @param GUIs List of GUIs to render
     */
    public void render(List<GuiTexture> GUIs) {
        gShader.start();

        this.lRender(GUIs);
        gShader.stop();
    }


    /**
     * Render the GUIs in the scene
     *
     * @param GUIs List of GUIs to render
     */
    private void lRender(List<GuiTexture> GUIs) {
        if (!Utils.isEmpty(GUIs)) {

            this.frameRenderAPI.enableBlend();
            this.frameRenderAPI.disableDepthTest();

            for (GuiTexture gui : GUIs) {
                prepareModel(gui.getRawModel());
                prepareInstance(gui);
                render(gui.getRawModel());

            }
            unPrepareModel();

            this.frameRenderAPI.disableBlend();
            this.frameRenderAPI.enableDepthTest();
        }
    }


    /**
     * Bind the attributes of openGL
     *
     * @param rawModel Model that contains the model of the entity with textures
     */
    private void prepareModel(IRawModel rawModel) {
        this.frameRenderAPI.prepare2DModel(rawModel, TGuiAttribute.position);
    }

    /**
     * Load the transformation matrix of the GUI
     *
     * @param gui Entity that is to get prepared to be loaded
     */
    private void prepareInstance(GuiTexture gui) {
        this.frameRenderAPI.activeAndBindTexture(gui.getTextureId());
        // Load the transformation matrix
        gShader.loadTransformationMatrix(getTransformationMatrix(gui));
    }

    /**
     * Call the render of the triangles to the entity itself
     *
     * @param quad The quad to render
     */
    private void render(IRawModel quad) {
        this.frameRenderAPI.drawQuadVertex(quad);
    }

    /**
     * UnBind the previous bound elements
     */
    private void unPrepareModel() {
        this.frameRenderAPI.unPrepareModel(TGuiAttribute.position);
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        gShader.cleanUp();
    }

}
