package com.dferreira.commons.gl_render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;

/**
 * Loader for parts that are specific to openGL
 */
public class GLLoader implements ILoaderRenderAPI {
	
	/**
	 * List of the vertex array objects loaded
	 */
	private List<Integer> vaos;

	/**
	 * List of the vertex buffer objects loaded
	 */
	private List<Integer> vbos;

	/**
	 * List of the textures that make part of the game engine
	 */
	private List<Integer> textures;
	

    /**
     * Provider of the resources (highly dependent from the architecture)
     */
    private final IResourceProvider resourceProvider;

    /**
     * Constructor of the loader GL
     */
    GLLoader(IResourceProvider resourceProvider) {
		this.vaos = new ArrayList<Integer>();
		this.vbos = new ArrayList<Integer>();
		this.textures = new ArrayList<Integer>();
        this.resourceProvider = resourceProvider;
    }
    
    /**
     * When loads one texture defines that by default should zoom in/out it
     *
     * @param target    The target of the filter
     * @param wrapParam Parameter used in the wrap filters
     */
    private void defineTextureFunctionFilters(int target, int wrapParam) {
    	//The texture minify function is used whenever the pixel being textured maps to an area greater than one texture element
    	GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
    	//The texture magnification function is used when the pixel being textured maps to an area less than or equal to one texture element
    	GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
    	//Sets the wrap parameter for texture coordinate s
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, wrapParam);
		
		//Sets the wrap parameter for texture coordinate t
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, wrapParam);
    }
	
    /**
     * @param repeat Boolean that indicates if is to repeat or not the texture
     * @return The value that should put in the filter of the texture
     */
    private int getWrapFilters(boolean repeat) {
        return repeat ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE;
    }
    
    /**
     * Load a texture in openGLES API
     *
     * @param textureData The data of the texture to load
     * @param repeat      Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Id from the texture that was bounded in openGL
     */
    private ITexture pLoadTexture(TextureData textureData, boolean repeat) {

    	int textureId = GL11.glGenTextures();
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

    	GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());


        defineTextureFunctionFilters(GL11.GL_TEXTURE_2D, getWrapFilters(repeat));

        GLTexture texture = new GLTexture();
        texture.setId(textureId);
        this.textures.add(textureId);
        
        return texture;
    }
    
    /**
     * Load texture from resource
     *
     * @param textureEnum Enum of the resource where the texture exists
     * @param repeat      Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Id from the texture that was bounded in openGL
     */
    @Override
    public ITexture loadTexture(TextureEnum textureEnum, boolean repeat) {
        TextureData textureData = this.resourceProvider.getResource(textureEnum);
        return pLoadTexture(textureData, repeat);
    }

    /**
     * Load texture from resource located in the mipmap folder
     *
     * @param textureFileName The name of the texture where the texture exists
     * @param repeat          Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Id from the texture that was bounded in openGL
     */
    @Override
    public ITexture loadTexture(String textureFileName, boolean repeat) {
        TextureData textureData = this.resourceProvider.getTexture(textureFileName);
        return pLoadTexture(textureData, repeat);
    }

    /**
     * Loads the data of a texture without bind
     *
     * @param textureEnum id of the resource where the texture exists
     * @return The texture read from the file without any openGL bind
     */
    @Override
    public TextureData getTextureData(TextureEnum textureEnum) {
        return this.resourceProvider.getResource(textureEnum);
    }

    /**
     * Loads a cubic texture
     *
     * @param textures The resources where should get the images of the cube
     * @param repeat   Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return Identifier of the texture cubic texture loaded
     */
    @Override
    public ITexture loadTCubeMap(TextureEnum[] textures, boolean repeat) {
        if (Utils.isEmpty(textures)) {
            return null;
        } else {
    		int[] cubicTextureTargets = new int[] { GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
    				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
    				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
    				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };

    		int textureId = GL11.glGenTextures();
    		GL13.glActiveTexture(GL13.GL_TEXTURE0);
    		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureId);

            for (int i = 0; i < cubicTextureTargets.length; i++) {
                TextureEnum texture = textures[i];

                TextureData textureData = resourceProvider.getResource(texture);
                if (textureData == null) {
                    return null;
                } else {
                    int target = cubicTextureTargets[i];
                    GL11.glTexImage2D(target, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
    						GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
                }
            }
            defineTextureFunctionFilters(GL13.GL_TEXTURE_CUBE_MAP, getWrapFilters(repeat));

            GLTexture texture = new GLTexture();
            texture.setId(textureId);
            this.textures.add(textureId);
            
            return texture;
        }
	}

	@Override
	public IRawModel loadToRawModel(IShape shape) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRawModel load2DPositionsToRawModel(float[] positions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRawModel load3DPositionsToRawModel(float[] positions) {
		// TODO Auto-generated method stub
		return null;
	}



}
