import Foundation

public class WfObject : NSObject, IShape {
    
    private var _vertices : UnsafeMutablePointer<Float> ;
    private var _countVertices : Int;
    private var _textureCoordinates: UnsafeMutablePointer<Float> ;
    private var _countTextureCoordinates :  Int;
    private var _normals: UnsafeMutablePointer<Float> ;
    private var _countNormals : Int;
    private var _indices : UnsafeMutablePointer<ushort>;
    private var _countIndices : Int;
    
    /**
    * Inicializes the waveFront file
    */
    public init(
        aVertices : Array<Float>,
        aTextureCoordinates : UnsafePointer<Float>,
        aCountTextureCoordinates : Int,
        aNormals : UnsafePointer<Float>,
        aCountNormals : Int,
        aIndices : UnsafePointer<ushort>,
        aCountIndices : Int)
    {
        //Allocate and fill the vertices memory
        self._vertices = UnsafeMutablePointer<Float>(calloc(aVertices.count, sizeof(CFloat)));
        self._countVertices = aVertices.count;
        
        for(var i = 0;i < aVertices.count;i++) {
            self._vertices[i] = aVertices[i];
        }
        
        
        //Allocate and fill the texture memory
        let countTextureBytes : Int = sizeof(CFloat) * aCountTextureCoordinates;
        self._textureCoordinates = UnsafeMutablePointer<Float>(calloc(aCountTextureCoordinates, sizeof(CFloat)));
        self._countTextureCoordinates = aCountTextureCoordinates;
        memcpy(self._textureCoordinates, aTextureCoordinates, countTextureBytes);
        
        
        //Allocate and fill the normals memory
        let countNormalsBytes = sizeof(CFloat) * aCountNormals;
        self._normals = UnsafeMutablePointer<Float>(calloc(aCountNormals, sizeof(CFloat)));
        self._countNormals = aCountNormals;
        memcpy(self._normals, aNormals, countNormalsBytes);
        
        
        //Allocate and fill the indices memory
        let countIndicesBytes = sizeof(CInt) * aCountIndices;
        self._indices = UnsafeMutablePointer<ushort>(calloc(aCountIndices, sizeof(CInt)));
        self._countIndices = aCountIndices;
        memcpy(self._indices, aIndices, countIndicesBytes);
        
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
    public func countVertices() -> Int32 {
        return Int32(self._countVertices);
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
    public func countTextureCoords() -> Int32 {
        return Int32(self._countTextureCoordinates);
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
    public func countNormals() -> Int32 {
        return Int32(self._countNormals);
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
    public func countIndices() -> Int32 {
        return Int32(self._countIndices);
    }
}