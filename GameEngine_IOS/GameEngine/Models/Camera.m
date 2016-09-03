#import "Camera.h"

@implementation Camera

/**
 Initiator of camera
 *
 */
- (id)init {
    self = [super init];
    if (self) {
        self -> _position = [[Vector3f alloc] init: 0.0f : 1.0f : 0.0f];
        self -> _yaw = -120;
    }
    return self;
}



/**
 * Read the keys pressed by the used and updates the
 * position of the camera
 */
- (void) move {
    double pitchR = GLKMathDegreesToRadians([self pitch]);
    double yawR = GLKMathDegreesToRadians([self yaw]);
    
    //if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
    _position.x += (-STEP * sin(yawR));
    _position.y += (+STEP * sin(pitchR));
    _position.z += (-STEP * sin(pitchR)) + (-STEP * cos(yawR));
    //}
    //if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
    /*_position.x += (+STEP * sin(yawR));
    _position.y += (-STEP * sin(pitchR));
    _position.z += (+STEP * sin(pitchR)) + (+STEP * cos(yawR));*/
    //}
}


/**
 * Rotate the camera that the user sees
 */
- (void) rotate {
    /*Looks down*/
    //if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
    //_pitch -= ANGLE_OF_ROTATION;
    //}
    
    /*Looks up*/
    //if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
    //_pitch += ANGLE_OF_ROTATION;
    //}
    
    /*Looks left*/
    //if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
    //_yaw += ANGLE_OF_ROTATION;
    //}
    
    /*Looks right*/
    //if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
    //_yaw -= ANGLE_OF_ROTATION;
}

/**
 Unassign the associated values of the class
 */
- (void) dealloc {
    self -> _position = nil;
}

@end
