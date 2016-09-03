package com.dferreira.game_engine.shaders.terrains;

import android.content.Context;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 3D
 */
public class TerrainShaderManager extends ShaderManager {

    /**
     * Position where to location attribute is going to be bind in the shader
     * program
     */
    public final static int LOCATION_ATTR_ID = 0;

    /**
     * Position where to texture attribute is going to be bind in the program
     * shader
     */
    public final static int TEX_COORDINATE_ATTR_ID = 1;

    /**
     * Position where the normal vector are going to be bind in the program
     * shader
     */
    public final static int NORMAL_VECTOR_ATTR_ID = 2;

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
     * Location of the transformation matrix in the program shader
     */
    private int location_TransformationMatrix;

    /**
     * Location of the view matrix in the program shader
     */
    private int location_viewMatrix;

    /**
     * Location of the projection matrix in the program shader
     */
    private int location_projectionMatrix;

    /**
     * Location of the light's position in the program shader
     */
    private int location_lightPosition;

    /**
     * Location of the camera's position in the program shader
     */
    private int location_cameraPosition;

    /**
     * Location of the light's color in the program shader
     */
    private int location_lightColor;

    /**
     * Location of the shineDamper uniform in the fragment shader
     */
    private int location_shineDamper;

    /**
     * Location of the reflectivity uniform in the fragment shader
     */
    private int location_reflectivity;

    /**
     * Location of the color of the sky in the fragment shader
     */
    private int location_skyColor;

    /**
     * The background texture
     */
    private int location_backgroundTexture;

    /**
     * The mud texture
     */
    private int location_mudTexture;

    /**
     * The grass texture
     */
    private int location_grassTexture;

    /**
     * The path texture
     */
    private int location_pathTexture;

    /**
     * The blend map texture
     */
    private int location_weightMapTexture;


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
     * Bind the attributes to the program
     */
    @Override
    protected void bindAttributes() {
        super.bindAttribute(LOCATION_ATTR_ID, "position");
        super.bindAttribute(TEX_COORDINATE_ATTR_ID, "textureCoords");
        super.bindAttribute(NORMAL_VECTOR_ATTR_ID, "normal");
    }

    /**
     * Associate the location variables with terrain textures in the shader
     */
    private void getTerrainTexturesLocations() {
		/* The background texture */
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");

		/* The mud texture */
        location_mudTexture = super.getUniformLocation("mudTexture");

		/* The grass texture */
        location_grassTexture = super.getUniformLocation("grassTexture");

		/* The path texture */
        location_pathTexture = super.getUniformLocation("pathTexture");

		/* The weight map */
        location_weightMapTexture = super.getUniformLocation("weightMapTexture");
    }

    /**
     * Get all the uniform location in the shader script
     */
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_TransformationMatrix = super.getUniformLocation("transformationMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_cameraPosition = super.getUniformLocation("cameraPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        getTerrainTexturesLocations();
    }

    /**
     * Associate the shader variables with textures that were defined in the
     * bind of textures
     */
    public void connectTextureUnits() {
        super.loadInt(location_backgroundTexture, TEXTURE_UNIT0);
        super.loadInt(location_mudTexture, TEXTURE_UNIT1);
        super.loadInt(location_grassTexture, TEXTURE_UNIT2);
        super.loadInt(location_pathTexture, TEXTURE_UNIT3);
        super.loadInt(location_weightMapTexture, TEXTURE_UNIT4);
    }

    /**
     * Load the projection matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadProjectionMatrix(GLTransformation matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    /**
     * Put passes the information of the light to the
     * Shader program
     *
     * @param light the light to load in the shader program
     */
    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    /**
     * Load the values of the specular light in the fragment shader
     *
     * @param damper		The damper of the specular lighting
     * @param reflectivity	The reflectivity of the material
     */
    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity ,reflectivity);
    }

    /**
     * Load the position of the camera in the world in the program shader
     *
     * @param cameraPosition position of the camera
     */
    public void loadCameraPosition(Vector3f cameraPosition) {
        super.loadVector(location_cameraPosition, cameraPosition);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadViewMatrix(GLTransformation matrix) {
        super.loadMatrix(location_viewMatrix, matrix);
    }

    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public void loadTransformationMatrix(GLTransformation matrix) {
        super.loadMatrix(location_TransformationMatrix, matrix);
    }

}

