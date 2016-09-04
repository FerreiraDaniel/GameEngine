#import "GameViewController.h"
#import "MasterRender.h"


@interface GameViewController () {
    
    
    MasterRender *renderer;

    NSMutableArray *entities;
    NSMutableArray *terrains;
    Light *light;
    SkyBox *skyBox;
    
}
@property (strong, nonatomic) EAGLContext *context;

- (void)setupGL;
- (void)tearDownGL;

@end

@implementation GameViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    

    
    self.context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES2];
    
    if (!self.context) {
        NSLog(@"Failed to create ES context");
    }
    
    GLKView *view = (GLKView *)self.view;
    view.context = self.context;
    view.drawableDepthFormat = GLKViewDrawableDepthFormat24;
    
    [self setupGL];
    
    /* Initializes the main variables responsible to render the 3D world */
    Loader *loader = [[Loader alloc]init];
    
    /* Prepares the entities that is going to be render */
    self -> entities = [WorldEntitiesGenerator getEntities: loader];
    
    /* Prepares the terrains that is going to render */
    self -> terrains = [WorldTerrainsGenerator getTerrains: loader];
    
    /* Load the lights that is going to render*/
    self -> light = [WorldEntitiesGenerator getLight];
    
    /* Load the sky box that is going to render*/
    self -> skyBox = [WorldSkyBoxGenerator getSky : loader];
}

- (void)dealloc
{
    [self tearDownGL];
    
    if ([EAGLContext currentContext] == self.context) {
        [EAGLContext setCurrentContext:nil];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    
    if ([self isViewLoaded] && ([[self view] window] == nil)) {
        self.view = nil;
        
        [self tearDownGL];
        
        if ([EAGLContext currentContext] == self.context) {
            [EAGLContext setCurrentContext:nil];
        }
        self.context = nil;
    }
    
    // Dispose of any resources that can be recreated.
    //Todo
}

- (BOOL)prefersStatusBarHidden {
    return YES;
}

- (void)setupGL
{
    [EAGLContext setCurrentContext:self.context];
    
    renderer = [[MasterRender alloc] init: self.view.bounds.size.width : self.view.bounds.size.height];
}

- (void)update
{
    
    /*NSDate* startDate = [NSDate date];*/
    // game logic
    [renderer processTerrains : terrains];
    [renderer processEntities : entities];
    [renderer processSkyBox : skyBox];
    
    [renderer render: light];
    
    // Logs frames/s
    /*NSDate* endDate = [NSDate date];

    NSTimeInterval secondsBetween = [endDate timeIntervalSinceDate:startDate];
    //printf("%f Frames/s\n", (1.0f / secondsBetween));*/
    
}

- (void)tearDownGL
{
    [EAGLContext setCurrentContext:self.context];
    renderer = nil;
    
    //glDeleteBuffers(1, &_vertexBuffer);
    //glDeleteVertexArraysOES(1, &_vertexArray);
}



@end
