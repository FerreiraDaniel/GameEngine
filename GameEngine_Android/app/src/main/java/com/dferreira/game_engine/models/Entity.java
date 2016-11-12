package com.dferreira.game_engine.models;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;

/**
 * Represents one entity in the 3D world
 */
public class Entity {

    /*3D model to be render*/
    private TexturedModel model;

    /*Position where the entity is*/
    private Vector3f position;

    /*Rotation of the 3D model*/
    private float rotX, rotY, rotZ;

    /*Scale of the model*/
    private float scale;

    /**
     * If the object has an atlas factor bigger that one this this index will
     * indicate which one of the textures the entity is using
     */
    private int textureIndex;

    /**
     * 2D vector that is going to determine where the texture of the entity is
     * going to start
     */
    private Vector2f textureOffset;

    /**
     * Constructor of the entity to be render in the 3D world
     *
     * @param model        Textured model
     * @param position     position where the model should be render
     * @param rotX         Rotation of the model in the X axle
     * @param rotY         Rotation of the model in the Y axle
     * @param rotZ         Rotation of the model in the Z axle
     * @param scale        Scale of the model
     * @param textureIndex Indicates which of the textures is going to use
     */
    @SuppressWarnings("SameParameterValue")
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
                  float scale, int textureIndex) {
        super();
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textureIndex = textureIndex;
        this.textureOffset = this.computeTextureOffset();
    }

    /**
     * Increases the position of the model using for that the specified components
     *
     * @param dx X component to be increase
     * @param dy Y component to be increase
     * @param dz Z component to be increase
     */
    void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    /**
     * Increases the rotation of the model using for that the specified components
     *
     * @param dx X component to be increase
     * @param dy Y component to be increase
     * @param dz Z component to be increase
     */
    @SuppressWarnings("SameParameterValue")
    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    /**
     * @return the textured model
     */
    public TexturedModel getModel() {
        return model;
    }

    /**
     * @param model the textured model to be set
     */
    public void setModel(TexturedModel model) {
        this.model = model;
    }

    /**
     * @return the position of the model
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position The position to be set
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the rotation of the entity in the X axle
     */
    public float getRotX() {
        return rotX;
    }

    /**
     * @param rotX the rotation of the entity in the X axle
     */
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    /**
     * @return the rotation of the entity in the Y axle
     */
    public float getRotY() {
        return rotY;
    }

    /**
     * @param rotY the rotation of the entity in the Y axle
     */
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    /**
     * @return the rotation of the entity in the Z axle
     */
    public float getRotZ() {
        return rotZ;
    }

    /**
     * @param rotZ the rotation of the entity in the Z axle
     */
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    /**
     * @return the scale the scale of the entity
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * @return the index of texture
     */
    public int getTextureIndex() {
        return textureIndex;
    }

    /**
     * @param textureIndex the index of the texture to set
     */
    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
        this.textureOffset = computeTextureOffset();
    }

    /**
     * @return A number between 0 and 1 that is going indicate where the texture
     * will have have there x-axle start based upon the texture atlas
     * factor
     */
    private float computeTextureXOffset() {
        if ((this.textureIndex == 0.0f) || (this.model == null) || (this.model.getTexture() == null)) {
            return 0.0f;
        } else {
            int atlasFactor = this.model.getTexture().getAtlasFactor();
            float column = textureIndex % (atlasFactor);
            // Return the value in percentage of the total
            return column / ((float) atlasFactor);
        }
    }

    /**
     * @return A number between 0 and 1 that is going indicate where the texture
     * will have have there y-axle start based upon the texture atlas
     * factor
     */
    private float computeTextureYOffset() {
        if ((this.textureIndex == 0.0f) || (this.model == null) || (this.model.getTexture() == null)) {
            return 0.0f;
        } else {
            int atlasFactor = this.model.getTexture().getAtlasFactor();
            float row = textureIndex / (atlasFactor);
            // Return the value in percentage of the total
            return row / ((float) atlasFactor);
        }
    }

    /**
     * @return A vector with coordinates where the texture starts
     */
    private Vector2f computeTextureOffset() {
        return new Vector2f(computeTextureXOffset(), computeTextureYOffset());
    }

    /**
     * @return the offset of the texture of the entity
     */
    public Vector2f getTextureOffset() {
        return textureOffset;
    }
}
