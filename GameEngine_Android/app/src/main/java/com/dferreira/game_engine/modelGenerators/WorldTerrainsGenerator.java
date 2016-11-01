package com.dferreira.game_engine.modelGenerators;


import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.TextureData;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.models.TerrainShape;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.textures.TerrainTexturesPack;

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
public class WorldTerrainsGenerator {

    private final static int NUMBER_OF_TERRAINS = 1;


    /**
     * Load the texture of the terrain
     *
     * @param loader Loader to load the raw model
     * @return the textured model of the terrain
     */
    private static TerrainTexturesPack getTexturedTerrain(Context context, Loader loader) {
        Integer weightMapTextureId = loader.loadTexture(context, R.mipmap.weight_map);
        Integer backgroundTextureId = loader.loadTexture(context, R.mipmap.terrain);
        Integer mudTextureId = loader.loadTexture(context, R.mipmap.mud);
        Integer grassTextureId = loader.loadTexture(context, R.mipmap.terrain_grass);
        Integer pathTextureId = loader.loadTexture(context, R.mipmap.path);
        // Create the package
        TerrainTexturesPack texturesPackage = new TerrainTexturesPack();
        texturesPackage.setWeightMapTextureId(weightMapTextureId);
        texturesPackage.setBackgroundTextureId(backgroundTextureId);
        texturesPackage.setMudTextureId(mudTextureId);
        texturesPackage.setGrassTextureId(grassTextureId);
        texturesPackage.setPathTextureId(pathTextureId);

        return texturesPackage;
    }

    /**
     * Creates a terrain in a specified position
     *
     * @param texturedTerrain Packages with textures of the terrain
     * @param terrainModel    Model of the terrain to render
     * @param heights         The height of the vertices of the terrain
     * @param position        Position where is to put the terrain
     *
     * @return The terrain in the position specified
     */
    private static Terrain getTerrain(TerrainTexturesPack texturedTerrain, RawModel terrainModel, float[][] heights, Vector3f position) {
        return new Terrain(texturedTerrain, terrainModel, heights, position);
    }

    /**
     * The terrains of the 3D scene
     *
     * @param loader Loader to load the raw model
     * @return A set of the terrains of the 3D scene
     */
    public static Terrain[] getTerrains(Context context, Loader loader) {
        TerrainTexturesPack terrainTexturesPackage = getTexturedTerrain(context, loader);
        TextureData heightMap = loader.getTextureData(context, R.mipmap.terrain_heightmap);

        TerrainShape terrain = new TerrainShape(heightMap);

        RawModel model = loader.loadToRawModel(terrain.getVertices(), terrain.getTextureCoords(), terrain.getNormals(),
                terrain.getIndices());

        Terrain[] terrains = new Terrain[NUMBER_OF_TERRAINS];


        Vector3f terrainPosition1 = new Vector3f(0.0f, 0.0f, -0.1f);
        Terrain terrain1 = getTerrain(terrainTexturesPackage, model, terrain.getHeights(), terrainPosition1);

        terrains[0] = terrain1;
        return terrains;
    }
}
