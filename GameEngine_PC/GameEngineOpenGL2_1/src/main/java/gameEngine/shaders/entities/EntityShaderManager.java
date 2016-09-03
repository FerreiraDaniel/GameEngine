package gameEngine.shaders.entities;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;

import gameEngine.shaders.ShaderManager;

/**
 * Manager of the shader files that are going to be load to render the 3D
 */
public class EntityShaderManager extends ShaderManager {

	/**
	 * Path of the vertex shader file
	 */
	private static final String VERTEX_FILE = COMMON_PATH + "entities/entity_vertex_shader.glsl";

	/**
	 * Path of the fragment shader file
	 */
	private static final String FRAGMENT_FILE = COMMON_PATH + "entities/entity_fragment_shader.glsl";

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
	 * Location of the variable that indicates the normals of the 
	 * object should point up
	 */
	private int location_normalsPointingUp;
	
	/**
	 * Constructor of the game shader where the vertex and fragment shader of
	 * the game engine are loaded
	 */
	public EntityShaderManager() {
		super(VERTEX_FILE, FRAGMENT_FILE);
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
		location_normalsPointingUp = super.getUniformLocation("normalsPointingUp");
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
	 * Set in the shader if the normals should all of them point up
	 * @param normalsPointingUp
	 */
	public void loadNormalsPointingUp(boolean normalsPointingUp) {
		super.loadBoolean(location_normalsPointingUp, normalsPointingUp);
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
