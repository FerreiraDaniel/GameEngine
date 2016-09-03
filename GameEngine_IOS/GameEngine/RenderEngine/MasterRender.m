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
    EntityRender* entityRender;
    
    /**
     * Reference to the render of the sky box
     */
    SkyBoxRender* skyBoxRender;
    
    
    /**
     * Reference to the camera from where the user is going to see the 3D world
     */
    Camera* camera;
    
    /**
     * Entities of the world that are going to be rendered
     */
    NSMutableDictionary *entities;
    
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
- (GLTransformation*) createProjectionMatrix {
    GLTransformation* matrix = [[GLTransformation alloc] init];
    [matrix glLoadIdentity];
    float aspect = fabs( ((1.0f) * width) / height);
    [matrix gluPerspective : FOV : aspect : NEAR_PLANE : FAR_PLANE];
    
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
- (GLTransformation*) createViewMatrix : (Camera*) aCamera {
    GLTransformation* matrix = [[GLTransformation alloc]init ];
    [matrix glLoadIdentity];
    [matrix glRotate : aCamera.pitch : 1.0f : 0.0f : 0.0f];
    [matrix glRotate : aCamera.yaw : 0.0f : 1.0f : 0.0f];
    Vector3f *camPosition = aCamera.position;
    [matrix glTranslate : -camPosition.x : -camPosition.y : -camPosition.z];
    return matrix;
}


/**
 * Calls the methods to update the camera and updates the matrix that
 * describe the camera in the scene
 */
- (GLTransformation*) updateCamera {
    [camera move];
    [camera rotate];
    
    // Matrix update
    GLTransformation *viewMatrix = [self createViewMatrix: camera];
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
        GLTransformation* projectionMatrix = [self createProjectionMatrix];
        
        
        //Initializes the entity render
        EntityShaderManager* eShader = [[EntityShaderManager alloc] init];
        entityRender = [[EntityRender alloc] init : eShader : projectionMatrix];
        
        // Initializes the entities to be render
        self-> entities = [[NSMutableDictionary alloc]init];
        
        // Initializes the sky box render
        SkyBoxShaderManager* sbManager = [[SkyBoxShaderManager alloc]init];
        self->skyBoxRender = [[SkyBoxRender alloc] init : sbManager : projectionMatrix];
        
        // Initializes the camera
        camera = [[Camera alloc] init];
        
        
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
 * Render the entire scene (Called by each frame)
 *
 * @param sun
 *            Sun of the scene
 */
- (void) render : (Light*) sun {
    [self prepare];
    GLTransformation* viewMatrix = [self updateCamera];
    Vector3f* skyColor = [[Vector3f alloc] init: SKY_R : SKY_G : SKY_B];
    [entityRender render: skyColor : sun : viewMatrix : entities];
    [skyBoxRender render: viewMatrix : skyBox];
    
    //TODO other renders
}

/**
 * Set the sky box the use during the render
 */
- (void) processSkyBox : (SkyBox*) aSkyBox {
    self -> skyBox = aSkyBox;
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

//TODO dealloc

@end