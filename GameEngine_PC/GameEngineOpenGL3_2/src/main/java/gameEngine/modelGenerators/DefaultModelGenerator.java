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
}
