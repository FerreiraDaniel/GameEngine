package com.dferreira.game_engine.views;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.modelGenerators.WorldEntitiesGenerator;
import com.dferreira.game_engine.modelGenerators.WorldSkyBoxGenerator;
import com.dferreira.game_engine.modelGenerators.WorldTerrainsGenerator;
import com.dferreira.game_engine.models.Entity;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.renderEngine.MasterRender;

import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Render of the view responsible to render the 3D world
 */
@SuppressWarnings("WeakerAccess")
public class GameEngineRenderer implements GLSurfaceView.Renderer {

    // Member variables
    private int mWidth;
    private int mHeight;

    private final static String TIME_TO_RENDER_TAG = "timeToRender";

    /**
     * Handle to a program object
     */
    private final Context context;

    @SuppressWarnings("FieldCanBeLocal")
    private Loader loader;

    private MasterRender renderer;


    /**
     * Array of entities to render
     */
    private Entity[] entities;

    /**
     * Array of terrains to render
     */
    Terrain[] terrains;

    /**
     * Position of the light in scene
     */
    private Light light;

    /**
     * SkyBox of the 3D world
     */
    private SkyBox skyBox;


    /**
     * Constructor of the Game Engine Render
     *
     * @param context Context where the game engine will be renderer
     */
    public GameEngineRenderer(Context context) {
        this.context = context;
    }


    /**
     * Initialize the shader and program object
     *
     * @param gl     Reference to the openGL variable
     * @param config Reference to the configuration
     */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Date startDate = new Date();
        // enable face culling feature
        gl.glEnable(GL10.GL_CULL_FACE);
        // specify which faces to not draw
        gl.glCullFace(GL10.GL_BACK);

        /*Initializes the main variables responsible to render the 3D world*/
        this.loader = new Loader();
        this.renderer = new MasterRender(context);

        Date renderInitialized = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time to initialize render " + (renderInitialized.getTime() - startDate.getTime()) + " ms");

        /*Prepares the entities that are going to be render*/
        this.entities = WorldEntitiesGenerator.getEntities(context, loader);

        Date entitiesLoaded = new Date();
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize entities " + (entitiesLoaded.getTime() - renderInitialized.getTime()) + " ms");

        /* Prepares the terrain that is going to render */
        this.terrains = WorldTerrainsGenerator.getTerrains(context,loader);

        Date terrainLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time to initialize terrains " + (terrainLoaded.getTime() - entitiesLoaded.getTime() )+ " ms");

        /* Load the lights that is going to render*/
        this.light = WorldEntitiesGenerator.getLight();


        Date lightLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG,"Time to initialize light " + (lightLoaded.getTime() - terrainLoaded.getTime()) + " ms");

        /* Load the sky box that is going to render*/
        this.skyBox = WorldSkyBoxGenerator.getSky(context, loader);

        Date skyBoxLoaded = new Date();

        Log.d(TIME_TO_RENDER_TAG, "Time initialize sky " + (skyBoxLoaded.getTime() - lightLoaded.getTime()) + " ms");

        Log.d(TIME_TO_RENDER_TAG, "Total time:" + (skyBoxLoaded.getTime() - renderInitialized.getTime()));
    }

    // /
    // Draw a triangle using the shader pair created in onSurfaceCreated()
    //
    public void onDrawFrame(GL10 glUnused) {
        // Set the viewport
        GLES20.glViewport(0, 0, mWidth, mHeight);

        //game logic
        renderer.processTerrains(terrains);
        renderer.processEntities(entities);
        renderer.processSkyBox(skyBox);
        renderer.render(light);


    }

    // /
    // Handle surface changes
    //
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}