package com.dferreira.game_engine.models.complexEntities;

import com.dferreira.commons.ColorRGBA;

/**
 * Has the parameters of the material of a rawModel
 */
public class Material {

    /**
     * The identifier of the texture
     */
    private Integer textureId;

    /**
     * The weight of the texture for the ambient color of the object
     */
    private float textureWeight;

    /**
     * constant color of the ambient component
     */
    private ColorRGBA diffuseColor;

    /**
     * How damped the shine is
     */
    private float shineDamper;

    /**
     * How reflective the model is
     */
    private float reflectivity;

    /**
     * Indicates if the model has transparency or not
     */
    private boolean hasTransparency;

    /**
     * Indicate that all our normals of the object are going to point up (in the
     * same direction
     */
    private boolean normalsPointingUp;

    /**
     * Constructor of the texture model
     *
     */
    public Material() {
        this.shineDamper = 1.0f;
        this.reflectivity = 0.0f;
        this.hasTransparency = false;
        this.normalsPointingUp = false;
    }

    /**
     *
     * @param textureId The identifier of the texture
     *
     */
    public void setTextureId(Integer textureId) {
        this.textureId = textureId;
    }

    /**
     * @return the identifier of the texture
     */
    public Integer getTextureId() {
        return textureId;
    }

    /**
     * @return the shineDamper
     */
    public float getShineDamper() {
        return shineDamper;
    }

    /**
     * @param shineDamper the shineDamper to set
     */
    @SuppressWarnings("SameParameterValue")
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    /**
     * @return the reflectivity
     */
    public float getReflectivity() {
        return reflectivity;
    }

    /**
     * @param reflectivity the reflectivity to set
     */
    @SuppressWarnings("SameParameterValue")
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    /**
     * @param hasTransparency If the model has any transparency or not
     */
    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    /**
     * Returns if the model has any transparency or not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasTransparency() {
        return this.hasTransparency;
    }

    /**
     * @param normalsPointingUp If the normals of the model should point up
     */
    public void setNormalsPointingUp(boolean normalsPointingUp) {
        this.normalsPointingUp = normalsPointingUp;
    }

    /**
     * @return if the normals of the model should point up
     */
    public boolean areNormalsPointingUp() {
        return normalsPointingUp;
    }

    /**
     * @param textureWeight the weight of texture to set
     */
    public void setTextureWeight(float textureWeight) {
        this.textureWeight = textureWeight;
    }

    /**
     * @return the weight of  component to the final diffuse
     */
    public float getTextureWeight() {
        return textureWeight;
    }

    /**
     * @return the diffuseColor
     */
    public ColorRGBA getDiffuseColor() {
        return diffuseColor;
    }


    /**
     * @param diffuseColor the diffuseColor to set
     */
    public void setDiffuseColor(ColorRGBA diffuseColor) {
        this.diffuseColor = diffuseColor;
    }
}
