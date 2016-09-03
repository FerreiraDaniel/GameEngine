package gameEngine.models;

/**
 * Represents one raw model of one entity
 */
public class RawModel {

	/**
	 * Identifier of the vertex array object of the raw model
	 */
	private int vaoId;

	/**
	 * Number of vertices of the raw model
	 */
	private int vertexCount;

	/**
	 * Constructor of the raw model
	 * 
	 * @param the
	 *            vaoId assigned by openGL
	 * @param the
	 *            number of vertex
	 */
	public RawModel(int vaoId, int vertexCount) {
		super();
		this.vaoId = vaoId;
		this.vertexCount = vertexCount;
	}

	/**
	 * @return the vaoId assigned by openGL
	 */
	public int getVaoId() {
		return vaoId;
	}

	/**
	 * @return the number of vertex
	 */
	public int getVertexCount() {
		return vertexCount;
	}

}