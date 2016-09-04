#import <Foundation/Foundation.h>
#import "Vector3f.h"
#import <GLKit/GLKit.h>

static const float ANGLE_OF_ROTATION =  0.4f;
static const float STEP = 2.0f;

/**
 * Represents the the camera used by the user to see the
 * 3D World
 */
@interface Camera : NSObject


/**
 * The position where the camera is
 */
@property (readonly) Vector3f *position;

/**
 * Pitch is up and down (rotation around X-axis)
 */
@property (readonly)  float pitch;

/**
 * Yaw is the angle when moving the head left and right (rotation around Y-axis)
 */
@property (readonly) float yaw;

/**
 * Roll, which we usually don't experience is when you tilt your head (rotation around Z-axis)
 */
@property (readonly)   float roll;

/**
 Initiator of camera
 *
 */
- (id)init;

/**
 * Read the keys pressed by the used and updates the
 * position of the camera
 */
- (void) move;

/**
 * Rotate the camera that the user sees
 */
- (void) rotate;
@end
