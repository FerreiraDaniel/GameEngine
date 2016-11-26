package gameEngine.renderEngine;

import gameEngine.shaders.entities.EntityShaderManager;
import gameEngine.shaders.guis.GuiShaderManager;
import gameEngine.shaders.skyBox.SkyBoxShaderManager;
import gameEngine.shaders.terrains.TerrainShaderManager;
import gameEngine.models.TexturedModel;
import gameEngine.models.ThirdPersonCamera;
import gameEngine.models.Camera;
import gameEngine.models.Entity;
import gameEngine.models.GuiTexture;
import gameEngine.models.Player;
import gameEngine.models.SkyBox;
import gameEngine.models.Terrain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;

/**
 * Groups the entities in a hash map like this the same entity will be just put
 * in different positions
 */
public class MasterRender {

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
	private ThirdPersonCamera camera;

	/**
	 * Entities of the world that are going to be rendered
	 */
	private Map<TexturedModel, List<Entity>> entities;

	/**
	 * List of terrains of the world that are going to be render
	 */
	private List<Terrain> terrains;
	
	/**
	 * The player that is going to be show in the scene
	 */
	private Player player;

	/**
	 * List of GUIs to show the status of the user
	 */
	private List<GuiTexture> guis;
	
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
	 * Constructor of the master renderer
	 * 
	 */
	public MasterRender() {

		// Initializes the projection matrix
		GLTransformation projectionMatrix = createProjectionMatrix();

		// Initializes the entity render
		EntityShaderManager eShader = new EntityShaderManager();
		this.entityRender = new EntityRender(eShader, projectionMatrix);

		// Initializes the entities to be render
		this.entities = new HashMap<TexturedModel, List<Entity>>();

		// Initializes the terrain render
		TerrainShaderManager tShader = new TerrainShaderManager();
		this.terrainRender = new TerrainRender(tShader, projectionMatrix);

		// Initializes the sky box render
		SkyBoxShaderManager sbManager = new SkyBoxShaderManager();
		this.skyBoxRender = new SkyBoxRender(sbManager, projectionMatrix);

		GuiShaderManager gShader = new GuiShaderManager();
		this.guiRender = new GuiRender(gShader);
		
		// Initializes the terrains to render
		this.terrains = new ArrayList<Terrain>();

		// Initializes the GUIs to render
		this.guis = new ArrayList<GuiTexture>();
		
		// Initializes the camera
		this.camera = new ThirdPersonCamera();

	}

	/**
	 * Put one entity in the list of entities to render
	 * 
	 * @param entity
	 *            the entity to add to the render
	 */
	private void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch == null) {
			batch = new ArrayList<Entity>();
			entities.put(entityModel, batch);
		}
		batch.add(entity);
	}

	/**
	 * Put the entities to process in the hash map dedicated to process entities
	 * by group
	 * 
	 * @param lEntities
	 *            list of entities to get render in the next frame
	 */
	public void processEntities(Entity[] lEntities) {
		this.entities.clear();
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
	 * @param terrain
	 *            the terrain to render
	 */
	private void processTerrain(Terrain terrain) {
		this.terrains.add(terrain);
	}

	/**
	 * Put the terrains to process in the list of terrains to process
	 * 
	 * @param lTerrains
	 *            list of terrains to process
	 */
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
	 * Put a GUI in the list of GUIs to render
	 * 
	 * @param gui
	 *            the GUI to render
	 */
	private void processGUI(GuiTexture gui) {
		this.guis.add(gui);
	}
	
	/**
	 * Put the GUIs to process in the list of GUIs to process
	 * 
	 * @param lGuis
	 *            array of GUIs to process
	 */
	public void processGUIs(GuiTexture[] lGuis) {
		this.guis.clear();
		if ((lGuis != null) && (lGuis.length > 0)) {
			for (int i = 0; i < lGuis.length; i++) {
				GuiTexture gui = lGuis[i];
				processGUI(gui);
			}
		}
	}
	
	/**
	 * Set the sky box the use during the render
	 * 
	 * @param skyBox The sky box that is going to set
	 */
	public void processSkyBox(SkyBox skyBox) {
		this.skyBox = skyBox;
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
	 * Clean the data of the previous frame
	 */
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0.3f, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Create the view matrix from the data that has about the camera
	 * 
	 * @param camera
	 *            the camera to which is to create the view matrix
	 * 
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
		if(player != null) {
			camera.update(player, this.terrains.get(0));
		}
				
		// Matrix update
		GLTransformation viewMatrix = createViewMatrix(camera);
		return viewMatrix;
	}
	
	/**
	 * Call the method to update the player position
	 */
	private void updatePlayer() {
		if(this.player != null) {
			this.player.move(this.timeToRender, this.terrains.get(0));
		}
	}

	/**
	 * Render the entire scene (Called by each frame)
	 * 
	 * @param sun
	 *            Sun of the scene
	 */
	public void render(Light sun) {
		this.prepare();
		this.updatePlayer();
		GLTransformation viewMatrix = this.updateCamera();
		Vector3f skyColor = new Vector3f(SKY_R, SKY_G, SKY_B);
		this.entityRender.render(skyColor, sun, viewMatrix, entities, player);
		this.terrainRender.render(skyColor, sun, viewMatrix, terrains);
		this.skyBoxRender.render(viewMatrix, skyBox);
		this.guiRender.render(this.guis);
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
		this.entities.clear();
		this.terrains.clear();
		this.guis.clear();		
		this.entities = null;
		this.terrains = null;
		this.guis = null;
		this.skyBox = null;
	}
}
