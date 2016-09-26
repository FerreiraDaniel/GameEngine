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
    
    
    /**
    * Initialize with all the parameters of the face
    *
    * @param vertexIndex  Index of the vertex of the face
    * @param textureIndex Index of the texture of the face
    * @param normalIndex  Index of normal for this face
    */
    public init(vertexIndex : Int, textureIndex : Int, normalIndex : Int) {
        self.vertexIndex = vertexIndex
        self.textureIndex = textureIndex
        self.normalIndex = normalIndex
    }
    
}