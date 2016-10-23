import Foundation

/**
* Represents one entity with a shape
* defined by one waveFront file
*/
public class WfObject : NSObject, IShape {
    
    private var _vertices : UnsafeMutablePointer<Float> ;
    private var _countVertices : Int;
    private var _textureCoordinates: UnsafeMutablePointer<Float> ;
    private var _countTextureCoordinates :  Int;
    private var _normals: UnsafeMutablePointer<Float> ;
    private var _countNormals : Int;
    private var _indices : UnsafeMutablePointer<ushort>;
    private var _countIndices : Int = 0;
    
    /**
    * Inicializes the waveFront file
    */
    public init(
        aVertices : Array<Float>,
        aTextureCoordinates : Array<Float>,
        aNormals : Array<Float>,
        aIndices : Array<Int>)
    {
        
        //Allocate and fill the vertices memory
        self._vertices = UnsafeMutablePointer<Float>(calloc(aVertices.count, sizeof(CFloat)));
        self._countVertices = aVertices.count;
        
        //Copy vertices one by one
        for i in 0 ..< aVertices.count {
            self._vertices[i] = aVertices[i];
        }
        
        
        //Allocate and fill the texture memory
        self._textureCoordinates = UnsafeMutablePointer<Float>(calloc(aTextureCoordinates.count, sizeof(CFloat)));
        self._countTextureCoordinates = aTextureCoordinates.count;
        for i in 0 ..< aTextureCoordinates.count {
            self._textureCoordinates[i] = aTextureCoordinates[i];
        }
        
        
        //Allocate and fill the normals memory
        self._normals = UnsafeMutablePointer<Float>(calloc(aNormals.count, sizeof(CFloat)));
        self._countNormals = aNormals.count;
        for i in 0 ..< self._countNormals {
            self._normals[i] = aNormals[i];
        }
        
        
        //Allocate and fill the indices memory
        self._indices = UnsafeMutablePointer<ushort>(calloc(aIndices.count, sizeof(CInt)));
        self._countIndices = aIndices.count;
        for i in 0 ..< self._countIndices {
            self._indices[i] = UInt16(aIndices[i]);
        }
        super.init();
    }
    
    /**
    * Dinicializes the waveFront file
    */
    deinit {
        free(self._vertices);
        free(self._textureCoordinates);
        free(self._normals);
        free(self._indices);
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
        return self._countVertices;
    }
    
    /**
    * @return the Coordinates of the textures of the shape
    */
    public func getTextureCoords()  -> UnsafeMutablePointer<Float> {
        return self._textureCoordinates;
    }
    
    /*
    Number of the texture coordinates
    */
    public func countTextureCoords() -> Int {
        return self._countTextureCoordinates;
    }
    
    /**
    *
    * @return the normal vectors that make the shape
    */
    public func getNormals() -> UnsafeMutablePointer<Float> {
        return self._normals;
    }
    
    /*
    * Number of normal that the shape has
    */
    public func countNormals() -> Int {
        return self._countNormals;
    }
    
    /**
    * @return The indices of the vertices that make the shape
    */
    public func getIndices() -> UnsafeMutablePointer<ushort> {
        return self._indices;
    }
    
    /*
    Number of indices that the shape has
    */
    public func countIndices() -> Int {
        return self._countIndices;
    }
}
