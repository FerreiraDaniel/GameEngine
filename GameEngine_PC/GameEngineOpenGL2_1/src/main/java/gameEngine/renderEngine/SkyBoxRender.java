package gameEngine.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.dferreira.commons.GLTransformation;

import gameEngine.models.RawModel;
import gameEngine.models.SkyBox;
import gameEngine.shaders.skyBox.SkyBoxShaderManager;

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
	 * @param sManager
	 *            Shader manager
	 * 
	 * @param projectionMatrix
	 *            The projection matrix
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
	 * @param viewMatrix
	 *            View matrix to render the scene
	 * @param skyBox
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
	 * @param texturedModel
	 */
	private void prepareSkyBox(SkyBox skyBox) {
		RawModel model = skyBox.getModel();

		// Enable the attributes to bind
		GL20.glEnableVertexAttribArray(SkyBoxShaderManager.LOCATION_ATTR_ID);

		// bind several textures of the sky box
		bindTextures(skyBox);

		// Load from buffers
		// Load the vertex data
		GL20.glVertexAttribPointer(SkyBoxShaderManager.LOCATION_ATTR_ID, RenderConstants.VERTEX_SIZE,
				RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, model.getVertexBuffer());

	}

	/**
	 * Bind the cube texture of the skyBox
	 */
	private void bindTextures(SkyBox skyBox) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skyBox.getTextureId());
	}

	/**
	 * Call the render of the triangles to the skyBox itself
	 * 
	 * @param skyBox
	 */
	private void render(SkyBox skyBox) {
		RawModel model = skyBox.getModel();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unbindTexture() {
		GL20.glDisableVertexAttribArray(SkyBoxShaderManager.LOCATION_ATTR_ID);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		sbShader.cleanUp();
	}
}
