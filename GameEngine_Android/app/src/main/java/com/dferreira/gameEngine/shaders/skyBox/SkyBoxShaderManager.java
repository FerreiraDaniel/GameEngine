package com.dferreira.gameEngine.shaders.skyBox;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.gameEngine.R;
import com.dferreira.gameEngine.shaders.ShaderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Manager of the shader files that are going to be load to render the
 * 3D SkyBox
 */
public class SkyBoxShaderManager extends ShaderManager {

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
    public SkyBoxShaderManager(IShaderManagerAPI renderAPI) {
        super(R.raw.sky_box_vertex_shader, R.raw.sky_box_fragment_shader, renderAPI);
    }


    /**
     * Bind the attributes of the program
     */
    @Override
    protected List<IEnum> getAttributes() {
        return new ArrayList<IEnum>(Arrays.asList(TSkyBoxAttribute.values()));

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
     * @param matrix the matrix to be loaded
     */
    public void loadProjectionMatrix(GLTransformation matrix) {
        super.loadMatrix(uniforms[TSkyBoxUniform.projectionMatrix.getValue()], matrix);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadViewMatrix(GLTransformation matrix) {
        matrix.setTranslation(0.0f, 0.0f, 0.0f);
        super.loadMatrix(uniforms[TSkyBoxUniform.viewMatrix.getValue()], matrix);
    }
}
