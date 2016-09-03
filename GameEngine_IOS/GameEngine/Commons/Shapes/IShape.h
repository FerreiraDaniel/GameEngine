#import <Foundation/Foundation.h>

/**
 * Interface that should be implemented for the different shapes available
 */
@protocol IShape <NSObject>

/**
 * @return the vertices of the shape
 */
- (float(*)) getVertices;

/**
 * @return number of vertices that make the shape
 */
- (int) countVertices;

/**
 * @return the Coordinates of the textures of the shape
 */
- (float*) getTextureCoords;

/*
 Number of the texture coordinates
 */
- (int) countTextureCoords;

/**
 *
 * @return the normal vectors that make the shape
 */
- (float*) getNormals;

/*
 * Number of normal that the shape has
 */
- (int) countNormals;

/**
 * @return The indices of the vertices that make the shape
 */
- (unsigned short*) getIndices;

/*
 Number of indices that the shapa has
 */
-(int) countIndices;

@end

