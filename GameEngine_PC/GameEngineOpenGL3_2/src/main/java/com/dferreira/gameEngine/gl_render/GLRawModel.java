package com.dferreira.gameEngine.gl_render;

import java.util.HashMap;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.RenderAttributeEnum;

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
	 * List of attributes associated with the model
	 */
	private final HashMap<RenderAttributeEnum, IEnum> attributes;

	/**
	 * Constructor of the raw model
	 * 
	 * @param vaoId
	 *            The identifier of vertex array object assigned by openGL
	 * @param vertexCount
	 *            number of vertex
	 * @param attributes
	 *            List of attributes associated with the model
	 */
	public GLRawModel(int vaoId, int vertexCount, HashMap<RenderAttributeEnum, IEnum> attributes) {
		super();
		this.vaoId = vaoId;
		this.vertexCount = vertexCount;
		this.attributes = attributes;
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
     * Takes the model and the render attribute and returns the corresponding IEnum
     *
     * @param renderAttribute The attribute to fetch
     * @return Enumeration of the render attribute
     */
    public IEnum getAttribute(RenderAttributeEnum renderAttribute) {
        return this.attributes.get(renderAttribute);
    }

	/**
	 * Clean the memory used by the model
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
