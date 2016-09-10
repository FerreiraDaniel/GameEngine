package gameEngine.shaders.terrains;

import java.util.ArrayList;
import java.util.List;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;

import gameEngine.shaders.ShaderManager;
import gameEngine.shaders.entities.TEntityAttribute;

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
	protected List<IEnum> getAttributes() {
		List<IEnum> attributes = new ArrayList<>();
		attributes.add(TEntityAttribute.position);
		attributes.add(TEntityAttribute.textureCoords);
		attributes.add(TEntityAttribute.normal);

		return attributes;
	}

	/**
	 * Get all the uniform location in the shader script
	 */
	@Override
	protected void getAllUniformLocations() {
		int size = TTerrainUniform.numOfTerrainLocations.ordinal();
		locations = new int[size];

		for (int i = 0; i < size; i++) {
			TTerrainUniform locationKey = TTerrainUniform.values()[i];
			locations[i] = super.getUniformLocation(locationKey);
		}
	}

	/**
	 * Associate the shader variables with textures that were defined in the
	 * bind of textures
	 */
	public void connectTextureUnits() {
		super.loadInt(locations[TTerrainUniform.backgroundTexture.ordinal()], TEXTURE_UNIT0);
		super.loadInt(locations[TTerrainUniform.mudTexture.ordinal()], TEXTURE_UNIT1);
		super.loadInt(locations[TTerrainUniform.grassTexture.ordinal()], TEXTURE_UNIT2);
		super.loadInt(locations[TTerrainUniform.pathTexture.ordinal()], TEXTURE_UNIT3);
		super.loadInt(locations[TTerrainUniform.weightMapTexture.ordinal()], TEXTURE_UNIT4);
	}

	/**
	 * Load the color of the sky in order to simulate fog
	 * 
	 * @param skyColor
	 *            Color of the sky
	 */
	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(locations[TTerrainUniform.skyColor.ordinal()], skyColor);
	}

	/**
	 * Load the projection matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadProjectionMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainUniform.projectionMatrix.ordinal()], matrix);
	}

	/**
	 * Put passes the information of the light to the Shader program
	 * 
	 * @param light
	 *            the light to load in the shader program
	 */
	public void loadLight(Light light) {
		super.loadVector(locations[TTerrainUniform.lightPosition.ordinal()], light.getPosition());
		super.loadVector(locations[TTerrainUniform.lightColor.ordinal()], light.getColor());
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
		super.loadFloat(locations[TTerrainUniform.shineDamper.ordinal()], damper);
		super.loadFloat(locations[TTerrainUniform.reflectivity.ordinal()], reflectivity);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadViewMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainUniform.viewMatrix.ordinal()], matrix);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadTransformationMatrix(GLTransformation matrix) {
		super.loadMatrix(locations[TTerrainUniform.transformationMatrix.ordinal()], matrix);
	}

}
