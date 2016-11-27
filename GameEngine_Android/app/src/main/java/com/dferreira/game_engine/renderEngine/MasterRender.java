package com.dferreira.game_engine.renderEngine;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_controller.GameEngineTouchListener;
import com.dferreira.game_controller.GamePad;
import com.dferreira.game_engine.models.Camera;
import com.dferreira.game_engine.models.Entity;
import com.dferreira.game_engine.models.GuiTexture;
import com.dferreira.game_engine.models.Player;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.models.Terrain;
import com.dferreira.game_engine.models.TexturedModel;
import com.dferreira.game_engine.models.ThirdPersonCamera;
import com.dferreira.game_engine.shaders.entities.EntityShaderManager;
import com.dferreira.game_engine.shaders.guis.GuiShaderManager;
import com.dferreira.game_engine.shaders.skyBox.SkyBoxShaderManager;
import com.dferreira.game_engine.shaders.terrains.TerrainShaderManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Groups the entities in a hash map like this
 * the same entity will be just put in different positions
 */
public class MasterRender {

    /* Constants of the camera */
    private static final float FOV = 70.0f;
    private static final float CAMERA_RATE = 16.0f / 9.0f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000.0f;

    /* Components of the color of the sky */
    private static final float SKY_R = 0.5f;
    private static final float SKY_G = 0.5f;
    private static final float SKY_B = 0.5f;

    /**
     * Reference to the render of the entities
     */
    private final EntityRender entityRender;


    /**
     * Reference to the render of the terrains
     */
    private final TerrainRender terrainRender;

    /**
     * Reference to the render of the sky box
     */
    private final SkyBoxRender skyBoxRender;

    /**
     * Reference to the render of GUIs
     */
    private final GuiRender guiRender;

    /**
     * Reference to the camera from where the user is going to see the 3D world
     */
    private final ThirdPersonCamera camera;


    /**
     * Entities of the world that are going to be rendered
     */
    private final Map<TexturedModel, List<Entity>> entities;

    /**
     * List of terrains of the world that are going to be render
     */
    private final List<Terrain> terrains;

    /**
     * The player that is going to be show in the scene
     */
    private Player player;

    /**
     * List of GUIs to show the status of the user
     */
    private List<GuiTexture> GUIs;

    /**
     * The sky box that is going to use during the render
     */
    private SkyBox skyBox;

    /**
     * Used to track how long tacks to render a frame
     */
    private Date startDate;

    /**
     * The time to render one last frame in seconds
     * Variable used to be frame rate independent when moves the entities around
     */
    private float timeToRender;

    /**
     * Constructor of the master renderer
     */
    public MasterRender(Context context) {
        //Initializes the projection matrix
        GLTransformation projectionMatrix = createProjectionMatrix();

        //Initializes the entity render
        EntityShaderManager eShader = new EntityShaderManager(context);
        this.entityRender = new EntityRender(eShader, projectionMatrix);

        // Initializes the entities to be render
        this.entities = new HashMap<>();

        // Initializes the terrain render
        TerrainShaderManager tShader = new TerrainShaderManager(context);
        this.terrainRender = new TerrainRender(tShader, projectionMatrix);

        // Initializes the sky box render
        SkyBoxShaderManager sbManager = new SkyBoxShaderManager(context);
        this.skyBoxRender = new SkyBoxRender(sbManager, projectionMatrix);

        GuiShaderManager gShader = new GuiShaderManager(context);
        this.guiRender = new GuiRender(gShader);

        // Initializes the terrains to render
        this.terrains = new ArrayList<>();

        // Initializes the GUIs to render
        this.GUIs = new ArrayList<>();

        // Initializes the camera
        this.camera = new ThirdPersonCamera();

    }

    /**
     * Create the projection matrix with parameters of the camera
     *
     * @return A projection matrix
     */
    private GLTransformation createProjectionMatrix() {
        GLTransformation matrix = new GLTransformation();
        matrix.glLoadIdentity();
        matrix.gluPerspective(FOV, CAMERA_RATE, NEAR_PLANE, FAR_PLANE);

        return matrix;
    }

