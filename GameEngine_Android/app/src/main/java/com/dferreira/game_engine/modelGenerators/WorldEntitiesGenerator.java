package com.dferreira.game_engine.modelGenerators;


import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.models.complexEntities.Entity;
import com.dferreira.game_engine.models.complexEntities.GenericEntity;
import com.dferreira.game_engine.models.complexEntities.RawModelMaterial;
import com.dferreira.game_engine.renderEngine.Loader;

import java.util.HashMap;
import java.util.Random;

/**
 * Responsible for creating the multiple entities of the 3D world
 */
public class WorldEntitiesGenerator extends GenericEntitiesGenerator {

    private final static int NUMBER_OF_TREES = 10;
    private final static int NUMBER_OF_BANANA_TREES = 5;
    private final static int NUMBER_OF_FERNS = 100;
    private final static int NUMBER_OF_GRASS = 10;
    private final static int NUMBER_OF_FLOWERS = 20;
    private final static int NUMBER_OF_MARBLES = 10;

    /**
     * Get one entity in a certain position
     *
     * @param genericEntity Generic entity
     * @param position      Position where is to put the entity in the 3D world
     * @return the entity to render
     */
    private static Entity getEntity(GenericEntity genericEntity, Vector3f position) {
        return new Entity(genericEntity,
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


        		/*Marble model*/
        DefaultModelGenerator marbleModel = new DefaultModelGenerator();
        marbleModel.setObjectReference(R.raw.marble);
        marbleModel.setTextureReference(R.mipmap.marble);
        marbleModel.setScale(5.0f);
        marbleModel.setHasTransparency(false);
        marbleModel.setNormalsPointingUp(false);

		/*Entity map of all the existing entities*/
        entitiesMap.put(fernModel, NUMBER_OF_FERNS);
        entitiesMap.put(treeModel, NUMBER_OF_TREES);
        entitiesMap.put(bananaTreeModel, NUMBER_OF_BANANA_TREES);
        entitiesMap.put(grassModel, NUMBER_OF_GRASS);
        entitiesMap.put(flowerModel, NUMBER_OF_FLOWERS);
        entitiesMap.put(marbleModel, NUMBER_OF_MARBLES);

        return entitiesMap;
    }

    /**
     * @param loader  loader that will load the entities of the 3D world
     * @param terrain The terrain used to determine the height position
     * @return The entities that will compose the 3D world
     */
    public static Entity[] getEntities(Context context, Loader loader, Terrain terrain) {

        HashMap<DefaultModelGenerator, Integer> entitiesMap = getEntitiesMap();

        int totalModels = 0;
        for (DefaultModelGenerator key : entitiesMap.keySet()) {
            totalModels += entitiesMap.get(key);
        }
        Entity[] entities = new Entity[totalModels];

        Random random = new Random();
        int count = 0;
        for (DefaultModelGenerator key : entitiesMap.keySet()) {
            RawModelMaterial texturedObj = getTexturedObj(context, loader, key.getObjectReference(), key.getTextureReference(), key.getHasTransparency(), key.getNormalsPointingUp());
            Integer numberOfEntities = entitiesMap.get(key);
            GenericEntity genericEntity = new GenericEntity(texturedObj);
            for (int i = 0; i < numberOfEntities; i++) {
                float xPosition = 20.0f + random.nextFloat() * 200.0f;
                float zPosition = random.nextFloat() * 200.0f;
                float yPosition = terrain.getHeightOfTerrain(xPosition, zPosition);

                Vector3f entityPosition = new Vector3f(xPosition, yPosition, zPosition);
                Entity entity = getEntity(genericEntity, entityPosition);
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
