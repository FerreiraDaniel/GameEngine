import Foundation

/**
 * Points to the properties of the a face of the polygon
 */
public class PolygonalFace : NSObject {
    
    /**
     *
     * Index of the vertex of the face
     */
    var vertexIndex : Int
    
    /**
     *
     * Index of the texture of the face
     */
    var textureIndex : Int
    
    
    /**
     *
     * Index of normal for this face
     */
    var normalIndex : Int
    
    
    var     groupName : String
    var  materialName : String
    
    /**
     * Initialize with all the parameters of the face
     *
     * @param vertexIndex    Index of the vertex of the face
     * @param textureIndex   Index of the texture of the face
     * @param normalIndex    Index of normal for this face
     * @param groupName      Name of the group that the face belongs
     * @param materialName   Material that the material has if any
     */
    public init(vertexIndex : Int, textureIndex : Int, normalIndex : Int, groupName : String, materialName : String) {
        self.vertexIndex = vertexIndex
        self.textureIndex = textureIndex
        self.normalIndex = normalIndex
        self.groupName = groupName;
        self.materialName = materialName;
    }
    
}
