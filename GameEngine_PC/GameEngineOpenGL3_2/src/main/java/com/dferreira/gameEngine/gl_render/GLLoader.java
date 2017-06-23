package com.dferreira.commons.gl_render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.RenderAttributeEnum;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.IEnum;

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
	 * Number of components that make part of one vertex
	 */
	private final int VERTEX_SIZE = 3;

	/**
	 * Number of components that compose the coordinates of the texture
	 */
	private final int COORD_SIZE = 2;

	/**
	 * Number of components that compose the normal vector
	 */
	private final int NORMAL_SIZE = 3;

	/**
	 * Offset between following vertices (If we have any data between them
	 */
	private final int STRIDE = 0;

	/**
	 * Start offset where should start to read the data to put in the frame
	 */
	private final int START_OFFSET = 0;

	/**
	 * Specifies whether fixed-point data values should be normalized (GL_TRUE)
	 * or converted directly as fixed-point values (GL_FALSE) when they are
	 * accessed.
	 */
	private final boolean VERTEX_NORMALIZED = false;

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
	 * @param target
	 *            The target of the filter
	 * @param wrapParam
	 *            Parameter used in the wrap filters
	 */
	private void defineTextureFunctionFilters(int target, int wrapParam) {
		// The texture minify function is used whenever the pixel being textured
		// maps to an area greater than one texture element
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		// The texture magnification function is used when the pixel being
		// textured maps to an area less than or equal to one texture element
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		// Sets the wrap parameter for texture coordinate s
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, wrapParam);

		// Sets the wrap parameter for texture coordinate t
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, wrapParam);
	}

	/**
	 * @param repeat
	 *            Boolean that indicates if is to repeat or not the texture
	 * @return The value that should put in the filter of the texture
	 */
	private int getWrapFilters(boolean repeat) {
		return repeat ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE;
	}

	/**
	 * Load a texture in openGLES API
	 *
	 * @param textureData
	 *            The data of the texture to load
	 * @param repeat
	 *            Indicate that should repeat the texture if the polygon surpass
	 *            the size of texture
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
	 * @param textureEnum
	 *            Enum of the resource where the texture exists
	 * @param repeat
	 *            Indicate that should repeat the texture if the polygon surpass
	 *            the size of texture
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
	 * @param textureFileName
	 *            The name of the texture where the texture exists
	 * @param repeat
	 *            Indicate that should repeat the texture if the polygon surpass
	 *            the size of texture
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
	 * @param textureEnum
	 *            id of the resource where the texture exists
	 * @return The texture read from the file without any openGL bind
	 */
	@Override
	public TextureData getTextureData(TextureEnum textureEnum) {
		return this.resourceProvider.getResource(textureEnum);
	}

	/**
	 * Loads a cubic texture
	 *
	 * @param textures
	 *            The resources where should get the images of the cube
	 * @param repeat
	 *            Indicate that should repeat the texture if the polygon surpass
	 *            the size of texture
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

	/**
	 * Create a vertex array object
	 * 
	 * @return the identifier of the VAO created
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		this.vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	/**
	 * UnBind the current vertex array object
	 */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Convert on array of Integers in a buffer of Integers that can be used in
	 * openGL
	 * 
	 * @param data
	 *            array with data to put in the Integer Buffer the integer
	 *            buffer created
	 * 
	 * @return The integer buffer created
	 */
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * 
	 * @param indices
	 *            the indices to vertex buffer object
	 */
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	/**
	 * Convert on array of Floats in a buffer of Floats that can be used in
	 * openGL
	 * 
	 * @param data
	 *            array with data to put in the Float Buffer the float buffer
	 *            created
	 * 
	 * @return The integer buffer created
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/**
	 * Store a certain element to be used in the program shader
	 * 
	 * @param attributeNumber
	 *            the id of the attribute to load in the program shader
	 * @param coordinateSize
	 *            Number of components of the attribute to store
	 * 
	 * @param data
	 *            Data to be store
	 */
	private void storeDataInAttributeList(IEnum attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		// Bind the VBO just created
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber.getValue(), coordinateSize, GL11.GL_FLOAT, VERTEX_NORMALIZED, STRIDE,
				START_OFFSET);
		// UnBind the current VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Load a list of positions to VAO
	 * 
	 * @param positions
	 *            Positions to load
	 * @param dimensions
	 *            Number of components that the positions has
	 * 
	 * @return The rawModel pointing to the created VAO
	 */
	private GLRawModel loadPositionsToVAO(float[] positions, int dimensions,
			HashMap<RenderAttributeEnum, IEnum> attributes) {
		int vaoId = createVAO();
		GLRawModel rawModel = new GLRawModel(vaoId, positions.length / dimensions, attributes);
		this.storeDataInAttributeList(rawModel.getAttribute(RenderAttributeEnum.position), dimensions, positions);
		unbindVAO();
		return rawModel;
	}

	/**
	 * Load from a shape to one equivalent IRawModel
	 *
	 * @param shape
	 *            The shape to load
	 * @param attributes
	 *            Map of attributes associated with the model
	 * @return A raw model with information loaded
	 */
	@Override
	public IRawModel loadToRawModel(IShape shape, HashMap<RenderAttributeEnum, IEnum> attributes) {
		int vaoID = createVAO();

		bindIndicesBuffer(shape.getIndices());

		IEnum positionEnum = attributes.get(RenderAttributeEnum.position);
		IEnum textureCoordsEnum = attributes.get(RenderAttributeEnum.textureCoords);
		IEnum normalEnum = attributes.get(RenderAttributeEnum.normal);

		storeDataInAttributeList(positionEnum, VERTEX_SIZE, shape.getVertices());
		storeDataInAttributeList(textureCoordsEnum, COORD_SIZE, shape.getTextureCoords());
		storeDataInAttributeList(normalEnum, NORMAL_SIZE, shape.getNormals());
		unbindVAO();

		return new GLRawModel(vaoID, shape.getIndices().length, attributes);
	}

	/**
	 * Load a list of 2D positions to IRawModel
	 *
	 * @param positions
	 *            Positions to load
	 * @param attributes
	 *            List of attributes associated with the model
	 * @return The model loaded
	 */
	@Override
	public IRawModel load2DPositionsToRawModel(float[] positions, HashMap<RenderAttributeEnum, IEnum> attributes) {
		int dimensions = 2;
		return loadPositionsToVAO(positions, dimensions, attributes);
	}

	/**
	 * Load a list of 3D positions to IRawModel
	 *
	 * @param positions
	 *            Positions to load
	 * @param attributes
	 *            List of attributes associated with the model
	 * @return The rawModel pointing to the positions
	 */
	@Override
	public IRawModel load3DPositionsToRawModel(float[] positions, HashMap<RenderAttributeEnum, IEnum> attributes) {
		int dimensions = 3;
		return loadPositionsToVAO(positions, dimensions, attributes);
	}

	
	/**
	 * A bit o memory cleaning
	 */
	@Override
	public void dispose() {
		// Release of vaos
		for (Integer vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		this.vaos = null;

		// Release of vbos
		for (Integer vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		this.vbos = null;
		// Textures
		for (Integer texture : textures) {
			GL11.glDeleteTextures(texture);
		}
		this.textures = null;
	}
}
