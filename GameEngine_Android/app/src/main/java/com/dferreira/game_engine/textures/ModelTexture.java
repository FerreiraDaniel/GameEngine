package com.dferreira.game_engine.textures;

/**
 * Has all the required parameters to render one textured entity
 */
public class ModelTexture {

    /**
     * The identifier of the texture
     */
    private final int textureId;

    /**
     * The atlas factor the end object will have
     * atlasFactor * atlasFactor = number of textures
     */
    private int atlasFactor;

    /**
     * Constructor of the texture model
     *
     * @param id Identifier of the texture id
     */
    public ModelTexture(int id) {
        this.textureId = id;
        this.atlasFactor = 1;    //By default will be one meaning that the object only have one texture
    }

    /**
     * @return the identifier of the texture
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * @return the atlasFactor
     * The end object will have
     * atlasFactor * atlasFactor = number of textures
     */
    public int getAtlasFactor() {
        return atlasFactor;
    }

    /**
     * @param atlasFactor the atlasFactor to set
     */
    public void setAtlasFactor(int atlasFactor) {
        this.atlasFactor = atlasFactor;
    }
}
