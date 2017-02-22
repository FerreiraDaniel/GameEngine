import Foundation
import OpenGLES

public class GLSLUtils {
    
    /**
    * Load and compiles the shader into device memory
    *
    * @param type      Type of shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
    * @return 0 -> There was an error
    *         not 0 -> Id of the shader compiled
    */
    private static func compileShader(inout shader: GLuint, type: GLenum, sourceCode: UnsafePointer<Int8>) -> Bool {
        var status: GLint = 0
        
        var castSource = UnsafePointer<GLchar>(sourceCode)
        
        shader = glCreateShader(type)
        glShaderSource(shader, 1, &castSource, nil)
        glCompileShader(shader)
        
        
        var logLength: GLint = 0
        glGetShaderiv(shader, GLenum(GL_INFO_LOG_LENGTH), &logLength);
        if logLength > 0 {
            let log = UnsafeMutablePointer<GLchar>(malloc(Int(logLength)))
            glGetShaderInfoLog(shader, logLength, &logLength, log);
            NSLog("Shader compile log: \n%s", log);
            free(log)
        }
        
        glGetShaderiv(shader, GLenum(GL_COMPILE_STATUS), &status)
        if status == 0 {
            glDeleteShader(shader);
            return false
        } else {
            return true
        }
        
    }
    
    /**
    * Compiles the shader sources and attache them to the program
    *
    * @param vertexShaderSrc
    *            Source code of the vertex shader
    * @param fragShaderSrc
    *            Source code of the fragment shader
    * @return If nil There was an error not 0 -> Id of the program loaded
    */
    public static func loadProgram(vertexShaderSrc: UnsafePointer<Int8>, fragShaderSrc: UnsafePointer<Int8>) -> ShaderProgram! {
        
        var vertShader : GLuint = 0;
        var fragShader : GLuint = 0;
        
        
        //Inicialize the shader program that is going to be return
        let shaderProgram : ShaderProgram = ShaderProgram()
        
        
        // Create shader program.
        shaderProgram.programId = glCreateProgram();
        
        // Create and compile vertex shader.
        
        if (!self.compileShader(&vertShader, type: GLenum(GL_VERTEX_SHADER), sourceCode: vertexShaderSrc)) {
            NSLog("Failed to compile vertex shader");
            return nil;
        }
        shaderProgram.vertexShaderId = vertShader;
        
        // Create and compile fragment shader.
        if (!self.compileShader(&fragShader, type: GLenum(GL_FRAGMENT_SHADER), sourceCode: fragShaderSrc)) {
            NSLog("Failed to compile fragment shader");
            return nil;
        }
        shaderProgram.fragmentShaderId = fragShader;
        
        // Attach vertex shader to program.
        glAttachShader(shaderProgram.programId, shaderProgram.vertexShaderId);
        
        // Attach fragment shader to program.
        glAttachShader(shaderProgram.programId, shaderProgram.fragmentShaderId);
        
        return shaderProgram;
    }
    
    /**
    * Link the program shader with their vertex shader and fragment shader
    *
    * @param shaderProgram The program shader not linked yet
    *
    * @return False = Not linked True = Linked
    */
    public static func linkProgram(shaderProgram: ShaderProgram) -> Bool {
        var status : GLint = 0;
        glLinkProgram(shaderProgram.programId);
        
        //Threat any error
        var logLength: GLint = 0
        glGetProgramiv(shaderProgram.programId, GLenum(GL_INFO_LOG_LENGTH), &logLength);
        if (logLength > 0) {
            let log = UnsafeMutablePointer<GLchar>.alloc(Int(logLength))
            glGetProgramInfoLog(shaderProgram.programId, logLength, &logLength, log);
            NSLog("Program link log:\n%s", log);
            free(log);
        }
        
        glGetProgramiv(shaderProgram.programId, GLenum(GL_LINK_STATUS), &status);
        return (status != 0)
        
    }
}
