package gameEngine.renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.waveFront.RGBAColorEnum;

import gameEngine.models.RawModel;
import gameEngine.models.complexEntities.LightingComponent;
import gameEngine.models.complexEntities.Material;
import gameEngine.shaders.entities.TEntityAttribute;

public class Loader {

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
	 * Folder where the resources are
	 */
	private final String RESOURCES_FOLDER = "res/";

	/**
	 * Extension of the png files
	 */
	private final String PNG_EXTENSION = ".png";

	/**
	 * Constructor of the loader class
	 */
	public Loader() {
		this.vaos = new ArrayList<Integer>();
		this.vbos = new ArrayList<Integer>();
		this.textures = new ArrayList<Integer>();
	}

	/**
	 * Load to a new vertex array object
	 * 
	 * @param positions
	 *            Positions to load
	 * @param textureCoords
	 *            Texture coordinates to load
	 * @param normals
	 *            Normals of the entity to load
	 * @param indices
	 *            Indices to be load
	 * 
	 * @return A row model with information loaded
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();

		bindIndicesBuffer(indices);
		storeDataInAttributeList(TEntityAttribute.position, VERTEX_SIZE, positions);
		storeDataInAttributeList(TEntityAttribute.textureCoords, COORD_SIZE, textureCoords);
		storeDataInAttributeList(TEntityAttribute.normal, NORMAL_SIZE, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	/**
	 * Load the diffuse component of the material
	 *
	 * @param externalMaterial
	 *            A reference to the external material to load
	 * @return The diffuse lighting component of the material
	 */
	private LightingComponent loadDiffuseLighting(IExternalMaterial externalMaterial) {
		Integer textureId;
		float textureWeight;
		ColorRGBA color;
		String textureFileName = externalMaterial.getDiffuseTextureFileName();

		if (Utils.isEmpty(textureFileName)) {
			textureId = 0;
			textureWeight = 0.0f;
			color = (externalMaterial.getDiffuseColor() == null) ? RGBAColorEnum.transparent.toRGBA()
					: new ColorRGBA(externalMaterial.getDiffuseColor());
			textureFileName = null;
		} else {
			textureId = this.loadTexture(externalMaterial.getDiffuseTextureFileName());
			textureWeight = 1.0f;
			color = RGBAColorEnum.transparent.toRGBA();
		}

		LightingComponent diffuse = new LightingComponent();
		diffuse.setTextureWeight(textureWeight);
		diffuse.setColor(color);
		diffuse.setFilename(textureFileName);
		diffuse.setTextureId(textureId);

		return diffuse;
	}

	/**
	 * 
	 * @param externalMaterial
	 *            A reference to an external material with all the information
	 *            needed to create a material
	 * 
	 * @return The material loaded
	 */
	public Material loadMaterial(IExternalMaterial externalMaterial) {
		if (externalMaterial == null) {
			return null;
		} else {
			Material material = new Material();

			LightingComponent diffuse = loadDiffuseLighting(externalMaterial);
			material.setDiffuse(diffuse);

			return material;
		}
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
	private RawModel loadPositionsToVAO(float[] positions, int dimensions) {
		int vaoId = createVAO();
		this.storeDataInAttributeList(TEntityAttribute.position, dimensions, positions);
		unbindVAO();
		return new RawModel(vaoId, positions.length / dimensions);
	}

	/**
	 * Load a list of 2D positions to VAO
	 * 
	 * @param positions
	 *            Positions to load
	 * 
	 * @return The rawModel pointing to the created VAO
	 */
	public RawModel load2DPositionsToVAO(float[] positions) {
		int dimensions = 2;
		return loadPositionsToVAO(positions, dimensions);
	}

	/**
	 * Load a list of 3D positions to VAO
	 * 
	 * @param positions
	 *            Positions to load
	 * 
	 * @return The rawModel pointing to the created VAO
	 */
	public RawModel load3DPositionsToVAO(float[] positions) {
		int dimensions = 3;
		return loadPositionsToVAO(positions, dimensions);
	}

	/**
	 * When loads one texture defines that by default should zoom in/out it
	 * 
	 * @param target
	 *            the target of the filter
	 */
	private void defineTextureFunctionFilters(int target) {
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	}

	/**
	 * 
	 * @param fileName
	 *            Name of the file to load without the .png extension in the end
	 * 
	 * @return Identifier of the texture loaded
	 */
	public Integer loadTexture(String fileName) {
		String fName = (fileName.contains(PNG_EXTENSION)) ? fileName : fileName + PNG_EXTENSION;
		TextureData textureData = LoadUtils.loadTexture(RESOURCES_FOLDER + fName);
		if (textureData == null) {
			return null;
		} else {
			int textureId = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
					GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());

			defineTextureFunctionFilters(GL11.GL_TEXTURE_2D);
			textures.add(textureId);
			return textureId;
		}
	}

	/**
	 * Loads a cubic texture
	 * 
	 * @param fileNames
	 *            Names of the file to load without the .png extension in the
	 *            end
	 * 
	 * @return Identifier of the texture cubic texture loaded
	 */
	public Integer loadTCubeMap(String[] fileNames) {
		if ((fileNames == null) || (fileNames.length == 0)) {
			return null;
		}
		int[] cubicTextureTargets = new int[] { GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
				GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };

		int textureId = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureId);

		for (int i = 0; i < cubicTextureTargets.length; i++) {
			String fileName = fileNames[i];

			TextureData textureData = LoadUtils.loadTexture(RESOURCES_FOLDER + fileName + PNG_EXTENSION);
			if (textureData == null) {
				return null;
			} else {
				int target = cubicTextureTargets[i];
				GL11.glTexImage2D(target, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
						GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());
			}
		}
		defineTextureFunctionFilters(GL13.GL_TEXTURE_CUBE_MAP);
		textures.add(textureId);
		return textureId;
	}

	/**
	 * Loads the data of a texture without bind
	 * 
	 * @param fileName
	 *            Name of the file to load without the .png extension in the end
	 *
	 * @return The texture read from the file without any openGL bind
	 */
	public TextureData getTextureData(String fileName) {
		TextureData textureData = LoadUtils.loadTexture(RESOURCES_FOLDER + fileName + PNG_EXTENSION);
		return textureData;
	}

	/**
	 * Create a vertex array object
	 * 
	 * @return the identifier of the VAO created
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
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
	 * UnBind the current vertex array object
	 */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
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
	 * A bit o memory cleaning
	 */
	public void cleanUp() {
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
