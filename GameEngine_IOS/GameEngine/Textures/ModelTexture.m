#import "ModelTexture.h"

@implementation ModelTexture

/**
 Initiator of model texture
 *
 * @param textureId Identifier of the texture
 *
 */
- (id)init : (int) textureId {
    self = [super init];
    if (self) {
        self -> _textureId = textureId;
        self -> _shineDamper = 1.0f;
        self -> _reflectivity = 0.0f;
    }
    return self;
}

@end
