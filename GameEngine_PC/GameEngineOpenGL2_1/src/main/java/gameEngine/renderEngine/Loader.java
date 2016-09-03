package gameEngine.renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.models.TextureData;

import gameEngine.models.RawModel;

/**
 * Load the data to render the scene
 */
public class Loader {

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
	}

	/**
	 * Load to a new vertex array object
	 * 
	 * @param positions
	 *            Positions to load
	 * @param textureCoords
	 *            Texture coordinates to load
	 * @param normals
	 *            Normals of the model to load
	 * @param indices
	 *            Indices to be load
	 * 
	 * @return A row model with information loaded
	 */
	public RawModel loadToRawModel(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
		IntBuffer indexBuffer = storeDataInIntBuffer(indices);
		FloatBuffer normalBuffer = storeDataInFloatBuffer(normals);
		FloatBuffer texCoordinatesBuffer = storeDataInFloatBuffer(textureCoords);
		return new RawModel(vertexBuffer, indexBuffer, normalBuffer, texCoordinatesBuffer);
	}

	/**
	 * Load to a new vertex array object
	 * 
	 * @param positions
	 *            Positions to load
	 * @param dimensions
	 * 			  Dimensions of the positions to load
	 */
	private RawModel loadPositionsToRawModel(float[] positions, int dimensions) {
		FloatBuffer vertexBuffer = storeDataInFloatBuffer(positions);
		return new RawModel(vertexBuffer, positions.length / dimensions);
	}

	/**
	 * Load a list of 3D positions to RawModel
	 * 
	 * @param positions
	 *            Positions to load
	 * 
	 * @return The rawModel pointing to the positions
	 */
	public RawModel load3DPositionsToRawModel(float[] positions) {
		int dimensions = 3;
		return loadPositionsToRawModel(positions, dimensions);
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
		TextureData textureData = LoadUtils.loadTexture(RESOURCES_FOLDER + fileName + PNG_EXTENSION);
		if (textureData == null) {
			return null;
		} else {
			int textureId = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0,
					GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.getBuffer());

			defineTextureFunctionFilters(GL11.GL_TEXTURE_2D);
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

			System.out.println(fileName);
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
		return textureId;
	}

	/**
	 * A bit o memory cleaning
	 */
	public void cleanUp() {
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
	 * @return The float buffer created
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
