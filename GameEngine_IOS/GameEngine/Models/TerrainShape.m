#import "TerrainShape.h"

@implementation TerrainShape
{
@private float vertices[TERRAIN_VERTICES_LENGTH];
@private float normals[TERRAIN_NORMALS_LENGTH];
@private float textureCoords[TERRAIN_TEXTURES_LENGTH];
@private unsigned short indices[TERRAIN_INDICES_LENGTH];
}

/**
 * Generates a completely flat terrain
 *
 */
- (void) generateTerrain {
    int vertext_count = TERRAIN_SHAPE_VERTEX_COUNT;
    int vertexPointer = 0;
    for (int i = 0; i < vertext_count; i++) {
        for (int j = 0; j < vertext_count; j++) {
            vertices[vertexPointer * 3] = (float) j / ((float) vertext_count - 1) * SIZE;
            vertices[vertexPointer * 3 + 1] = 0;
            vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertext_count - 1) * SIZE;
            normals[vertexPointer * 3] = 0;
            normals[vertexPointer * 3 + 1] = 1;
            normals[vertexPointer * 3 + 2] = 0;
            textureCoords[vertexPointer * 2] = (float) j / ((float) vertext_count - 1);
            textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertext_count - 1);
            vertexPointer++;
        }
    }
    int pointer = 0;
    for (int gz = 0; gz < vertext_count - 1; gz++) {
        for (int gx = 0; gx < vertext_count - 1; gx++) {
            int topLeft = (gz * vertext_count) + gx;
            int topRight = topLeft + 1;
            int bottomLeft = ((gz + 1) * vertext_count) + gx;
            int bottomRight = bottomLeft + 1;
            indices[pointer++] = topLeft;
            indices[pointer++] = bottomLeft;
            indices[pointer++] = topRight;
            indices[pointer++] = topRight;
            indices[pointer++] = bottomLeft;
            indices[pointer++] = bottomRight;
        }
    }
}


/**
 * Initiator of the terrain shape
 */
- (id)init {
    self = [super init];
    if (self) {
        [self generateTerrain];
    }
    return self;
}




/**
 * @return the vertices of the shape
 */
- (float*) getVertices {
    return vertices;
}

/**
 * @return number of vertices that make the shape
 */
- (int) countVertices {
    return TERRAIN_VERTICES_LENGTH;
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
    return normals;
}

/*
 * Number of normal that the shape has
 */
- (int) countNormals{
    return TERRAIN_NORMALS_LENGTH;
}

/**
 * @return The indices of the vertices that make the shape
 */
- (unsigned short*) getIndices{
    return indices;
}

/*
 Number of indices that the shapa has
 */
-(int) countIndices{
    return TERRAIN_INDICES_LENGTH;
}

@end
