package com.dferreira.game_engine.models;

import com.dferreira.game_engine.textures.ModelTexture;

/**
 * Wrapper that besides of have the raw model also has the texture to put in the model
 */
public class TexturedModel {

    /**
     * Raw model of the entity
     */
    private final RawModel rawModel;

    /**
     * Reference to the texture of the entity
     */
    private final ModelTexture texture;
    /**
     * Indicates if the model has transparency or not
     */
    private final boolean hasTransparency;
    /**
     * Indicate that all our normals of the object are going to point up (in the same
     * direction
     */
    private final boolean normalsPointingUp;
    /**
     * How damped the shine is
     */
    private float shineDamper;
    /**
     * How reflective the model is
     */
    private float reflectivity;

    /**
     * Constructor of the textured model
     *
     * @param rawModel			Raw model of the entity
     * @param texture			Reference to the texture of the entity
     * @param hasTransparency	Indicates if the model has transparency or not not
     * @param normalsPointingUp	Indicates if the normals of the model should point up
     */
    public TexturedModel(RawModel rawModel, ModelTexture texture, boolean hasTransparency, boolean normalsPointingUp) {
        super();
        this.rawModel = rawModel;
        this.texture = texture;
        this.shineDamper = 1.0f;
        this.reflectivity = 0.0f;
        this.hasTransparency = hasTransparency;
        this.normalsPointingUp = normalsPointingUp;
    }


    /**
     * @return the raw model of the entity
     */
    public RawModel getRawModel() {
        return rawModel;
    }


    /**
     * @return the description of the texture of the entity
     */
    public ModelTexture getTexture() {
        return texture;
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
     * Returns if the model has any transparency or not
     */
    public boolean hasTransparency() {
        return this.hasTransparency;
    }


    /**
     * @return if the normals of the model should point up
     */
    public boolean isNormalsPointingUp() {
        return normalsPointingUp;
    }
}
