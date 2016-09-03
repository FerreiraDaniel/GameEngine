package com.dferreira.commons.waveFront;

import java.io.Serializable;

/**
 * Points to the properties of the a face of the polygon
 */
public class PolygonalFace implements Serializable{


    private static final long serialVersionUID = -1392812648568716201L;

    private final Long vertexIndex;
    private final Long textureIndex;
    private final Long normalIndex;


    /**
     * Constructor with all the parameters of the face
     *
     * @param vertexIndex  Index of the vertex of the face
     * @param textureIndex Index of the texture of the face
     * @param normalIndex  Index of normal for this face
     */
    public PolygonalFace(Long vertexIndex, Long textureIndex, Long normalIndex) {
        this.vertexIndex = vertexIndex;
        this.textureIndex = textureIndex;
        this.normalIndex = normalIndex;
    }

    /**
     *
     * @return Index of the vertex of the face
     */
    public Long getVertexIndex() {
        return vertexIndex;
    }

    /**
     *
     * @return Index of the texture of the face
     */
    public Long getTextureIndex() {
        return textureIndex;
    }

    /**
     *
     * @return Index of normal for this face
     */
    public Long getNormalIndex() {
        return normalIndex;
    }
}