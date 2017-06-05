package com.dferreira.gameEngine.models;

import com.dferreira.commons.gl_render.GLRawModel;

/**
 * Represents a box with sky textures
 */
public class SkyBox {

	/**
	 * RawModel of the skyBox
	 */
	private final GLRawModel model;

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
	public SkyBox(int textureId, GLRawModel model) {
		super();
		this.textureId = textureId;
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public GLRawModel getModel() {
		return model;
	}

	/**
	 * @return the texture identifier
	 */
	public int getTextureId() {
		return textureId;
	}

}
