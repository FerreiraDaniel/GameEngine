package com.dferreira.game_engine.models.complexEntities;

/**
 * Has the parameters of the material of a rawModel
 */
public class Material {

    /**
     * The identifier of the texture
     */
    private int textureId;

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
     * @param id Identifier of the texture id
     */
    public Material(int id) {
        this.textureId = id;
        this.shineDamper = 1.0f;
        this.reflectivity = 0.0f;
        this.hasTransparency = false;
        this.normalsPointingUp = false;
    }

    /**
     * @return the identifier of the texture
     */
    public int getTextureId() {
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

}
