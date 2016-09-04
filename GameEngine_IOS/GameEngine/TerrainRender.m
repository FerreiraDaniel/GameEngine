#import "TerrainRender.h"

@implementation TerrainRender
{
@private
    
    /**
     * Reference to the shader manager
     */
    TerrainShaderManager *tShader;
}

/**
 * Initializer of the terrain render
 *
 * @param aShader           Shader manager
 * @param projectionMatrix  The projection matrix of the render
 */
- (id)init: (TerrainShaderManager*) aShader : (GLTransformation*) projectionMatrix  {
    self = [super init];
    if (self) {
        tShader = aShader;
        [tShader start];
        [tShader loadProjectionMatrix: projectionMatrix];
        [tShader stop];
        
    }
    return self;
}

/**
 * Get the transformation matrix of one terrain
 *
 * @param terrain
 *            Entity for which is to create the transformation matrix
 *
 * @return The transformation matrix that put the terrain in its right
 *         position
 */
- (GLTransformation*) getTransformationMatrix : (Terrain*) terrain {
    GLTransformation *matrix = [[GLTransformation alloc] init];
    [matrix glLoadIdentity];
    [matrix glTranslate : terrain.x : terrain.y : terrain.z];
    float terrainRotation = 0.0f;
    [matrix glRotate : terrainRotation : 1.0f : 0.0f : 0.0f];
    [matrix glRotate : terrainRotation : 0.0f : 1.0f : 0.0f];
    [matrix glRotate : terrainRotation : 0.0f : 0.0f : 1.0f];
    return matrix;
}

- (void) render : (Vector3f*) skyColor : (Light*) sun : (GLTransformation*) viewMatrix : (NSMutableArray*) terrains {
    [tShader start];
    [tShader loadSkyColor: skyColor];
    [tShader loadLight: sun];
    [tShader loadViewMatrix: viewMatrix];
    
    [self render: terrains];
    [tShader stop];
}

/**
 * Render one list of terrains
 *
 * @param terrains
 *            Terrains to render
 */
- (void) render : (NSMutableArray*) terrains {
    if ((terrains == nil) || ([terrains count] == 0)) {
        return;
    } else {
        for (int i = 0;i < [terrains count];i++) {
            Terrain* terrain = [terrains objectAtIndex: i];
            [self prepareTerrain: terrain];
            [self prepareInstance: terrain];
            [self render_terrain: terrain];
            [self unbindTexturedModel];
        }
    }
}

/**
 * Bind the attributes of openGL
 *
 * @param terrain
 *      Terrain to get prepared
 */
- (void) prepareTerrain : (Terrain*) terrain {
    RawModel* model = [terrain model];

    
    glBindVertexArrayOES([model vaoId]);
    glEnableVertexAttribArray(LOCATION_ATTR_ID);
    glEnableVertexAttribArray(TEX_COORDINATE_ATTR_ID);
    glEnableVertexAttribArray(NORMAL_VECTOR_ATTR_ID);
    
    // bind several textures of the terrain
    [self bindTextures : terrain];
    
    // Bind the light properties
    [tShader loadShineVariables: 1.0f : 0.0f];
}

/**
 * When loads one texture defines that by default should zoom in/out it
 */
- (void) defineTextureFunctionFilters {
    // The texture minifying function is used whenever the pixel being
    // textured maps to an area greater than one texture element
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    
    // The texture magnification function is used when the pixel being
    // textured maps to an area less than or equal to one texture element
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    
    // Sets the wrap parameter for texture coordinate s
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    
    // Sets the wrap parameter for texture coordinate t
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
}

/**
 * Bind the several textures of the terrain
 *
 * @param terrain Terrain that is going to get the textures bound
 */
- (void) bindTextures : (Terrain*) terrain {
    TerrainTexturesPack* texturesPackage = [terrain texturePack];
    int backgroundTextureId = [texturesPackage backgroundTextureId];
    int mudTextureId = [texturesPackage mudTextureId];
    int grassTextureId = [texturesPackage grassTextureId];
    int pathTextureId = [texturesPackage pathTextureId];
    int weightMapTextureId = [texturesPackage weightMapTextureId];
    
    
    NSDictionary * texturesMatching = [NSDictionary dictionaryWithObjectsAndKeys:
                                       [NSNumber numberWithInt: backgroundTextureId], [NSNumber numberWithInt: GL_TEXTURE0],
                                       [NSNumber numberWithInt: mudTextureId], [NSNumber numberWithInt: GL_TEXTURE1],
                                       [NSNumber numberWithInt: grassTextureId], [NSNumber numberWithInt: GL_TEXTURE2],
                                       [NSNumber numberWithInt: pathTextureId], [NSNumber numberWithInt: GL_TEXTURE3],
                                       [NSNumber numberWithInt: weightMapTextureId], [NSNumber numberWithInt: GL_TEXTURE4],
                                  nil];
    
    for(id key in texturesMatching) {
        int iKey = [key intValue];
        int iTexture = [(texturesMatching[key]) intValue];
        
        glActiveTexture(iKey);
        glBindTexture(GL_TEXTURE_2D, iTexture);
        
        // Set filtering of the texture
        [self defineTextureFunctionFilters];
    }
}

/**
 * Render the terrain itself
 *
 * @param terrain
 *            the terrain to render
 */
- (void)  prepareInstance: (Terrain*) terrain {
    // Load the transformation matrix
    [tShader loadTransformationMatrix: [self getTransformationMatrix: terrain]];
}

/**
 * Call the render of the triangles to the terrain itself
 *
 * @param terrain A reference to the terrain to get render
 */
- (void) render_terrain : (Terrain*) terrain {
    RawModel *rawModel = [terrain model];
    glDrawElements(GL_TRIANGLES, [rawModel indicesCount], GL_UNSIGNED_SHORT, [rawModel indicesData]);
}

/**
 * UnBind the previous binded elements
 */
- (void)  unbindTexturedModel {
    glDisableVertexAttribArray(LOCATION_ATTR_ID);
    glDisableVertexAttribArray(TEX_COORDINATE_ATTR_ID);
    glDisableVertexAttribArray(NORMAL_VECTOR_ATTR_ID);
    glBindVertexArrayOES(0);
}

/**
 * Clean up because we need to clean up when we finish the program
 */
- (void) dealloc {
    tShader = nil;
}

@end
