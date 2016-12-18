package com.dferreira.game_engine.modelGenerators;

/**
 * Define the default properties that the element should obey
 */
@SuppressWarnings("WeakerAccess")
public class DefaultModelGenerator {

    /**
     * The name of the .obj that represents the model
     */
    private int objectReference;


    /**
     * The scale of the model
     */
    private float scale;

    /**
     * Indicates if the model has transparency or not
     */
    private boolean hasTransparency;

    /**
     * Indicate that all our normals of the object are going to point up (in the same
     * direction
     */
    private boolean normalsPointingUp;


    /**
     * @return The reference to the object that represents the model
     */
    public int getObjectReference() {
        return objectReference;
    }

    /**
     * Set the reference to object the represents the model
     *
     * @param objectReference reference to the object to set
     */
    public void setObjectReference(int objectReference) {
        this.objectReference = objectReference;
    }


    /**
     * @return the scale
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
     * @return the hasTransparency
     */
    public boolean getHasTransparency() {
        return hasTransparency;
    }

    /**
     * @param hasTransparency the hasTransparency to set
     */
    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    /**
     * @return the normalsPointingUp
     */
    public boolean getNormalsPointingUp() {
        return normalsPointingUp;
    }

    /**
     * @param normalsPointingUp the normalsPointingUp to set
     */
    public void setNormalsPointingUp(boolean normalsPointingUp) {
        this.normalsPointingUp = normalsPointingUp;
    }
}
