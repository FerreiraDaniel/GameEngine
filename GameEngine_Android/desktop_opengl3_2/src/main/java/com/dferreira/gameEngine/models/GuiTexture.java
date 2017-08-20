package com.dferreira.gameEngine.models;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_resources.TextureEnum;

/**
 * Represents one texture to be show in the UI of the game (In 2D)
 */
public class GuiTexture {

    /**
     * Raw model of the entity
     */
    private final IRawModel rawModel;

    /**
     * Identifier of the texture
     */
    private final TextureEnum textureEnum;

    /**
     * Position of the texture between (0,0) and (1.0,1.0)
     */
    private final Vector2f position;
    /**
     * Scale factor of the texture
     */
    private final Vector2f scale;

    /**
     * Identifier of the texture
     */
    private ITexture texture;

    /**
     * @param rawModel    The rawModel that will be used by the guiTexture
     * @param textureEnum Identifier of resource that contains the texture
     * @param position    Position of the texture between (0,0) and (1.0,1.0)
     * @param scale       Scale factor of the texture
     */
    public GuiTexture(IRawModel rawModel, TextureEnum textureEnum, Vector2f position, Vector2f scale) {
        super();
        this.rawModel = rawModel;
        this.textureEnum = textureEnum;
        this.position = position;
        this.scale = scale;
    }

    /**
     * @return the raw model of the entity
     */
    public IRawModel getRawModel() {
        return rawModel;
    }

    /**
     * @return Identifier of resource that contains the texture of the gui
     */
    public TextureEnum getTextureEnum() {
        return this.textureEnum;
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

    /**
     * @return Position of the texture between (0,0) and (1.0,1.0)
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * @return Scale factor of the texture
     */
    public Vector2f getScale() {
        return scale;
    }
}
