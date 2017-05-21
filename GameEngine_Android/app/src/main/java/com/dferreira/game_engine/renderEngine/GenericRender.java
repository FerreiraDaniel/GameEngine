package com.dferreira.game_engine.renderEngine;

import com.dferreira.commons.generic_render.IFrameRenderAPI;

/**
 * Contains elements that are commons to the different renders
 */

abstract class GenericRender {
    /**
     * API to render the frame
     */
    final IFrameRenderAPI frameRenderAPI;

    /**
     * @param frameRenderAPI Reference to the API responsible for render the frame
     */
    GenericRender(IFrameRenderAPI frameRenderAPI) {
        this.frameRenderAPI = frameRenderAPI;
    }
}
