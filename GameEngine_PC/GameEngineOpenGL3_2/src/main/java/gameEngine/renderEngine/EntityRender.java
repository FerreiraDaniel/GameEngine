package gameEngine.renderEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.models.Light;

import gameEngine.models.Player;
import gameEngine.models.RawModel;
import gameEngine.models.complexEntities.Entity;
import gameEngine.models.complexEntities.GenericEntity;
import gameEngine.models.complexEntities.Material;
import gameEngine.models.complexEntities.MaterialGroup;
import gameEngine.models.complexEntities.RawModelMaterial;
import gameEngine.shaders.entities.EntityShaderManager;
import gameEngine.shaders.entities.TEntityAttribute;

/**
 * Class responsible to render the entities in the screen
 */
public class EntityRender {

	/**
	 * Reference to the shader manager
	 */
	private EntityShaderManager eShader;

	/**
	 * Constructor of the entity render
	 * 
	 * @param sManager
	 *            Shader manager
	 * @param projectionMatrix
	 *            The projection matrix to the entity render
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
	 * @param skyColor
	 *            Color of the sky
	 * 
	 * @param lights
	 *            The lights of the scene
	 * @param viewMatrix
	 *            View matrix to render the scene
	 * @param entities
	 *            List of entities of the scene
	 * @param player
	 *            The player of the scene
	 */
	public void render(ColorRGBA skyColor, Light[] lights, GLTransformation viewMatrix,
			Map<GenericEntity, List<Entity>> entities, Player player) {
		eShader.start();
		eShader.loadSkyColor(skyColor);
		eShader.loadLights(lights);
		eShader.loadViewMatrix(viewMatrix);

		this.render(entities);
		this.renderPlayer(player);
		eShader.stop();
	}

	/**
	 * Render one hashMap of entities where each key is a group of similar
	 * entities to be render
	 * 
	 * @param entities
	 *            HashMap of entities to render
	 */
	private void render(Map<GenericEntity, List<Entity>> entities) {
		if ((entities == null) || (entities.isEmpty())) {
			return;
		} else {
			for (GenericEntity genericEntity : entities.keySet()) {
				HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
				for (String groupName : groupsOfMaterials.keySet()) {
					MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
					for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
						RawModel model = rawModelMaterial.getRawModel();
						Material material = rawModelMaterial.getMaterial();
						prepareMaterial(material);
						prepareModel(model);
						List<Entity> batch = entities.get(genericEntity);
						for (Entity entity : batch) {
							loadEntityTransformation(entity);
							render(model);
						}
						unprepareModel();
						unPrepareMaterial(material);
					}
				}
			}
		}
	}

	/**
	 * Render one player of the scene
	 * 
	 * @param player
	 *            the player that is to render in the scene
	 */
	private void renderPlayer(Player player) {
		GenericEntity genericEntity = player.getGenericEntity();
		HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
		for (String groupName : groupsOfMaterials.keySet()) {
			MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
			for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
				RawModel model = rawModelMaterial.getRawModel();
				Material material = rawModelMaterial.getMaterial();
				prepareMaterial(material);
				prepareModel(model);
				loadEntityTransformation(player);
				render(model);
				unprepareModel();
				unPrepareMaterial(material);
			}
		}
	}

	/**
	 * Enable culling of faces to get better performance
	 */
	private void enableCulling() {
		// Enable the GL cull face feature
		GL11.glEnable(GL11.GL_CULL_FACE);
		// Avoid to render faces that are away from the camera
		GL11.glCullFace(GL11.GL_BACK);
	}

	/**
	 * Disable the culling of the faces vital for transparent model
	 */
	private void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	/**
	 * Bind the attributes of the material with openGL
	 * 
	 * @param material
	 *            Contains a reference to the material to bind
	 */
	private void prepareMaterial(Material material) {

		// Enable the culling to not force the render of polygons that are not
		// going to be visible
		if (!material.hasTransparency()) {
			enableCulling();
		}

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, material.getTextureId());

		// Load if should put the normals of the entity point up or not
		eShader.loadNormalsPointingUp(material.areNormalsPointingUp());

		// Load the the light properties
		eShader.loadShineVariables(material.getShineDamper(), material.getReflectivity());

		// Load the texture weight of the material
		eShader.loadTextureWeight(material.getTextureWeight());
		
		// Load the diffuse color of the material
		eShader.loadDiffuseColor(material.getDiffuseColor());
	}

	/**
	 * Bind the model to render the entity with openGL
	 * 
	 * @param model
	 *            Model that contains the model of the entity with textures
	 */
	private void prepareModel(RawModel model) {
		GL30.glBindVertexArray(model.getVaoId());
		// Enable the attributes to bind
		GL20.glEnableVertexAttribArray(TEntityAttribute.position.ordinal());
		GL20.glEnableVertexAttribArray(TEntityAttribute.textureCoords.ordinal());
		GL20.glEnableVertexAttribArray(TEntityAttribute.normal.ordinal());
	}

	/**
	 * Load the transformation matrix of the entity
	 * 
	 * @param entity
	 *            Entity that is to get prepared to be loaded
	 */
	private void loadEntityTransformation(Entity entity) {
		// Load the transformation matrix
		eShader.loadTransformationMatrix(getTransformationMatrix(entity));
	}

	/**
	 * Call the render of the triangles to the model
	 * 
	 * @param model
	 *            Raw model to get render
	 */
	private void render(RawModel model) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	/**
	 * UnBind the previous binded elements
	 */
	private void unprepareModel() {
		GL20.glDisableVertexAttribArray(TEntityAttribute.position.ordinal());
		GL20.glDisableVertexAttribArray(TEntityAttribute.textureCoords.ordinal());
		GL20.glDisableVertexAttribArray(TEntityAttribute.normal.ordinal());
		GL30.glBindVertexArray(0);
	}

	/**
	 * UnBind the attributes of the material with openGL
	 * 
	 * @param material
	 *            Contains a reference to the material to bind
	 */
	private void unPrepareMaterial(Material material) {
		// Restore the state if has transparency
		if (!material.hasTransparency()) {
			disableCulling();
		}
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		eShader.cleanUp();
	}

}
