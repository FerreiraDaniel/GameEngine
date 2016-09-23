#import "MasterRender.h"

@implementation MasterRender
{
@private
    
    /**
     * Width of the screen
     */
    int width;
    
    /**
     * Height of the screen
     */
    int height;
    
    /**
     * Reference to the render of the entities
     */
    EntityRenderSwift * entityRenderSwift;
    
    /**
     * Reference to the render of the terrains
     */
    TerrainRenderSwift* terrainRender;
    
    /**
     * Reference to the render of the sky box
     */
    SkyBoxRenderSwift* skyBoxRender;
    
    
    /**
     * Reference to the camera from where the user is going to see the 3D world
     */
    Camera* camera;
    
    /**
     * Entities of the world that are going to be rendered
     */
    NSMutableDictionary *entities;
    
    /**
     * List of terrains of the world that are going to be render
     */
    NSMutableArray* terrains;
    
    /**
     * The sky box that is going to use during the render
     */
    SkyBox* skyBox;
}

/* Constants of the camera */
const float FOV = 65.0f;
const float NEAR_PLANE = 0.1f;
const float FAR_PLANE = 1000.0f;


/*Components of the color of the sky*/
const float SKY_R = 0.5f;
const float SKY_G = 0.5f;
const float SKY_B = 0.5f;



/**
 * Create the projection matrix with parameters of the camera
 *
 * @return A projection matrix
 */
- (GLTransformationSwift*) createProjectionMatrixSwift {
    GLTransformationSwift* matrix = [[GLTransformationSwift alloc] init];
    [matrix glLoadIdentity];
    float aspect = fabs( ((1.0f) * width) / height);
    
    [matrix gluPerspective:FOV aspect:aspect nearZ:NEAR_PLANE farZ:FAR_PLANE];
    
    return matrix;
}


/**
 * Create the view matrix from the data that has about the camera
 *
 * @param aCamera
 *            the camera to which is to create the view matrix
 *
 * @return The view matrix
 */
- (GLTransformationSwift*) createViewMatrixSwift : (Camera*) aCamera {
    GLTransformationSwift* matrix = [[GLTransformationSwift alloc]init ];
    [matrix glLoadIdentity];
    [matrix glRotate:aCamera.pitch x:1.0 y:0.0 z:0.0];
    [matrix glRotate:aCamera.yaw x:0.0 y:1.0 z:0.0];
    
    Vector3f *camPosition = aCamera.position;
    [matrix glTranslate: -camPosition.x ty: -camPosition.y tz: -camPosition.z];
    return matrix;
}


/**
 * Calls the methods to update the camera and updates the matrix that
 * describe the camera in the scene
 */
- (GLTransformationSwift*) updateCameraSwift {
    [camera move];
    [camera rotate];
    
    // Matrix update
    GLTransformationSwift *viewMatrix = [self createViewMatrixSwift: camera];
    return viewMatrix;
}

/**
 Initiator of the master render
 */
- (id)init : (int) aWidth : (int) aHeight {
    self = [super init];
    if (self) {
        width = aWidth;
        height = aHeight;
        
        //Initializes the projection matrix
        GLTransformationSwift *projectionMatrixSwift = [self createProjectionMatrixSwift];
        
        
        //Initializes the entity render        
        EntityShaderManagerSwift * eShaderSwift = [[EntityShaderManagerSwift alloc] init];
        self -> entityRenderSwift = [[EntityRenderSwift alloc] initWithAShader : eShaderSwift projectionMatrix : projectionMatrixSwift];
        
        // Initializes the entities to render
        self-> entities = [[NSMutableDictionary alloc]init];
        
        // Initializes the terrain render
        TerrainShaderManagerSwift* tShader = [[TerrainShaderManagerSwift alloc] init];
        self-> terrainRender = [[TerrainRenderSwift alloc] initWithAShader : tShader projectionMatrix: projectionMatrixSwift];
        
        // Initializes the terrains to render
        self -> terrains = [[NSMutableArray alloc] init];
        
        // Initializes the sky box render
        SkyBoxShaderManager* sbManager = [[SkyBoxShaderManager alloc]init];
        
        self-> skyBoxRender = [[SkyBoxRenderSwift alloc] initWithAShader : sbManager  projectionMatrix: projectionMatrixSwift];
        
        // Initializes the camera
        self ->camera = [[Camera alloc] init];
        
        projectionMatrixSwift = nil;
    }
    return self;
}

/**
 * Put one entity in the list of entities to render
 *
 * @param entity
 *            the entity to add to the render
 */
- (void) processEntity : (Entity*) entity {
    TexturedModel* entityModel = entity.model;
    NSString* key = [NSString stringWithFormat:@"%ld", entityModel.id];
    NSMutableArray* batch = [entities objectForKey: key];
    
    if (batch == nil) {
        batch = [[NSMutableArray alloc] init];
        [entities setValue: batch forKey: key];
    }
    [batch addObject: entity];
}


/**
 * Put the entities to process in the hash map dedicated to process entities
 * by group
 *
 * @param lEntities
 *            list of entities to get render in the next frame
 */
- (void) processEntities: (NSMutableArray*) lEntities {
    [self-> entities removeAllObjects];
    if ((lEntities != nil) && (lEntities.count > 0)) {
        for (int i = 0; i < lEntities.count; i++) {
            Entity* entity = lEntities[i];
            [self processEntity : entity];
        }
    }
}

/**
 * Put a terrain in the list of terrains to render
 *
 * @param terrain
 *            the terrain to render
 */
- (void)  processTerrain : (Terrain*) terrain {
    [self-> terrains addObject: terrain];
}


- (void) processTerrains : (NSMutableArray*) lTerrains {
    [self->terrains removeAllObjects];
    
    if ((lTerrains != nil) && (lTerrains.count > 0)) {
        for (int i = 0; i < lTerrains.count; i++) {
            Terrain* terrain = lTerrains[i];
            [self processTerrain : terrain];
        }
    }
}

/**
 * Set the sky box the use during the render
 */
- (void) processSkyBox : (SkyBox*) aSkyBox {
    self -> skyBox = aSkyBox;
}

/**
 * Render the entire scene (Called by each frame)
 *
 * @param sun
 *            Sun of the scene
 */
- (void) render : (Light*) sun {
    [self prepare];
    GLTransformationSwift* viewMatrixSwift = [self updateCameraSwift];
    Vector3f* skyColor = [[Vector3f alloc] init: SKY_R : SKY_G : SKY_B];
    //[entityRender render: skyColor : sun : viewMatrix : entities];
    [entityRenderSwift render:skyColor sun: sun viewMatrix: viewMatrixSwift entities: entities];
    [terrainRender render:skyColor sun:sun viewMatrix:viewMatrixSwift terrains:terrains];
    
    
    [skyBoxRender render:viewMatrixSwift skyBox:skyBox];
    
}


/**
 * Clean the data of the previous frame
 */
- (void)prepare
{
    glEnable(GL_DEPTH_TEST);
    
    // Set the color clear value
    glClearColor(0, 0.3f, 0, 1);
    
    // Clear the color and depth buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

/**
 *  Releases the resources used by the class
 */
- (void) dealloc {
    self -> entityRenderSwift = nil;
    self -> entities = nil;
    self -> terrainRender = nil;
    self -> terrains = nil;
    self -> skyBoxRender = nil;
    self -> camera = nil;
}

@end