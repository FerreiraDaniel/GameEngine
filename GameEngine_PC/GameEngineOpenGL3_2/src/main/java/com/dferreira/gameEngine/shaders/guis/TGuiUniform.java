package com.dferreira.gameEngine.shaders.guis;

import com.dferreira.commons.IEnum;

/**
 * Uniforms in the shader program of the GUIs
 */
public enum TGuiUniform implements IEnum {

	/**
	 * Location of the transformation matrix in the program shader
	 */
	transformationMatrix,

	/**
	 * Meta-data used maintain the existing locations
	 */
	numOfGUILocations;

	/**
	 * The value of the enumeration
	 */
	@Override
	public int getValue() {
		return this.ordinal();
	}
}
