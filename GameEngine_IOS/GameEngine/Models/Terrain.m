#import "Terrain.h"

@implementation Terrain

- (id)init : (TerrainTexturesPack*) aTexturePack : (RawModel*) aRawModel : (Vector3f*) aPosition {
    self = [super init];
    if (self) {
        
        self -> _texturePack = aTexturePack;
        self -> _model = aRawModel;
        self -> _x = aPosition.x * SIZE;
        self -> _y = aPosition.y * SIZE;
        self -> _z = aPosition.z * SIZE;
    }
    return self;
}
@end
