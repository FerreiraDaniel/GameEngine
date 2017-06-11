package com.dferreira.gameEngine.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.shaders.guis.GuiShaderManager;
import com.dferreira.gameEngine.shaders.guis.TGuiAttribute;

/**
 * Class responsible to render the GUIs in the screen
 */
public class GuiRender extends GenericRender {

	/**
	 * Reference to the shader manager
	 */
	private GuiShaderManager gShader;

	/**
	 * Constructor of the gui render
	 *
	 * @param gManager
	 *            Shader manager
	 * @param frameRenderAPI
	 *            Reference to the API responsible for render the frame
	 */
	public GuiRender(GuiShaderManager gManager, IFrameRenderAPI frameRenderAPI) {
		super(frameRenderAPI);

		this.gShader = gManager;
	}

	/**
	 * Get the transformation matrix of one entity
	 * 
	 * @param gui
	 *            GUI for which is to create the transformation matrix
	 * 
	 * @return The transformation matrix that put the entity in its right
	 *         position
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
	 * 
	 * @param guis
	 *            List of GUIs to render
	 */
	public void render(List<GuiTexture> guis) {
		gShader.start();

		this.lrender(guis);
		gShader.stop();
	}

	/**
	 * Render the GUIs in the scene
	 * 
	 * @param guis
	 *            List of GUIs to render
	 */
	private void lrender(List<GuiTexture> guis) {
		if ((guis == null) || (guis.isEmpty())) {
			return;
		} else {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			for (GuiTexture gui : guis) {
				prepareModel(gui.getRawModel());
				prepareInstance(gui);
				render(gui.getRawModel());

				unbindTexturedModel();
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
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
	 * @param gui
	 *            Entity that is to get prepared to be loaded
	 */
	private void prepareInstance(GuiTexture gui) {
		this.frameRenderAPI.activeAndBindTexture(gui.getTexture());
		// Load the transformation matrix
		gShader.loadTransformationMatrix(getTransformationMatrix(gui));
	}

    /**
     * Call the render of the triangles to the entity itself
     *
     * @param quad The quad to render
     */
    private void render(IRawModel quad) {
		GLRawModel quad2 = (GLRawModel)quad;
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad2.getVertexCount());
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(TGuiAttribute.position.ordinal());
		// Unbind vbo
		GL30.glBindVertexArray(0);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	@Override
	public void dispose() {
		gShader.dispose();
	}

}
