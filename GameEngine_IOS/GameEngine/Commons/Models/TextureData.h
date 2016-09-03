#import <Foundation/Foundation.h>

/**
 * Holds the data of the pixels of a texture as well as the width and height
 */
@interface TextureData : NSObject

/**
 * Width of the texture
 */
@property (readonly) int width;

/**
 * Height of the texture
 */
@property (readonly)  int height;

/**
 * The buffer with data about the pixels of the image
 */
@property (readonly) Byte *buffer;

/**
 * The initialize of the texture data
 
 * @param buffer
 *            The buffer with data about the pixels of the image
 * @param width
 *            Width of the texture
 * @param height
 *            Height of the texture
 
 */
- (id)init : (Byte*) buffer : (int) width : (float) height;
@end
