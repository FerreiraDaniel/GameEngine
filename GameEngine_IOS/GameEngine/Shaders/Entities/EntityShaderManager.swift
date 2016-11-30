import Foundation

public class EntityShaderManager : ShaderManager {
    /**
     * Initializor of the game shader where the vertex and fragment shader of
     * the game engine are loaded
     *
     */
    public init() {
        super.init(vertexFile: "entity_vertex_shader" , fragmentFile: "entity_fragment_shader", numberOfUniforms: TEntityUniform.numOfEntityLocations.rawValue)
    }
    
    /**
     * Called to bind the attributes to the program shader
     */
    override internal func getAttributes() -> Dictionary<Int, String>! {
        let attributesDic : Dictionary<Int, String> = [
            TEntityAttribute.position.rawValue : "\(TEntityAttribute.position)",
            TEntityAttribute.textureCoords.rawValue : "\(TEntityAttribute.textureCoords)",
            TEntityAttribute.normal.rawValue : "\(TEntityAttribute.normal)"
        ];
        return attributesDic;
    }
    
    /**
     * Called to provide the uniform locations that are going to get bound to the shader
     */
    override internal func getUniformLocations() -> Dictionary<NSInteger, String>! {
        let uniformsDic : Dictionary<NSInteger, String> = [
            TEntityUniform.projectionMatrix.rawValue : "\(TEntityUniform.projectionMatrix)",
            TEntityUniform.viewMatrix.rawValue : "\(TEntityUniform.viewMatrix)",
            TEntityUniform.transformationMatrix.rawValue : "\(TEntityUniform.transformationMatrix)",
            TEntityUniform.lightPosition.rawValue : "\(TEntityUniform.lightPosition)",
            TEntityUniform.lightColor.rawValue : "\(TEntityUniform.lightColor)",
            TEntityUniform.shineDamper.rawValue : "\(TEntityUniform.shineDamper)",
            TEntityUniform.reflectivity.rawValue : "\(TEntityUniform.reflectivity)",
            TEntityUniform.skyColor.rawValue : "\(TEntityUniform.skyColor)",
            TEntityUniform.normalsPointingUp.rawValue : "\(TEntityUniform.normalsPointingUp)"
        ]
        
        return uniformsDic;
    }
    
    
    /**
     * Load the projection matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadProjectionMatrix (matrix : GLTransformation) {
        super.loadMatrix(uniforms[TEntityUniform.projectionMatrix.rawValue], matrix: matrix);
    }
    
    /**
     * Load the view matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadViewMatrix (matrix : GLTransformation) {
        super.loadMatrix(uniforms[TEntityUniform.viewMatrix.rawValue], matrix: matrix);
    }
    
    /**
     * Load the transformation matrix
     *
     * @param matrix
     *            the matrix to be loaded
     */
    public func loadTransformationMatrix(matrix : GLTransformation) {
        super.loadMatrix(uniforms[TEntityUniform.transformationMatrix.rawValue], matrix: matrix);
    }
    
    /**
     * Put passes the information of the light to the
     * Shader program
     *
     * @param light the light to load in the shader program
     */
    public func loadLight(light : Light) {
        super.loadVector(uniforms[TEntityUniform.lightPosition.rawValue], vector: light.position);
        super.loadVector(uniforms[TEntityUniform.lightColor.rawValue], vector: light.color);
    }
    
    /**
     * Load the values of the specular light in the fragment shader
     *
     * @param damper		The damper of the specular lighting
     * @param reflectivity	The reflectivity of the material
     */
    public func loadShineVariables(damper : Float, reflectivity : Float) {
        super.loadFloat(uniforms[TEntityUniform.shineDamper.rawValue], value: damper);
        super.loadFloat(uniforms[TEntityUniform.reflectivity.rawValue], value : reflectivity);
    }
    
    /**
     * Set in the shader if the normals should all of them point up
     *
     * @param normalsPointingUp Flag that indicates if all the normals of the entity are poiting up or not
     */
    public func loadNormalsPointingUp(normalsPointingUp : Bool) {
        super.loadBoolean( uniforms[TEntityUniform.normalsPointingUp.rawValue], value :  normalsPointingUp);
    }
    
    /**
     * Load the color of the sky in order to simulate fog
     *
     * @param skyColor
     * 			Color of the sky
     */
    public func  loadSkyColor(skyColor : Vector3f) {
        super.loadVector( uniforms[TEntityUniform.skyColor.rawValue], vector : skyColor);
    }
}
