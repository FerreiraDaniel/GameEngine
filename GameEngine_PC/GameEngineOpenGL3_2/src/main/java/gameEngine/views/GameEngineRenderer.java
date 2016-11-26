package gameEngine.views;

import com.dferreira.commons.models.Light;

import gameEngine.modelGenerators.WorldEntitiesGenerator;
import gameEngine.modelGenerators.WorldGUIsGenerator;
import gameEngine.modelGenerators.WorldLightsGenerator;
import gameEngine.modelGenerators.WorldPlayersGenerator;
import gameEngine.modelGenerators.WorldSkyBoxGenerator;
import gameEngine.modelGenerators.WorldTerrainsGenerator;
import gameEngine.models.Entity;
import gameEngine.models.GuiTexture;
import gameEngine.models.Player;
import gameEngine.models.SkyBox;
import gameEngine.models.Terrain;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.renderEngine.Loader;
import gameEngine.renderEngine.MasterRender;

public class GameEngineRenderer {

	/**
	 * Loader should handle the loading of resources from disk
	 */
	private Loader loader;

	/**
	 * The master render is going put all the elements together in order to
	 * render the scene
	 */
	private MasterRender renderer;

	/**
	 * Array of entities to render
	 */
	private Entity[] entities;

	/**
	 * Array of terrains to render
	 */
	private Terrain[] terrains;
	
	/**
	 * Array of GUIs to render
	 */
	private GuiTexture[] guis;
	

	/**
	 * The lights in scene
	 */
	private Light[] lights;

	/**
	 * SkyBox of the 3D world
	 */
	private SkyBox skyBox;

	/**
	 * The player that is going to be show in the scene
	 */
	private Player player;

	/**
	 * Constructor of the game engine render
	 */
	public GameEngineRenderer() {
		super();
	}

	/**
	 * Initialize the shader programs objects and load the different components
	 * of the application
	 *
	 */
	public void onSurfaceCreated() {
		/* Initializes the main variables responsible to render the 3D world */
		this.loader = new Loader();
		this.renderer = new MasterRender();

		/* Prepares the terrains that is going to render */
		this.terrains = WorldTerrainsGenerator.getTerrains(loader);
				
		/* Prepares the entities that is going to be render */
		this.entities = WorldEntitiesGenerator.getEntities(loader, terrains[0]);


		/* Load the light that is going to render */
		this.lights = WorldLightsGenerator.getLights();

		/* Prepares the GUIs that is going to render*/
		this.guis = WorldGUIsGenerator.getGUIs(loader);
		
		/* Load the sky box that is going to render */
		this.skyBox = WorldSkyBoxGenerator.getSky(loader);

		/* Prepares the player that is going to be used in the scene */
		this.player = WorldPlayersGenerator.getPlayer(loader);
	}

	/**
	 * Draw the entities of the scene
	 *
	 */
	public void onDrawFrame() {
		// game logic
		renderer.startFrameRender();
		renderer.processTerrains(terrains);
		renderer.processEntities(entities);
		renderer.processSkyBox(skyBox);
		renderer.processPlayer(player);
		renderer.processGUIs(this.guis);

		renderer.render(lights);

		DisplayManager.updateDisplay();

		renderer.endFrameRender();
	}

	/**
	 * Called when is to release resources used
	 */
	public void dealloc() {
		this.loader.cleanUp();
		this.renderer.cleanUp();
	}
}
