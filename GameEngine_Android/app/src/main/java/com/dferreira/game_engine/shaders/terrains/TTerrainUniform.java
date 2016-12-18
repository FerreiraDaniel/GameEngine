package com.dferreira.game_engine.shaders.terrains;

import com.dferreira.commons.IEnum;

/**
 * Locations in the shader program of the terrains
 */
public enum TTerrainUniform implements IEnum {

    /**
     * Location of the transformation matrix in the program shader
     */
    transformationMatrix,

    /**
     * Location of the view matrix in the program shader
     */
    viewMatrix,

    /**
     * Location of the projection matrix in the program shader
     */
    projectionMatrix,

    /**
     * Location of the light's position in the program shader
     */
    lightPosition,

    /**
     * Location of the light's color in the program shader
     */
    lightColor,

    /**
     * Location of the shineDamper uniform in the fragment shader
     */
    shineDamper,

    /**
     * Location of the reflectivity uniform in the fragment shader
     */
    reflectivity,

    /**
     * Location of the color of the sky in the fragment shader
     */
    skyColor,

    /**
     * The background texture
     */
    backgroundTexture,

    /**
     * The mud texture
     */
    mudTexture,

    /**
     * The grass_mtl texture
     */
    grassTexture,

    /**
     * The path texture
     */
    pathTexture,

    /**
     * The blend map texture
     */
    weightMapTexture,

    /**
     * Meta-data used maintain the existing locations
     */
    numOfTerrainLocations;

    /**
     * The value of the enumeration
     */
    @Override
    public int getValue() {
        return this.ordinal();
    }
}
