package gameEngine.views;


import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.models.Light;

import gameEngine.audioEngine.AudioLoader;
import gameEngine.audioEngine.MasterPlayer;
import gameEngine.audioEngine.TAudioEnum;
import gameEngine.modelGenerators.WorldAudioGenerator;
import gameEngine.modelGenerators.WorldEntitiesGenerator;
import gameEngine.modelGenerators.WorldGUIsGenerator;
import gameEngine.modelGenerators.WorldLightsGenerator;
import gameEngine.modelGenerators.WorldPlayersGenerator;
import gameEngine.modelGenerators.WorldSkyBoxGenerator;
import gameEngine.modelGenerators.WorldTerrainsGenerator;
import gameEngine.models.AudioBuffer;
import gameEngine.models.AudioSource;
import gameEngine.models.GuiTexture;
import gameEngine.models.Player;
import gameEngine.models.SkyBox;
import gameEngine.models.Terrain;
import gameEngine.models.complexEntities.Entity;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.renderEngine.Loader;
import gameEngine.renderEngine.MasterRender;

public class GameEngineRenderer {

	/**
	 * Number of audio sources available
	 */
	private static final int POOL_SOURCES_SIZE = 32;
	
	/**
	 * Loader should handle the loading of resources from disk
	 */
	private Loader loader;
	
	/**
	 * Loader should handle the loading of resources from disk
	 */
	private AudioLoader audioLoader;
	

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
	 * Dictionary of sounds supported by the game
	 */
	private HashMap<TAudioEnum, AudioBuffer> audioLibrary;
	
	/**
	 * Reference to the player of sounds of the game
	 */
	private MasterPlayer masterPlayer;

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
		/* Initializes the main variable responsible to the audio of the 3D world*/
		this.audioLoader = new AudioLoader();
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
		
		
		/* Prepares the sounds to be used by the engine*/
		this.audioLibrary = WorldAudioGenerator.getBuffers(this.audioLoader);
		
		/*Sounds player*/
		List<AudioSource> sourceLst = this.audioLoader.genAudioSources(POOL_SOURCES_SIZE);
		this.masterPlayer = new MasterPlayer(sourceLst);
		
	}

	/**
	 * Makes all the necessary calls to update the 
	 * frame
	 */
	private void renderFrame() {
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
	 * Calls everything necessary to play the sounds of the game
	 */
	private void playAudio() {
		masterPlayer.processEntities(entities);
		masterPlayer.processPlayer(player);
		masterPlayer.play(this.audioLibrary);
	}
	
	/**
	 * Draw the entities of the scene
	 *
	 */
	public void onDrawFrame() {
		this.renderFrame();
		this.playAudio();
	}

	/**
	 * Called when is to release resources used
	 */
	public void dealloc() {
		this.loader.cleanUp();
		this.renderer.cleanUp();
		this.masterPlayer.cleanUp();
	}
}
