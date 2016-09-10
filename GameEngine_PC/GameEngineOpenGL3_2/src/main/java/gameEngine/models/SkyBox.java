package gameEngine.models;

/**
 * Represents a box with sky textures
 */
public class SkyBox {

	/**
	 * RawModel of the skyBox
	 */
	private final RawModel model;

	/**
	 * The identifier of the sky box cubic texture
	 */
	private final int textureId;

	/**
	 * The constructor of the skyBox
	 * 
	 * @param textureId
	 *            the Identifier of the texture of the sky
	 * @param model
	 *            The model of the sky box
	 */
	public SkyBox(int textureId, RawModel model) {
		super();
		this.textureId = textureId;
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public RawModel getModel() {
		return model;
	}

	/**
	 * @return the texture identifier
	 */
	public int getTextureId() {
		return textureId;
	}

}
