package com.dferreira.gameEngine.models.complexEntities;

import com.dferreira.commons.ColorRGBA;

/**
 * A material can be composed by several components like: (Emissive lighting,
 * Ambient lighting, Diffuse, Specular, )
 */

public class LightingComponent {

	/**
	 * The identifier of the texture
	 */
	private Integer textureId;

	/**
	 * The name of the file associated with texture of component (If any) (Note:
	 * this is important to separate the GL logic from the model)
	 */
	private String filename;

	/**
	 * The weight of the texture for the end color component
	 */
	private float textureWeight;

	/**
	 * Constant color of the component (If any)
	 */
	private ColorRGBA color;

	/**
	 * @param textureId
	 *            The identifier of the texture
	 */
	public void setTextureId(Integer textureId) {
		this.textureId = textureId;
	}

	/**
	 * @return the identifier of the texture
	 */
	public Integer getTextureId() {
		return textureId;
	}

	/**
	 * @return The file name of the texture of the component
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Set the file name of the file with texture of the component
	 *
	 * @param filename
	 *            The file name to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @param textureWeight
	 *            the weight of texture to set
	 */
	public void setTextureWeight(float textureWeight) {
		this.textureWeight = textureWeight;
	}

	/**
	 * @return the weight of component to the final diffuse
	 */
	public float getTextureWeight() {
		return textureWeight;
	}

	/**
	 * +
	 *
	 * @return The color of the component
	 */
	public ColorRGBA getColor() {
		return color;
	}

	/**
	 * Set the color to the component
	 *
	 * @param color
	 *            The color to set in the component
	 */
	public void setColor(ColorRGBA color) {
		this.color = color;
	}

}
