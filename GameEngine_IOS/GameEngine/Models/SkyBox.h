#import <Foundation/Foundation.h>
#import "RawModel.h"

/**
 * Represents a box with sky textures
 */
@interface SkyBox : NSObject

/**
 * RawModel of the skyBox
 */
@property (readonly) RawModel *model;

/**
 * The identifier of the sky box cubic texture
 */
@property (readonly) int textureId;

/**
 * The initiator of the skyBox
 *
 * @param aTextureId
 *            the Identifier of the texture of the sky
 * @param aModel
 *            The model of the sky box
 */
- (id)init : (int) aTextureId : (RawModel*) aModel ;
@end
