package gameEngine.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;

import gameEngine.models.RawModel;
import gameEngine.models.SkyBox;
import gameEngine.shaders.skyBox.SkyBoxShaderManager;
import gameEngine.shaders.skyBox.TSkyBoxAttribute;

/**
 * Class responsible to render the sky in the screen
 */
public class SkyBoxRender extends GenericRender {

	/**
	 * Reference to the shader manager
	 */
	private SkyBoxShaderManager sbShader;

	/**
	 * Constructor of the skyBox render
	 *
	 * @param sbManager
	 *            Shader manager
	 * @param projectionMatrix
	 *            The projection matrix
	 * @param frameRenderAPI
	 *            Reference to the API responsible for render the frame
	 */
	public SkyBoxRender(SkyBoxShaderManager sbManager, GLTransformation projectionMatrix,
			IFrameRenderAPI frameRenderAPI) {
		super(frameRenderAPI);

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
	 *            The sky box like one entity
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
	 * @param skyBox
	 *            The sky box description that should be prepared
	 */
	private void prepareSkyBox(SkyBox skyBox) {
		RawModel model = skyBox.getModel();

		GL30.glBindVertexArray(model.getVaoId());
		GL20.glEnableVertexAttribArray(TSkyBoxAttribute.position.getValue());

		// bind several textures of the sky box
		bindTextures(skyBox);
	}

	/**
	 * Bind the cube texture of the skyBox
	 *
	 * @param skyBox
	 *            Reference to the sky box
	 */
	private void bindTextures(SkyBox skyBox) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, skyBox.getTextureId());
	}

	/**
	 * Call the render of the triangles to the skyBox itself
	 * 
	 * @param skyBox
	 *            The sky box to be render
	 */
	private void render(SkyBox skyBox) {
		RawModel model = skyBox.getModel();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
	}

	/**
	 * UnBind the previous bound elements
	 */
	private void unbindTexture() {
		GL20.glDisableVertexAttribArray(TSkyBoxAttribute.position.getValue());
		GL30.glBindVertexArray(0);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		sbShader.dispose();
	}

}