    /**
     * Clean the data of the previous frame
     */
    private void prepare() {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // Set the color clear value
        GLES20.glClearColor(0, 0.3f, 0, 1);

        // Clear the color and depth buffers
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES10.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Put one entity in the list of entities to render
     *
     * @param entity the entity to add to the render
     */
    private void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch == null) {
            batch = new ArrayList<>();
            entities.put(entityModel, batch);
        }
        batch.add(entity);
    }

    /**
     * Put the entities to process in the hash map dedicated to process entities by group
     *
     * @param lEntities list of entities to get render in the next frame
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void processEntities(Entity[] lEntities) {
        entities.clear();
        if ((lEntities != null) && (lEntities.length > 0)) {
            for (int i = 0; i < lEntities.length; i++) {
                Entity entity = lEntities[i];
                processEntity(entity);
            }
        }
    }

    /**
     * Put a terrain in the list of terrains to render
     *
     * @param terrain the terrain to render
     */
    private void processTerrain(Terrain terrain) {
        this.terrains.add(terrain);
    }

    /**
     * Put the terrains to process in the list of terrains to process
     *
     * @param lTerrains list of terrains to process
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void processTerrains(Terrain[] lTerrains) {
        this.terrains.clear();
        if ((lTerrains != null) && (lTerrains.length > 0)) {
            for (int i = 0; i < lTerrains.length; i++) {
                Terrain terrain = lTerrains[i];
                processTerrain(terrain);
            }
        }
    }

    /**
     * Set the sky box the use during the render
     */
    public void processSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    /**
     * Put a GUI in the list of GUIs to render
     *
     * @param gui the GUI to render
     */
    private void processGUI(GuiTexture gui) {
        this.GUIs.add(gui);
    }

    /**
     * Put the GUIs to process in the list of GUIs to process
     *
     * @param lGUIs array of GUIs to process
     */
    public void processGUIs(GuiTexture[] lGUIs) {
        this.GUIs.clear();
        if ((lGUIs != null) && (lGUIs.length > 0)) {
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < lGUIs.length; i++) {
                GuiTexture gui = lGUIs[i];
                processGUI(gui);
            }
        }
    }

    /**
     * Set the player that is going to use during the render
     *
     * @param player The player that is going to set
     */
    public void processPlayer(Player player) {
        this.player = player;
    }

    /**
     * Create the view matrix from the data that has about the camera
     *
     * @param camera the camera to which is to create the view matrix
     * @return The view matrix
     */
    private GLTransformation createViewMatrix(Camera camera) {
        GLTransformation matrix = new GLTransformation();
        matrix.glLoadIdentity();
        matrix.glRotate(camera.getPitch(), 1.0f, 0.0f, 0.0f);
        matrix.glRotate(camera.getYaw(), 0.0f, 1.0f, 0.0f);
        matrix.glTranslate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

        return matrix;
    }

    /**
     * Calls the methods to update the camera and updates the matrix that
     * describe the camera in the scene
     */
    private GLTransformation updateCamera() {

        //Update the camera taking in account the position of the player
        if (player != null) {
            camera.update(player, this.terrains.get(0));
        }

        // Matrix update
        return createViewMatrix(camera);
    }

    /**
     * Uses the GUIs to update the game pad of the game
     */
    private void updateGamePad() {
        if ((this.GUIs != null) && (!this.GUIs.isEmpty())) {
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < this.GUIs.size(); i++) {
                GuiTexture guiTexture = this.GUIs.get(i);
                if (guiTexture.getGamePadKey() != null) {
                    boolean keyPressed = guiTexture.containsLocation(GameEngineTouchListener.getGlX(), GameEngineTouchListener.getGlY());
                    GamePad.setKey(guiTexture.getGamePadKey(), keyPressed && GameEngineTouchListener.getIsPressed());
                }
            }
        }
    }

    /**
     * Call the method to update the player position
     */
    private void updatePlayer() {
        if (this.player != null) {
            this.player.move(this.timeToRender, this.terrains.get(0));
        }
    }

    /**
     * Render the entire scene (Called by each frame)
     *
     * @param sun Sun of the scene
     */
    public void render(Light sun) {
        this.prepare();
        this.updateGamePad();
        this.updatePlayer();
        GLTransformation viewMatrix = this.updateCamera();
        Vector3f skyColor = new Vector3f(SKY_R, SKY_G, SKY_B);
        this.entityRender.render(skyColor, sun, viewMatrix, entities);
        this.entityRender.render(skyColor, sun, viewMatrix, player);
        this.terrainRender.render(skyColor, sun, viewMatrix, terrains);
        this.skyBoxRender.render(viewMatrix, skyBox);
        this.guiRender.render(this.GUIs);
    }

    /**
     * Indicates that is going to start the rendering of a new frame Like that
     * the master render can compute how long tacks to render the frame
     */
    public void startFrameRender() {
        this.startDate = new Date();
    }

    /**
     * Indicates that is going to end the rendering of a frame Like that the
     * master render can compute how long tacks to render the frame
     */
    public void endFrameRender() {
        // Logs frames/s
        Date endDate = new Date();
        this.timeToRender = (endDate.getTime() - startDate.getTime()) / 1000.0f;
        //System.out.println((timeToRender) + " ms");
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        this.entityRender.cleanUp();
        this.terrainRender.cleanUp();
        this.skyBoxRender.cleanUp();
        this.guiRender.cleanUp();
        this.entities.clear();
        this.terrains.clear();
        this.GUIs.clear();
        this.GUIs = null;
        this.skyBox = null;
    }
}

