package com.dferreira.commons.gl_render;

import com.dferreira.commons.generic_render.IRawModel;

/**
 * Represents one raw model of one entity
 */
public class GLRawModel implements IRawModel {

	/**
	 * Identifier of the vertex array object of the raw model
	 */
	private final int vaoId;

	/**
	 * Number of vertices of the raw model
	 */
	private final int vertexCount;

	/**
	 * Constructor of the raw model
	 * 
	 * @param vaoId
	 *            The identifier of vertex array object assigned by openGL
	 * @param vertexCount
	 *            number of vertex
	 */
	public GLRawModel(int vaoId, int vertexCount) {
		super();
		this.vaoId = vaoId;
		this.vertexCount = vertexCount;
	}

	/**
	 * @return the The identifier of vertex array object assigned by openGL
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

	/**
	 * Clean the memory used by the model
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
