package com.dferreira.gameEngine.audioEngine;

import com.dferreira.commons.SEnum;

/**
 * Enumeration for all the audio supported by the game
 */
public enum TAudioEnum implements SEnum {

	/**
	 * Used when the player jumps
	 */
	bounce,

	/**
	 * Used when the player goes against one tree
	 */
	breakingWood,

	/**
	 * Used to represent on bird singing in the a tree
	 */
	falcon,

	/**
	 * Used to indicate that the player is moving
	 */
	footsteps,

	/**
	 * Used to indicate that the user is in one height level so with more wind
	 */
	wind;

	/**
	 * The value of the enumeration
	 */
	@Override
	public String getValue() {
		String fileName;
		switch (this) {
		case bounce:
			fileName = "bounce";
			break;
		case breakingWood:
			fileName = "breaking wood";
			break;
		case falcon:
			fileName = "falcon";
			break;
		case footsteps:
			fileName = "footsteps";
			break;
		case wind:
			fileName = "wind";
			break;
		default:
			fileName = "";
			break;

		}
		return fileName;
	}
}
