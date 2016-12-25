package gameEngine.renderEngine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;

import gameEngine.models.AudioBuffer;
import gameEngine.models.AudioSource;
import gameEngine.models.RawModel;
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
	 * List of audio buffers in use by the game
	 */
	private List<Integer> audioBuffers;

	/**
	 * Decoder for ogg files
	 */
	private final OggDecoder oggDecoder;

	/**
	 * List of audio sources in use by the game
	 */
	private List<Integer> audioSources;

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
	 * Extension of vorbis files
	 */
	private final String OGG_EXTENSION = ".ogg";

	/**
	 * Constructor of the loader class
	 */
	public Loader() {
		this.vaos = new ArrayList<Integer>();
		this.vbos = new ArrayList<Integer>();
		this.textures = new ArrayList<Integer>();
		this.audioBuffers = new ArrayList<Integer>();
		this.audioSources = new ArrayList<Integer>();
		this.oggDecoder = new OggDecoder();
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
			Integer textureId;
			float textureWeight;
			ColorRGBA diffuseColor;

			if ((externalMaterial.getDiffuseTextureFileName() == null)
					|| ("".equals(externalMaterial.getDiffuseTextureFileName().trim()))) {
				textureId = 0;
				textureWeight = 0.0f;
				diffuseColor = (externalMaterial.getDiffuseColor() == null) ? new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f)
						: new ColorRGBA(externalMaterial.getDiffuseColor());
			} else {
				textureId = this.loadTexture(externalMaterial.getDiffuseTextureFileName());
				textureWeight = 1.0f;
				diffuseColor = new ColorRGBA(0.0f, 0.0f, 0.0f, 1.0f);
			}
			Material material = new Material(textureId);

			material.setTextureWeight(textureWeight);
			material.setDiffuseColor(diffuseColor);

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
	 * Load one audio file in a buffer
	 * 
	 * @param fileName
	 *            The file name of the file to load
	 * 
	 * @return The identifier of the buffer return by open GL
	 */
	public AudioBuffer loadSound(String fileName) {
		int bufferId = AL10.alGenBuffers();
		AudioBuffer audioBuffer = null;

		/**
		 * Was not possible to one buffer return AL false
		 */
		if (AL10.alGetError() != AL10.AL_NO_ERROR) {
			return null;
		}

		FileInputStream fin = null;
		BufferedInputStream bin = null;
		OggData oggFile = null;
		try {
			fin = new FileInputStream(RESOURCES_FOLDER + fileName + OGG_EXTENSION);
			bin = new BufferedInputStream(fin);
			oggFile = oggDecoder.getData(bin);

			if (oggFile == null) {
				// Was not possible read the file so releases the resources
				AL10.alDeleteBuffers(bufferId);
			} else {
				audioBuffers.add(bufferId);
				int oggFormat = oggFile.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
				AL10.alBufferData(bufferId, oggFormat, oggFile.data, oggFile.rate);
				// oggFile.dispose();
				audioBuffer = new AudioBuffer(bufferId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oggFile != null) {
				oggFile.data.clear();
				oggFile = null;
			}
			try {
				if (bin != null) {
					bin.close();
				}
				if (fin != null) {
					fin.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return audioBuffer;
	}

	/**
	 * 
	 * @param errorId
	 * 
	 * @return A string with description of the error
	 */
	private String getALErrorString(int errorId) {
		switch (errorId) {
		case AL10.AL_NO_ERROR:
			return "No Error token";
		case AL10.AL_INVALID_NAME:
			return "Invalid Name parameter";
		case AL10.AL_INVALID_ENUM:
			return "Invalid parameter";
		case AL10.AL_INVALID_VALUE:
			return "Invalid enum parameter value";
		case AL10.AL_INVALID_OPERATION:
			return "Illegal call";
		case AL10.AL_OUT_OF_MEMORY:
			return "Unable to allocate memory";
		default:
			return "";
		}
	}

	/**
	 * 
	 * @return If possible one audio source with everything set
	 */
	private AudioSource genAudioSource() {
		int sourceId = AL10.alGenSources();
		int errorId = AL10.alGetError();
		if (errorId == AL10.AL_NO_ERROR) {
			return new AudioSource(sourceId);
		} else {
			System.err.println(getALErrorString(errorId));
			return null;
		}
	}

	/**
	 * 
	 * @param numberOfSources
	 *            Number of audio sources to generate
	 * 
	 * @return A list of the audio sources generated
	 */
	public List<AudioSource> genAudioSources(int numberOfSources) {
		List<AudioSource> sourceLst = new ArrayList<AudioSource>();
		for (int i = 0; i < numberOfSources; i++) {
			AudioSource audioSource = genAudioSource();
			if (audioSource == null) {
				break;
			} else {
				sourceLst.add(audioSource);
			}
		}
		return sourceLst;
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
		// Release of audio sources
		for (int sourceId : this.audioSources) {
			AL10.alSourceStop(sourceId);
			AL10.alDeleteSources(sourceId);
		}

		// Release of audio buffers
		for (int buffer : this.audioBuffers) {
			AL10.alDeleteBuffers(buffer);
		}
		this.audioBuffers = null;
	}

}
