package com.dferreira.game_engine.modelGenerators;


import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.models.TextureData;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.models.TerrainShape;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.textures.TerrainTexturesPack;

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
public class WorldTerrainsGenerator {

    /**
     * Load the texture of the terrain
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @return the textured model of the terrain
     */
    @SuppressWarnings("ConstantConditions")
    private static TerrainTexturesPack getTexturedTerrain(ILoaderRenderAPI loaderRenderAPI) {
        boolean repeat = true;
        Integer weightMapTextureId = loaderRenderAPI.loadTexture(R.mipmap.weight_map, repeat);
        Integer backgroundTextureId = loaderRenderAPI.loadTexture(R.mipmap.terrain, repeat);
        Integer mudTextureId = loaderRenderAPI.loadTexture(R.mipmap.mud, repeat);
        Integer grassTextureId = loaderRenderAPI.loadTexture(R.mipmap.terrain_grass, repeat);
        Integer pathTextureId = loaderRenderAPI.loadTexture(R.mipmap.path, repeat);
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
     * @param terrainModel Model of the terrain to render
     * @param heights      The height of the vertices of the terrain
     * @param position     Position where is to put the terrain
     * @return The terrain in the position specified
     */
    private static Terrain getTerrain(IRawModel terrainModel, float[][] heights, Vector3f position) {
        return new Terrain(terrainModel, heights, position);
    }

    /**
     * @param context         Context where the terrain would be created
     * @param loader          Loader to load the raw model
     * @param loaderRenderAPI The API responsible for load elements specifics to the render
     * @return The terrain of the 3D scene
     */
    public static Terrain getTerrain(Context context, Loader loader, ILoaderRenderAPI loaderRenderAPI) {
        TextureData heightMap = loader.getTextureData(context, R.mipmap.terrain_heightmap);

        TerrainShape terrain = new TerrainShape(heightMap);

        IRawModel model = loaderRenderAPI.loadToRawModel(terrain);

        Vector3f terrainPosition1 = new Vector3f(0.0f, 0.0f, -0.1f);
        return getTerrain(model, terrain.getHeights(), terrainPosition1);
    }

    /**
     * Load the textures of one terrain
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @param terrain         The terrain that is going to get the textures loaded
     */
    public static void loadTextures(ILoaderRenderAPI loaderRenderAPI, Terrain terrain) {
        TerrainTexturesPack terrainTexturesPackage = getTexturedTerrain(loaderRenderAPI);
        terrain.setTexturePack(terrainTexturesPackage);
    }
}
