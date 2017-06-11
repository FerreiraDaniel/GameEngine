package com.dferreira.gameEngine.shaders.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.commons.generic_resources.TextEnum;
import com.dferreira.commons.models.Light;
import com.dferreira.gameEngine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 3D
 * Entities
 */
public class EntityShaderManager extends ShaderManager {

	/**
	 * Path of the vertex shader file
	 */
	//private static final String VERTEX_FILE = COMMON_PATH + "entities/entity_vertex_shader.glsl";

	/**
	 * Path of the fragment shader file
	 */
	//private static final String FRAGMENT_FILE = COMMON_PATH + "entities/entity_fragment_shader.glsl";

	/**
	 * All the uniform locations in the shader programs
	 */
	private int[] uniforms;

	/**
	 * Constructor of the game shader where the vertex and fragment shader of
	 * the game engine are loaded
	 * 
	 * @param renderAPI Reference to the API that is going to manage the program shader
	 */
	public EntityShaderManager(IShaderManagerAPI renderAPI) {
		super(TextEnum.entity_vertex_shader, TextEnum.entity_fragment_shader, renderAPI);
	}

	/**
	 * Bind the attributes of the program shader
	 * 
	 */
	@Override
	protected List<IEnum> getAttributes() {
		return new ArrayList<IEnum>(Arrays.asList(TEntityAttribute.values()));
	}

	/**
	 * Get all the uniform location in the shader script
	 */
	@Override
	protected void getAllUniformLocations() {
		int size = TEntityUniform.numOfEntityLocations.getValue();
		uniforms = new int[size];

		for (int i = 0; i < size; i++) {
			TEntityUniform location = TEntityUniform.values()[i];
			uniforms[i] = super.getUniformLocation(location);
		}
	}

	/**
	 * Load the color of the sky in order to simulate fog
	 * 
	 * @param skyColor
	 *            Color of the sky
	 */
	public void loadSkyColor(ColorRGBA skyColor) {
		super.loadColorRGBA(uniforms[TEntityUniform.skyColor.getValue()], skyColor);
	}

	/**
	 * Load the projection matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadProjectionMatrix(GLTransformation matrix) {
		super.loadMatrix(uniforms[TEntityUniform.projectionMatrix.getValue()], matrix);
	}

	/**
	 * Put passes the information of the lights to the Shader program
	 * 
	 * @param lights
	 *            the lights to load in the shader program
	 */
	public void loadLights(Light[] lights) {
		super.loadVector(uniforms[TEntityUniform.lightPosition.getValue()], lights[0].getPosition());
		super.loadColorRGB(uniforms[TEntityUniform.lightColor.getValue()], lights[0].getColor());
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
		super.loadFloat(uniforms[TEntityUniform.shineDamper.getValue()], damper);
		super.loadFloat(uniforms[TEntityUniform.reflectivity.getValue()], reflectivity);
	}

	/**
	 * Set in the shader if the normals should all of them point up
	 * 
	 * @param normalsPointingUp
	 *            Flag that indicates if all the normals of the entity are
	 *            pointing up or not
	 */
	public void loadNormalsPointingUp(boolean normalsPointingUp) {
		super.loadBoolean(uniforms[TEntityUniform.normalsPointingUp.getValue()], normalsPointingUp);
	}

	/**
	 * Load the view matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadViewMatrix(GLTransformation matrix) {
		super.loadMatrix(uniforms[TEntityUniform.viewMatrix.getValue()], matrix);
	}

	/**
	 * Load the transformation matrix
	 * 
	 * @param matrix
	 *            the matrix to be loaded
	 */
	public void loadTransformationMatrix(GLTransformation matrix) {
		super.loadMatrix(uniforms[TEntityUniform.transformationMatrix.getValue()], matrix);
	}

	/**
	 * Load the texture weight of the material
	 * 
	 * @param textureWeight
	 *            texture weight of the material
	 */
	public void loadTextureWeight(float textureWeight) {
		super.loadFloat(uniforms[TEntityUniform.textureWeight.getValue()], textureWeight);
	}

	/**
	 * Load the diffuse color of the material
	 * 
	 * @param diffuseColor
	 *            diffuse color of the material
	 */
	public void loadDiffuseColor(ColorRGBA diffuseColor) {
		super.loadColorRGBA(uniforms[TEntityUniform.diffuseColor.getValue()], diffuseColor);
	}

}
