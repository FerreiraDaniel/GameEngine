import Foundation

/**
* Points to the properties of the a face of the polygon
*/
public class PolygonalFaceSwift : NSObject {
    
    /**
    *
    * Index of the vertex of the face
    */
    var vertexIndex : ushort
    
    /**
    *
    * Index of the texture of the face
    */
    var textureIndex : ushort
    
    
    /**
    *
    * Index of normal for this face
    */
    var normalIndex : ushort
    
    
    /**
    * Initialize with all the parameters of the face
    *
    * @param vertexIndex  Index of the vertex of the face
    * @param textureIndex Index of the texture of the face
    * @param normalIndex  Index of normal for this face
    */
    public init(vertexIndex : ushort, textureIndex : ushort, normalIndex : ushort) {
        self.vertexIndex = vertexIndex
        self.textureIndex = textureIndex
        self.normalIndex = normalIndex
    }
    
}