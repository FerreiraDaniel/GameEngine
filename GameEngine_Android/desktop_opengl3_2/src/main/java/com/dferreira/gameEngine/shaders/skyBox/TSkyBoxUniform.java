package com.dferreira.gameEngine.shaders.skyBox;

import com.dferreira.commons.IEnum;

/**
 * Uniforms in the shader program of the skyBox
 */
public enum TSkyBoxUniform implements IEnum {

    /**
     * Location of the projection matrix in the program shader
     */
    projectionMatrix,

    /**
     * Location of the view matrix in the program shader
     */
    viewMatrix,

    /**
     * Meta-data used maintain the existing locations
     */
    numOfSkyBoxLocations;

    /**
     * The value of the enumeration
     */
    @Override
    public int getValue() {
        return this.ordinal();
    }

}
