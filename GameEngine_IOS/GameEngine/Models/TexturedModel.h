#import <Foundation/Foundation.h>
#import "RawModel.h"
#import "ModelTexture.h"

/**
 * Wrapper that besides of have the raw model also has the texture to put in the model
 */
@interface TexturedModel : NSObject

/**
 * Identifier of the textured model
 */
@property (readonly)  long id;

/**
 * Raw model of the entity
 */
@property (readonly)  RawModel* rawModel;

/**
 * Reference to the texture of the entity
 */
@property (readonly)  ModelTexture* texture;

/**
 * Indicates if the model has transparency or not
 */
@property (readonly)  BOOL hasTransparency;


/**
 * Indicate that all our normals of the object are going to point up (in the same
 * direction
 */
@property  BOOL normalsPointingUp;

/**
 * Initiator of the textured model
 *
 * @param aRawModel			Raw model of the entity
 * @param aTexture			Reference to the texture of the entity
 * @param aHasTransparency	Indicates if the model has transparency or not not
 * @param aNormalsPointingUp	Indicates if the normals of the model should point up
 */
- (id)init : (RawModel*) aRawModel : (ModelTexture*) aTexture : (bool) aHasTransparency : (bool) aNormalsPointingUp;


/**
 * Initiator of the textured model
 
 * @param aRawModel	Raw model of the entity
 * @param aTexture	Reference to the texture of the entity
 */
- (id)init : (RawModel*) aRawModel :  (ModelTexture*) aTexture;

@end
