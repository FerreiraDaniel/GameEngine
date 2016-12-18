import Foundation

/**
 * Manager of the shader files that are going to be load to render the 3D
 * Terrain
 */
public class TerrainShaderManager : ShaderManager {
    
    /**
     * Initializor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     */
    public init() {
        super.init(vertexFile: "terrain_vertex_shader" , fragmentFile: "terrain_fragment_shader", numberOfUniforms: TTerrainUniform.numOfTerrainLocations.rawValue)
    }
    
    
    /**
     * Called to bind the attributes to the program shader
     */
    override internal func getAttributes() -> Dictionary<Int, String>! {
        let attributesDic : Dictionary<Int, String> = [
            TTerrainAttribute.position.rawValue : "\(TTerrainAttribute.position)",
            TTerrainAttribute.textureCoords.rawValue : "\(TTerrainAttribute.textureCoords)",
            TTerrainAttribute.normal.rawValue : "\(TTerrainAttribute.normal)"
        ];
        return attributesDic;
    }
    
    /**
     * Called to provide the uniform locations that are going to get bound to the shader
     */
    override internal func getUniformLocations() -> Dictionary<NSInteger, String>! {
        let uniformsDic : Dictionary<NSInteger, String> = [
            TTerrainUniform.projectionMatrix.rawValue : "\(TTerrainUniform.projectionMatrix)",
            TTerrainUniform.viewMatrix.rawValue : "\(TTerrainUniform.viewMatrix)",
            TTerrainUniform.transformationMatrix.rawValue : "\(TTerrainUniform.transformationMatrix)",
            TTerrainUniform.lightPosition.rawValue : "\(TTerrainUniform.lightPosition)",
            TTerrainUniform.lightColor.rawValue : "\(TTerrainUniform.lightColor)",
            TTerrainUniform.shineDamper.rawValue : "\(TTerrainUniform.shineDamper)",
            TTerrainUniform.reflectivity.rawValue : "\(TTerrainUniform.reflectivity)",
            TTerrainUniform.skyColor.rawValue : "\(TTerrainUniform.skyColor)",
            TTerrainUniform.backgroundTexture.rawValue : "\(TTerrainUniform.backgroundTexture)",
            TTerrainUniform.mudTexture.rawValue : "\(TTerrainUniform.mudTexture)",
            TTerrainUniform.grassTexture.rawValue : "\(TTerrainUniform.grassTexture)",
            TTerrainUniform.pathTexture.rawValue : "\(TTerrainUniform.pathTexture)",
            TTerrainUniform.weightMapTexture.rawValue : "\(TTerrainUniform.weightMapTexture)"
        ]
        
        return uniformsDic;
    }
    
    /**
     * Associate the shader variables with textures that were defined in the
     * bind of textures
     */
    public func connectTextureUnits() {
        super.loadInt(uniforms[TTerrainUniform.backgroundTexture.rawValue], value: TTerrainTexture.TEXTURE_UNIT0.rawValue);
        super.loadInt(uniforms[TTerrainUniform.mudTexture.rawValue], value: TTerrainTexture.TEXTURE_UNIT1.rawValue);
        super.loadInt(uniforms[TTerrainUniform.grassTexture.rawValue], value: TTerrainTexture.TEXTURE_UNIT2.rawValue);
        super.loadInt(uniforms[TTerrainUniform.pathTexture.rawValue], value: TTerrainTexture.TEXTURE_UNIT3.rawValue);
        super.loadInt(uniforms[TTerrainUniform.weightMapTexture.rawValue], value: TTerrainTexture.TEXTURE_UNIT4.rawValue);
    }
    
    /**
     * Load the projection matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadProjectionMatrix (matrix : GLTransformation) {
        super.loadMatrix(uniforms[TTerrainUniform.projectionMatrix.rawValue], matrix: matrix);
    }
    
    
    /**
     * Load the view matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadViewMatrix (matrix : GLTransformation) {
        super.loadMatrix(uniforms[TTerrainUniform.viewMatrix.rawValue], matrix: matrix);
    }
    
    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadTransformationMatrix(matrix : GLTransformation) {
        super.loadMatrix(uniforms[TTerrainUniform.transformationMatrix.rawValue], matrix: matrix);
    }
    
    /**
     * Put passes the information of the light to the
     * Shader program
     *
     * @param light the light to load in the shader program
     */
    public func loadLight(light : Light) {
        super.loadVector(uniforms[TTerrainUniform.lightPosition.rawValue], vector: light.position);
        super.loadColorRGB(uniforms[TTerrainUniform.lightColor.rawValue], color: light.color);
    }
    
    /**
     * Load the values of the specular light in the fragment shader
     *
     * @param damper		The damper of the specular lighting
     * @param reflectivity	The reflectivity of the material
     */
    public func loadShineVariables(damper : Float, reflectivity : Float) {
        super.loadFloat(uniforms[TTerrainUniform.shineDamper.rawValue], value: damper);
        super.loadFloat(uniforms[TTerrainUniform.reflectivity.rawValue], value : reflectivity);
    }
    
    
    /**
     * Load the color of the sky in order to simulate fog
     *
     * @param skyColor
     * 			Color of the sky
     */
    public func  loadSkyColor(skyColor : ColorRGBA) {
        super.loadColorRGBA(uniforms[TTerrainUniform.skyColor.rawValue], color: skyColor);
    }
}
