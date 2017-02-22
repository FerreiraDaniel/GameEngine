import Foundation

/**
 * Represents the 2D GUI
 */
public class GuiShape : NSObject, IShape {
    private var _vertices : UnsafeMutablePointer<Float> ;
    
    public static let SIZE : Float = 1.0
    private let GUI_NUMBER_OF_ELEMENTS : Int = 8;
    
    /**
     * Vertices of the GUI quad
     */
    private let guiVertexData : Array<Float> = [
        -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, SIZE, -SIZE
    ];
    
    
    /**
     * Initiator of the GUI shape
     */
    public override init() {
        //Allocate and fill the vertices memory
        self._vertices = UnsafeMutablePointer<Float>.alloc(guiVertexData.count);
        
        //Copy vertices one by one
        for i in 0 ..< guiVertexData.count {
            self._vertices[i] = guiVertexData[i];
        }
    }
    
    /**
     * Deinitialization of the gui shape
     */
    deinit {
        free(self._vertices);
    }
    
    
    /**
     *
     * @return The vertices of the quad
     */
    public func getVertices() -> UnsafeMutablePointer<Float> {
        return self._vertices;
    }
    
    /**
     * @return number of vertices that make the shape
     */
    public func countVertices() -> Int {
        return GUI_NUMBER_OF_ELEMENTS;
    }
    
    /**
     * @return the Coordinates of the textures of the shape
     */
    public func getTextureCoords()  -> UnsafeMutablePointer<Float> {
        return nil;
    }
    
    /**
     *Number of the texture coordinates
     */
    public func countTextureCoords() -> Int {
        return 0;
    }
    
    /**
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
    
    /**
     * @return the groupName Name of the group wish belongs
     */
    public func getGroupName() -> String? {
        return nil;
    }
    
    /**
     *
     * @return The material that is associated with shape
     */
    public func getMaterial() -> IExternalMaterial? {
        return nil;
    }
}
