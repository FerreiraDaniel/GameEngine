import Foundation

/**
 ** The model to the terrain entity
 */
public class Terrain {
    
    private let SIZE : Float = 500
    
    /**
     * Position of the terrain in the x-axle
     */
    var x : Float;
    
    /**
     * Position of the terrain in the y-axle
     */
    var y : Float;
    
    /**
     * Position of the terrain in the z-axle
     */
    var z : Float;
    
    /* Heights of the components of the terrain */
    var heights : [[Float]];
    
    /**
     * RawModel of the terrain
     */
    var model : RawModel;
    
    /**
     * The different textures of the terrain
     */
    var texturePack : TerrainTexturesPack;
    
    /**
     * The constructor of the terrain entity
     *
     * @param texturePack		The identifiers of the textures to the terrain
     * @param rawModel			The model of the terrain
     * @param heights         The height of the vertices of the terrain
     * @param position			Position where the terrain will be put in
     */
    public init(texturePack : TerrainTexturesPack, rawModel : RawModel, heights : [[Float]], position : Vector3f) {
        self.texturePack = texturePack;
        self.heights = heights;
        self.model = rawModel;
        self.x = position.x * SIZE;
        self.y = position.y * SIZE;
        self.z = position.z * SIZE;
    }
    
    /**
     * The height of the terrain in a certain position of the world
     *
     * @param worldX The x-component of the location to compute the height of the
     *               terrain
     * @param worldZ The y-component of the location to compute the height of the
     *               terrain
     * @return The height of the terrain in the specified position of the world
     */
    public func getHeightOfTerrain(worldX : Float, worldZ : Float) -> Float {
        let terrainX : Float = worldX - self.x;
        let terrainZ : Float = worldZ - self.z;
        let gridSquareSize : Float = TerrainShape.SIZE / Float(self.heights.count - 1);
        let gridX = Int(Math.floor(terrainX / gridSquareSize));
        let gridZ = Int(Math.floor(terrainZ / gridSquareSize));
        if (gridX >= heights.count - 1 || gridZ >= heights.count - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        } else {
            let xCoord : Float = Math.floor(terrainX % gridSquareSize);
            let zCoord : Float = Math.floor(terrainZ % gridSquareSize);
            
            var height : Float;
            if (xCoord <= (gridSquareSize - zCoord)) {
                let v1Height : Float = heights[gridX][gridZ];
                let v3Height : Float = heights[gridX][gridZ + 1];
                height = Math.barryCentric(Vector3f(x: 0.0, y: v1Height, z: 0.0),
                                           v2: Vector3f(x: gridSquareSize, y: heights[gridX + 1][gridZ], z: 0),
                                           v3: Vector3f(x: 0.0, y: v3Height, z: gridSquareSize),
                                           pos: Vector2f(x: xCoord, y: zCoord));
            } else {
                let v1Height : Float = heights[gridX + 1][gridZ];
                let v2Height : Float = heights[gridX + 1][gridZ + 1];
                let v3Height : Float = heights[gridX][gridZ + 1];
                height = Math.barryCentric(Vector3f(x: gridSquareSize, y: v1Height, z: 0.0),
                                           v2: Vector3f(x: gridSquareSize, y: v2Height, z: gridSquareSize),
                                           v3: Vector3f(x: 0.0, y: v3Height, z: gridSquareSize),
                                           pos: Vector2f(x: xCoord, y: zCoord));
            }
            return height;
        }
        
    }
}
