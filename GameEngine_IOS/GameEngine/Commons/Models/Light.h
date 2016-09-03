#import <Foundation/Foundation.h>
#import "Vector3f.h"

/**
 * Represents one light source in the scene
 */
@interface Light : NSObject

/**
 * Position where the light will exist
 */
@property Vector3f *position;

/**
 * The intensity of the light in the different components
 */
@property Vector3f *color;

/**
 * The constructor of the light entity
 *
 * @param aPosition
 *            Position where the light will exist
 * @param aColor
 *            The intensity of the light in the different components
 */
- (id)init : (Vector3f*) aPosition :  (Vector3f*) aColor;

@end
