#import <Foundation/Foundation.h>
#import "TextureData.h"
#import <GLKit/GLKit.h>

@interface LoadUtils : NSObject

/**
 * Reads a string from a certain resource
 *
 * @param fileName
 *            Name of the resource where the text exists
 * @return The text that exists in the resource
 */
+ (const char*) readTextFromRawResource: (NSString*) fileName;


/**
 * Load texture from resource
 *
 * @param fileName
 *            Name of the where the texture exists
 */
+ (TextureData*) loadTexture : (NSString*) fileName;
@end
