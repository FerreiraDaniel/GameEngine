#import "Entity.h"

@implementation Entity

/**
 * Initiator of the entity to be render in the 3D world
 *
 * @param aModel    Textured model
 * @param aPosition  Position where the model should be render
 * @param aRotX     Rotation of the model in the X axle
 * @param aRotY     Rotation of the model in the Y axle
 * @param aRotZ     Rotation of the model in the Z axle
 * @param aScale    Scale of the model
 */
- (id)init : (TexturedModel*) aModel : (Vector3f*) aPosition : (float) aRotX : (float) aRotY : (float) aRotZ : (float) aScale {
    self = [super init];
    if (self) {
        self -> _model = aModel;
        self -> _position = aPosition;
        self -> _rotX = aRotX;
        self -> _rotY = aRotY;
        self -> _rotZ = aRotZ;
        self -> _scale = aScale;
    }
    return self;
}

/**
 * Increases the position of the model using for that the specified components
 *
 * @param dx	X component to be increase
 * @param dy	Y component to be increase
 * @param dz	Z component to be increase
 */
- (void) increasePosition : (float) dx : (float) dy : (float) dz {
    self -> _position.x += dx;
    self -> _position.y += dy;
    self -> _position.z += dz;
}

/**
 * Increases the rotation of the model using for that the specified components
 * @param dx	X component to be increase
 * @param dy	Y component to be increase
 * @param dz	Z component to be increase
 */
- (void) increaseRotation : (float) dx : (float) dy : (float) dz {
    self -> _rotX += dx;
    self -> _rotY += dy;
    self -> _rotZ += dz;
}

@end
