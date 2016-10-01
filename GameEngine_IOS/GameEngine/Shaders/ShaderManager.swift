import Foundation
import OpenGLES

/**
*  Master class to handle shaders
*
*/
public class ShaderManager : NSObject{
    
    public var shaderProgram : ShaderProgram!
    internal var uniforms : Array<Int>
    
    /**
    * Initialize of the program shader manager
    *
    * @param vertexFile   File with vertex description
    * @param fragmentFile File with fragment description
    * @param numberOfUniforms number of uniform locations of the shader program
    */
    public init(vertexFile : String, fragmentFile : String, numberOfUniforms : Int) {
        self.uniforms = Array<Int>(count: numberOfUniforms, repeatedValue: -1)
        
        super.init()
        
        var vertShaderPathname, fragShaderPathname: String
        var vertexShaderSrc, fragShaderSrc : UnsafePointer<Int8>
        
        //Read the vertex file to one variable
        vertShaderPathname = NSBundle.mainBundle().pathForResource(vertexFile, ofType: "vsh")!
        vertexShaderSrc = LoadUtils.readTextFromRawResource(vertShaderPathname);
        
        //Read the fragment shader file to one variable
        fragShaderPathname = NSBundle.mainBundle().pathForResource(fragmentFile, ofType: "fsh")!
        fragShaderSrc = LoadUtils.readTextFromRawResource(fragShaderPathname);
        
        self.shaderProgram = GLSLUtils.loadProgram(vertexShaderSrc, fragShaderSrc: fragShaderSrc);
        
        if (shaderProgram == nil) {
            return;
        }
        
        self.bindAttributes();
        
        // Link program.
        
        if (GLSLUtils.linkProgram(shaderProgram)) {
            self.getAllUniformLocations();
        } else {
            NSLog("Failed to link program: %d", shaderProgram.programId);
            
            if (shaderProgram.vertexShaderId != 0) {
                glDeleteShader(shaderProgram.vertexShaderId);
                shaderProgram.vertexShaderId = 0;
            }
            if (shaderProgram.fragmentShaderId != 0) {
                glDeleteShader(shaderProgram.fragmentShaderId);
                shaderProgram.fragmentShaderId = 0;
            }
            if (shaderProgram.programId != 0) {
                glDeleteProgram(shaderProgram.programId);
                shaderProgram.programId = 0;
            }
            
        }
    }
    
    /*
     * Called to provide the attributes that are goin to get bound to the shader
     */
    internal func getAttributes() -> Dictionary<Int, String>! {
        NSException.raise("Invoked abstract method", format: "Invoked abstract method", arguments:getVaList(["nil"]))
        return nil;
    }
    
    
    /**
    * Called to bind the attributes to the program shader
    */
    private func bindAttributes() {
        let attributes : Dictionary<Int, String>! = self.getAttributes();
        
        
        if(attributes != nil) {
            for (key, value) in attributes {
                self.bindAttribute(key, variableName: value);
                }
            }
        }
    
    /**
     * Called to provide the uniform locations that are goin to get bound to the shader
    */
    internal func getUniformLocations() -> Dictionary<NSInteger, String>! {
        NSException.raise("Invoked abstract method", format: "Invoked abstract method", arguments:getVaList(["nil"]))
        return nil;
    }

    /**
    * Called to ensure that all the shader managers get their uniform locations
    */
    private func getAllUniformLocations() {
        
        let uniformsDic : Dictionary<NSInteger, String> = getUniformLocations();
        
        //Iteract over different elements to get the locations in the program shader
        for (key, value) in uniformsDic {
            uniforms[key] = getUniformLocation(value)
            if(uniforms[key] < 0) {
                NSLog("Problems getting %@'s location in the shader", value);
            }
        }
    }
    
    /**
    * Bind one attribute
    *
    * @param attributeIndex Index of the attribute to bind
    * @param variableName   Name of the attribute to bind
    */
    private func bindAttribute(attributeIndex : Int , variableName : String) {
        glBindAttribLocation(shaderProgram.programId, GLuint(attributeIndex), variableName);
    }
    
    /**
    * Get the position of one uniform variable in the program shader
    *
    * @param uniformName the name of the uniform variable as appears in the shader code
    * @return the position of the uniform variable in program shader
    */
    internal func  getUniformLocation(uniformName : String) -> Int {
        return Int(glGetUniformLocation(shaderProgram.programId, uniformName));
    }
    
    /**
    * Load a integer value to be used in the shader script
    *
    * @param location
    *            location of the shader variable in the script
    * @param value
    *            The value to load
    */
    internal func loadInt(location : Int, value : Int) {
        glUniform1i(GLint(location), Int32(value));
    }
    
    /**
    * Load a float value to be used in the shader script
    *
    * @param location location of the shader variable in the script
    * @param value    value to load
    */
    internal func loadFloat(location : Int, value : Float) {
        glUniform1f(GLint(location), value);
    }
    
    /**
    * Load a vector to be used in the shader script
    *
    * @param location location of the shader variable in the script
    * @param vector The vector to load
    */
    internal func  loadVector(location : Int, vector : Vector3fSwift) {
        glUniform3f(GLint(location), vector.x, vector.y, vector.z);
    }
    
    /**
    * Load a boolean value to be used in the shader script
    *
    * @param location The location of the shader variable in the script
    * @param value    value to load
    */
    internal func  loadBoolean(location : Int, value : Bool) {
        let toLoad : Float = value ? 1.0 : 0.0;
        glUniform1f(GLint(location), toLoad);
    }
    
    /**
    * Load a matrix to be used in the shader script
    *
    * @param location The location of the shader variable in the script
    * @param matrix   Matrix to load
    */
    public func loadMatrix (location : Int, matrix : GLTransformation) {
        glUniformMatrix4fv(GLint(location), 1, 0, matrix.getMatrix());
    }
    
    /**
    * Indicates that should start to use a certain program shader
    */
    public func start() {
        if (shaderProgram != nil) {
            glUseProgram(shaderProgram.programId);
        }
    }
    
    /**
    * Indicate that should not use a certain program no more
    */
    public func  stop() {
        glUseProgram(0);
    }
    
    /**
    * Clean the program shader from memory
    */
    deinit {
        if ((shaderProgram != nil) && (shaderProgram.programId != 0)) {
            glDeleteProgram(shaderProgram.programId);
            shaderProgram.programId = 0;
        }
        shaderProgram = nil;
    }
    
}