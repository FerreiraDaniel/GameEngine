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
	 * Constructor of the texture model
	 * 
	 * @param id Identifier of the texture id
	 */
	public ModelTexture(int id) {
		this.textureId = id;
	}

	/**
	 * @return the identifier of the texture
	 */
	public int getTextureId() {
		return textureId;
	}
	
	
}
