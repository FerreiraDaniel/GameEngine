package gameEngine.shaders;

import java.util.List;
import org.lwjgl.opengl.GL20;

import com.dferreira.commons.GLSLUtils;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.ShaderProgram;
import com.dferreira.commons.Vector3f;

/**
 * Generic shader manager with methods to load vertex shader and fragment shader
 * files as well as useful methods to bind arguments
 */
public abstract class ShaderManager {

	/**
	 * Directory where the shader are in the project
	 */
	protected static final String COMMON_PATH = "src/main/java/GameEngine/Shaders/";

	private ShaderProgram shaderProgram;

	/**
	 * Constructor of the program shader manager
	 * 
	 * @param vertexFile
	 *            The file with vertex description
	 * @param fragmentFile
	 *            The file with fragment description
	 */
	public ShaderManager(String vertexFile, String fragmentFile) {

		String vertexShaderSrc = LoadUtils.readTextFromRawResource(vertexFile);
		String fragShaderSrc = LoadUtils.readTextFromRawResource(fragmentFile);
		this.shaderProgram = GLSLUtils.loadProgram(vertexShaderSrc, fragShaderSrc);

		if (this.shaderProgram == null) {
			return;
		}
		bindAttributes();
		boolean linked = GLSLUtils.linkProgram(shaderProgram);
		if (linked) {
			getAllUniformLocations();
		}
	}

	/**
	 * Bind the attributes of the shader
	 */
	private void bindAttributes() {
		List<IEnum> attributes = getAttributes();
		if ((attributes != null) && (!attributes.isEmpty())) {
			for (IEnum attribute : attributes) {
				bindAttribute(attribute.getValue(), attribute.toString());
			}
		}
	}

	/**
	 * Called to bind the attributes to the program shader
	 */
	protected abstract List<IEnum> getAttributes();

	/**
	 * Called to ensure that all the shader managers get their uniform locations
	 */
	protected abstract void getAllUniformLocations();

	/**
	 * Bind one attribute
	 * 
	 * @param attributeIndex
	 *            Index of the attribute to bind
	 * @param variableName
	 *            Name of the variable in the vertex shader
	 */
	private void bindAttribute(int attributeIndex, String variableName) {
		GL20.glBindAttribLocation(shaderProgram.getProgramId(), attributeIndex, variableName);
	}

	/**
	 * Get the position of one uniform variable in the program shader
	 * 
	 * @param uniformName
	 *            the name of the uniform variable as appears in the shader code
	 * 
	 * @return the position of the uniform variable in program shader
	 */
	protected int getUniformLocation(Enum<?> uniformName) {
		int location = GL20.glGetUniformLocation(shaderProgram.getProgramId(), uniformName.toString());
		if (location < 0) {
			System.err.println("Was not possible to load the location : " + uniformName);
		}
		return location;
	}

	/**
	 * Load a integer value to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param value
	 *            The value to load
	 */
	protected void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}

	/**
	 * Load a float value to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param value
	 *            value to load
	 */
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	/**
	 * Load a vector to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param vector
	 *            The vector to load
	 */
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}

	/**
	 * Load a boolean value to be used in the shader script
	 * 
	 * @param location
	 *            The location of the shader variable in the script
	 * @param value
	 *            value to load
	 */
	protected void loadBoolean(int location, boolean value) {
		float toLoad = value ? 1 : 0;
		GL20.glUniform1f(location, toLoad);
	}

	/**
	 * Load a matrix to be used in the shader script
	 * 
	 * @param location
	 *            The location of the shader variable in the script
	 * @param matrix
	 *            Matrix to load
	 */
	protected void loadMatrix(int location, GLTransformation matrix) {
		GL20.glUniformMatrix4(location, false, matrix.getAsFloatBuffer());
	}

	/**
	 * Indicates that should start to use a certain program shader
	 */
	public void start() {
		GL20.glUseProgram(shaderProgram.getProgramId());
	}

	/**
	 * Indicate that should not use a certain program no more
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}

	/**
	 * A bit of memory management
	 */
	public void cleanUp() {
		this.stop();
		int vertexShaderID = shaderProgram.getVertexShaderId();
		int fragmentShaderID = shaderProgram.getFragmentShaderId();
		int programId = shaderProgram.getProgramId();

		GL20.glDetachShader(programId, vertexShaderID);
		GL20.glDetachShader(programId, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programId);
	}
}
