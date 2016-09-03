package com.dferreira.commons.models;

import java.nio.ByteBuffer;

/**
 * Holds the data of the pixels of a texture as well as the width and height
 */
public class TextureData {

	/**
	 * Width of the texture
	 */
	private final int width;

	/**
	 * Height of the texture
	 */
	private final int height;

	/**
	 * The buffer with data about the pixels of the image
	 */
	private final ByteBuffer buffer;

	/**
	 * The constructor of the texture data
	 * 
	 * @param width
	 *            Width of the texture
	 * @param height
	 *            Height of the texture
	 * @param buffer
	 *            The buffer with data about the pixels of the image
	 */
	public TextureData(ByteBuffer buffer, int width, int height) {
		super();
		this.buffer = buffer;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the buffer
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}
}
