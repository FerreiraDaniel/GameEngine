package com.dferreira.commons.models;

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
    private Vector3f color;

    /**
     * The constructor of the light entity
     *
     * @param position Position where the light will exist
     * @param color    The intensity of the light in the different components
     */
    public Light(Vector3f position, Vector3f color) {
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
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the color
     */
    public Vector3f getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Vector3f color) {
        this.color = color;
    }

}
