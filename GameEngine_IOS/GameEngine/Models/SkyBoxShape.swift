import Foundation

public class SkyBoxShape : NSObject, IShape {
    
    private var _vertices : UnsafeMutablePointer<Float> ;
    
    private let SKY_BOX_NUMBER_OF_ELEMENTS : Int = 108;
    private static let SIZE : Float =  500.0
    
    private let skyBoxVertexData : Array<Float> = [
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
        self._vertices = UnsafeMutablePointer<Float>(calloc(skyBoxVertexData.count, sizeof(CFloat)));
        
        //Copy vertices one by one
        for(var i = 0;i < skyBoxVertexData.count;i++) {
            self._vertices[i] = skyBoxVertexData[i];
        }
    }
    
    /**
    * Deinitialization of the sky box shape
    */
    deinit {
        free(self._vertices);
    }
    
    /**
    * @return the vertices of the shape
    */
    public func getVertices() -> UnsafeMutablePointer<Float> {
        return self._vertices;
    }
    
    /**
    * @return number of vertices that make the shape
    */
    public func countVertices() -> Int {
        return SKY_BOX_NUMBER_OF_ELEMENTS;
    }
    
    /**
    * @return the Coordinates of the textures of the shape
    */
    public func getTextureCoords()  -> UnsafeMutablePointer<Float> {
        return nil;
    }
    
    /*
    Number of the texture coordinates
    */
    public func countTextureCoords() -> Int {
        return 0;
    }
    
    /**
    *
    * @return the normal vectors that make the shape
    */
    public func getNormals() -> UnsafeMutablePointer<Float> {
        return nil;
    }
    
    /*
    * Number of normal that the shape has
    */
    public func countNormals() -> Int {
        return 0;
    }
    
    /**
    * @return The indices of the vertices that make the shape
    */
    public func getIndices() -> UnsafeMutablePointer<ushort> {
        return nil;
    }
    
    /*
    Number of indices that the shape has
    */
    public func countIndices() -> Int {
        return 0;
    }
}