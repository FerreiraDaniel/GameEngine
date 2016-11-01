package com.dferreira.commons.models;

import java.nio.ByteBuffer;

/**
 * Holds the data of the pixels of a texture as well as the width and height
 */
public class TextureData {

    /* Number of components of the image (RGBA) */
    private static final int COMPONENTS_IMAGE = 4;


    /* Offset of the red component */
    private static final int R_POSITION = 0;
    /* Offset of the green component */
    private static final int G_POSITION = 1;
    /* Offset of the blue component */
    private static final int B_POSITION = 2;

    /*Max allowed value of the a component of a pixel*/
    private static final int MAX_COMPONENT = 255;

    /*Maximum value that the component RGB has*/
    public static final float MAX_PIXEL_COLOR = MAX_COMPONENT * MAX_COMPONENT * MAX_COMPONENT;

    /**
     * Width of the texture
     */
    private final int width;

    /**
     * Height of the texture
     */
    private final int height;

    /**
     * The buffer with data about the pixels of the image
     */
    private final ByteBuffer buffer;

    /**
     * The constructor of the texture data
     *
     * @param width  Width of the texture
     * @param height Height of the texture
     * @param buffer The buffer with data about the pixels of the image
     */
    public TextureData(ByteBuffer buffer, int width, int height) {
        super();
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the buffer
     */
    public ByteBuffer getBuffer() {
        return buffer;
    }

    /**
     * Get the component of the image
     *
     * @param x               x-coordinate
     * @param y               y-coordinate
     * @param componentOffset The off
     *
     * @return  The value representing the component read
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    private char getComponent(int x, int y, int componentOffset) {
        // First check if the coordinates are in the range of the image
        if ((x < 0) || (x >= this.getHeight()) || (y < 0) || (y >= this.getHeight())) {
            // No in the range
            return 0;
        } else {
            int index = ((y * this.getWidth()) + x) * COMPONENTS_IMAGE;
            char component = (char) this.getBuffer().get(index + componentOffset);
            return component;
        }
    }

    /**
     * @param x the x-coordinate
     * @param y the y-coordinate
     *
     * @return One integer representing the RGB colors of the pixel in the
     * position passed
     */
    public long getRGB(int x, int y) {
        char rComponent = getComponent(x, y, R_POSITION);
        char gComponent = getComponent(x, y, G_POSITION);
        char bComponent = getComponent(x, y, B_POSITION);

        return (bComponent << 16) + (gComponent << 8) + (rComponent);
    }
}
