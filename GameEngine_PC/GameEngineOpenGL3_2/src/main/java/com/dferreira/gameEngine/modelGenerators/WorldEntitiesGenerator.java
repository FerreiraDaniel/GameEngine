package com.dferreira.gameEngine.modelGenerators;

import java.util.HashMap;
import java.util.Random;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.models.complexEntities.GenericEntity;
import com.dferreira.gameEngine.models.complexEntities.MaterialGroup;
import com.dferreira.gameEngine.renderEngine.Loader;

/**
 * Responsible for creating the multiple entities of the 3D world
 */
public class WorldEntitiesGenerator extends GenericEntitiesGenerator {
	private final static int NUMBER_OF_TREES = 100;
	private final static int NUMBER_OF_BANANA_TREES = 30;
	private final static int NUMBER_OF_FERNS = 100;
	private final static int NUMBER_OF_GRASS = 20;
	private final static int NUMBER_OF_FLOWERS = 100;
	private final static int NUMBER_OF_MARBLES = 10;

	/**
	 * Get one entity in a certain position
	 * 
	 * @param genericEntity
	 *            Generic entity
	 * @param position
	 *            Position where is to put the entity in the 3D world
	 * 
	 * @return the entity to render
	 */
	private static Entity getEntity(GenericEntity genericEntity, Vector3f position) {
		Entity entity = new Entity(genericEntity, position, // Position
				0.0f, 0.0f, 0.0f, // Rotation
				0.0f // Scale
		);
		return entity;
	}

	/**
	 * Get the default values of the entities that are going make the world
	 *
	 * @param rProvider
	 *            Provider of the resources to load
	 * @return The map of model VS quantities of entities
	 */
	private static HashMap<DefaultModelGenerator, Integer> getEntitiesMap(IResourceProvider rProvider) {
		HashMap<DefaultModelGenerator, Integer> entitiesMap = new HashMap<DefaultModelGenerator, Integer>();

		/* Fern model */
		DefaultModelGenerator fernModel = new DefaultModelGenerator();
		fernModel.setObjectType(ModelEnum.fern);
		fernModel.setObjectReference(rProvider.getResource(ModelEnum.fern));
		fernModel.setScale(1.0f);
		fernModel.setHasTransparency(true);
		fernModel.setNormalsPointingUp(true);

		/* Tree model */
		DefaultModelGenerator treeModel = new DefaultModelGenerator();
		treeModel.setObjectType(ModelEnum.tree);
		treeModel.setObjectReference(rProvider.getResource(ModelEnum.tree));
		treeModel.setScale(10.0f);
		treeModel.setHasTransparency(false);
		treeModel.setNormalsPointingUp(false);

		/* Banana tree */
		DefaultModelGenerator bananaTreeModel = new DefaultModelGenerator();
		bananaTreeModel.setObjectType(ModelEnum.banana_tree);
		bananaTreeModel.setObjectReference(rProvider.getResource(ModelEnum.banana_tree));
		bananaTreeModel.setScale(1.0f);
		bananaTreeModel.setHasTransparency(true);
		bananaTreeModel.setNormalsPointingUp(false);

		/* grass model */
		DefaultModelGenerator grassModel = new DefaultModelGenerator();
		grassModel.setObjectType(ModelEnum.grass);
		grassModel.setObjectReference(rProvider.getResource(ModelEnum.grass));
		grassModel.setScale(1.0f);
		grassModel.setHasTransparency(true);
		grassModel.setNormalsPointingUp(true);

		/* flower model */
		DefaultModelGenerator flowerModel = new DefaultModelGenerator();
		flowerModel.setObjectType(ModelEnum.flower);
		flowerModel.setObjectReference(rProvider.getResource(ModelEnum.flower));
		flowerModel.setScale(1.0f);
		flowerModel.setHasTransparency(true);
		flowerModel.setNormalsPointingUp(false);

		/* Marble model */
		DefaultModelGenerator marbleModel = new DefaultModelGenerator();
		marbleModel.setObjectType(ModelEnum.marble);
		marbleModel.setObjectReference(rProvider.getResource(ModelEnum.marble));
		marbleModel.setScale(5.0f);
		marbleModel.setHasTransparency(false);
		marbleModel.setNormalsPointingUp(false);

		/* Entity map of all the existing entities */
		entitiesMap.put(bananaTreeModel, NUMBER_OF_BANANA_TREES);
		entitiesMap.put(fernModel, NUMBER_OF_FERNS);
		entitiesMap.put(treeModel, NUMBER_OF_TREES);
		entitiesMap.put(grassModel, NUMBER_OF_GRASS);
		entitiesMap.put(flowerModel, NUMBER_OF_FLOWERS);
		entitiesMap.put(marbleModel, NUMBER_OF_MARBLES);

		return entitiesMap;
	}

	/**
	 * @param loader
	 *            loader that will load the entities of the 3D world
	 * @param loaderAPI
	 *            loader that will load the entities of the 3D world
	 * @param resourceProvider
	 *            Provider of the resources used in the application
	 * @param terrain
	 *            The terrain used to determine the height position
	 *
	 * @return The entities that will compose the 3D world
	 */
	public static Entity[] getEntities(Loader loader, ILoaderRenderAPI loaderAPI, IResourceProvider resourceProvider,
			Terrain terrain) {

		HashMap<DefaultModelGenerator, Integer> entitiesMap = getEntitiesMap(resourceProvider);

		int totalModels = 0;
		for (DefaultModelGenerator key : entitiesMap.keySet()) {
			totalModels += entitiesMap.get(key);
		}
		Entity[] entities = new Entity[totalModels];

		Random random = new Random();
		int count = 0;
		for (DefaultModelGenerator key : entitiesMap.keySet()) {
			HashMap<String, MaterialGroup> groupsOfMaterials = getTexturedObj(loader, loaderAPI,
					key.getObjectReference(), key.getHasTransparency(), key.getNormalsPointingUp());
			GenericEntity genericEntity = new GenericEntity(groupsOfMaterials, key.getObjectType());
			// Prepare generic entity end
			Integer numberOfObjs = entitiesMap.get(key);
			for (int i = 0; i < numberOfObjs; i++) {
				float xPosition = 20.0f + random.nextFloat() * 400.0f;
				float zPosition = random.nextFloat() * 400.0f;
				float yPosition = terrain.getHeightOfTerrain(xPosition, zPosition);
				Vector3f entityPosition = new Vector3f(xPosition, yPosition, zPosition);
				Entity entity = getEntity(genericEntity, entityPosition);
				float scale = random.nextFloat() * key.getScale();
				entity.setScale(scale);
				entities[count] = entity;
				++count;
			}
		}
		return entities;
	}

	/**
	 * Load the textures of a list of entities
	 *
	 * @param loaderRenderAPI
	 *            Loader to load content specific to the render API
	 * @param entities
	 *            The entities for which is to load the textures
	 */
	public static void loadTextures(ILoaderRenderAPI loaderRenderAPI, Entity[] entities) {
		if (!Utils.isEmpty(entities)) {
			for (Entity entity : entities) {
				loadTexturesOfEntity(loaderRenderAPI, entity);
			}
		}
	}
}
