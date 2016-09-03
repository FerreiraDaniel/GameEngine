#import "LoadUtils.h"

@implementation LoadUtils

/**
 * Reads a string from a certain resource
 *
 * @param fileName
 *            id of the resource where the text exists
 * @return The text that exists in the resource
 */
+ (const char*) readTextFromRawResource: (NSString*) fileName
{
    return [[NSString stringWithContentsOfFile:fileName encoding:NSUTF8StringEncoding error:nil] UTF8String];
}

/**
 * Load texture from resource
 *
 * @param path
 *            To the file where the texture exists
 */
+ (TextureData*) loadTexture : (NSString*) path {
    
    CGImageRef spriteImage = [UIImage imageNamed:path].CGImage;
    
    if (spriteImage == nil) {
        NSLog(@"Error loading file: %@", path);
        return nil;
    } else {
        //Get the sizes
        int width = CGImageGetWidth(spriteImage);
        int height = CGImageGetHeight(spriteImage);
        //Get the data
        Byte *spriteData = (Byte*) calloc(width*height*4, sizeof(GLubyte));
        CGContextRef spriteContext = CGBitmapContextCreate(spriteData, width, height, 8, width * 4,CGImageGetColorSpace(spriteImage), kCGImageAlphaPremultipliedLast);
        CGContextDrawImage(spriteContext, CGRectMake(0, 0, width, height), spriteImage);
        CGContextRelease(spriteContext);
        
        
        return [[TextureData alloc] init : spriteData : width : height ];
    }
}
@end
