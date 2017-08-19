package com.dferreira.commons.generic_resources;

import java.util.List;

import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IShape;

/**
 * Contains all the methods required to get the resources of the game
 */
public interface IResourceProvider {

	/**
	 * @param modelEnum
	 *            The model to load
	 * @return The list of shapes that make part of the model to load
	 */
	List<IShape> getResource(ModelEnum modelEnum);

	/**
	 * @param textureEnum
	 *            The texture to load
	 * @return The information of the texture to load
	 */
	TextureData getResource(TextureEnum textureEnum);

	/**
	 * @param textureFileName
	 *            Name of the file where the texture it is
	 * @return The texture data of the file passed
	 */
	TextureData getTexture(String textureFileName);

	/**
	 * @param textEnum
	 *            The text to load
	 * @return The text loaded
	 */
	String getResource(TextEnum textEnum);

	/**
	 * Get the file to use in the player reproduction
	 * 
	 * @param audioEnum
	 *            The audio to load
	 * 
	 * @return Input stream to the audio to load
	 */
	IAudioData getResource(AudioEnum audioEnum);

	/**
	 * Dispose the resources used by the resource provider
	 */
	void dispose();
}
