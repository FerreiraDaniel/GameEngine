package com.dferreira.commons.generic_render;

/**
 * Interface that should be implemented to access to the API that is going to render the scene
 */
public interface IRenderAPIAccess {

    /**
     * @return The loader render API
     */
    ILoaderRenderAPI getLoaderRenderAPI();

    /**
     * @return The frame render API
     */
    IFrameRenderAPI getFrameRenderAPI();

    /**
     * @return The shader manager API
     */
    IShaderManagerAPI getShaderManagerAPI();
    
    /**
     * Dispose resources used by the render API
     */
    void dispose();
}
