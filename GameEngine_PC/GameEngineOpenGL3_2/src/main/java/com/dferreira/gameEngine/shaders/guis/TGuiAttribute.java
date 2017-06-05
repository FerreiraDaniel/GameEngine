package com.dferreira.gameEngine.shaders.guis;

import com.dferreira.commons.IEnum;

/**
 * Enumeration of attributes to the GUI shader
 */
public enum TGuiAttribute implements IEnum {
	
	/**
	 * Position where to location attribute is going to be bind in the shader
	 * program
	 */
	position,

	/**
	 * Number of GUI attributes
	 */
	numOfAttributes;

	/**
	 * The value of the enumeration
	 */
	@Override
	public int getValue() {
		return this.ordinal();
	}
}
