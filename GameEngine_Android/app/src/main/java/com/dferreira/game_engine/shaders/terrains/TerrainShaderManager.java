package com.dferreira.game_engine.shaders.terrains;

import android.content.Context;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.shaders.ShaderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manager of the shader files that are going to be load to render the 3D
 */
public class TerrainShaderManager extends ShaderManager {

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
     * All the locations in the shader program
     */
    private int[] uniforms;

    /**
     * Constructor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     * @param context   Context where the game engine will be created
     */
    public TerrainShaderManager(Context context) {
        super(context, R.raw.terrain_vertex_shader, R.raw.terrain_fragment_shader);
    }

    /**
     * Bind the attributes of the program shader
     *
     */
    @Override
    protected List<IEnum> getAttributes() {
        return new ArrayList<IEnum>(Arrays.asList(TTerrainAttribute.values()));
    }


    /**
     * Associate the location variables with terrain textures in the shader
     */
    @Override
    protected void getAllUniformLocations() {
        int size = TTerrainUniform.numOfTerrainLocations.ordinal();
        uniforms = new int[size];

        for (int i = 0; i < size; i++) {
            TTerrainUniform locationKey = TTerrainUniform.values()[i];
            uniforms[i] = super.getUniformLocation(locationKey);
        }
    }

    /**
     * Associate the shader variables with textures that were defined in the
     * bind of textures
     */
    public void connectTextureUnits() {
        super.loadInt(uniforms[TTerrainUniform.backgroundTexture.ordinal()], TEXTURE_UNIT0);
        super.loadInt(uniforms[TTerrainUniform.mudTexture.ordinal()], TEXTURE_UNIT1);
        super.loadInt(uniforms[TTerrainUniform.grassTexture.ordinal()], TEXTURE_UNIT2);
        super.loadInt(uniforms[TTerrainUniform.pathTexture.ordinal()], TEXTURE_UNIT3);
        super.loadInt(uniforms[TTerrainUniform.weightMapTexture.ordinal()], TEXTURE_UNIT4);
    }

    /**
     * Load the color of the sky in order to simulate fog
     *
     * @param skyColor
     *            Color of the sky
     */
    public void loadSkyColor(Vector3f skyColor) {
        super.loadVector(uniforms[TTerrainUniform.skyColor.ordinal()], skyColor);
    }

    /**
     * Load the projection matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadProjectionMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TTerrainUniform.projectionMatrix.ordinal()], matrix);
    }

    /**
     * Put passes the information of the light to the Shader program
     *
     * @param light
     *            the light to load in the shader program
     */
    public void loadLight(Light light) {
        super.loadVector(uniforms[TTerrainUniform.lightPosition.ordinal()], light.getPosition());
        super.loadVector(uniforms[TTerrainUniform.lightColor.ordinal()], light.getColor());
    }

    /**
     * Load the values of the specular light in the fragment shader
     *
     * @param damper
     *            The damper of the specular lighting
     * @param reflectivity
     *            The reflectivity of the material
     */
    @SuppressWarnings("SameParameterValue")
    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(uniforms[TTerrainUniform.shineDamper.ordinal()], damper);
        super.loadFloat(uniforms[TTerrainUniform.reflectivity.ordinal()], reflectivity);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadViewMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TTerrainUniform.viewMatrix.ordinal()], matrix);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadTransformationMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TTerrainUniform.transformationMatrix.ordinal()], matrix);
    }

}

