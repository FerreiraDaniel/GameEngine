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
	
	/**
	 * Constructor of the texture model
	 * 
	 * @param id Identifier of the texture id
	 */
	public ModelTexture(int id) {
		this.textureId = id;
		this.shineDamper = 1.0f;
		this.reflectivity = 0.0f;
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
	 * @param shineDamper the shineDamper to set
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
	 * @param reflectivity the reflectivity to set
	 */
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}	
}
