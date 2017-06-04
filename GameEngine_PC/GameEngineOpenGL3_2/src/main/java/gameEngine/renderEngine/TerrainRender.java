package gameEngine.renderEngine;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.models.Light;

import gameEngine.models.RawModel;
import gameEngine.models.Terrain;
import gameEngine.shaders.terrains.TTerrainAttribute;
import gameEngine.shaders.terrains.TerrainShaderManager;
import gameEngine.textures.TerrainTexturesPack;

/**
 * Class responsible to render the entities in the screen
 */
public class TerrainRender extends GenericRender {

	/**
	 * Reference to the shader manager
	 */
	private TerrainShaderManager tShader;

    /**
     * Constructor of the terrain render
     *
     * @param sManager         Shader manager
     * @param projectionMatrix The projection matrix of the render
     * @param frameRenderAPI   Reference to the API responsible for render the frame
     */
	public TerrainRender(TerrainShaderManager sManager, GLTransformation projectionMatrix, IFrameRenderAPI frameRenderAPI) {
        super(frameRenderAPI);
		this.tShader = sManager;

		sManager.start();
		sManager.loadProjectionMatrix(projectionMatrix);
		sManager.connectTextureUnits();
		sManager.stop();
	}

	/**
	 * Get the transformation matrix of one terrain
	 * 
	 * @param terrain
	 *            Entity for which is to create the transformation matrix
	 * 
	 * @return The transformation matrix that put the terrain in its right
	 *         position
	 */
	private GLTransformation getTransformationMatrix(Terrain terrain) {
		GLTransformation matrix = new GLTransformation();
		matrix.loadIdentity();
		matrix.translate(terrain.getX(), terrain.getY(), terrain.getZ());
		float terrainRotation = 0.0f;
		matrix.rotate(terrainRotation, 1.0f, 0.0f, 0.0f);
		matrix.rotate(terrainRotation, 0.0f, 1.0f, 0.0f);
		matrix.rotate(terrainRotation, 0.0f, 0.0f, 1.0f);

		return matrix;
	}

	/**
	 * Render the terrains in the scene
	 * 
	 * @param skyColor
	 *            Color of the sky
	 * @param lights
	 *            The lights of the scene
	 * @param viewMatrix
	 *            View matrix to render the scene
	 * @param terrains
	 *            List of terrains of the scene
	 */
	public void render(ColorRGBA skyColor, Light[] lights, GLTransformation viewMatrix, List<Terrain> terrains) {
		tShader.start();
		tShader.loadSkyColor(skyColor);
		tShader.loadLights(lights);
		tShader.loadViewMatrix(viewMatrix);

		this.render(terrains);
		tShader.stop();
	}

	/**
	 * Render one list of terrains
	 * 
	 * @param terrains
	 *            List of Terrains to render
	 */
	private void render(List<Terrain> terrains) {
		if ((terrains != null) && (!terrains.isEmpty())) {
			for (int i = 0; i < terrains.size(); i++) {
				Terrain terrain = terrains.get(i);
				prepareTerrain(terrain);
				prepareInstance(terrain);
				render(terrain);
				unbindTexturedModel();
			}
		}
	}

	/**
	 * Bind the attributes of openGL
	 * 
	 * @param terrain
	 *            Terrain to get prepared
	 */
	private void prepareTerrain(Terrain terrain) {
		RawModel model = terrain.getModel();

		GL30.glBindVertexArray(model.getVaoId());
		GL20.glEnableVertexAttribArray(TTerrainAttribute.position.getValue());
		GL20.glEnableVertexAttribArray(TTerrainAttribute.textureCoords.getValue());
		GL20.glEnableVertexAttribArray(TTerrainAttribute.normal.getValue());

		// bind several textures of the terrain
		bindTextures(terrain);

		// Bind the light properties
		tShader.loadShineVariables(1.0f, 0.0f);
	}

	/**
	 * When loads one texture defines that by default should zoom in/out it
	 */
	private void defineTextureFunctionFilters() {
		// The texture minification function is used whenever the pixel being
		// textured maps to an area greater than one texture element
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		// The texture magnification function is used when the pixel being
		// textured maps to an area less than or equal to one texture element
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		// Sets the wrap parameter for texture coordinate s
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);

		// Sets the wrap parameter for texture coordinate t
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	}

	/**
	 * Bind the several textures of the terrain
	 * 
	 * @param terrain
	 *            Terrain that is going to get the textures bound
	 */
	private void bindTextures(Terrain terrain) {
		TerrainTexturesPack texturesPackage = terrain.getTexturePack();
		HashMap<Integer, Integer> texturesMatching = new HashMap<>();
		texturesMatching.put(GL13.GL_TEXTURE0, texturesPackage.getBackgroundTextureId());
		texturesMatching.put(GL13.GL_TEXTURE1, texturesPackage.getMudTextureId());
		texturesMatching.put(GL13.GL_TEXTURE2, texturesPackage.getGrassTextureId());
		texturesMatching.put(GL13.GL_TEXTURE3, texturesPackage.getPathTextureId());
		texturesMatching.put(GL13.GL_TEXTURE4, texturesPackage.getWeightMapTextureId());

		for (int key : texturesMatching.keySet()) {
			GL13.glActiveTexture(key);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturesMatching.get(key));

			// Set filtering of the texture
			defineTextureFunctionFilters();
		}
	}

	/**
	 * Render the terrain itself
	 * 
	 * @param terrain
	 *            the terrain to render
	 */
	private void prepareInstance(Terrain terrain) {
		// Load the transformation matrix
		tShader.loadTransformationMatrix(getTransformationMatrix(terrain));
	}

	/**
	 * Call the render of the triangles to the terrain itself
	 * 
	 * @param terrain
	 *            A reference to the terrain to get render
	 */
	private void render(Terrain terrain) {
		RawModel model = terrain.getModel();
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(TTerrainAttribute.position.getValue());
		GL20.glDisableVertexAttribArray(TTerrainAttribute.textureCoords.getValue());
		GL20.glDisableVertexAttribArray(TTerrainAttribute.normal.getValue());
		GL30.glBindVertexArray(0);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		tShader.dispose();
	}

}
