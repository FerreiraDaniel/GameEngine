package gameEngine.models;

import com.dferreira.commons.Vector2f;

/**
 * Represents one texture to be show in the UI of the game (In 2D)
 */
public class GuiTexture {

	/**
	 * Raw model of the entity
	 */
	private final RawModel rawModel;

	/**
	 * Identifier of the texture
	 */
	private final int textureId;

	/**
	 * Position of the texture between (0,0) and (1.0,1.0)
	 */
	private final Vector2f position;

	/**
	 * Scale factor of the texture
	 */
	private final Vector2f scale;

	/**
	 * @param textureId
	 *            Identifier of the texture
	 * @param position
	 *            Position of the texture between (0,0) and (1.0,1.0)
	 * @param scale
	 *            Scale factor of the texture
	 */
	public GuiTexture(RawModel rawModel, int textureId, Vector2f position, Vector2f scale) {
		super();
		this.rawModel = rawModel;
		this.textureId = textureId;
		this.position = position;
		this.scale = scale;
	}

	/**
	 * @return the raw model of the entity
	 */
	public RawModel getRawModel() {
		return rawModel;
	}

	/**
	 * @return Identifier of the texture
	 */
	public int getTextureId() {
		return textureId;
	}

	/**
	 * @return Position of the texture between (0,0) and (1.0,1.0)
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @return Scale factor of the texture
	 */
	public Vector2f getScale() {
		return scale;
	}
}
