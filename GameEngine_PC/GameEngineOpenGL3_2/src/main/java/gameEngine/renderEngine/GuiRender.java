package gameEngine.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.GLTransformation;

import gameEngine.models.GuiTexture;
import gameEngine.models.RawModel;
import gameEngine.shaders.guis.GuiShaderManager;
import gameEngine.shaders.guis.TGuiAttribute;

/**
 * Class responsible to render the GUIs in the screen
 */
public class GuiRender {

	/**
	 * Reference to the shader manager
	 */
	private GuiShaderManager gShader;
	
	/**
	 * Constructor of the entity render
	 * 
	 * @param gManager
	 *            Shader manager
	 */
	public GuiRender(GuiShaderManager gManager) {
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
		matrix.glLoadIdentity();
		matrix.glTranslate(gui.getPosition().x, gui.getPosition().y, 0.0f);

		matrix.glScale(gui.getScale().x, gui.getScale().y, 1.0f);
		return matrix;
	}

	/**
	 * Render the GUIs in the scene
	 * 
	 * 
	 * @param guis	List of GUIs to render
	 */
	public void render(List<GuiTexture> guis) {
		gShader.start();

		this.lrender(guis);
		gShader.stop();
	}
	

	/**
	 * Render the GUIs in the scene
	 * 
	 * @param guis	List of GUIs to render
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
	 * @param texturedModel
	 *            Model that contains the model of the entity with textures
	 */
	private void prepareModel(RawModel quad) {
		GL30.glBindVertexArray(quad.getVaoId());
		GL20.glEnableVertexAttribArray(TGuiAttribute.position.ordinal());
	}

	/**
	 * Load the transformation matrix of the GUI
	 * 
	 * @param gui
	 *            Entity that is to get prepared to be loaded
	 */
	private void prepareInstance(GuiTexture gui) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureId());
		// Load the transformation matrix
		gShader.loadTransformationMatrix(getTransformationMatrix(gui));
	}

    /**
     * Call the render of the triangles to the entity itself
     *
     * @param quad
     *            The quad to render
     */
	private void render(RawModel quad) {
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(TGuiAttribute.position.ordinal());
		//Unbind vbo
		GL30.glBindVertexArray(0);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		gShader.cleanUp();
	}

}
