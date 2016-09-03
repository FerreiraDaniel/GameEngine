package gameEngine.modelGenerators;

import java.util.HashMap;
import java.util.Random;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.waveFront.OBJLoader;

import gameEngine.models.Entity;
import gameEngine.models.RawModel;
import gameEngine.models.TexturedModel;
import gameEngine.renderEngine.Loader;
import gameEngine.textures.ModelTexture;

/**
 * Responsible for creating the multiple entities of the 3D world
 */
public class WorldEntitiesGenerator {
	private final static int NUMBER_OF_TREES = 20;
	private final static int NUMBER_OF_BANANA_TREES = 30;
	private final static int NUMBER_OF_FERNS = 20;
	private final static int NUMBER_OF_GRASS = 20;
	private final static int NUMBER_OF_FLOWERS = 100;
	//private final static int NUMBER_OF_CARS = 1;
	
	/**
	 * 
	 * @return A source of light to the scene
	 */
	public static Light getLight() {
		Vector3f lightPosition = new Vector3f(10.0f,100.0f,10.0f);
		Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

		return new Light(lightPosition, lightColor);
	}
	
	/**
	 * Load a textured model
	 *
	 * @param loader
	 *            the loader of the texture
	 *
	 * @return the textured model of the dragon
	 */
	private static TexturedModel getTexturedObj(Loader loader, String objName, String texureName, boolean hasTransparency, boolean normalsPointingUp) {
		IShape shape = OBJLoader.loadObjModel(objName);
		
		RawModel model = loader.loadToVAO(shape.getVertices(), shape.getTextureCoords(), shape.getNormals(),
				shape.getIndices());
		Integer textureId = loader.loadTexture(texureName);
		ModelTexture texture = new ModelTexture(textureId);
		texture.setShineDamper(10.0f);
		texture.setReflectivity(1.0f);
		TexturedModel texturedModel = new TexturedModel(model, texture, hasTransparency, normalsPointingUp);

		return texturedModel;
	}
	
	/**
	 * Get one entity in a certain position
	 * 
	 * @param texturedEntity
	 *            Model of the entity to render
	 * @param position
	 *            Position where is to put the entity in the 3D world
	 * 
	 * @return the entity to render
	 */
	private static Entity getEntity(TexturedModel texturedEntity, Vector3f position) {
		Entity entity = new Entity(texturedEntity, position, 0.0f, 0.0f, 0.0f, // Rotation
				0.0f // Scale
		);
		return entity;
	}
	
	/**
	 * Get the default values of the entities that are going make the world
	 */
	private static HashMap<DefaultModelGenerator, Integer> getEntitiesMap() {
		HashMap<DefaultModelGenerator, Integer> entitiesMap = new HashMap<DefaultModelGenerator, Integer>();

		/* Fern model */
		DefaultModelGenerator fernModel = new DefaultModelGenerator();
		fernModel.setObjectName("fern");
		fernModel.setTextureName("fern");
		fernModel.setScale(1.0f);
		fernModel.setHasTransparency(true);
		fernModel.setNormalsPointingUp(true);

		/* Tree model */
		DefaultModelGenerator treeModel = new DefaultModelGenerator();
		treeModel.setObjectName("tree");
		treeModel.setTextureName("tree");
		treeModel.setScale(10.0f);
		treeModel.setHasTransparency(false);
		treeModel.setNormalsPointingUp(false);
		
		/*Banana tree*/
		DefaultModelGenerator bananaTreeModel = new DefaultModelGenerator();
		bananaTreeModel.setObjectName("banana_tree");
		bananaTreeModel.setTextureName("banana_tree");
		bananaTreeModel.setScale(1.0f);
		bananaTreeModel.setHasTransparency(true);
		bananaTreeModel.setNormalsPointingUp(false);

		/* grass model */
		DefaultModelGenerator grassModel = new DefaultModelGenerator();
		grassModel.setObjectName("grass");
		grassModel.setTextureName("grass");
		grassModel.setScale(1.0f);
		grassModel.setHasTransparency(true);
		grassModel.setNormalsPointingUp(true);

		/* flower model */
		DefaultModelGenerator flowerModel = new DefaultModelGenerator();
		flowerModel.setObjectName("flower");
		flowerModel.setTextureName("flower");
		flowerModel.setScale(1.0f);
		flowerModel.setHasTransparency(true);
		flowerModel.setNormalsPointingUp(true);
		
		
		/*Car model*/
		DefaultModelGenerator carModel = new DefaultModelGenerator();
		carModel.setObjectName("ford_fiesta");
		carModel.setTextureName("grass");
		carModel.setScale(10.0f);
		carModel.setHasTransparency(true);
		carModel.setNormalsPointingUp(true);

		
		/* Entity map of all the existing entities */
		entitiesMap.put(bananaTreeModel, NUMBER_OF_BANANA_TREES);
		entitiesMap.put(fernModel, NUMBER_OF_FERNS);
		entitiesMap.put(treeModel, NUMBER_OF_TREES);
		entitiesMap.put(grassModel, NUMBER_OF_GRASS);
		entitiesMap.put(flowerModel, NUMBER_OF_FLOWERS);

		return entitiesMap;
	}
	
	/**
	 * 
	 * @param loader
	 *            loader that will load the entities of the 3D world
	 * 
	 * @return The entities that will compose the 3D world
	 */
	public static Entity[] getEntities(Loader loader) {
		HashMap<DefaultModelGenerator, Integer> entitiesMap = getEntitiesMap();
		
		int totalModels = 0;
		for(DefaultModelGenerator key : entitiesMap.keySet()) {
			totalModels += entitiesMap.get(key);
		}
		Entity[] entities = new Entity[totalModels];
		
		Random random = new Random();
		int count = 0;
		for(DefaultModelGenerator key : entitiesMap.keySet()) {
			TexturedModel texturedObj = getTexturedObj(loader, key.getObjectName(), key.getTextureName(), key.getHasTransparency(), key.getNormalsPointingUp());
			Integer numberOfObjs = entitiesMap.get(key);
			for(int i = 0;i < numberOfObjs;i++)
			{
				float xPosition = 20.0f + random.nextFloat() * 400.0f;
				float zPosition =  random.nextFloat() * 400.0f;
				Vector3f entityPosition = new Vector3f(xPosition, 0.0f, zPosition);
				Entity entity = getEntity(texturedObj, entityPosition);
				entity.setScale(key.getScale());
				entities[count] = entity;
				++count;	
			}
		}
		return entities;
	}
}
