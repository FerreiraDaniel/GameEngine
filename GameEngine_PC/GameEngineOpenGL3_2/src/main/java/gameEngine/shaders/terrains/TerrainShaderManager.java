package gameEngine.shaders.terrains;

import java.util.HashMap;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;

import gameEngine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 3D
 * Terrain
 */
public class TerrainShaderManager extends ShaderManager {

	/**
	 * Path of the vertex shader file
	 */
	private static final String VERTEX_FILE = COMMON_PATH + "terrains/terrain_vertex_shader.glsl";

	/**
	 * Path of the fragment shader file
	 */
	private static final String FRAGMENT_FILE = COMMON_PATH + "terrains/terrain_fragment_shader.glsl";

	/**
	 * Position where to location attribute is going to be bind in the shader
	 * program
	 */
	public final static int LOCATION_ATTR_ID = 0;

	/**
	 * Position where to texture attribute is going to be bind in the program
	 * shader
	 */
	public final static int TEX_COORDINATE_ATTR_ID = 1;

	/**
	 * Position where the normal vector are going to be bind in the program
	 * shader
	 */
	public final static int NORMAL_VECTOR_ATTR_ID = 2;

	/**
	 * Id of attribute the position where the light of scene is
	 */
	public final static int LIGHT_POSITION_ATTR_ID = 2;

	/**
	 * Id of attribute the color where the light of scene have
	 */
	public final static int LIGHT_COLOR_ATTR_ID = 3;

	/**
	 * Texture unit that was bind with glBindTexture GL_TEXTURE0
	 */
	private final static int TEXTURE_UNIT0 = 0;

	/**
	 * Texture unit that was bind with glBindTexture GL_TEXTURE1
	 */
	private final static int TEXTURE_UNIT1 = 1;

	/**
	 * Texture unit that was bind with glBindTexture GL_TEXTURE3
	 */
	private final static int TEXTURE_UNIT2 = 2;

	/**
	 * Texture unit that was bind with glBindTexture GL_TEXTURE3
	 */
	private final static int TEXTURE_UNIT3 = 3;

	/**
	 * Texture unit that was bind with glBindTexture GL_TEXTURE4
	 */
	private final static int TEXTURE_UNIT4 = 4;

	/**
	 * The background texture
	 */
	private int backgroundTexture;

	/**
	 * The mud texture
	 */
	private int mudTexture;

	/**
	 * The grass texture
	 */
	private int grassTexture;

	/**
	 * The path texture
	 */
	private int pathTexture;

	/**
	 * The blend map texture
	 */
	private int weightMapTexture;

	/**
	 * All the locations in the shader programs
	 */
	private int[] locations;

	/**
	 * Constructor of the game shader where the vertex and fragment shader of
	 * the game engine are loaded
	 */
	public TerrainShaderManager() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/**
	 * Bind the attributes to the program
	 * 
	 */
	@Override
	protected HashMap<Integer, String> getAttributes() {
		HashMap<Integer, String> attributes = new HashMap<>();
		attributes.put(LOCATION_ATTR_ID, "position");
		attributes.put(TEX_COORDINATE_ATTR_ID, "textureCoords");
		attributes.put(NORMAL_VECTOR_ATTR_ID, "normal");

		return attributes;
	}

	/**
	 * Get all the uniform location in the shader script
	 */
	@Override
	protected void getAllUniformLocations() {
		int size = TTerrainLocation.numOfEntityLocations.ordinal();
		locations = new int[size];

		for (int i = 0; i < size; i++) {
			TTerrainLocation locationKey = TTerrainLocation.values()[i];
			locations[i] = super.getUniformLocation(locationKey.toString());
		}
	}

	/**
	 * Associate the shader variables with textures that were defined in the
	 * bind of textures
	 */
	public void connectTextureUnits() {
		super.loadInt(backgroundTexture, TEXTURE_UNIT0);
		super.loadInt(mudTexture, TEXTURE_UNIT1);
		super.loadInt(grassTexture, TEXTURE_UNIT2);
		super.loadInt(pathTexture, TEXTURE_UNIT3);
		super.loadInt(weightMapTexture, TEXTURE_UNIT4);
	}

	
	/**
	 * Load the color of the sky in order to simulate fog
	 * 
	 * @param skyColor
	 * 			Color of the sky
	 */
	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(locations[TTerrainLocation.skyColor.ordinal()], skyColor);
	}

	/**
	 * Load the projection matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadProjectionMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainLocation.projectionMatrix.ordinal()], matrix);
	}

	/**
	 * Put passes the information of the light to the Shader program
	 * 
	 * @param light
	 *            the light to load in the shader program
	 */
	public void loadLight(Light light) {
		super.loadVector(locations[TTerrainLocation.lightPosition.ordinal()], light.getPosition());
		super.loadVector(locations[TTerrainLocation.lightColor.ordinal()], light.getColor());
	}

	/**
	 * Load the values of the specular light in the fragment shader
	 * 
	 * @param damper
	 *            The damper of the specular lighting
	 * @param reflectivity
	 *            The reflectivity of the material
	 */
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(locations[TTerrainLocation.shineDamper.ordinal()], damper);
		super.loadFloat(locations[TTerrainLocation.reflectivity.ordinal()], reflectivity);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadViewMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainLocation.viewMatrix.ordinal()], matrix);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadTransformationMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainLocation.transformationMatrix.ordinal()], matrix);
	}

}