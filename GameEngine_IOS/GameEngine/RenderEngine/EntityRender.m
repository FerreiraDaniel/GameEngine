#import "EntityRender.h"
#import <OpenGLES/ES2/glext.h>

/**
 * Class responsible to render the entities in the screen
 */
@implementation EntityRender
{
@private
    
    /**
     * Reference to the shader manager
     */
    EntityShaderManager *eShader;
}

/**
 * Initializer of the entity render
 *
 * @param aShader           Shader manager
 * @param projectionMatrix  The projection matrix of the render
 */
- (id)init: (EntityShaderManager*) aShader : (GLTransformation*) projectionMatrix  {
    self = [super init];
    if (self) {
        eShader = aShader;
        [eShader start];
        [eShader loadProjectionMatrix: projectionMatrix];
        [eShader stop];
        
    }
    return self;
}

/**
 * Get the transformation matrix of one entity
 *
 * @param entity
 *            Entity for which is to create the transformation matrix
 *
 * @return The transformation matrix that put the entity in its right
 *         position
 */
- (GLTransformation*) getTransformationMatrix : (Entity*) entity {
    GLTransformation *matrix = [[GLTransformation alloc] init];
    [matrix glLoadIdentity];
    Vector3f *entityPosition = [entity position];
    [matrix glTranslate : entityPosition.x : entityPosition.y : entityPosition.z];
    [matrix glRotate : entity.rotX : 1.0f : 0.0f : 0.0f];
    [matrix glRotate : entity.rotY : 0.0f : 1.0f : 0.0f];
    [matrix glRotate : entity.rotZ : 0.0f : 0.0f : 1.0f];
    
    [matrix glScale : entity.scale : entity.scale : entity.scale];
    return matrix;
}

//---------------------------------------------------------------------------------
//Render method
- (void) render : (Vector3f*) skyColor : (Light*) sun : (GLTransformation*) viewMatrix : (NSMutableDictionary*) entities;
{
    // Render the object
    [eShader start];
    //Load the elements of the scene
    [eShader loadSkyColor : skyColor];
    [eShader loadLight : sun];
    [eShader loadViewMatrix: viewMatrix];
    
    // Render the entities
    [self render : entities];
    
    [eShader stop];
    
}


/**
 * Render one hashMap of entities where each key is a group of similar
 * entities to be render
 *
 * @param entities
 *            HashMap of entities to render
 */
- (void) render : (NSMutableDictionary*) entities{
    if ((entities == nil) || ([entities count] == 0)) {
        return;
    } else {
        [entities enumerateKeysAndObjectsUsingBlock:^(NSString* key, NSArray* entitiesLst, BOOL* stop) {
            TexturedModel* model = [[entitiesLst objectAtIndex: 0] model];
            [self prepareTexturedModel: model];
            for (int i = 0 ; i < [entitiesLst count]; i++) {
                Entity* entity = entitiesLst[i];
                [self render_entity: entity];
            }
            
            [self unbindTexturedModel];
            //Restore the state if has transparency
            if(!model.hasTransparency) {
                [self disableCulling];
            }
            
        }];
        
        
    }
}

/**
 * Call the render of the triangles to the entity itself
 *
 * @param entity
 * 			Entity to get render
 */
- (void) render_entity : (Entity*) entity {
    
    TexturedModel *model = entity.model;
    RawModel *rawModel = model.rawModel;
    [self prepareInstance: entity];
    
    glDrawElements(GL_TRIANGLES, [rawModel indicesCount], GL_UNSIGNED_SHORT, [rawModel indicesData]);
}

//----------------------------------------------------------------------------------

/**
 * Bind the attributes of openGL
 *
 * @param texturedModel Model that contains the model of the entity with textures
 */
- (void) prepareTexturedModel : (TexturedModel*) texturedModel {
    RawModel* model = [texturedModel rawModel];
    ModelTexture* modelTexture = [texturedModel texture];
    
    if(!texturedModel.hasTransparency) {
        [self enableCulling];
    };
    glBindVertexArrayOES([model vaoId]);
    
    glEnableVertexAttribArray(LOCATION_ATTR_ID);
    glEnableVertexAttribArray(TEX_COORDINATE_ATTR_ID);
    glEnableVertexAttribArray(NORMAL_VECTOR_ATTR_ID);
    
    //Activate the texture of the entity
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texturedModel.texture.textureId);
    
    // Load if should put the normals of the entity point up or not
    [eShader loadNormalsPointingUp: [texturedModel normalsPointingUp]];
    
    // Load the the light properties
    [eShader loadShineVariables: [modelTexture shineDamper] : [modelTexture reflectivity]];
    
}

/**
 * Load the transformation matrix of the entity
 *
 * @param entity
 * 			Entity that is to get prepared to be loaded
 */
- (void) prepareInstance : (Entity*) entity {
    // Load the transformation matrix
    [eShader loadTransformationMatrix: [self getTransformationMatrix : entity]];
}


/**
 * Enable culling of faces to get better performance
 */
- (void) enableCulling {
    //Enable the GL cull face feature
    glEnable(GL_CULL_FACE);
    //Avoid to render faces that are away from the camera
    glCullFace(GL_BACK);
}

/**
 * Disable the culling of the faces vital for transparent model
 */
- (void) disableCulling {
    glDisable(GL_CULL_FACE);
}

/**
 * UnBind the previous binded elements
 */
- (void) unbindTexturedModel {
    glDisableVertexAttribArray(LOCATION_ATTR_ID);
    glDisableVertexAttribArray(TEX_COORDINATE_ATTR_ID);
    glDisableVertexAttribArray(NORMAL_VECTOR_ATTR_ID);
    // UnBind the current VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
}

/**
 * Clean up because we need to clean up when we finish the program
 */
- (void) dealloc {
    eShader = nil;
}

@end
