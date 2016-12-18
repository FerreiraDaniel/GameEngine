package com.dferreira.commons.models;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.Vector3f;

/**
 * Represents one light source in the scene
 */
public class Light {

    /**
     * Position where the light will exist
     */
    private Vector3f position;

    /**
     * The intensity of the light in the different components
     */
    private ColorRGB color;

    /**
     * The constructor of the light entity
     *
     * @param position Position where the light will exist
     * @param color    The intensity of the light in the different components
     */
    public Light(Vector3f position, ColorRGB color) {
        super();
        this.position = position;
        this.color = color;
    }

    /**
     * @return the position
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    @SuppressWarnings("unused")
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the color
     */
    public ColorRGB getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    @SuppressWarnings("unused")
    public void setColor(ColorRGB color) {
        this.color = color;
    }

}
