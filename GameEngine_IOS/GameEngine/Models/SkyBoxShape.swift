import Foundation

/**
 * Represents the sky box in the 3D world
 */
open class SkyBoxShape : NSObject, IShape {
    
    fileprivate var _vertices : UnsafeMutablePointer<Float>? ;
    
    fileprivate let SKY_BOX_NUMBER_OF_ELEMENTS : Int = 108;
    fileprivate static let SIZE : Float =  500.0
    
    /**
     * Vertices of the sky box
     */
    fileprivate let skyBoxVertexData : Array<Float> = [
        -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
        -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
        
        -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE,
        -SIZE, SIZE,
        
        SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE,
        -SIZE,
        
        -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE,
        SIZE,
        
        -SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
        -SIZE,
        
        -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE,
        -SIZE, SIZE
    ];
    
    /**
     * Initiator of the sky box shape
     */
    public override init() {
        //Allocate and fill the vertices memory
        self._vertices = UnsafeMutablePointer<Float>.allocate(capacity: skyBoxVertexData.count);
        
        //Copy vertices one by one
        for i in 0 ..< skyBoxVertexData.count {
            self._vertices![i] = skyBoxVertexData[i];
        }
    }
    
    /**
     * Deinitialization of the sky box shape
     */
    deinit {
        if(self._vertices != nil)
        {
            free(self._vertices!);
        }
    }
    
    /**
     * @return the vertices of the shape
     */
    open func getVertices() -> UnsafeMutablePointer<Float>? {
        return self._vertices;
    }
    
    /**
     * @return number of vertices that make the shape
     */
    open func countVertices() -> Int {
        return SKY_BOX_NUMBER_OF_ELEMENTS;
    }
    
    /**
     * @return the Coordinates of the textures of the shape
     */
    open func getTextureCoords()  -> UnsafeMutablePointer<Float>? {
        return nil;
    }
    
    /**
     *Number of the texture coordinates
     */
    open func countTextureCoords() -> Int {
        return 0;
    }
    
    /**
     *
     * @return the normal vectors that make the shape
     */
    open func getNormals() -> UnsafeMutablePointer<Float>? {
        return nil;
    }
    
    /*
     * Number of normal that the shape has
     */
    open func countNormals() -> Int {
        return 0;
    }
    
    /**
     * @return The indices of the vertices that make the shape
     */
    open func getIndices() -> UnsafeMutablePointer<ushort>? {
        return nil;
    }
    
    /*
     Number of indices that the shape has
     */
    open func countIndices() -> Int {
        return 0;
    }
    
    /**
     * @return the groupName Name of the group wish belongs
     */
    open func getGroupName() -> String? {
        return nil;
    }
    
    /**
     *
     * @return The material that is associated with shape
     */
    open func getMaterial() -> IExternalMaterial? {
        return nil;
    }
}
