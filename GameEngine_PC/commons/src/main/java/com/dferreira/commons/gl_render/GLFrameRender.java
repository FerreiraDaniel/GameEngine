package com.dferreira.commons.gl_render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;


/**
 * Contains useful methods when is render on frame using OpenGL
 */

class GLFrameRender implements IFrameRenderAPI {

	@Override
	public void prepareFrame() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Prepares one model to be render in scene
     *
     * @param model         The model to be prepared to be rendered
     * @param position      The position attribute
     * @param textureCoords The texture attribute
     * @param normal        The normal attribute position
     */
    @Override
    public void prepareModel(IRawModel model, IEnum position, IEnum textureCoords, IEnum normal) {
        GLRawModel rawModel = (GLRawModel) model;
    	
		GL30.glBindVertexArray(rawModel.getVaoId());
		
		// Enable the attributes to bind
		GL20.glEnableVertexAttribArray(position.getValue());
		GL20.glEnableVertexAttribArray(textureCoords.getValue());
		GL20.glEnableVertexAttribArray(normal.getValue());
	}

	@Override
	public void prepare2DModel(IRawModel model, IEnum position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare3DModel(IRawModel model, IEnum position) {
		// TODO Auto-generated method stub
		
	}

    /**
     * UnBind the previous bound elements
     *
     * @param position      The position attribute
     * @param textureCoords The texture attribute
     * @param normal        The normal attribute position
     */
    @Override
	public void unPrepareModel(IEnum position, IEnum textureCoords, IEnum normal) {
		GL20.glDisableVertexAttribArray(position.getValue());
		GL20.glDisableVertexAttribArray(textureCoords.getValue());
		GL20.glDisableVertexAttribArray(normal.getValue());
		GL30.glBindVertexArray(0);
		
	}

	@Override
	public void unPrepareModel(IEnum position) {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * Activates and binds the texture with ID passed in the specified target
     *
     * @param target    Target where is to bind the texture
     * @param textureId The identifier of the texture
     */
    private void activeAndBind2DTexture(int target, int textureId) {
        //Enable the specific texture
    	GL13.glActiveTexture(target);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    /**
     * Activates and binds the texture with ID passed
     *
     * @param textureId The identifier of the texture
     */
    @Override
    public void activeAndBindTexture(Integer textureId) {
    	activeAndBind2DTexture(GL13.GL_TEXTURE0, textureId);
	}

    /**
     * Activates and binds a cubic texture with ID passed
     *
     * @param textureId The identifier of the texture
     */
    @Override
    public void activeAndBindCubeTexture(Integer textureId) {
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureId);
    }

    /**
     * Activates and binds a set of textures with IDs passed
     *
     * @param textureId1 The identifier of the texture 1
     * @param textureId2 The identifier of the texture 2
     * @param textureId3 The identifier of the texture 3
     * @param textureId4 The identifier of the texture 4
     * @param textureId5 The identifier of the texture 5
     */
    @Override
    public void activeAndBindTextures(int textureId1, int textureId2, int textureId3, int textureId4, int textureId5) {
        activeAndBind2DTexture(GL13.GL_TEXTURE0, textureId1);
        activeAndBind2DTexture(GL13.GL_TEXTURE1, textureId2);
        activeAndBind2DTexture(GL13.GL_TEXTURE2, textureId3);
        activeAndBind2DTexture(GL13.GL_TEXTURE3, textureId4);
        activeAndBind2DTexture(GL13.GL_TEXTURE4, textureId5);
    }

    /**
     * Draw a set of triangles using for that there indexes
     *
     * @param model The model to be drawn
     */
    @Override
    public void drawTrianglesIndexes(IRawModel model) {
		GLRawModel rawModel = (GLRawModel) model;
		
		//Specify the indexes
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	@Override
	public void drawTrianglesVertex(IRawModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawQuadVertex(IRawModel quad) {
		// TODO Auto-generated method stub
		
	}

    /**
     * Enable culling of faces to get better performance
     */
    @Override
	public void enableCulling() {
		// Enable the GL cull face feature
		GL11.glEnable(GL11.GL_CULL_FACE);
		// Avoid to render faces that are away from the camera
		GL11.glCullFace(GL11.GL_BACK);
	}

    /**
     * Disable the culling of the faces vital for transparent model
     */
    @Override
	public void disableCulling() {
    	GL11.glDisable(GL11.GL_CULL_FACE);
	}

    /**
     * Enable the test of the depth will render one element in the screen
     */
    @Override
	public void enableDepthTest() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Disable the test of the depth will render one element in the screen
     */
    @Override
	public void disableDepthTest() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Enable the blend of colors taking in account the alpha component of the color
     */
    @Override
	public void enableBlend() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Disable the blend of colors taking in account the alpha component of the color
     */
    @Override
	public void disableBlend() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Specifies the transformation of x and y from normalized device coordinates to window coordinates
     *
     * @param x      Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
     * @param y      Specify the lower left corner of the viewport rectangle, in pixels. The initial value is (0,0).
     * @param width  Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
     * @param height Specify the width and height of the viewport. When a GL context is first attached to a window, width and height are set to the dimensions of that window.
     */
    @Override
	public void setViewPort(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
