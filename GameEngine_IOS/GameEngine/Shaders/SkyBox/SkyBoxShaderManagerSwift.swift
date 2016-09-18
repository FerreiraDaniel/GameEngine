import Foundation

public class SkyBoxShaderManagerSwift : ShaderManagerSwift {
    
    private var uniforms : Array<Int>
    
    /**
    * Initializor of the game shader where the vertex and fragment shader of
    * the game engine are loaded
    *
    */
    public init() {
        self.uniforms = Array<Int>(count: TSkyBoxUniform.numOfSkyBoxLocations.rawValue, repeatedValue: -1)
        super.init(vertexFile: "sky_box_vertex_shader" , fragmentFile: "sky_box_fragment_shader")
    }
    
    /**
    * Called to bind the attributes to the program shader
    */
    override internal func  bindAttributes() {
        super.bindAttribute(TSkyBoxAttribute.position.rawValue, variableName: "position");
    }
    
    /**
    * Called to ensure that all the shader managers get their uniform locations
    */
    internal  override func getAllUniformLocations() {
        let uniformsDic : Dictionary<NSInteger, String> = [
            TSkyBoxUniform.projectionMatrix.rawValue : "projectionMatrix",
            TSkyBoxUniform.viewMatrix.rawValue : "viewMatrix"
        ]
        
        //Iteract over different elements to get the locations in the program shader
        for (key, value) in uniformsDic {
            uniforms[key] = super.getUniformLocation(value)
            if(uniforms[key] < 0) {
                NSLog("Problems getting %@'s location in the shader", value);
            }
        }
    }
    
    /**
    * Load the projection matrix
    *
    * @param matrix
    *            the matrix to be loaded
    */
    public func loadProjectionMatrix (matrix : GLTransformationSwift) {
        super.loadMatrix(uniforms[TSkyBoxUniform.projectionMatrix.rawValue],matrix: matrix);
    }
    
    /**
    * Load the view matrix
    *
    * @param matrix
    *            the matrix to be loaded
    */
    public func loadViewMatrix (matrix : GLTransformationSwift) {
        super.loadMatrix(uniforms[TSkyBoxUniform.viewMatrix.rawValue],matrix: matrix);
    }
}