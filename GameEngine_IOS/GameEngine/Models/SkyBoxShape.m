#import "SkyBoxShape.h"

/**
 * Represents the sky box in the 3D world
 */
@implementation SkyBoxShape

int const __SKY_BOX_NUMBER_OF_ELEMENTS = 108;

/**
 * Vertices of the sky box
 */
float __skyBoxVertexData[__SKY_BOX_NUMBER_OF_ELEMENTS] =
{ -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
    -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
    
    -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE,
    -SIZE, SIZE,
    
    SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE,
    -SIZE,
    
    -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE,
    SIZE,
    
    -SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
    -SIZE,
    
    -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE,
    -SIZE, SIZE };

/**
 * Initiator of the sky box shape
 */
- (id)init {
    self = [super init];
    return self;
}



/**
 * @return the vertices of the shape
 */
- (float*) getVertices {
    return __skyBoxVertexData;
}

/**
 * @return number of vertices that make the shape
 */
- (int) countVertices {
    return __SKY_BOX_NUMBER_OF_ELEMENTS;
}

/**
 * @return the Coordinates of the textures of the shape
 */
- (float*) getTextureCoords{
    return nil;
}

/*
 Number of the texture coordinates
 */
- (int) countTextureCoords {
    return 0;
}

/**
 *
 * @return the normal vectors that make the shape
 */
- (float*) getNormals{
    return nil;
}

/*
 * Number of normal that the shape has
 */
- (int) countNormals{
    return 0;
}

/**
 * @return The indices of the vertices that make the shape
 */
- (unsigned short*) getIndices{
    return nil;
}

/*
 Number of indices that the shapa has
 */
-(int) countIndices{
    return 0;
}


@end
