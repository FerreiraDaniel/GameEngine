package com.dferreira.gameEngine.shaders.guis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.gameEngine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 2D GUIs
 */
public class GuiShaderManager extends ShaderManager {

	/**
	 * Path of the vertex shader file
	 */
	private static final String VERTEX_FILE = COMMON_PATH + "guis/gui_vertex_shader.glsl";

	/**
	 * Path of the fragment shader file
	 */
	private static final String FRAGMENT_FILE = COMMON_PATH + "guis/gui_fragment_shader.glsl";

	/**
	 * All the uniform locations in the shader programs
	 */
	private int[] uniforms;

	/**
	 * Constructor of the game shader where the vertex and fragment shader of
	 * the game engine are loaded
	 * 
	 * @param renderAPI
	 *            Reference to the API that is going to manage the program
	 *            shader
	 */
	public GuiShaderManager(IShaderManagerAPI renderAPI) {
		super(VERTEX_FILE, FRAGMENT_FILE, renderAPI);
	}

	/**
	 * Bind the attributes of the program shader
	 * 
	 */
	@Override
	protected List<IEnum> getAttributes() {
		return new ArrayList<IEnum>(Arrays.asList(TGuiAttribute.values()));
	}

	/**
	 * Get all the uniform location in the shader script
	 */
	@Override
	protected void getAllUniformLocations() {
		int size = TGuiUniform.numOfGUILocations.getValue();
		uniforms = new int[size];

		for (int i = 0; i < size; i++) {
			TGuiUniform location = TGuiUniform.values()[i];
			uniforms[i] = super.getUniformLocation(location);
		}
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadTransformationMatrix(GLTransformation matrix) {
		super.loadMatrix(uniforms[TGuiUniform.transformationMatrix.getValue()], matrix);
	}

}
