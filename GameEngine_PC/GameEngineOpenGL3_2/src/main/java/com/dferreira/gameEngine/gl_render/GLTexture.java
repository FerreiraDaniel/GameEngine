package com.dferreira.commons.gl_render;

import com.dferreira.commons.generic_render.ITexture;

/**
 * represents on texture to use in openGL API
 */

class GLTexture implements ITexture {

    /**
     * Identifier of the texture to load
     */
    private int id;

    /**
     * @return The identifier of the texture to use
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The identifier of the texture to use
     */
    public void setId(int id) {
        this.id = id;
    }
}
