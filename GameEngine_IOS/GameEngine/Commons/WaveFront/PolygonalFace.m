#import "PolygonalFace.h"

@implementation PolygonalFace
/**
 * Initialize with all the parameters of the face
 *
 * @param vertexIndex  Index of the vertex of the face
 * @param textureIndex Index of the texture of the face
 * @param normalIndex  Index of normal for this face
 */
- (id)init : (unsigned short) vertexIndex : (unsigned short) textureIndex : (unsigned short) normalIndex {
    self = [super init];
    if (self) {
        self ->_vertexIndex = vertexIndex;
        self ->_textureIndex = textureIndex;
        self ->_normalIndex = normalIndex;
    }
    
    return self;
}
@end
