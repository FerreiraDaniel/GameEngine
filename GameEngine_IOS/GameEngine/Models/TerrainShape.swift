import Foundation

/**
* Represents one terrain in the 3D world
*/
public class TerrainShape : NSObject, IShape {
    
    private static let SIZE : Float = 800.0
    
    /* Number of vertices in each side of the terrain */
    private static let TERRAIN_SHAPE_VERTEX_COUNT : Int = 128;
    private static let TERRAIN_SHAPE_COUNT : Int = 16384; //count * count
    private static let TERRAIN_VERTICES_LENGTH : Int = 49152; //3 * count * count
    private static let TERRAIN_NORMALS_LENGTH : Int = 49152; //3 * count * count
    private static let TERRAIN_TEXTURES_LENGTH : Int = 32768; //2 *count * count
    private static let TERRAIN_INDICES_LENGTH : Int = 96774; //6 * (count -1 ) * (count -1)
    
    private var _vertices : UnsafeMutablePointer<Float> ;
    private var _normals : UnsafeMutablePointer<Float>;
    private var _textureCoords : UnsafeMutablePointer<Float> ;
    private var _indices : UnsafeMutablePointer<ushort>;
    
    
    
    /**
    * Allocate space and copy the data from swift arrays to native c arrays
    */
    private static func fillPointersData(vertices  : Array<Float>, _ normals : Array<Float>, _ textureCoords : Array<Float>, _ indices : Array<ushort>)
        ->
        (vertices: UnsafeMutablePointer<Float>, normals : UnsafeMutablePointer<Float>, textureCoords : UnsafeMutablePointer<Float>, indices : UnsafeMutablePointer<ushort>)
    {
        return (Utils.arrayToPointer(vertices),
            Utils.arrayToPointer(normals),
            Utils.arrayToPointer(textureCoords),
            Utils.arrayToPointer(indices))
    }
    
    /**
    * Generates a completely flat terrain
    *
    */
    private static func generateTerrain()
        ->
        (vertices: UnsafeMutablePointer<Float>, normals : UnsafeMutablePointer<Float>, textureCoords : UnsafeMutablePointer<Float>, indices : UnsafeMutablePointer<ushort>)
    {
        var vertices : Array<Float> = Array<Float>(count: TERRAIN_VERTICES_LENGTH, repeatedValue: 0.0);
        var normals : Array<Float> = Array<Float>(count: TERRAIN_NORMALS_LENGTH, repeatedValue: 0.0);
        var textureCoords : Array<Float> = Array<Float>(count: TERRAIN_TEXTURES_LENGTH, repeatedValue: 0.0);
        var indices : Array<ushort> = Array<ushort>(count: TERRAIN_INDICES_LENGTH, repeatedValue: 0);
        
        let vertext_count : Int = TERRAIN_SHAPE_VERTEX_COUNT;
        let vertex_count : Float = Float(TERRAIN_SHAPE_VERTEX_COUNT);
        var vertexPointer : Int = 0;
        for ii : Int in 0 ..< vertext_count {
            let i = Float(ii)
            for ij : Int in 0 ..< vertext_count {
                let j = Float(ij)
                vertices[vertexPointer * 3] = Float(j / (vertex_count - 1)) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = i / (vertex_count - 1) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = j / (vertex_count - 1);
                textureCoords[vertexPointer * 2 + 1] = i / (vertex_count - 1);
                vertexPointer += 1;
            }
        }
        var pointer : Int = 0;
        for gz : Int in 0 ..< vertext_count - 1 {
            for gx : Int in 0 ..< (vertext_count - 1) {
                let topLeft = (gz * vertext_count) + gx;
                let topRight = topLeft + 1;
                let bottomLeft = ((gz + 1) * vertext_count) + gx;
                let bottomRight = bottomLeft + 1;
                indices[pointer++] = ushort(topLeft);
                indices[pointer++] = ushort(bottomLeft);
                indices[pointer++] = ushort(topRight);
                indices[pointer++] = ushort(topRight);
                indices[pointer++] = ushort(bottomLeft);
                indices[pointer++] = ushort(bottomRight);
            }
        }
        return fillPointersData(vertices, normals, textureCoords , indices);
    }
    
    
    
    /**
    * Initiator of the terrain shape
    */
    public override init() {
        let shape = TerrainShape.generateTerrain();
        self._vertices = shape.vertices
        self._normals = shape.normals
        self._textureCoords = shape.textureCoords
        self._indices = shape.indices
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
        return TerrainShape.TERRAIN_VERTICES_LENGTH;
    }
    
    /**
    * @return the Coordinates of the textures of the shape
    */
    public func getTextureCoords()  -> UnsafeMutablePointer<Float> {
        return self._textureCoords;
    }
    
    /*
    Number of the texture coordinates
    */
    public func countTextureCoords() -> Int {
        return TerrainShape.TERRAIN_TEXTURES_LENGTH;
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
    public  func countNormals() -> Int {
        return TerrainShape.TERRAIN_NORMALS_LENGTH;
    }
    
    /**
    * @return The indices of the vertices that make the shape
    */
    public  func getIndices() -> UnsafeMutablePointer<ushort> {
        return self._indices;
    }
    
    /*
    Number of indices that the shapa has
    */
    public func countIndices() -> Int {
        return TerrainShape.TERRAIN_INDICES_LENGTH;
    }
    
}
