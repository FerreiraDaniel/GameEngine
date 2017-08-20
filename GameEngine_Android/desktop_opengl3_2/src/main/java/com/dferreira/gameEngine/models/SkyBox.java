package com.dferreira.gameEngine.models;

import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;

/**
 * Represents a box with sky textures
 */
public class SkyBox {

    /**
     * GLRawModel of the skyBox
     */
    private final IRawModel model;

    /**
     * The Cubic texture in the Graphics API
     */
    private ITexture texture;

    /**
     * The constructor of the skyBox
     *
     * @param model The model of the sky box
     */
    public SkyBox(IRawModel model) {
        super();
        this.model = model;
    }

    /**
     * @return the model
     */
    public IRawModel getModel() {
        return model;
    }

    /**
     * @return Texture in the Graphics API Framework
     */
    public ITexture getTexture() {
        return texture;
    }

    /**
     * @param texture Texture API Framework
     */
    public void setTexture(ITexture texture) {
        this.texture = texture;
    }
}
