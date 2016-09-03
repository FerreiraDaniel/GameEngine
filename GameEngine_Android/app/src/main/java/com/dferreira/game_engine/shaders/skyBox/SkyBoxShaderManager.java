package com.dferreira.game_engine.shaders.skyBox;

import android.content.Context;

import com.dferreira.commons.GLTransformation;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.shaders.ShaderManager;


/**
 * Manager of the shader files that are going to be load to render the
 * 3D SkyBox
 */
public class SkyBoxShaderManager extends ShaderManager {


    /**
     * Position where to location attribute is going to be bind in the shader
     * program
     */
    public final static int LOCATION_ATTR_ID = 0;

    /**
     * Location of the projection matrix in the program shader
     */
    private int location_projectionMatrix;

    /**
     * Location of the view matrix in the program shader
     */
    private int location_viewMatrix;


    /**
     * Constructor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     * @param context Context where the game engine will be created
     */
    public SkyBoxShaderManager(Context context) {
        super(context, R.raw.sky_box_vertex_shader, R.raw.sky_box_fragment_shader);
    }

    /**
     * Bind the attributes to the program
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(LOCATION_ATTR_ID, "position");
    }

    /**
     * Get all the uniform location in the shader script
     */
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }


    /**
     * Load the projection matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadProjectionMatrix(GLTransformation matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix the matrix to be loaded
     */
    public void loadViewMatrix(GLTransformation matrix) {
        matrix.setTranslation(0.0f, 0.0f, 0.0f);
        super.loadMatrix(location_viewMatrix, matrix);
    }
}
