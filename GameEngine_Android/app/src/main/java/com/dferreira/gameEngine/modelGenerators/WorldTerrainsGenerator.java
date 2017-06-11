package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.models.TerrainShape;
import com.dferreira.gameEngine.textures.TerrainTexturesPack;

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
        ITexture weightMapTexture = loaderRenderAPI.loadTexture(TextureEnum.weight_map, repeat);
        ITexture backgroundTexture = loaderRenderAPI.loadTexture(TextureEnum.terrain, repeat);
        ITexture mudTexture = loaderRenderAPI.loadTexture(TextureEnum.mud, repeat);
        ITexture grassTexture = loaderRenderAPI.loadTexture(TextureEnum.terrain_grass, repeat);
        ITexture pathTexture = loaderRenderAPI.loadTexture(TextureEnum.path, repeat);
        // Create the package
        TerrainTexturesPack texturesPackage = new TerrainTexturesPack();
        texturesPackage.setWeightMapTexture(weightMapTexture);
        texturesPackage.setBackgroundTexture(backgroundTexture);
        texturesPackage.setMudTexture(mudTexture);
        texturesPackage.setGrassTexture(grassTexture);
        texturesPackage.setPathTexture(pathTexture);

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
     * @param loaderRenderAPI The API responsible for load elements specifics to the render
     * @return The terrain of the 3D scene
     */
    public static Terrain getTerrain(ILoaderRenderAPI loaderRenderAPI) {
        TextureData heightMap = loaderRenderAPI.getTextureData(TextureEnum.terrain_heightmap);

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
