package com.dferreira.gameEngine.views

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.Log
import com.dferreira.androidUtils.AndroidResourceProvider
import com.dferreira.commons.generic_render.IRenderAPIAccess
import com.dferreira.commons.generic_resources.IResourceProvider
import com.dferreira.commons.models.Light
import com.dferreira.gameEngine.gl_render.GLRenderAPIAccess
import com.dferreira.gameEngine.modelGenerators.*
import com.dferreira.gameEngine.models.GuiTexture
import com.dferreira.gameEngine.models.Player
import com.dferreira.gameEngine.models.SkyBox
import com.dferreira.gameEngine.models.Terrain
import com.dferreira.gameEngine.models.complexEntities.Entity
import com.dferreira.gameEngine.renderEngine.Loader
import com.dferreira.gameEngine.renderEngine.MasterRender
import java.util.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Render of the view responsible to render the 3D world
 */
class GameEngineRenderer
/**
 * Constructor of the Game Engine Render
 *
 * @param context Context where the game engine will be renderer
 */(
        /**
         * Handle to a program object
         */
        private val context: Context) : GLSurfaceView.Renderer {

    // Member variables
    private var mWidth = 0
    private var mHeight = 0

    /**
     * NOTE: The loader could be a local variable at this stage but in the future
     * resources should be release in different methods
     */
    private var loader: Loader? = null

    /**
     * Reference to the API responsible for render the scene
     */
    private var renderAPIAccess: IRenderAPIAccess? = null

    /**
     * The master render is going put all the elements together
     * in order to render the scene
     */
    private var renderer: MasterRender? = null

    /**
     * Array of entities to render
     */
    private var entities: Array<Entity> = emptyArray()

    /**
     * Terrain to render
     */
    private var terrains: Array<Terrain> = emptyArray()

    /**
     * Array of GUIs to render
     */
    private var GUIs: Array<GuiTexture> = emptyArray()

    /**
     * The lights in scene
     */
    private var lights: Array<Light> = emptyArray()

    /**
     * SkyBox of the 3D world
     */
    private var skyBox: SkyBox? = null

    /**
     * The player_mtl that is going to be show in the scene
     */
    private var player: Player? = null

    /**
     * Initialize the shader programs objects
     * and load the different components of the application
     *
     * @param gl     Reference to the openGL variable
     * @param config Reference to the configuration
     */
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        val startDate = Date()

        /*Initializes the main variables responsible to render the 3D world*/loader = Loader()
        val resourceProvider: IResourceProvider = AndroidResourceProvider(context)
        renderAPIAccess = GLRenderAPIAccess(resourceProvider)
        val loaderAPI = renderAPIAccess?.getLoaderRenderAPI()
        renderer = MasterRender(renderAPIAccess)
        val renderInitialized = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize render " + (renderInitialized.time - startDate.time) + " ms")

        /* Prepares the terrain that is going to render */
        val terrain = WorldTerrainsGenerator.getTerrain(loaderAPI)
        WorldTerrainsGenerator.loadTextures(loaderAPI, terrain)
        terrains = arrayOf(terrain)
        val terrainLoaded = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize terrains " + (terrainLoaded.time - renderInitialized.time) + " ms")

        /*Prepares the entities that are going to be render*/
        entities = WorldEntitiesGenerator.getEntities(loader, loaderAPI, resourceProvider, terrain)
        WorldEntitiesGenerator.loadTextures(loaderAPI, entities)
        val entitiesLoaded = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize entities " + (entitiesLoaded.time - terrainLoaded.time) + " ms")


        /* Load the light that is going to render */lights = WorldLightsGenerator.getLights()
        val lightLoaded = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time to initialize light " + (lightLoaded.time - terrainLoaded.time) + " ms")

        /* Prepares the GUIs that is going to render*/GUIs = WorldGUIsGenerator.getGUIs(loaderAPI)
        WorldGUIsGenerator.loadTextures(loaderAPI, GUIs)

        /* Load the sky box that is going to render*/skyBox = WorldSkyBoxGenerator.getSky(loaderAPI)
        WorldSkyBoxGenerator.loadTextures(loaderAPI, skyBox)
        val skyBoxLoaded = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time initialize sky " + (skyBoxLoaded.time - lightLoaded.time) + " ms")

        /*Prepares the player_mtl that is going to be used in the scene*/player = WorldPlayersGenerator.getPlayer(loader, loaderAPI, resourceProvider)
        WorldPlayersGenerator.loadTextures(loaderAPI, player)
        val playerLoader = Date()
        Log.d(TIME_TO_RENDER_TAG, "Time initialize player_mtl " + (playerLoader.time - skyBoxLoaded.time) + " ms")
        Log.d(TIME_TO_RENDER_TAG, "Total time:" + (playerLoader.time - renderInitialized.time))
    }

    /**
     * Draw the entities of the scene
     *
     * @param glUnused Variable for reference of openGL 1.0 not used at all
     */
    override fun onDrawFrame(glUnused: GL10) {
        // Set the viewport
        renderAPIAccess!!.frameRenderAPI.setViewPort(0, 0, mWidth, mHeight)

        //game logic

        renderer!!.startFrameRender()
        renderer!!.processTerrains(terrains)
        renderer!!.processEntities(entities)
        renderer!!.processPlayer(player)
        renderer!!.processSkyBox(skyBox)
        renderer!!.processGUIs(GUIs)
        renderer!!.render(lights)
        renderer!!.endFrameRender()
    }

    /**
     * Handle surface changes
     *
     * @param glUnused Variable for reference of openGL 1.0 not used at all
     * @param width    The new width of the screen
     * @param height   The new height of the screen
     */
    override fun onSurfaceChanged(glUnused: GL10, width: Int, height: Int) {
        mWidth = width
        mHeight = height
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    fun cleanUp() {
        renderer!!.dispose()
    }

    companion object {
        private const val TIME_TO_RENDER_TAG = "timeToRender"
    }

}