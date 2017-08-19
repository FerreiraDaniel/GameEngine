package com.dferreira.commons.waveFront;

import java.io.Serializable;

/**
 * Pointers to the properties of the a face of the polygon
 */
@SuppressWarnings("WeakerAccess")
public class PolygonalFace implements Serializable {


    @SuppressWarnings("unused")
    private static final long serialVersionUID = -1392812648568716201L;

    private final Integer vertexIndex;
    private final Integer textureIndex;
    private final Integer normalIndex;
    private final String groupName;
    private final String materialName;


    /**
     * Constructor with all the parameters of the face
     *
     * @param vertexIndex  Index of the vertex of the face
     * @param textureIndex Index of the texture of the face
     * @param normalIndex  Index of normal for this face
     * @param groupName    Name of the group that the face belongs
     * @param materialName Material that the material has if any
     */
    public PolygonalFace(Integer vertexIndex, Integer textureIndex, Integer normalIndex, String groupName,
                         String materialName) {
        this.vertexIndex = vertexIndex;
        this.textureIndex = textureIndex;
        this.normalIndex = normalIndex;
        this.groupName = groupName;
        this.materialName = materialName;
    }

    /**
     * @return Index of the vertex of the face
     */
    public Integer getVertexIndex() {
        return vertexIndex;
    }

    /**
     * @return Index of the texture of the face
     */
    public Integer getTextureIndex() {
        return textureIndex;
    }

    /**
     * @return Index of normal for this face
     */
    public Integer getNormalIndex() {
        return normalIndex;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @return the material
     */
    public String getMaterialName() {
        return materialName;
    }
}