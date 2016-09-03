#import <Foundation/Foundation.h>

/**
 * Define the default properties that the element should obey
 */
@interface DefaultModelGenerator : NSObject <NSCopying>

/**
 * The name of the .obj that represents the model
 */
@property NSString* objectName;

/**
 * The name of the .png that represents the texture of the model
 */
@property NSString* textureName;

/**
 * The scale of the model
 */
@property float scale;

/**
 * Indicates if the model has transparency or not
 */
@property Boolean hasTransparency;

/**
 * Indicate that all our normals of the object are going to point up (in the same
 * direction
 */
@property Boolean normalsPointingUp;

-(id) copyWithZone: (NSZone *) zone;

@end
