#import "Light.h"

@implementation Light

/**
 * The constructor of the light entity
 *
 * @param aPosition
 *            Position where the light will exist
 * @param aColor
 *            The intensity of the light in the different components
 */
- (id)init : (Vector3f*) aPosition :  (Vector3f*) aColor
{
    self = [super init];
    if (self) {
        self -> _position = aPosition;
        self -> _color = aColor;
    }
    return self;
}



@end
