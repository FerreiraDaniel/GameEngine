package com.dferreira.game_engine.models;

import com.dferreira.commons.Vector2f;
import com.dferreira.game_controller.GamePadKey;

/**
 * Represents one texture to be show in the UI of the game (In 2D)
 */
public class GuiTexture {

    /**
     * Raw model of the entity
     */
    private final RawModel rawModel;

    /**
     * Identifier of the texture
     */
    private final int textureResourceId;


    /**
     * Identifier of the texture
     */
    private int textureId;

    /**
     * Position of the texture between (0,0) and (1.0,1.0)
     */
    private final Vector2f position;

    /**
     * Scale factor of the texture
     */
    private final Vector2f scale;

    /**
     * The game pad key that the gui texture will trigger if pressed
     * Note if null nothing will be trigger
     */
    private final GamePadKey gamePadKey;

    /**
     * @param rawModel   The rawModel that will be used by the guiTexture
     * @param textureResourceId  Identifier of resource that contains the texture
     * @param position   Position of the texture between (0,0) and (1.0,1.0)
     * @param scale      Scale factor of the texture
     * @param gamePadKey The reference to the key that will be trigger
     */
    public GuiTexture(RawModel rawModel, int textureResourceId, Vector2f position, Vector2f scale, GamePadKey gamePadKey) {
        super();
        this.rawModel = rawModel;
        this.textureResourceId = textureResourceId;
        this.position = position;
        this.scale = scale;
        this.gamePadKey = gamePadKey;
    }

    /**
     * @return the raw model of the entity
     */
    public RawModel getRawModel() {
        return rawModel;
    }

    /**
     *
     * @return  Identifier of resource that contains the texture of the gui
     */
    public int getTextureResourceId() {
        return textureResourceId;
    }

    /**
     *
     * @param textureId Identifier of the texture
     */
    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    /**
     * @return Identifier of the texture
     */
    public int getTextureId() {
        return textureId;
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

    /**
     * @return The reference to the key that will be trigger
     */
    public GamePadKey getGamePadKey() {
        return gamePadKey;
    }

    /**
     * @param locationX x-coordinate of the location passed
     * @param locationY y-coordinate of the location passed
     * @return False do not contains the location passed
     * True contains the location passed
     */
    public boolean containsLocation(float locationX, float locationY) {
        return (locationX >= (position.x - scale.x)) &&
                (locationX <= (position.x + scale.x)) &&
                ((locationY >= (position.y - scale.y)) && ((locationY <= (position.y + scale.y))));
    }
}
