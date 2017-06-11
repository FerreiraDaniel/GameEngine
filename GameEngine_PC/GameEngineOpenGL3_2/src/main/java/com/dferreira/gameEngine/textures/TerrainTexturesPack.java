package com.dferreira.gameEngine.textures;

import com.dferreira.commons.generic_render.ITexture;

/**
 * Contains the all textures of a terrain
 */
public class TerrainTexturesPack {

	/**
	 * Weight map texture
	 */
	private ITexture weightMapTexture;

	/**
	 * Background texture
	 */
	private ITexture backgroundTexture;

	/**
	 * The mud texture
	 */
	private ITexture mudTexture;

	/**
	 * The grass texture
	 */
	private ITexture grassTexture;

	/**
	 * Path texture
	 */
	private ITexture pathTexture;

	/**
	 * @return the blend Map Texture
	 */
	public ITexture getWeightMapTexture() {
		return this.weightMapTexture;
	}

	/**
	 * @param weightMapTexture
	 *            the blendMapTexture to set
	 */
	public void setWeightMapTexture(ITexture weightMapTexture) {
		this.weightMapTexture = weightMapTexture;
	}

	/**
	 * @return the backgroundTexture
	 */
	public ITexture getBackgroundTexture() {
		return backgroundTexture;
	}

	/**
	 * @param backgroundTexture
	 *            the backgroundTexture to set
	 */
	public void setBackgroundTexture(ITexture backgroundTexture) {
		this.backgroundTexture = backgroundTexture;
	}

	/**
	 * @return the mudTexture
	 */
	public ITexture getMudTexture() {
		return mudTexture;
	}

	/**
	 * @param mudTexture
	 *            the texture mud to set
	 */
	public void setMudTexture(ITexture mudTexture) {
		this.mudTexture = mudTexture;
	}

	/**
	 * @return the texture grass
	 */
	public ITexture getGrassTexture() {
		return grassTexture;
	}

	/**
	 * @param grassTexture
	 *            the texture grass to set
	 */
	public void setGrassTexture(ITexture grassTexture) {
		this.grassTexture = grassTexture;
	}

	/**
	 * @return the texture path
	 */
	public ITexture getPathTexture() {
		return this.pathTexture;
	}

	/**
	 * @param pathTexture
	 *            the path texture to set
	 */
	public void setPathTexture(ITexture pathTexture) {
		this.pathTexture = pathTexture;
	}

}
