package com.dferreira.gameEngine.shaders.skyBox;

import com.dferreira.commons.IEnum;

/**
 * Enumeration of attributes to the skyBox shader
 */
public enum TSkyBoxAttribute implements IEnum {
	/**
	 * Position where to location attribute is going to be bind in the shader
	 * program
	 */
	position;

	/**
	 * The value of the enumeration
	 */
	@Override
	public int getValue() {
		return this.ordinal();
	}
}
