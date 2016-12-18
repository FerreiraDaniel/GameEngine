package com.dferreira.commons;

/**
 * Represents one color with (Red, Green and Blue) components
 */
public class ColorRGB {

    /**
     * Red component
     */
    public final float r;

    /**
     * Green component
     */
    public final float g;

    /**
     * Blue component
     */
    public final float b;

    /**
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     */
    public ColorRGB(float r, float g, float b) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
    }

}