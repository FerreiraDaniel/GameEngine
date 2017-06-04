package com.dferreira.gameEngine.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRenderAPIAccess;
import com.dferreira.commons.gl_render.GLRenderAPIAccess;
import com.dferreira.commons.models.Light;
import com.dferreira.gameEngine.modelGenerators.WorldEntitiesGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldGUIsGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldPlayersGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldSkyBoxGenerator;
import com.dferreira.gameEngine.modelGenerators.WorldTerrainsGenerator;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.SkyBox;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.renderEngine.Loader;
import com.dferreira.gameEngine.renderEngine.MasterRender;

import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Render of the view responsible to render the 3D world
 */
@SuppressWarnings("WeakerAccess")
public class GameEngineRenderer implements GLSurfaceView.Renderer {

    private final static String TIME_TO_RENDER_TAG = "timeToRender";
    /**
     * Handle to a program object
     */
    private final Context context;
    // Member variables
    private int mWidth;
    private int mHeight;

    /**
     * NOTE: The loader could be a local variable at this stage but in the future
     * resources should be release in different methods
     */
    @SuppressWarnings("FieldCanBeLocal")
    private Loader loader;

    /**
     * Reference to the API responsible for render the scene
     */
    @SuppressWarnings("FieldCanBeLocal")
    private IRenderAPIAccess renderAPIAccess;

    /**
     * The master render is going put all the elements together
     * in order to render the scene
     */
    private MasterRender renderer;


    /**
     * Array of entities to render
     */
    private Entity[] entities;

    /**
     * Terrain to render
     */
    private Terrain[] terrains;

    /**
     * Array of GUIs to render
     */
    private GuiTexture[] GUIs;

    /**
     * Position of the light in scene
     */
    private Light light;

    /**
     * SkyBox of the 3D world
     */
    private SkyBox skyBox;

    /**
     * The player_mtl that is going to be show in the scene
     */
    private Player player;


    /**
     * Constructor of the Game Engine Render
     *
     * @param context Context where the game engine will be renderer
     */
    public GameEngineRenderer(Context context) {
        this.context = context;
    }


    /**
     * Initialize the shader programs objects
     * and load the different components of the application
     *
     * @param gl     Reference to the openGL variable
     * @param config Reference to the configuration
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Date startDate = new Date();

        /*Initializes the main variables responsible to render the 3D world*/
        this.loader = new Loader();
        this.renderAPIAccess = new GLRenderAPIAccess();
        ILoaderRenderAPI loaderAPI = renderAPIAccess.getLoaderRenderAPI();

        this.renderer = new MasterRender(renderAPIAccess);

        Date renderInitialized = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time to initialize render " + (renderInitialized.getTime() - startDate.getTime()) + " ms");

        /* Prepares the terrain that is going to render */
        Terrain terrain = WorldTerrainsGenerator.getTerrain(loader, loaderAPI);
        WorldTerrainsGenerator.loadTextures(loaderAPI, terrain);

        this.terrains = new Terrain[1];
        this.terrains[0] = terrain;

        Date terrainLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time to initialize terrains " + (terrainLoaded.getTime() - renderInitialized.getTime()) + " ms");

        /*Prepares the entities that are going to be render*/
        this.entities = WorldEntitiesGenerator.getEntities(context, loader, loaderAPI, terrain);
        WorldEntitiesGenerator.loadTextures(loaderAPI, this.entities);

        Date entitiesLoaded = new Date();
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize entities " + (entitiesLoaded.getTime() - terrainLoaded.getTime()) + " ms");



        /* Load the lights that is going to render*/
        this.light = WorldEntitiesGenerator.getLight();


        Date lightLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time to initialize light " + (lightLoaded.getTime() - terrainLoaded.getTime()) + " ms");

        		/* Prepares the GUIs that is going to render*/
        this.GUIs = WorldGUIsGenerator.getGUIs(loaderAPI);
        WorldGUIsGenerator.loadTextures(loaderAPI, this.GUIs);

        /* Load the sky box that is going to render*/
        this.skyBox = WorldSkyBoxGenerator.getSky(loaderAPI);
        WorldSkyBoxGenerator.loadTextures(loaderAPI, this.skyBox);

        Date skyBoxLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time initialize sky " + (skyBoxLoaded.getTime() - lightLoaded.getTime()) + " ms");

        /*Prepares the player_mtl that is going to be used in the scene*/
        this.player = WorldPlayersGenerator.getPlayer(context, loader, loaderAPI);
        WorldPlayersGenerator.loadTextures(loaderAPI, this.player);

        Date playerLoader = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time initialize player_mtl " + (playerLoader.getTime() - skyBoxLoaded.getTime()) + " ms");


        Log.d(TIME_TO_RENDER_TAG, "Total time:" + (playerLoader.getTime() - renderInitialized.getTime()));
    }

    /**
     * Draw the entities of the scene
     *
     * @param glUnused Variable for reference of openGL 1.0 not used at all
     */
    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Set the viewport
        renderAPIAccess.getFrameRenderAPI().setViewPort(0, 0, mWidth, mHeight);

        //game logic
        renderer.startFrameRender();
        renderer.processTerrains(terrains);
        renderer.processEntities(entities);
        renderer.processPlayer(player);
        renderer.processSkyBox(skyBox);
        renderer.processGUIs(this.GUIs);
        renderer.render(light);

        renderer.endFrameRender();
    }

    /**
     * Handle surface changes
     *
     * @param glUnused Variable for reference of openGL 1.0 not used at all
     * @param width    The new width of the screen
     * @param height   The new height of the screen
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        this.renderer.cleanUp();
    }
}