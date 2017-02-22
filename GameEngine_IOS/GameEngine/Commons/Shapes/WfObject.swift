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
    private var _groupName : String?;
    private var _material : IExternalMaterial?;
    
    
    /**
     *  Get one array of floats and returns one pointer to native memory of it
     */
    private static func floatArray2Pointer(floatArray : Array<Float>) -> UnsafeMutablePointer<Float>
    {
        let countElements : Int = floatArray.count;
        let pointer = UnsafeMutablePointer<Float>.alloc(countElements);
        
        //Copy vertices one by one
        for i in 0 ..< countElements {
            pointer[i] = floatArray[i];
        }
        return pointer
    }
    
    /**
     *  Get one array of ints and returns one pointer to native memory of it
     */
    private static func intArray2Pointer(intArray : Array<Int>) -> UnsafeMutablePointer<ushort>
    {
        let countElements : Int = intArray.count;
        let pointer = UnsafeMutablePointer<ushort>.alloc(countElements);
        
        //Copy vertices one by one
        for i in 0 ..< countElements {
            pointer[i] = UInt16(intArray[i]);
        }
        return pointer
    }
    
    /**
     * Inicializes the waveFront file
     */
    public init(
        _ aVertices : Array<Float>,
          _ aTextureCoordinates : Array<Float>,
            _ aNormals : Array<Float>,
              _ aIndices : Array<Int>,
                _ groupName : String?,
                  _ material : IExternalMaterial?
        )
    {
        
        //Allocate and fill the vertices memory
        self._vertices = WfObject.floatArray2Pointer(aVertices);
        self._countVertices = aVertices.count;
        
        //Allocate and fill the texture memory
        self._textureCoordinates = WfObject.floatArray2Pointer(aTextureCoordinates);
        self._countTextureCoordinates = aTextureCoordinates.count;
        
        //Allocate and fill the normals memory
        self._normals = WfObject.floatArray2Pointer(aNormals);
        self._countNormals = aNormals.count;
        
        //Allocate and fill the indices memory
        self._indices = WfObject.intArray2Pointer(aIndices);
        self._countIndices = aIndices.count;
        
        //Assigns the name of the group to the internal attribute
        self._groupName = groupName
        self._material = material
        
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
    
    /**
     * @return the groupName Name of the group wish belongs
     */
    public func getGroupName() -> String? {
        return self._groupName;
    }
    
    /**
     *
     * @return The material associated with shape
     */
    public func getMaterial() -> IExternalMaterial? {
        return self._material;
    }
}
