package com.dferreira.gameEngine.models.complexEntities;

/**
 * Has the parameters of the material of a rawModel
 */
public class Material {

    /**
     * Diffuse component of the color
     */
    private LightingComponent diffuse;

    /**
     * How damped the shine is
     */
    private float shineDamper;

    /**
     * How reflective the model is
     */
    private float reflectivity;

    /**
     * Indicates if the material has transparency or not
     */
    private boolean hasTransparency;

    /**
     * Indicate that all the normals of the material are going to point up (in
     * the same direction
     */
    private boolean normalsPointingUp;

    /**
     * Constructor of the texture model
     */
    public Material() {
        this.shineDamper = 1.0f;
        this.reflectivity = 0.0f;
        this.hasTransparency = false;
        this.normalsPointingUp = false;
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

    /**
     * @return Get the diffuse component of the material
     */
    public LightingComponent getDiffuse() {
        return diffuse;
    }

    /**
     * Sets the diffuse component of the material
     *
     * @param diffuse The diffuse component to set
     */
    public void setDiffuse(LightingComponent diffuse) {
        this.diffuse = diffuse;
    }
}
