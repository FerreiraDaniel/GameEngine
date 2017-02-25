import Foundation

/**
 * Protocol that should be implemented for the different shapes available
 */
public protocol IShape {
    
    /**
     * @return the vertices of the shape
     */
    func getVertices() -> UnsafeMutablePointer<Float>?
    
    /**
     * @return number of vertices that make the shape
     */
    func countVertices() -> Int
    
    /**
     * @return the Coordinates of the textures of the shape
     */
    func getTextureCoords()  -> UnsafeMutablePointer<Float>?
    
    /*
     Number of the texture coordinates
     */
    func countTextureCoords() -> Int
    
    /**
     *
     * @return the normal vectors that make the shape
     */
    func getNormals() -> UnsafeMutablePointer<Float>?
    
    /*
     * Number of normal that the shape has
     */
    func countNormals() -> Int
    
    /**
     * @return The indices of the vertices that make the shape
     */
    func getIndices() -> UnsafeMutablePointer<ushort>?
    
    /*
     Number of indices that the shapa has
     */
    func countIndices() -> Int
    
    /**
     * @return the groupName Name of the group wish belongs
     */
    func getGroupName() -> String?;
    
    /**
     *
     * @return The material that is associated with shape
     */
    func getMaterial() -> IExternalMaterial?;
    
}
