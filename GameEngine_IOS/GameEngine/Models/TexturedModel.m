#import "TexturedModel.h"

@implementation TexturedModel

/**
 * Static variable to generate new ids
 */
static long newId = 1;


/**
 * Initiator of the textured model
 *
 * @param aRawModel			Raw model of the entity
 * @param aTexture			Reference to the texture of the entity
 * @param aHasTransparency	Indicates if the model has transparency or not not
 * @param aNormalsPointingUp	Indicates if the normals of the model should point up
 */
- (id)init : (RawModel*) aRawModel : (ModelTexture*) aTexture : (bool) aHasTransparency : (bool) aNormalsPointingUp {
    self = [super init];
    if (self) {
        self -> _rawModel = aRawModel;
        self -> _texture = aTexture;
        self -> _id = newId++;
        self -> _hasTransparency = aHasTransparency;
        self -> _normalsPointingUp = aNormalsPointingUp;
    }
    return self;
}



/**
 * Initiator of the textured model
 *
 * @param aRawModel	Raw model of the entity
 * @param aTexture	Reference to the texture of the entity
 *
 */
- (id)init : (RawModel*) aRawModel :  (ModelTexture*) aTexture {
    return [self init : aRawModel : aTexture : false : false];
}

@end
