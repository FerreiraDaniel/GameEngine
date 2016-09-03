#import "SkyBox.h"

@implementation SkyBox

/**
 * The initiator of the skyBox
 *
 * @param aTexture
 *            the Identifier of the texture of the sky
 * @param aModel
 *            The model of the sky box
 */
- (id)init : (int) aTextureId : (RawModel*) aModel {
    self = [super init];
    if (self) {
        self -> _textureId = aTextureId;
        self -> _model = aModel;
    }
    return self;
}

@end
