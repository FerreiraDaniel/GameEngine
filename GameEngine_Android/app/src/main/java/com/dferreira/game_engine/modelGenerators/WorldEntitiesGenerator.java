package com.dferreira.game_engine.modelGenerators;


import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.Entity;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.TexturedModel;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.shapes.IShape;
import com.dferreira.game_engine.textures.ModelTexture;
import com.dferreira.game_engine.wave_front.OBJLoader;

import java.util.HashMap;
import java.util.Random;

/**
 * Responsible for creating the multiple entities of the 3D world
 */
public class WorldEntitiesGenerator {

    private final static int NUMBER_OF_TREES = 10;
    private final static int NUMBER_OF_BANANA_TREES = 5;
    private final static int NUMBER_OF_FERNS = 10;
    private final static int NUMBER_OF_GRASS = 10;
    private final static int NUMBER_OF_FLOWERS = 20;

    /**
     * Load the texture of the dragon model
     *
     * @param loader the loader of the texture
     * @return the textured model of the dragon
     */
    private static TexturedModel getTexturedObj(Context context, Loader loader, int modelId, int mTextureId, boolean hasTransparency, boolean normalsPointingUp) {
        IShape objModel = OBJLoader.loadObjModel(context, modelId);
        RawModel model = loader.loadToRawModel(objModel.getVertices(), objModel.getTextureCoords(),
                objModel.getNormals(), objModel.getIndices());
        Integer textureId = loader.loadTexture(context, mTextureId);
        ModelTexture texture = new ModelTexture(textureId);
        TexturedModel texturedModel = new TexturedModel(model, texture, hasTransparency, normalsPointingUp);

        texturedModel.setShineDamper(10.0f);
        texturedModel.setReflectivity(1.0f);

        return texturedModel;
    }


    /**
     * Get one entity in a certain position
     *
     * @param texturedEntity Model of the entity to render
     * @param position       Position where is to put the entity in the 3D world
     * @return the entity to render
     */
    private static Entity getEntity(TexturedModel texturedEntity, Vector3f position) {
        return new Entity(texturedEntity,
                position,
                0.0f, 0.0f, 0.0f,    //Rotation
                0.0f    //Scale
        );
    }

    /**
     * Get the default values of the  entities that are going make the world
     */
    private static HashMap<DefaultModelGenerator, Integer> getEntitiesMap() {
        HashMap<DefaultModelGenerator, Integer> entitiesMap = new HashMap<>();

		/*Fern model*/
        DefaultModelGenerator fernModel = new DefaultModelGenerator();
        fernModel.setObjectReference(R.raw.fern);
        fernModel.setTextureReference(R.mipmap.fern);
        fernModel.setScale(1.0f);
        fernModel.setHasTransparency(true);
        fernModel.setNormalsPointingUp(true);

		/*Tree model*/
        DefaultModelGenerator treeModel = new DefaultModelGenerator();
        treeModel.setObjectReference(R.raw.tree);
        treeModel.setTextureReference(R.mipmap.tree);
        treeModel.setScale(10.0f);
        treeModel.setHasTransparency(false);
        treeModel.setNormalsPointingUp(false);

        /*Banana tree*/
        DefaultModelGenerator bananaTreeModel = new DefaultModelGenerator();
        bananaTreeModel.setObjectReference(R.raw.banana_tree);
        bananaTreeModel.setTextureReference(R.mipmap.banana_tree);
        bananaTreeModel.setScale(1.0f);
        bananaTreeModel.setHasTransparency(true);
        bananaTreeModel.setNormalsPointingUp(false);


		/*grass model*/
        DefaultModelGenerator grassModel = new DefaultModelGenerator();
        grassModel.setObjectReference(R.raw.grass);
        grassModel.setTextureReference(R.mipmap.grass);
        grassModel.setScale(1.0f);
        grassModel.setHasTransparency(true);
        grassModel.setNormalsPointingUp(true);

        		/* flower model */
        DefaultModelGenerator flowerModel = new DefaultModelGenerator();
        flowerModel.setObjectReference(R.raw.flower);
        flowerModel.setTextureReference(R.mipmap.flower);
        flowerModel.setScale(1.0f);
        flowerModel.setHasTransparency(true);
        flowerModel.setNormalsPointingUp(true);


		/*Entity map of all the existing entities*/
        entitiesMap.put(fernModel, NUMBER_OF_FERNS);
        entitiesMap.put(treeModel, NUMBER_OF_TREES);
        entitiesMap.put(bananaTreeModel, NUMBER_OF_BANANA_TREES);
        entitiesMap.put(grassModel, NUMBER_OF_GRASS);
        entitiesMap.put(flowerModel, NUMBER_OF_FLOWERS);

        return entitiesMap;
    }

    /**
     * @param loader loader that will load the entities of the 3D world
     * @return The entities that will compose the 3D world
     */
    public static Entity[] getEntities(Context context, Loader loader) {

        HashMap<DefaultModelGenerator, Integer> entitiesMap = getEntitiesMap();

        int totalModels = 0;
        for (DefaultModelGenerator key : entitiesMap.keySet()) {
            totalModels += entitiesMap.get(key);
        }
        Entity[] entities = new Entity[totalModels];

        Random random = new Random();
        int count = 0;
        for (DefaultModelGenerator key : entitiesMap.keySet()) {
            TexturedModel texturedObj = getTexturedObj(context, loader, key.getObjectReference(), key.getTextureReference(), key.getHasTransparency(), key.getNormalsPointingUp());
            Integer numberOfEntities = entitiesMap.get(key);
            for (int i = 0; i < numberOfEntities; i++) {
                float xPosition = 20.0f + random.nextFloat() * 200.0f;
                float zPosition = random.nextFloat() * 200.0f;
                Vector3f entityPosition = new Vector3f(xPosition, 0.0f, zPosition);
                Entity entity = getEntity(texturedObj, entityPosition);
                entity.setScale(key.getScale() * random.nextFloat());
                entities[count] = entity;
                ++count;
            }
        }
        return entities;
    }

    /**
     * @return A light source to the scene
     */
    public static Light getLight() {
        Vector3f lightPosition = new Vector3f(10.0f, 100.0f, 10.0f);
        Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

        return new Light(lightPosition, lightColor);
    }
}
