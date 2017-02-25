import Foundation

/**
 * Manager of the shader files that are going to be load to render the 2D GUIs
 */
open class GuiShaderManager : ShaderManager {
    
    /**
     * Initializor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     */
    public init() {
        super.init(vertexFile: "gui_vertex_shader" , fragmentFile: "gui_fragment_shader", numberOfUniforms:
            TGuiUniform.numOfGUILocations.rawValue)
    }
    
    /**
     * Bind the attributes of the program shader
     *
     */
    override internal func getAttributes() -> Dictionary<Int, String>! {
        let attributesDic : Dictionary<Int, String> = [
            TGuiAttribute.position.rawValue : "\(TGuiAttribute.position)"
        ];
        return attributesDic;
    }
    
    /**
     * Get all the uniform location in the shader script
     */
    override internal func getUniformLocations() -> Dictionary<NSInteger, String>! {
        let uniformsDic : Dictionary<NSInteger, String> = [
            TGuiUniform.transformationMatrix.rawValue : "\(TGuiUniform.transformationMatrix)"
        ]
        
        return uniformsDic;
    }
    
    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    open func loadTransformationMatrix(_ matrix : GLTransformation) {
        super.loadMatrix(uniforms[TGuiUniform.transformationMatrix.rawValue], matrix: matrix);
    }
    
}
