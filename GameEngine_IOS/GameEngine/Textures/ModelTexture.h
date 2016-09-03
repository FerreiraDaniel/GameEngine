#import <Foundation/Foundation.h>

/**
 * Has all the required parameters to render one textured entity
 */
@interface ModelTexture : NSObject

/**
 * The identifier of the texture
 */
@property (readwrite) int textureId;

/**
 * How damped the shine is
 */
@property float shineDamper;

/**
 * How reflective the model is
 */
@property float reflectivity;


/**
 Initiator of model texture
 *
 * @param textureId Identifier of the texture
 *
 */
- (id)init : (int) textureId;

@end
