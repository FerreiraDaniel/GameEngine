package com.dferreira.commons.generic_render;

/**
 * Interface of the frame render API
 */

public interface IFrameRenderAPI {

	/**
	 * Clear the screen as well as the depth buffer
	 */
	void prepareFrame();

	/**
	 * Prepares one model to be render in scene
	 *
	 * @param model
	 *            The model to be prepared to be rendered
	 */
	void prepareModel(IRawModel model);

	/**
	 * Prepares one model to be render in scene
	 *
	 * @param model
	 *            The model to be prepared to be rendered
	 */
	@SuppressWarnings("SameParameterValue")
	void prepare2DModel(IRawModel model);

	/**
	 * Prepares one model to be render in scene
	 *
	 * @param model
	 *            The model to be prepared to be rendered
	 */
	@SuppressWarnings("SameParameterValue")
	void prepare3DModel(IRawModel model);

	/**
	 * UnBind the previous bound elements
	 *
	 * @param model
	 *            The model to be prepared to be rendered
	 */
	void unPrepareModel(IRawModel model);

	/**
	 * Activates and binds the texture with passed
	 *
	 * @param texture
	 *            The texture to use
	 */
	void activeAndBindTexture(ITexture texture);

	/**
	 * Activates and binds a cubic texture with ID passed
	 *
	 * @param texture
	 *            The texture to use
	 */
	void activeAndBindCubeTexture(ITexture texture);

	/**
	 * Activates and binds a set of textures with IDs passed
	 *
	 * @param texture1
	 *            The texture 1
	 * @param texture2
	 *            The texture 2
	 * @param texture3
	 *            The texture 3
	 * @param texture4
	 *            The texture 4
	 * @param texture5
	 *            The texture 5
	 */
	void activeAndBindTextures(ITexture texture1, ITexture texture2, ITexture texture3, ITexture texture4,
			ITexture texture5);

	/**
	 * Draw a set of triangles using for that there indexes
	 *
	 * @param model
	 *            The model to be drawn
	 */
	void drawTrianglesIndexes(IRawModel model);

	/**
	 * Draw a set of triangles using for that there vertex
	 *
	 * @param model
	 *            The model to be drawn
	 */
	void drawTrianglesVertex(IRawModel model);

	/**
	 * Draw a quad using for that there vertex
	 *
	 * @param quad
	 *            The model to be drawn
	 */
	void drawQuadVertex(IRawModel quad);

	/**
	 * Enable culling of faces to get better performance
	 */
	void enableCulling();

	/**
	 * Disable the culling of the faces vital for transparent model
	 */
	void disableCulling();

	/**
	 * Enable the test of the depth will render one element in the screen
	 */
	void enableDepthTest();

	/**
	 * Disable the test of the depth will render one element in the screen
	 */
	void disableDepthTest();

	/**
	 * Enable the blend of colors taking in account the alpha component of the
	 * color
	 */
	void enableBlend();

	/**
	 * Disable the blend of colors taking in account the alpha component of the
	 * color
	 */
	void disableBlend();

	/**
	 * Specifies the affine transformation of x and y from normalized device
	 * coordinates to window coordinates
	 *
	 * @param x
	 *            Specify the lower left corner of the viewport rectangle, in
	 *            pixels. The initial value is (0,0).
	 * @param y
	 *            Specify the lower left corner of the viewport rectangle, in
	 *            pixels. The initial value is (0,0).
	 * @param width
	 *            Specify the width and height of the viewport. When a GL
	 *            context is first attached to a window, width and height are
	 *            set to the dimensions of that window.
	 * @param height
	 *            Specify the width and height of the viewport. When a GL
	 *            context is first attached to a window, width and height are
	 *            set to the dimensions of that window.
	 */
	@SuppressWarnings("SameParameterValue")
	void setViewPort(int x, int y, int width, int height);

	/**
	 * Clean up the resources used by the render API
	 */
	void dispose();
}
