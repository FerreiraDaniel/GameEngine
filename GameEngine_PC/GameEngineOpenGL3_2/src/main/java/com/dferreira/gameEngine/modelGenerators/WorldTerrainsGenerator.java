package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.commons.models.TextureData;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.models.TerrainShape;
import com.dferreira.gameEngine.renderEngine.Loader;
import com.dferreira.gameEngine.textures.TerrainTexturesPack;

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
public class WorldTerrainsGenerator {

	private final static int NUMBER_OF_TERRAINS = 1;

    /**
     * Load the texture of the terrain
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @return the textured model of the terrain
     */
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
	 * @param texturedTerrain
	 *            Packages with textures of the terrain
	 * @param terrainModel
	 *            Model of the terrain to render
	 * @param heights
	 *            The height of the vertices of the terrain
	 * @param position
	 *            Position where is to put the terrain
	 * 
	 * @return The terrain in the position specified
	 */
	private static Terrain getTerrain(TerrainTexturesPack texturedTerrain, GLRawModel terrainModel, float[][] heights,
			Vector3f position) {
		Terrain terrain = new Terrain(texturedTerrain, terrainModel, heights, position);
		return terrain;
	}

	/**
	 * The terrains of the 3D scene
	 *
	 * @param loader
	 *            The loader in charge of loading the textures of the terrains
	 *
	 * @return list of terrains of the scene
	 */
	public static Terrain[] getTerrains(Loader loader, ILoaderRenderAPI loaderRenderAPI) {
		TerrainTexturesPack texturedTerrain = getTexturedTerrain(loaderRenderAPI);
		TextureData heightMap = loaderRenderAPI.getTextureData(TextureEnum.terrain_heightmap);
		TerrainShape terrain = new TerrainShape(heightMap);
		GLRawModel model = loader.loadToVAO(terrain.getVertices(), terrain.getTextureCoords(), terrain.getNormals(),
				terrain.getIndices());

		Terrain[] terrains = new Terrain[NUMBER_OF_TERRAINS];

		Vector3f terrainPosition1 = new Vector3f(0.0f, 0.0f, -0.1f);
		Terrain terrain1 = getTerrain(texturedTerrain, model, terrain.getHeights(), terrainPosition1);

		terrains[0] = terrain1;
		return terrains;
	}
}
