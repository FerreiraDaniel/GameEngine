#import <Foundation/Foundation.h>

/**
 * Points to the properties of the a face of the polygon
 */
@interface PolygonalFace : NSObject

/**
 *
 * Index of the vertex of the face
 */
@property (readonly) unsigned short vertexIndex;

/**
 *
 * Index of the texture of the face
 */
@property (readonly) unsigned short  textureIndex;


/**
 *
 * Index of normal for this face
 */
@property (readonly) unsigned short  normalIndex;


/**
 * Initialize with all the parameters of the face
 *
 * @param vertexIndex  Index of the vertex of the face
 * @param textureIndex Index of the texture of the face
 * @param normalIndex  Index of normal for this face
 */
- (id)init : (unsigned short ) vertexIndex : (unsigned short ) textureIndex : (unsigned short) normalIndex;

@end
