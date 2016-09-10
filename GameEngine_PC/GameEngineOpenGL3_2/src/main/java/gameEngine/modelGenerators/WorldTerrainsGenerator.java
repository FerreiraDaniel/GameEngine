package gameEngine.modelGenerators;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.shapes.IShape;

import gameEngine.models.RawModel;
import gameEngine.models.Terrain;
import gameEngine.models.TerrainShape;
import gameEngine.renderEngine.Loader;
import gameEngine.textures.TerrainTexturesPack;

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
public class WorldTerrainsGenerator {
	private final static String TERRAIN_FOLDER = "terrain/";
	private final static int NUMBER_OF_TERRAINS = 2;

	/**
	 * Load the textures of the terrain
	 *
	 * @param loader
	 *            the loader of the texture
	 *
	 * @return the textures package of the terrain
	 */
	private static TerrainTexturesPack getTexturedTerrain(Loader loader) {
		Integer weightMapTextureId = loader.loadTexture(TERRAIN_FOLDER + "blendMap");
		Integer backgroundTextureId = loader.loadTexture(TERRAIN_FOLDER + "terrain");
		Integer mudTextureId = loader.loadTexture(TERRAIN_FOLDER + "mud");
		Integer grassTextureId = loader.loadTexture(TERRAIN_FOLDER + "terrain_grass");
		Integer pathTextureId = loader.loadTexture(TERRAIN_FOLDER + "path");
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
	 * @param texturedTerrain
	 *            Model of the terrain to render
	 * @param texturedTerrain
	 *            Model of the terrain to render
	 * @param position
	 *            Position where is to put the terrain
	 * 
	 * @return The terrain in the position specified
	 */
	private static Terrain getTerrain(TerrainTexturesPack texturedTerrain, RawModel terrainModel, Vector3f position) {
		Terrain terrain = new Terrain(texturedTerrain, terrainModel, position);
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
	public static Terrain[] getTerrains(Loader loader) {
		TerrainTexturesPack texturedTerrain = getTexturedTerrain(loader);
		IShape terrain = new TerrainShape();
		RawModel model = loader.loadToVAO(terrain.getVertices(), terrain.getTextureCoords(), terrain.getNormals(),
				terrain.getIndices());

		Terrain[] terrains = new Terrain[NUMBER_OF_TERRAINS];

		Vector3f terrainPosition1 = new Vector3f(0.0f, 0.0f, -0.1f);
		Terrain terrain1 = getTerrain(texturedTerrain, model, terrainPosition1);
		Vector3f terrainPosition2 = new Vector3f(1.0f, 0.0f, -0.1f);
		Terrain terrain2 = getTerrain(texturedTerrain, model, terrainPosition2);

		terrains[0] = terrain1;
		terrains[1] = terrain2;
		return terrains;
	}
}
