package com.dferreira.game_engine.renderEngine;

import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.game_engine.models.GuiTexture;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.shaders.guis.GuiShaderManager;
import com.dferreira.game_engine.shaders.guis.TGuiAttribute;
import com.dferreira.game_engine.shaders.skyBox.TSkyBoxAttribute;

import java.util.List;

/**
 * Class responsible to render the GUIs in the screen
 */
@SuppressWarnings("WeakerAccess")
public class GuiRender {

    /**
     * Reference to the shader manager
     */
    private final GuiShaderManager gShader;

    /**
     * Constructor of the entity render
     *
     * @param gManager Shader manager
     */
    public GuiRender(GuiShaderManager gManager) {
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
        if ((GUIs != null) && (!GUIs.isEmpty())) {
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            for (GuiTexture gui : GUIs) {
                prepareModel(gui.getRawModel());
                prepareInstance(gui);
                render(gui.getRawModel());

                unbindTexturedModel();
            }
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            GLES20.glDisable(GLES20.GL_BLEND);
        }
    }


    /**
     * Bind the attributes of openGL
     *
     * @param rawModel Model that contains the model of the entity with textures
     */
    private void prepareModel(RawModel rawModel) {
        //Enable the attributes to bind
        GLES20.glEnableVertexAttribArray(TGuiAttribute.position.ordinal());

        //Load from buffers
        // Load the vertex data
        GLES20.glVertexAttribPointer(TSkyBoxAttribute.position.getValue(), RenderConstants.VERTEX_SIZE_2D, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, rawModel.getVertexBuffer());

    }

    /**
     * Load the transformation matrix of the GUI
     *
     * @param gui Entity that is to get prepared to be loaded
     */
    private void prepareInstance(GuiTexture gui) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, gui.getTextureId());
        // Load the transformation matrix
        gShader.loadTransformationMatrix(getTransformationMatrix(gui));
    }

    /**
     * Call the render of the triangles to the entity itself
     *
     * @param quad The quad to render
     */
    private void render(RawModel quad) {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
    }

    /**
     * UnBind the previous bound elements
     */
    private void unbindTexturedModel() {
        GLES20.glDisableVertexAttribArray(TGuiAttribute.position.ordinal());
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        gShader.cleanUp();
    }

}
