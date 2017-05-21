package com.dferreira.game_engine.shaders.entities;

import android.content.Context;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.shaders.ShaderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager of the shader files that are going to be load to render the 3D
 */
public class EntityShaderManager extends ShaderManager {

    /**
     * All the uniform locations in the shader programs
     */
    private int[] uniforms;


    /**
     * Constructor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     * @param context   Context where the game engine will be created
     * @param renderAPI Reference to the API that is going to manage the program shader
     */
    public EntityShaderManager(Context context, IShaderManagerAPI renderAPI) {
        super(context, R.raw.entity_vertex_shader, R.raw.entity_fragment_shader, renderAPI);
    }

    /**
     * Bind the attributes of the program shader
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
     * @param skyColor Color of the sky
     */
    public void loadSkyColor(ColorRGBA skyColor) {
        super.loadColorRGBA(uniforms[TEntityUniform.skyColor.getValue()], skyColor);
    }

    /**
     * Load the projection matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadProjectionMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TEntityUniform.projectionMatrix.getValue()], matrix);
    }

    /**
     * Load the view matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadViewMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TEntityUniform.viewMatrix.getValue()], matrix);
    }


    /**
     * Put passes the information of the light to the Shader program
     *
     * @param light the light to load in the shader program
     */
    public void loadLight(Light light) {
        super.loadVector(uniforms[TEntityUniform.lightPosition.getValue()], light.getPosition());
        super.loadColorRGB(uniforms[TEntityUniform.lightColor.getValue()], light.getColor());
    }

    /**
     * Load the values of the specular light in the fragment shader
     *
     * @param damper       The damper of the specular lighting
     * @param reflectivity The reflectivity of the material
     */
    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(uniforms[TEntityUniform.shineDamper.getValue()], damper);
        super.loadFloat(uniforms[TEntityUniform.reflectivity.getValue()], reflectivity);
    }

    /**
     * Set in the shader if the normals should all of them point up
     *
     * @param normalsPointingUp Flag that indicates if all the normals of the entity are
     *                          pointing up or not
     */
    public void loadNormalsPointingUp(boolean normalsPointingUp) {
        super.loadBoolean(uniforms[TEntityUniform.normalsPointingUp.getValue()], normalsPointingUp);
    }


    /**
     * Load the transformation matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadTransformationMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TEntityUniform.transformationMatrix.getValue()], matrix);
    }


    /**
     * Load the texture weight of the material
     *
     * @param textureWeight texture weight of the material
     */
    public void loadTextureWeight(float textureWeight) {
        super.loadFloat(uniforms[TEntityUniform.textureWeight.getValue()], textureWeight);
    }

    /**
     * Load the diffuse color of the material
     *
     * @param diffuseColor diffuse color of the material
     */
    public void loadDiffuseColor(ColorRGBA diffuseColor) {
        super.loadColorRGBA(uniforms[TEntityUniform.diffuseColor.getValue()], diffuseColor);
    }
}
