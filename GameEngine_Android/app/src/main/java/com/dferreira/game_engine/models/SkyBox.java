package com.dferreira.game_engine.models;

/**
 * Represents a box with sky textures
 */
public class SkyBox {

    /**
     * RawModel of the skyBox
     */
    private final RawModel model;

    /**
     * The identifier of the sky box cubic texture
     */
    private Integer textureId;

    /**
     * The constructor of the skyBox
     *
     * @param model     The model of the sky box
     */
    public SkyBox(RawModel model) {
        super();
        this.model = model;
    }

    /**
     * @return the model
     */
    public RawModel getModel() {
        return model;
    }

    /**
     * Set the texture identifier
     * @param textureId The Identifier of the texture of the sky
     */
    public void setTextureId(Integer textureId) {
        this.textureId = textureId;
    }

    /**
     * @return the textureId
     */
    public Integer getTextureId() {
        return textureId;
    }
}
