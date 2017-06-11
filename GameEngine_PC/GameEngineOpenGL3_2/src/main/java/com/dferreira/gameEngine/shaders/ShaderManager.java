package com.dferreira.gameEngine.shaders;

import java.util.List;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.commons.generic_render.ShaderProgram;
import com.dferreira.commons.generic_resources.TextEnum;
import com.dferreira.commons.utils.Utils;

/**
 * Generic shader manager with methods to load vertex shader and fragment shader
 * files as well as useful methods to bind arguments
 */
public abstract class ShaderManager {

	/**
	 * Directory where the shader are in the project
	 */
	//protected static final String COMMON_PATH = "src/main/java/com/dferreira/gameEngine/shaders/";

	/**
	 * Reference to the program shader used 
	 */
	private ShaderProgram shaderProgram;

    /**
     * API responsible for managing the Shader
     */
    private final IShaderManagerAPI shaderManagerAPI;
	
    /**
     * Constructor of the program shader manager
     *
     * @param vertexFile       Identifier of the file with vertex description
     * @param fragmentFile     Identifier of the file with fragment description
     * @param shaderManagerAPI Reference to the API that is going to manage the program shader
     */
	protected ShaderManager(TextEnum vertexFile, TextEnum fragmentFile,  IShaderManagerAPI shaderManagerAPI) {

		this.shaderManagerAPI = shaderManagerAPI;
		this.shaderProgram = shaderManagerAPI.loadProgram(vertexFile, fragmentFile);

		if (this.shaderProgram == null) {
			System.err.print("Was impossible compile the program shader");
			return;
		}
		bindAttributes();
		boolean linked = shaderManagerAPI.linkProgram(shaderProgram);
		if (linked) {
			getAllUniformLocations();
		}
	}

	/**
	 * Bind the attributes of the shader
	 */
	private void bindAttributes() {
        List<IEnum> attributes = getAttributes();
        if (!Utils.isEmpty(attributes)) {
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
		this.shaderManagerAPI.glBindAttributeLocation(shaderProgram, attributeIndex, variableName);
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
		int location = this.shaderManagerAPI.getUniformLocation(shaderProgram, uniformName);
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
		this.shaderManagerAPI.loadInt(location, value);
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
		this.shaderManagerAPI.loadFloat(location, value);
	}


	/**
	 * Load a 3D vector to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param vector
	 *            The vector to load
	 */
	protected void loadVector(int location, Vector3f vector) {
		this.shaderManagerAPI.loadVector(location, vector);
	}

	/**
	 * Load a color RGB to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param color
	 *            The color to load
	 */
	protected void loadColorRGB(int location, ColorRGB color) {
		this.shaderManagerAPI.loadColorRGB(location, color);
	}

	/**
	 * Load a color RGBA to be used in the shader script
	 * 
	 * @param location
	 *            location of the shader variable in the script
	 * @param color
	 *            The color to load
	 */
	protected void loadColorRGBA(int location, ColorRGBA color) {
		this.shaderManagerAPI.loadColorRGBA(location, color);
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
		this.shaderManagerAPI.loadBoolean(location, value);
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
		 this.shaderManagerAPI.loadMatrix(location, matrix);
	}

	/**
	 * Indicates that should start to use a certain program shader
	 */
	public void start() {
		 this.shaderManagerAPI.start(shaderProgram);
	}

	/**
	 * Indicate that should not use a certain program no more
	 */
	public void stop() {
		this.shaderManagerAPI.stop();
	}

	/**
	 * A bit of memory management
	 */
	public void dispose() {
		this.shaderManagerAPI.deleteProgram(shaderProgram);
	}
}
