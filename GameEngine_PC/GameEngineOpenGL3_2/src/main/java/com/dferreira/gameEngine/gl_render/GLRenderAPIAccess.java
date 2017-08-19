package com.dferreira.gameEngine.gl_render;

import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRenderAPIAccess;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.commons.generic_resources.IResourceProvider;

/**
 * Has all the required components to render the scene with openGL
 */
public class GLRenderAPIAccess implements IRenderAPIAccess {

	/**
	 * Loader for OpenGL stuff
	 */
	private final ILoaderRenderAPI loader;

	/**
	 * Contains useful methods when is render on frame using OpenGL
	 */
	private final IFrameRenderAPI frameRender;

	/**
	 * Used to access a shader program
	 */
	private final IShaderManagerAPI shaderManagerAPI;

    /**
     * Constructor to the render responsible to access the openGL API
     */
    public GLRenderAPIAccess(IResourceProvider resourceProvider) {
		this.loader = new GLLoader(resourceProvider);
		this.frameRender = new GLFrameRender();
		this.shaderManagerAPI = new GLShaderManager(resourceProvider);
	}

	/**
	 * @return The loader render API
	 */
	@Override
	public ILoaderRenderAPI getLoaderRenderAPI() {
		return this.loader;
	}

	/**
	 * @return Gets the frame render API
	 */
	@Override
	public IFrameRenderAPI getFrameRenderAPI() {
		return frameRender;
	}

	/**
	 * @return The API to access the program shader
	 */
	@Override
	public IShaderManagerAPI getShaderManagerAPI() {
		return shaderManagerAPI;
	}

	/**
	 * Dispose the resources used by the Render API
	 */
	@Override
	public void dispose() {
		this.loader.dispose();;
		this.frameRender.dispose();
		this.shaderManagerAPI.dispose();
	}
}
