#import <Foundation/Foundation.h>

/**
 * Represents a 2D Vector with their x,y,z coordinates
 */
@interface Vector2f : NSObject

@property  float x;
@property  float y;

/**
 * The initialize of the vector
 *
 * @param aX
 *            coordinate
 * @param aY
 *            coordinate
 */
- (id)init : (float) aX : (float) aY;

@end
