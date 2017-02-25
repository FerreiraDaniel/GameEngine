import Foundation

/**
 * Represents one terrain in the 3D world
 */
open class TerrainShape : NSObject, IShape {
    
    open static let SIZE : Float = 500.0
    
    /* Number of vertices in each side of the terrain */
    fileprivate static let TERRAIN_SHAPE_VERTEX_COUNT : Int = 128;
    fileprivate static let TERRAIN_SHAPE_COUNT : Int = 16384; //count * count
    fileprivate static let TERRAIN_VERTICES_LENGTH : Int = 49152; //3 * count * count
    fileprivate static let TERRAIN_NORMALS_LENGTH : Int = 49152; //3 * count * count
    fileprivate static let TERRAIN_TEXTURES_LENGTH : Int = 32768; //2 *count * count
    fileprivate static let TERRAIN_INDICES_LENGTH : Int = 96774; //6 * (count -1 ) * (count -1)
    
    /**
     * Vertices of the terrain
     */
    fileprivate var _vertices : UnsafeMutablePointer<Float>? ;
    
    /**
     * Heights of the vertices that make the terrain
     */
    fileprivate var _heights : [[Float]];
    
    /**
     * Normals of the terrain
     */
    fileprivate var _normals : UnsafeMutablePointer<Float>?;
    
    /**
     * Coordinates of the textures
     */
    fileprivate var _textureCoords : UnsafeMutablePointer<Float>?;
    
    /**
     * The indices of the terrain
     */
    fileprivate var _indices : UnsafeMutablePointer<ushort>?;
    
    /*Minimum height that the terrain has*/
    fileprivate static let MIN_HEIGHT : Float = -40.0;
    /*Maximum height that the terrain has*/
    fileprivate static let MAX_HEIGHT : Float = 40.0;
    
    /**
     * Allocate space and copy the data from swift arrays to native c arrays
     */
    fileprivate static func fillPointersData(_ vertices  : Array<Float>, _ normals : Array<Float>, _ textureCoords : Array<Float>, _ indices : Array<ushort>, _ heights: Array<Array<Float>>)
        ->
        (vertices: UnsafeMutablePointer<Float>, normals : UnsafeMutablePointer<Float>, textureCoords : UnsafeMutablePointer<Float>, indices : UnsafeMutablePointer<ushort>, heights: Array<Array<Float>>)
    {
        return (Utils.toPointer(vertices),
                Utils.toPointer(normals),
                Utils.toPointer(textureCoords),
                Utils.toPointer(indices),
                heights
        )
    }
    
    /**
     * Generates a completely flat terrain
     *
     * @param heightMap Texture with different heights in the terrain
     *
     */
    fileprivate static func generateTerrain(_ heightMap : TextureData)
        ->
        (vertices: UnsafeMutablePointer<Float>, normals : UnsafeMutablePointer<Float>, textureCoords : UnsafeMutablePointer<Float>, indices : UnsafeMutablePointer<ushort>, heights: Array<Array<Float>>)
    {
        var vertices : Array<Float> = Array<Float>(repeating: 0.0, count: TERRAIN_VERTICES_LENGTH);
        var normals : Array<Float> = Array<Float>(repeating: 0.0, count: TERRAIN_NORMALS_LENGTH);
        var textureCoords : Array<Float> = Array<Float>(repeating: 0.0, count: TERRAIN_TEXTURES_LENGTH);
        var indices : Array<ushort> = Array<ushort>(repeating: 0, count: TERRAIN_INDICES_LENGTH);
        var heights : Array<Array<Float>> = Array(repeating: Array(repeating: 0.0, count: TERRAIN_SHAPE_VERTEX_COUNT), count: TERRAIN_SHAPE_VERTEX_COUNT);
        
        let vertext_count : Int = TERRAIN_SHAPE_VERTEX_COUNT;
        let vertex_count : Float = Float(TERRAIN_SHAPE_VERTEX_COUNT);
        var vertexPointer : Int = 0;
        for ii : Int in 0 ..< vertext_count {
            let i = Float(ii)
            for ij : Int in 0 ..< vertext_count {
                let j = Float(ij)
                heights[ij][ii] = getHeight(ij, y: ii, heightMap: heightMap)
                vertices[vertexPointer * 3] = Float(j / (vertex_count - 1)) * SIZE;
                vertices[vertexPointer * 3 + 1] = heights[ij][ii]
                vertices[vertexPointer * 3 + 2] = i / (vertex_count - 1) * SIZE;
                normals[vertexPointer * 3] = 0
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
                indices[pointer] = ushort(topLeft);
                pointer += 1;
                indices[pointer] = ushort(bottomLeft);
                pointer += 1;
                indices[pointer] = ushort(topRight);
                pointer += 1;
                indices[pointer] = ushort(topRight);
                pointer += 1;
                indices[pointer] = ushort(bottomLeft);
                pointer += 1;
                indices[pointer] = ushort(bottomRight);
                pointer += 1;
            }
        }
        return fillPointersData(vertices, normals, textureCoords , indices, heights);
    }
    
    
    
    /**
     * Initiator of the terrain shape
     *
     * @param heightMap Texture with different heights in the terrain
     */
    public init(heightMap: TextureData) {
        let shape = TerrainShape.generateTerrain(heightMap);
        self._vertices = shape.vertices
        self._normals = shape.normals
        self._textureCoords = shape.textureCoords
        self._indices = shape.indices
        self._heights = shape.heights
    }
    
    /**
     * Get the height that was specified in the height map image
     *
     * @param x         x-coordinate
     * @param y         y-coordinate
     * @param heightMap Texture with different heights in the terrain
     *
     * @return the height of the terrain in the specified position
     */
    fileprivate static func getHeight(_ x : Int, y : Int, heightMap : TextureData) -> Float {
        let rgb : Float = Float(heightMap.getRGB(x, y: y)!);
        let heightNormal : Float = (rgb / TextureData.MAX_PIXEL_COLOR);
        let finalHeight : Float = (heightNormal * (MAX_HEIGHT - MIN_HEIGHT)) + MIN_HEIGHT;
        
        return finalHeight;
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
        return TerrainShape.TERRAIN_VERTICES_LENGTH;
    }
    
    /**
     * @return the Coordinates of the textures of the shape
     */
    open func getTextureCoords()  -> UnsafeMutablePointer<Float>? {
        return self._textureCoords;
    }
    
    /*
     Number of the texture coordinates
     */
    open func countTextureCoords() -> Int {
        return TerrainShape.TERRAIN_TEXTURES_LENGTH;
    }
    
    /**
     *
     * @return the normal vectors that make the shape
     */
    open func getNormals() -> UnsafeMutablePointer<Float>? {
        return self._normals;
    }
    
    /*
     * Number of normal that the shape has
     */
    open  func countNormals() -> Int {
        return TerrainShape.TERRAIN_NORMALS_LENGTH;
    }
    
    /**
     * @return The indices of the vertices that make the shape
     */
    open  func getIndices() -> UnsafeMutablePointer<ushort>? {
        return self._indices;
    }
    
    /*
     Number of indices that the shapa has
     */
    open func countIndices() -> Int {
        return TerrainShape.TERRAIN_INDICES_LENGTH;
    }
    
    /**
     * @return The heights of the vertices of the terrain
     */
    open func getHeights() -> [[Float]] {
        return self._heights;
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
