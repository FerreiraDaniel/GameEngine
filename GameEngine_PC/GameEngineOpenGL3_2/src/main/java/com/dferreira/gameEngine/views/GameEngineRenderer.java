package com.dferreira.gameEngine.views;


import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRenderAPIAccess;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.gl_render.GLRenderAPIAccess;
import com.dferreira.commons.models.Light;
import com.dferreira.desktopUtils.DesktopResourceProvider;
import com.dferreira.gameEngine.audioEngine.AudioLoader;
import com.dferreira.gameEngine.audioEngine.MasterPlayer;
import com.dferreira.gameEngine.audioEngine.TAudioEnum;
import com.dferreira.gameEngine.modelGenerators.WorldAudioGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldEntitiesGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldGUIsGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldLightsGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldPlayersGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldSkyBoxGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldTerrainsGenerator;
import com.dferreira.gameEngine.models.AudioBuffer;
import com.dferreira.gameEngine.models.AudioSource;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.SkyBox;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.renderEngine.DisplayManager;
import com.dferreira.gameEngine.renderEngine.Loader;
import com.dferreira.gameEngine.renderEngine.MasterRender;

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
     * Reference to the API responsible for render the scene
     */
    private IRenderAPIAccess renderAPIAccess;

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
	private GuiTexture[] GUIs;
	

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
		
		IResourceProvider resourceProvider = new DesktopResourceProvider();
		this.renderAPIAccess = new GLRenderAPIAccess(resourceProvider);
		ILoaderRenderAPI loaderAPI = renderAPIAccess.getLoaderRenderAPI();
		this.renderer = new MasterRender(renderAPIAccess);
		

		/* Prepares the terrains that is going to render */
		this.terrains = WorldTerrainsGenerator.getTerrains(loader, loaderAPI);
				
		/* Prepares the entities that is going to be render */
		this.entities = WorldEntitiesGenerator.getEntities(loader, terrains[0]);
		WorldEntitiesGenerator.loadTextures(loaderAPI, this.entities);

		/* Load the light that is going to render */
		this.lights = WorldLightsGenerator.getLights();

		/* Prepares the GUIs that is going to render*/
		this.GUIs = WorldGUIsGenerator.getGUIs(loader);
		WorldGUIsGenerator.loadTextures(loaderAPI, this.GUIs);
		
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
		renderer.processGUIs(this.GUIs);
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
		this.renderer.dispose();
		this.masterPlayer.cleanUp();
	}
}
