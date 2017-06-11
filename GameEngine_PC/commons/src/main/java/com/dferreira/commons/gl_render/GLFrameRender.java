package com.dferreira.commons.gl_render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;


/**
 * Contains useful methods when is render on frame using OpenGL
 */
class GLFrameRender implements IFrameRenderAPI {

    /**
     * Clear the screen as well as the depth buffer
     */
    @Override
	public void prepareFrame() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0, 0.3f, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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

    /**
     * Prepares one 2D model to be render in scene
     *
     * @param model    The model to be prepared to be rendered
     * @param position The position attribute
     */
	@Override
	public void prepare2DModel(IRawModel model, IEnum position) {
		GLRawModel rawModel = (GLRawModel) model;
		
		GL30.glBindVertexArray(rawModel.getVaoId());
		GL20.glEnableVertexAttribArray(position.getValue());
		
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
     * @param target  Target where is to bind the texture
     * @param texture The texture to use
     */
    private void activeAndBind2DTexture(int target, ITexture texture) {
        GLTexture glTexture = (GLTexture) texture;
        
        //Enable the specific texture
    	GL13.glActiveTexture(target);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTexture.getId());
    }

    /**
     * Activates and binds the texture with passed
     *
     * @param texture The texture to use
     */
    @Override
    public void activeAndBindTexture(ITexture texture) {
    	activeAndBind2DTexture(GL13.GL_TEXTURE0, texture);
	}

    /**
     * Activates and binds a cubic texture with ID passed
     *
     * @param texture The texture to use
     */
    @Override
    public void activeAndBindCubeTexture(ITexture texture) {
    	GLTexture glTexture = (GLTexture) texture;
    	
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, glTexture.getId());
    }

    /**
     * Activates and binds a set of textures passed
     *
     * @param texture1 The texture 1
     * @param texture2 The texture 2
     * @param texture3 The texture 3
     * @param texture4 The texture 4
     * @param texture5 The texture 5
     */
    @Override
    public void activeAndBindTextures(ITexture texture1, ITexture texture2, ITexture texture3, ITexture texture4, ITexture texture5) {
        activeAndBind2DTexture(GL13.GL_TEXTURE0, texture1);
        activeAndBind2DTexture(GL13.GL_TEXTURE1, texture2);
        activeAndBind2DTexture(GL13.GL_TEXTURE2, texture3);
        activeAndBind2DTexture(GL13.GL_TEXTURE3, texture4);
        activeAndBind2DTexture(GL13.GL_TEXTURE4, texture5);
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
