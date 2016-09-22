import Foundation

public class TerrainShaderManagerSwift : ShaderManagerSwift {
    
    /**
    * Initializor of the game shader where the vertex and fragment shader of
    * the game engine are loaded
    *
    */
    public init() {
        super.init(vertexFile: "terrain_vertex_shader" , fragmentFile: "terrain_fragment_shader", numberOfUniforms: TTerrainUniform.numOfTerrainLocations.rawValue)
    }

}