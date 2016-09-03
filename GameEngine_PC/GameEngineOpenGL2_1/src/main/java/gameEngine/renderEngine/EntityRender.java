package gameEngine.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.models.Light;

import gameEngine.models.Entity;
import gameEngine.models.RawModel;
import gameEngine.models.TexturedModel;
import gameEngine.shaders.entities.EntityShaderManager;

/**
 * Class responsible to render the entities in the screen
 */
public class EntityRender {

	/**
	 * Reference to the shader manager
	 */
	private final EntityShaderManager eShader;

	/**
	 * Constructor of the entity render
	 * 
	 * @param sManager
	 *            Shader manager
	 * @param projectionMatrix
	 */
	public EntityRender(EntityShaderManager sManager, GLTransformation projectionMatrix) {
		this.eShader = sManager;

		sManager.start();
		sManager.loadProjectionMatrix(projectionMatrix);
		sManager.stop();
	}

	/**
	 * Get the transformation matrix of one entity
	 * 
	 * @param entity
	 *            Entity for which is to create the transformation matrix
	 * 
	 * @return The transformation matrix that put the entity in its right
	 *         position
	 */
	private GLTransformation getTransformationMatrix(Entity entity) {
		GLTransformation matrix = new GLTransformation();
		matrix.glLoadIdentity();
		matrix.glTranslate(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z);
		matrix.glRotate(entity.getRotX(), 1.0f, 0.0f, 0.0f);
		matrix.glRotate(entity.getRotY(), 0.0f, 1.0f, 0.0f);
		matrix.glRotate(entity.getRotZ(), 0.0f, 0.0f, 1.0f);

		matrix.glScale(entity.getScale(), entity.getScale(), entity.getScale());
		
		return matrix;
	}

	/**
	 * Render the entities in the scene
	 * 
	 * @param sun
	 *            The source of light of the scene
	 * @param viewMatrix
	 *            View matrix to render the scene
	 * @param entities
	 *            List of entities of the scene
	 */
	public void render(Light sun, GLTransformation viewMatrix, Map<TexturedModel, List<Entity>> entities) {
		eShader.start();
		eShader.loadLight(sun);
		eShader.loadViewMatrix(viewMatrix);

		this.render(entities);
		eShader.stop();
	}

	/**
	 * Render one hashMap of entities where each key is a group of similar
	 * entities to be render
	 * 
	 * @param entities
	 *            HashMap of entities to render
	 */
	private void render(Map<TexturedModel, List<Entity>> entities) {
		if ((entities == null) || (entities.size() == 0)) {
			return;
		} else {
			for (TexturedModel model : entities.keySet()) {
				List<Entity> batch = entities.get(model);
				prepareTexturedModel(model);

				for (Entity entity : batch) {
					prepareInstance(entity);
					render(entity);
				}
				//Restore the state if has transparency
				if(!model.hasTransparency()) {
					disableCulling();
				}
				
				unbindTexturedModel();
			}
		}
	}
	
	/**
	 * Enable culling of faces to get better performance
	 */
	private void enableCulling() {
		//Enable the GL cull face feature
		GL11.glEnable(GL11.GL_CULL_FACE);
		//Avoid to render faces that are away from the camera
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	/**
	 * Disable the culling of the faces vital for transparent model
	 */
	private void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	/**
	 * Bind the attributes of openGL
	 * 
	 * @param texturedModel
	 */
	private void prepareTexturedModel(TexturedModel texturedModel) {
		RawModel model = texturedModel.getRawModel();
		
		if(!texturedModel.hasTransparency()) {
			enableCulling();
		}

		// Enable the attributes to bind
		GL20.glEnableVertexAttribArray(EntityShaderManager.LOCATION_ATTR_ID);
		GL20.glEnableVertexAttribArray(EntityShaderManager.TEX_COORDINATE_ATTR_ID);
		GL20.glEnableVertexAttribArray(EntityShaderManager.NORMAL_VECTOR_ATTR_ID);

		// Enable the specific texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureId());

		// Load if should put the normals of the entity point up or not
		eShader.loadNormalsPointingUp(texturedModel.isNormalsPointingUp());
		
		// Load the light properties
		eShader.loadShineVariables(texturedModel.getShineDamper(), texturedModel.getReflectivity());

		// Load from buffers
		// Load the vertex data
		GL20.glVertexAttribPointer(EntityShaderManager.LOCATION_ATTR_ID, RenderConstants.VERTEX_SIZE,
				RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, model.getVertexBuffer());
		// Load the texture coordinate
		GL20.glVertexAttribPointer(EntityShaderManager.TEX_COORDINATE_ATTR_ID,
				RenderConstants.NUMBER_COMPONENTS_PER_VERTEX_ATTR, RenderConstants.VERTEX_NORMALIZED,
				RenderConstants.STRIDE, model.getTexCoordinates());
		// Load the normals data
		GL20.glVertexAttribPointer(EntityShaderManager.NORMAL_VECTOR_ATTR_ID,
				RenderConstants.NUMBER_COMPONENTS_PER_NORMAL_ATTR, RenderConstants.VERTEX_NORMALIZED,
				RenderConstants.STRIDE, model.getNormalBuffer());
	}

	/**
	 * Render the entity itself
	 * 
	 * @param entity
	 */
	private void prepareInstance(Entity entity) {
		// Load the transformation matrix
		eShader.loadTransformationMatrix(getTransformationMatrix(entity));
	}

	/**
	 * Call the render of the triangles to the entity itself
	 * 
	 * @param entity
	 */
	private void render(Entity entity) {
		TexturedModel texturedModel = entity.getModel();
		RawModel model = texturedModel.getRawModel();

		// Specify the indexes
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getIndexBuffer());
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(EntityShaderManager.LOCATION_ATTR_ID);
		GL20.glDisableVertexAttribArray(EntityShaderManager.TEX_COORDINATE_ATTR_ID);
		GL20.glDisableVertexAttribArray(EntityShaderManager.NORMAL_VECTOR_ATTR_ID);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		eShader.cleanUp();
	}

}
