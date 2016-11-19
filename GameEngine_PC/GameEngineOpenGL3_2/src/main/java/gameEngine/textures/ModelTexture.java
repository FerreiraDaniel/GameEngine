package gameEngine.textures;

/**
 * Has all the required parameters to render one textured entity
 */
public class ModelTexture {

	/**
	 * The identifier of the texture
	 */
	private int textureId;

	/**
	 * How damped the shine is
	 */
	private float shineDamper;

	/**
	 * How reflective the model is
	 */
	private float reflectivity;
	
	/*
	 * The atlas factor the end object will have
	 * atlasFactor * atlasFactor = number of textures
	 */
	private int atlasFactor;

	/**
	 * Constructor of the texture model
	 * 
	 * @param id
	 *            Identifier of the texture id
	 */
	public ModelTexture(int id) {
		this.textureId = id;
		this.shineDamper = 1.0f;
		this.reflectivity = 0.0f;
		this.atlasFactor = 1;	//By default will be one meaning that the object only have one texture
	}

	/**
	 * @return the identifier of the texture
	 */
	public int getTextureId() {
		return textureId;
	}

	/**
	 * @return the shineDamper
	 */
	public float getShineDamper() {
		return shineDamper;
	}

	/**
	 * @param shineDamper
	 *            the shineDamper to set
	 */
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	/**
	 * @return the reflectivity
	 */
	public float getReflectivity() {
		return reflectivity;
	}

	/**
	 * @param reflectivity
	 *            the reflectivity to set
	 */
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	/**
	 * @return the atlasFactor
	 * The end object will have
	 * atlasFactor * atlasFactor = number of textures
	 */
	public int getAtlasFactor() {
		return atlasFactor;
	}

	/**
	 * @param atlasFactor the atlasFactor to set
	 */
	public void setAtlasFactor(int atlasFactor) {
		this.atlasFactor = atlasFactor;
	}
	
	
}
