#import <Foundation/Foundation.h>
#import "Vector2f.h"

@interface Vector3f : Vector2f

@property  float z;

/**
 * The Initialize of the vector
 *
 * @param aX
 *            X-coordinate
 * @param aY
 *            Y-coordinate
 * @param aZ
 *            Z-coordinate
 */
- (id)init : (float) aX : (float) aY : (float) aZ;

@end
