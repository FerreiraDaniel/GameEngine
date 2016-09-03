#import "SkyBoxRender.h"

@implementation SkyBoxRender
{
@private
    
    /**
     * Reference to the shader manager
     */
    SkyBoxShaderManager *sbShader;
}

/**
 * Initializer of the sky box render
 *
 * @param aShader           Shader manager
 * @param projectionMatrix  The projection matrix of the render
 */
- (id)init: (SkyBoxShaderManager*) aShader : (GLTransformation*) projectionMatrix  {
    self = [super init];
    if (self) {
        sbShader = aShader;
        [sbShader start];
        [sbShader loadProjectionMatrix: projectionMatrix];
        [sbShader stop];
        
    }
    return self;
}


//---------------------------------------------------------------------------------
//Render method
- (void) render : (GLTransformation*) viewMatrix : (SkyBox*) skyBox
{
    // Render the object
    [sbShader start];
    //Load the elements of the scene
    [sbShader loadViewMatrix: viewMatrix];
    
    [self prepareSkyBox: skyBox];
    
    // Render the sky box
    [self render : skyBox];
    
    [self unbindTexture];
    
    [sbShader stop];
    
}
//---------------------------------------------------------------------------------


/**
 * Bind the attributes of openGL
 *
 * @param skyBox
 * 			The sky box description that should be prepared
 */
- (void) prepareSkyBox : (SkyBox*) skyBox {
    RawModel* model = [skyBox model];
    
    glBindVertexArrayOES([model vaoId]);
    
    glEnableVertexAttribArray(LOCATION_ATTR_ID);
    
    
    // bind several textures of the sky box
    [self bindTextures : skyBox];
}

/**
 * Bind the cube texture of the skyBox
 */
- (void) bindTextures : (SkyBox*) skyBox {
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_CUBE_MAP, skyBox.textureId);
}



/**
 * Call the render of the triangles to the skyBox itself
 *
 * @param skyBox
 * 			The sky box to be render
 */
- (void) render : (SkyBox*) skyBox {
    RawModel *rawModel = skyBox.model;
    glDrawArrays(GL_TRIANGLES, 0, [rawModel indicesCount]);
}





/**
 * UnBind the previous binded elements
 */
- (void) unbindTexture {
    glDisableVertexAttribArray(LOCATION_ATTR_ID);
    // UnBind the current VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
}

/**
 * Clean up because we need to clean up when we finish the program
 */
- (void) dealloc {
    sbShader = nil;
}

@end
