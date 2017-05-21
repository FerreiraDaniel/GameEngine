package com.dferreira.commons.generic_render;


import com.dferreira.commons.IEnum;

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
     * @param model         The model to be prepared to be rendered
     * @param position      The position attribute
     * @param textureCoords The texture attribute
     * @param normal        The normal attribute position
     */
    void prepareModel(IRawModel model, IEnum position, IEnum textureCoords, IEnum normal);

    /**
     * Prepares one model to be render in scene
     *
     * @param model    The model to be prepared to be rendered
     * @param position The position attribute
     */
    @SuppressWarnings("SameParameterValue")
    void prepare2DModel(IRawModel model, IEnum position);

    /**
     * Prepares one model to be render in scene
     *
     * @param model    The model to be prepared to be rendered
     * @param position The position attribute
     */
    @SuppressWarnings("SameParameterValue")
    void prepare3DModel(IRawModel model, IEnum position);

    /**
     * UnBind the previous bound elements
     *
     * @param position      The position attribute
     * @param textureCoords The texture attribute
     * @param normal        The normal attribute position
     */
    void unPrepareModel(IEnum position, IEnum textureCoords, IEnum normal);

    /**
     * UnBind the previous bound elements
     *
     * @param position The position attribute
     */
    void unPrepareModel(IEnum position);

    /**
     * Activates and binds the texture with ID passed
     *
     * @param textureId The identifier of the texture
     */
    void activeAndBindTexture(Integer textureId);

    /**
     * Activates and binds a cubic texture with ID passed
     *
     * @param textureId The identifier of the texture
     */
    void activeAndBindCubeTexture(Integer textureId);

    /**
     * Activates and binds a set of textures with IDs passed
     *
     * @param textureId1 The identifier of the texture 1
     * @param textureId2 The identifier of the texture 2
     * @param textureId3 The identifier of the texture 3
     * @param textureId4 The identifier of the texture 4
     * @param textureId5 The identifier of the texture 5
     */
    void activeAndBindTextures(int textureId1, int textureId2, int textureId3, int textureId4, int textureId5);

    /**
     * Draw a set of triangles using for that there indexes
     *
     * @param model The model to be drawn
     */
    void drawTrianglesIndexes(IRawModel model);

    /**
     * Draw a set of triangles using for that there vertex
     *
     * @param model The model to be drawn
     */
    void drawTrianglesVertex(IRawModel model);

    /**
     * Draw a quad using for that there vertex
     *
     * @param quad The model to be drawn
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
     * Enable the blend of colors taking in account the alpha component of the color
     */
    void enableBlend();


    /**
     * Disable the blend of colors taking in account the alpha component of the color
     */
    void disableBlend();

    /**
     * Specifies the affine transformation of x and y from normalized device coordinates to window coordinates
     *
     * @param x      Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
     * @param y      Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
     * @param width  Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
     * @param height Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
     */
    @SuppressWarnings("SameParameterValue")
    void setViewPort(int x, int y, int width, int height);
}
