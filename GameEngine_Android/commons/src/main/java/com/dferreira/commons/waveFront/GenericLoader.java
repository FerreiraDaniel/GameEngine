package com.dferreira.commons.waveFront;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.Vector3f;

/**
 * Contains methods used by the wavefront loaders
 */
class GenericLoader {

    static final String SPLIT_TOKEN = " ";

    /**
     * Parse two strings and return the equivalent vector2f
     *
     * @param xStr X component
     * @param yStr Y component
     * @return The parsed vector
     */
    static Vector2f parseVector2f(String xStr, String yStr) {
        float x = Float.parseFloat(xStr);
        float y = Float.parseFloat(yStr);

        return new Vector2f(x, y);
    }

    /**
     * Parse two strings and return the equivalent vector23
     *
     * @param xStr X component
     * @param yStr Y component
     * @param zStr Z component
     * @return The parsed vector
     */
    static Vector3f parseVector3f(String xStr, String yStr, String zStr) {
        float x = Float.parseFloat(xStr);
        float y = Float.parseFloat(yStr);
        float z = Float.parseFloat(zStr);

        return new Vector3f(x, y, z);
    }

    /**
     * @param line        The line of the file with all the information
     * @param currentLine Array of strings with different parts of line
     * @return A a string defined by in one line
     */
    static String parseStringComponent(String line, String[] currentLine) {
        return line.substring(currentLine[0].length()).trim();
    }
}
