#import "WfObject.h"

@implementation WfObject
{
    @private
    float* vertices;
    int countVertices;
    float* textureCoordinates;
    int countTextureCoordinates;
    float* normals;
    int countNormals;
    unsigned short* indices;
    int countIndices;
}

/**
 * Inicializes the waveFront file
 */
- (id)init :
(float*) aVertices :
(int) aCountVertices :
(float*) aTextureCoordinates :
(int) aCountTextureCoordinates :
(float*) aNormals :
(int) aCountNormals :
(unsigned short*) aIndices :
(int) aCountIndices
{
    self = [super init];
    if (self) {
        //Allocate and fill the vertices memory
        int countVerticesBytes = sizeof(float) * aCountVertices;
        self->vertices = calloc(aCountVertices, sizeof(float));
        self->countVertices = aCountVertices;
        memcpy(self->vertices, aVertices, countVerticesBytes);
        
                
        //Allocate and fill the texture memory
        int countTextureBytes = sizeof(float) * aCountTextureCoordinates;
        self->textureCoordinates = calloc(aCountTextureCoordinates, sizeof(float));
        self->countTextureCoordinates = aCountTextureCoordinates;
        memcpy(self->textureCoordinates, aTextureCoordinates, countTextureBytes);
        
        
        //Allocate and fill the normals memory
        int countNormalsBytes = sizeof(float) * aCountNormals;
        self->normals = calloc(aCountNormals, sizeof(float));
        self->countNormals = aCountNormals;
        memcpy(self->normals, aNormals, countNormalsBytes);

        
        //Allocate and fill the indices memory
        int countIndicesBytes = sizeof(int) * aCountIndices;
        self->indices = calloc(aCountIndices, sizeof(int));
        self->countIndices = aCountIndices;
        memcpy(self->indices, aIndices, countIndicesBytes);

    }
    return self;
}

/**
 *Desaloc the space used by the pointers of the object
 */
- (void)dealloc
{
    free(self->vertices);
    free(self->textureCoordinates);
    free(self->normals);
    free(self->indices);
}


/**
 * @return the vertices of the shape
 */
- (float*) getVertices {
    return self->vertices;
}

/**
 * @return number of vertices that make the shape
 */
- (int) countVertices {
    return self->countVertices;
}

/**
 * @return the Coordinates of the textures of the shape
 */
- (float*) getTextureCoords{
    return self->textureCoordinates;
}

/*
 Number of the texture coordinates
 */
- (int) countTextureCoords {
    return self->countTextureCoordinates;
}

/**
 *
 * @return the normal vectors that make the shape
 */
- (float*) getNormals{
    return self->normals;
}

/*
 * Number of normal that the shape has
 */
- (int) countNormals{
    return self->countNormals;
}

/**
 * @return The indices of the vertices that make the shape
 */
- (unsigned short*) getIndices{
    return self->indices;
}

/*
 Number of indices that the shape has
 */
-(int) countIndices{
    return self->countIndices;
}

@end
