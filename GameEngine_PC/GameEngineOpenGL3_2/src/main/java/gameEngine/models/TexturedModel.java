package gameEngine.models;

import gameEngine.textures.ModelTexture;

/**
 * Wrapper that besides of have the raw model also has the texture to put in the
 * model
 */
public class TexturedModel {
	private static long newId = 1;

	/**
	 * Identifier of the textured model
	 */
	private long id;

	/**
	 * Raw model of the entity
	 */
	private RawModel rawModel;

	/**
	 * Reference to the texture of the entity
	 */
	private ModelTexture texture;

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
	 * Constructor of the textured model
	 * 
	 * @param rawModel
	 *            Raw model of the entity
	 * @param texture
	 *            Reference to the texture of the entity
	 * @param hasTransparency
	 *            Indicates if the model has transparency or not not
	 * @param normalsPointingUp
	 *            Indicates if the normals of the model should point up
	 */
	public TexturedModel(RawModel rawModel, ModelTexture texture, boolean hasTransparency, boolean normalsPointingUp) {
		super();
		this.rawModel = rawModel;
		this.texture = texture;
		this.id = newId++;
		this.hasTransparency = hasTransparency;
		this.normalsPointingUp = normalsPointingUp;
	}

	/**
	 * Constructor of the textured model
	 * 
	 * @param rawModel
	 *            Raw model of the entity
	 * @param texture
	 *            Reference to the texture of the entity
	 */
	public TexturedModel(RawModel rawModel, ModelTexture texture) {
		this(rawModel, texture, false, false);
	}

	/**
	 * @return the raw model of the entity
	 */
	public RawModel getRawModel() {
		return rawModel;
	}

	/**
	 * @return the description of the texture of the entity
	 */
	public ModelTexture getTexture() {
		return texture;
	}

	/**
	 * @return the identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * Returns if the model has any transparency or not
	 */
	public boolean hasTransparency() {
		return this.hasTransparency;
	}

	/**
	 * @return if the normals of the model should point up
	 */
	public boolean isNormalsPointingUp() {
		return normalsPointingUp;
	}
}
