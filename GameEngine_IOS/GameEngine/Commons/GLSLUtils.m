#import "GLSLUtils.h"


@implementation GLSLUtils
#pragma mark -  OpenGL ES 2 shader compilation

/**
 * Load and compiles the shader into device memory
 *
 * @param type      Type of shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
 * @return 0 -> There was an error
 *         not 0 -> Id of the shader compiled
 */
+ (BOOL)compileShader:(GLuint *)shader type:(GLenum)type source:(const char *)sourceCode
{
    GLint status;
    const GLchar *source;
    
    source = (GLchar *)sourceCode;
    if (!source) {
        NSLog(@"Failed to load vertex shader");
        return NO;
    }
    
    *shader = glCreateShader(type);
    glShaderSource(*shader, 1, &source, NULL);
    glCompileShader(*shader);
    
#if defined(DEBUG)
    GLint logLength;
    glGetShaderiv(*shader, GL_INFO_LOG_LENGTH, &logLength);
    if (logLength > 0) {
        GLchar *log = (GLchar *)calloc(logLength, sizeof(GLchar));
        glGetShaderInfoLog(*shader, logLength, &logLength, log);
        NSLog(@"Shader compile log:\n%s", log);
        free(log);
    }
#endif
    
    glGetShaderiv(*shader, GL_COMPILE_STATUS, &status);
    if (status == 0) {
        glDeleteShader(*shader);
        return NO;
    }
    
    return YES;
}

/**
 * Compiles the shader sources and attache them to the program
 *
 * @param vertexShaderSrc
 *            Source code of the vertex shader
 * @param fragShaderSrc
 *            Source code of the fragment shader
 * @return nil There was an error not 0 -> Id of the program loaded
 */
+ (ShaderProgram*)loadProgram: (const char *)vertexShaderSrc :(const char *)fragShaderSrc
{
    
    GLuint vertShader, fragShader;
    ShaderProgram *shaderProgram;
    
    //Inicialize the shader program that is going to be return
    shaderProgram = [[ShaderProgram alloc] init];
    
    // Create shader program.
    [shaderProgram setProgramId: glCreateProgram()];
    
    // Create and compile vertex shader.

    if (![self compileShader:&vertShader type:GL_VERTEX_SHADER source:vertexShaderSrc]) {
        NSLog(@"Failed to compile vertex shader");
        return nil;
    }
    [shaderProgram setVertexShaderId: vertShader];
    
    // Create and compile fragment shader.
    if (![self compileShader:&fragShader type:GL_FRAGMENT_SHADER source:fragShaderSrc]) {
        NSLog(@"Failed to compile fragment shader");
        return nil;
    }
    [shaderProgram setFragmentShaderId: fragShader];
    
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
+ (BOOL)linkProgram: (ShaderProgram*) shaderProgram
{
    GLint status;
    glLinkProgram(shaderProgram.programId);
    
#if defined(DEBUG)
    GLint logLength;
    glGetProgramiv(shaderProgram.programId, GL_INFO_LOG_LENGTH, &logLength);
    if (logLength > 0) {
        GLchar *log = (GLchar *)calloc(logLength, sizeof(GLchar));
        glGetProgramInfoLog(shaderProgram.programId, logLength, &logLength, log);
        NSLog(@"Program link log:\n%s", log);
        free(log);
    }
#endif
    
    glGetProgramiv(shaderProgram.programId, GL_LINK_STATUS, &status);
    if (status == 0) {
        return NO;
    }
    
    return YES;
}
@end
