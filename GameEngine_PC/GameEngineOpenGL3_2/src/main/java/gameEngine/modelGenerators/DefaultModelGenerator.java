package gameEngine.modelGenerators;

/**
 * Define the default properties that the element should obey
 */
public class DefaultModelGenerator {

	/**
	 * The name of the .obj that represents the model
	 */
	private String objectName;

	/**
	 * The name of the .png that represents the texture of the model
	 */
	private String textureName;

	/**
	 * The scale of the model
	 */
	private float scale;

	/**
	 * Indicates if the model has transparency or not
	 */
	private boolean hasTransparency;

	/**
	 * Indicate that all our normals of the object are going to point up (in the
	 * same direction
	 */
	private boolean normalsPointingUp;
	
	/**
	 * Atlas factor if bigger that one means that the object as multiple textures in the same image
	 */
	private int atlasFactor;

	/**
	 * @return the objectName that represents the model
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @param objectName
	 *            the objectName to set that represents the model
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @return the textureName
	 */
	public String getTextureName() {
		return textureName;
	}

	/**
	 * @param textureName
	 *            the textureName to set
	 */
	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * @return the hasTransparency
	 */
	public boolean getHasTransparency() {
		return hasTransparency;
	}

	/**
	 * @param hasTransparency
	 *            the hasTransparency to set
	 */
	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	/**
	 * @return the normalsPointingUp
	 */
	public boolean getNormalsPointingUp() {
		return normalsPointingUp;
	}

	/**
	 * @param normalsPointingUp
	 *            the normalsPointingUp to set
	 */
	public void setNormalsPointingUp(boolean normalsPointingUp) {
		this.normalsPointingUp = normalsPointingUp;
	}

	/**
	 * @return the atlasFactor (Bigger that one means that the object as multiple textures in the same image)
	 */
	public int getAtlasFactor() {
		return atlasFactor;
	}

	/**
	 * Bigger that one means that the object as multiple textures in the same image
	 * @param atlasFactor the atlasFactor to set
	 */
	public void setAtlasFactor(int atlasFactor) {
		this.atlasFactor = atlasFactor;
	}

	
}
