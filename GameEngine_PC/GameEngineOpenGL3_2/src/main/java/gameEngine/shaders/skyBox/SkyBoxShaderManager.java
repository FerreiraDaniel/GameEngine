package gameEngine.shaders.skyBox;

import java.util.ArrayList;
import java.util.List;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;

import gameEngine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 3D
 * Terrain
 */
public class SkyBoxShaderManager extends ShaderManager {

	/**
	 * Path of the vertex shader file
	 */
	private static final String VERTEX_FILE = COMMON_PATH + "skyBox/sky_box_vertex_shader.glsl";

	/**
	 * Path of the fragment shader file
	 */
	private static final String FRAGMENT_FILE = COMMON_PATH + "skyBox/sky_box_fragment_shader.glsl";

	/**
	 * Position where to location attribute is going to be bind in the shader
	 * program
	 */
	public final static int LOCATION_ATTR_ID = 0;

	/**
	 * All the uniform locations in the shader programs
	 */
	private int[] uniforms;

	/**
	 * Constructor of the game shader where the vertex and fragment shader of
	 * the game engine are loaded
	 */
	public SkyBoxShaderManager() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/**
	 * Bind the attributes to the program
	 * 
	 */
	@Override
	protected List<IEnum> getAttributes() {
		List<IEnum> attributes = new ArrayList<>();
		attributes.add(TSkyBoxAttribute.position);

		return attributes;
	}

	/**
	 * Get all the uniform location in the shader script
	 */
	@Override
	protected void getAllUniformLocations() {
		int size = TSkyBoxUniform.numOfSkyBoxLocations.getValue();
		this.uniforms = new int[size];

		for (int i = 0; i < size; i++) {
			TSkyBoxUniform uniformKey = TSkyBoxUniform.values()[i];
			uniforms[i] = super.getUniformLocation(uniformKey);
		}
	}

	/**
	 * Load the projection matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadProjectionMatrix(GLTransformation matrix) {
		super.loadMatrix(uniforms[TSkyBoxUniform.projectionMatrix.getValue()], matrix);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadViewMatrix(GLTransformation matrix) {
		matrix.setTranslation(0.0f, 0.0f, 0.0f);
		super.loadMatrix(uniforms[TSkyBoxUniform.viewMatrix.getValue()], matrix);
	}
}
