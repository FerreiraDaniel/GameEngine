import Foundation

open class SkyBoxShaderManager : ShaderManager {
    
    
    
    /**
    * Initializor of the game shader where the vertex and fragment shader of
    * the game engine are loaded
    *
    */
    public init() {
        super.init(vertexFile: "sky_box_vertex_shader" , fragmentFile: "sky_box_fragment_shader", numberOfUniforms: TSkyBoxUniform.numOfSkyBoxLocations.rawValue)
    }
    
    /*
    * Called to provide the attributes that are goint to get bound to the shader
    */
    override internal func getAttributes() -> Dictionary<Int, String>! {
        let attributesDic : Dictionary<Int, String> = [
            TSkyBoxAttribute.position.rawValue : "\(TSkyBoxAttribute.position)"
        ];
        return attributesDic;
    }
    
    /**
    * Called to provide the uniform locations that are going to get bound to the shader
    */
    override internal func getUniformLocations() -> Dictionary<NSInteger, String>! {
        let uniformsDic : Dictionary<NSInteger, String> = [
            TSkyBoxUniform.projectionMatrix.rawValue : "\(TSkyBoxUniform.projectionMatrix)",
            TSkyBoxUniform.viewMatrix.rawValue : "\(TSkyBoxUniform.viewMatrix)"
        ]
        
        return uniformsDic;
    }
    
    /**
    * Load the projection matrix
    *
    * @param matrix
    *            the matrix to be loaded
    */
    open func loadProjectionMatrix (_ matrix : GLTransformation) {
        super.loadMatrix(uniforms[TSkyBoxUniform.projectionMatrix.rawValue],matrix: matrix);
    }
    
    /**
    * Load the view matrix
    *
    * @param matrix
    *            the matrix to be loaded
    */
    open func loadViewMatrix (_ matrix : GLTransformation) {
        matrix.setTranslation(x: 0.0, y: 0.0, z: 0.0)
        super.loadMatrix(uniforms[TSkyBoxUniform.viewMatrix.rawValue],matrix: matrix);
    }
}
