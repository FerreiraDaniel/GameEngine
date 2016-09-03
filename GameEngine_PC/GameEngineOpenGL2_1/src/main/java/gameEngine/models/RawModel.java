package gameEngine.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Represents one raw model of one entity
 */
public class RawModel {

	/**
	 * Buffer that supports the vertices of the object
	 */
	private final FloatBuffer vertexBuffer;
	
	/**
	 * Number of vertices of the raw model
	 */
	private int vertexCount;

	/**
	 * Buffer that supports the indexes of the object
	 */
	private final IntBuffer indexBuffer;

	/**
	 * Buffer that supports the normals of the object
	 */
	private final FloatBuffer normalBuffer;

	/**
	 * Buffer that supports the coordinates of the textures of the object
	 */
	private final FloatBuffer texCoordinates;

	/**
	 * Constructor of a raw model
	 * 
	 * @param vertexBuffer
	 *            Buffer that describes the position of the vertices
	 * @param indexBuffer
	 *            Buffer that describes which vertices will be used
	 * @param normalBuffer
	 *            Buffer that describes which normal will be user
	 * @param texCoordinates
	 *            Coordinates of the textures in the model
	 */
	public RawModel(FloatBuffer vertexBuffer, IntBuffer indexBuffer, FloatBuffer normalBuffer,
			FloatBuffer texCoordinates) {
		super();
		this.vertexBuffer = vertexBuffer;
		this.indexBuffer = indexBuffer;
		this.normalBuffer = normalBuffer;
		this.texCoordinates = texCoordinates;
	}

	/**
	 * Constructor of a raw model
	 * 
	 * @param vertexBuffer
	 *            Buffer that describes the position of the vertices
	 * @param vertexCount
	 * 			  Number of vertices that compose the raw model
	 */
	public RawModel(FloatBuffer vertexBuffer, int vertexCount) {
		super();
		this.vertexBuffer = vertexBuffer;
		this.vertexCount = vertexCount;
		this.indexBuffer = null;
		this.normalBuffer = null;
		this.texCoordinates = null;
	}
	
	
	/**
	 *
	 * @return The buffer that describes the positions of the vertices
	 */
	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	/**
	 *
	 * @return The buffer that describes the vertices that are going to be used
	 *         to render the model
	 */
	public IntBuffer getIndexBuffer() {
		return indexBuffer;
	}

	/**
	 *
	 * @return The Coordinates of the textures
	 */
	public FloatBuffer getTexCoordinates() {
		return texCoordinates;
	}

	/**
	 * 
	 * @return The normals of the entity
	 */
	public FloatBuffer getNormalBuffer() {
		return normalBuffer;
	}
	
	/**
	 * @return the number of vertex
	 */
	public int getVertexCount() {
		return vertexCount;
	}
}
