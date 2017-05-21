package com.dferreira.commons.wavefront;

import com.dferreira.commons.ColorRGBA;

/**
 * Enumeration with colors predefined
 */

public enum RGBAColorEnum {
    /**
     * When the color is totally transparent
     */
    transparent;


    /**
     * Convert one RGBAColorEnum into ColorRGBA class
     *
     * @return The matching rgba color
     */
    public ColorRGBA toRGBA() {
        switch (this) {
            case transparent:
                return new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f);
        }
        return null;
    }

}
