#import "TextureData.h"

@implementation TextureData

/**
 * The initialize of the texture data
 
 * @param buffer
 *            The buffer with data about the pixels of the image
 * @param width
 *            Width of the texture
 * @param height
 *            Height of the texture
 
 */
- (id)init : (Byte*) buffer : (int) width : (float) height {
    self = [super init];
    if (self) {
        self -> _buffer = buffer;
        self -> _width = width;
        self -> _height = height;
    }
    return self;
}

/**
 *
 *  Releases memory of the buffer
 *
 */
- (void) dealloc {
    free(self-> _buffer);
}


@end
