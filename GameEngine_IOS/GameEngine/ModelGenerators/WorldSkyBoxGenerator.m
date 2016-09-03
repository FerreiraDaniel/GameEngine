#import "WorldSkyBoxGenerator.h"


@implementation WorldSkyBoxGenerator

    
+ (SkyBox*) getSky : (Loader*) loader{
    id<IShape> skyBoxShape = [[SkyBoxShape alloc] init];
    RawModel* rawModel = [loader load3DPositionsToVAO: [skyBoxShape getVertices] : [skyBoxShape countVertices]];
    
    /* The names that the textures of the sky have */
    NSArray* TEXTURE_FILES = [[NSArray alloc] initWithObjects: @"sky_right", @"sky_left", @"sky_top", @"sky_bottom", @"sky_back", @"sky_front" , nil];
    
    int textureId = [loader loadTCubeMap: TEXTURE_FILES];
    return [[SkyBox alloc] init : textureId : rawModel];
}

@end
