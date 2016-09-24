#import "Loader.h"

@implementation Loader
{
@private
    /**
     * List of the vertex array objects loaded
     */
    NSMutableArray *vaos;
    
    /**
     * List of the vertex buffer objects loaded
     */
    NSMutableArray *vbos;
    
    /**
     * List of the textures that make part of the game engine
     */
    NSMutableArray *textures;
}

/**
 * Extension of the png files
 */
NSString* PNG_EXTENSION = @"png";

const int NUMBER_CUBE_FACES = 6;

/**
 Initiator of the loader
 */
- (id)init {
    self = [super init];
    if (self) {
        vaos = [NSMutableArray array];
        vbos = [NSMutableArray array];
        textures = [NSMutableArray array];
    }
    return self;
}

/**
 * Create a vertex array object
 *
 * @return the identifier of the VAO created
 */
- (int) createVAO {
    GLuint vaoID;
    glGenVertexArraysOES(1, &vaoID);
    [vaos addObject: [NSNumber numberWithInteger:vaoID]];
    glBindVertexArrayOES(vaoID);
    return vaoID;
}

/**
 * Store a certain element to be used in the program shader
 *
 * @param attributeNumber
 *            the id of the attribute to load in the program shader
 * @param coordinateSize
 *            Number of components of the attribute to store
 *
 * @param data
 *            Data to be store
 * @para dLength
 *            Number of elements that the data has
 */
- (void) storeDataInAttributeList: (int) attributeNumber : (int) coordinateSize : (float*) data : (int) dLength {
    GLuint vboID;
    
    glEnableVertexAttribArray(attributeNumber);
    glGenBuffers(1, &vboID);
    [vbos addObject: [NSNumber numberWithInteger:vboID]];
    // Bind the VBO just created
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    int countBytes = dLength * sizeof(float);
    glBufferData(GL_ARRAY_BUFFER, countBytes, data, GL_STATIC_DRAW);
    glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, VERTEX_NORMALIZED, STRIDE,
                          START_OFFSET);
    // UnBind the current VBO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
}

/**
 * Load a shape in a VAO (Vertex array object)
 *
 * @param shape
 *            The shape to load
 *
 * @return A row model with information loaded
 */
- (RawModel*) loadToVAO: (id<IShape>) shape {
    int vaoID = [self createVAO];
    
    int indicesLength = [shape countIndices];
    int vertexLength = [shape countVertices];
    int textureLength = [shape countTextureCoords];
    int normalsLength = [shape countNormals];
    
    unsigned short *indicesData = [shape getIndices];
    float *vertexData = [shape getVertices];
    float *textureData = [shape getTextureCoords];
    float *normalData = [shape getNormals];
    
    [self bindIndicesBuffer: indicesData : indicesLength];
    [self storeDataInAttributeList: LOCATION_ATTR_ID :VERTEX_SIZE : vertexData : vertexLength];
    [self storeDataInAttributeList: TEX_COORDINATE_ATTR_ID : COORD_SIZE : textureData : textureLength];
    [self storeDataInAttributeList: NORMAL_VECTOR_ATTR_ID : NORMAL_SIZE : normalData : normalsLength];
    
    
    [self unbindVAO];
    return [[RawModel alloc] init : vaoID : indicesData : indicesLength];
}

/**
 * Load a list of positions to VAO
 *
 * @param positions
 *            Positions to load
 * @para positionsLength
 *            Number of positions to load in the vertex array object
 * @param dimensions
 *            Number of components that the positions has
 *
 * @return The rawModel pointing to the created VAO
 */
- (RawModel*) loadPositionsToVAO: (float*) positions : (int) positionsLength : (int) dimensions {
    int vaoID = [self createVAO];
    [self storeDataInAttributeList: LOCATION_ATTR_ID : dimensions : positions: positionsLength];
    [self unbindVAO];
    return [[RawModel alloc] init : vaoID : nil : positionsLength/dimensions];
}

/**
 * Load a list of 3D positions to VAO
 *
 * @param positions
 *            Positions to load
 * @para positionsLength
 *            Number of positions to load in the vertex array object
 *
 * @return The rawModel pointing to the created VAO
 */
- (RawModel*) load3DPositionsToVAO : (float*) positions : (int) positionsLength {
    int dimensions = 3;
    return [self loadPositionsToVAO : positions : positionsLength : dimensions];
}

/**
 * When loads one texture defines that by default should zoom in/out it
 *
 * @param target
 *            the target of the filter
 */
- (void) defineTextureFunctionFilters : (int) target {
    glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
}

/**
 *  Load one texture from a file and set it in openGL
 *
 * @param fileName
 *            Name of the file to load without the .png extension in the end
 *
 * @return Identifier of the texture loaded
 */
- (int) loadTexture : (NSString*) fileName {
    NSString* imagePath = [[NSBundle mainBundle] pathForResource:fileName ofType: PNG_EXTENSION];
    TextureDataSwift *textureData = [LoadUtils loadTexture: imagePath];
    
    if (textureData == nil) {
        return -1;
    } else {
        GLuint textureId;
        glGenTextures(1, &textureId);
        glBindTexture(GL_TEXTURE_2D, textureId);
        
        int width = [textureData width];
        int height = [textureData height];
        Byte *buffer = [textureData buffer];
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,  width, height, 0,
                     GL_RGBA, GL_UNSIGNED_BYTE,  buffer);
        [self defineTextureFunctionFilters: GL_TEXTURE_2D];
        
        [textures addObject: [NSNumber numberWithInteger:textureId]];
        return textureId;
    }
}

/**
 * Loads a cubic texture
 *
 * @param fileNames
 *            Names of the file to load without the .png extension in the
 *            end
 *
 * @return Identifier of the texture cubic texture loaded
 */
- (int) loadTCubeMap : (NSArray*) fileNames {
    if (fileNames == nil) {
        return -1;
    } else {
        int cubicTextureTargets[NUMBER_CUBE_FACES] = { GL_TEXTURE_CUBE_MAP_POSITIVE_X,
            GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
            GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
            GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
        
        GLuint textureId;
        glGenTextures(1, &textureId);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureId);
        
        for (int i = 0; i < NUMBER_CUBE_FACES; i++) {
            NSString *fileName = fileNames[i];
            
            NSString* imagePath = [[NSBundle mainBundle] pathForResource:fileName ofType: PNG_EXTENSION];
            TextureData *textureData = [LoadUtils loadTexture: imagePath];
            
            if (textureData == nil) {
                return -1;
            } else {
                int target = cubicTextureTargets[i];
                int width = [textureData width];
                int height = [textureData height];
                Byte *buffer = [textureData buffer];
                
                glTexImage2D(target, 0, GL_RGBA,  width, height, 0,
                             GL_RGBA, GL_UNSIGNED_BYTE,  buffer);
            }
        }
        [self defineTextureFunctionFilters: GL_TEXTURE_CUBE_MAP];
        [textures addObject: [NSNumber numberWithInteger:textureId]];
        return textureId;
    }
}


/**
 * UnBind the current vertex array object
 */
- (void) unbindVAO {
    glBindVertexArrayOES(0);
}

/**
 *
 * @param indices
 *            the indices to vertex buffer object
 * @para dLength
 *            Number of indices that the data has
 */
- (void) bindIndicesBuffer : (unsigned short*) indices : (int) dLength {
    GLuint vboID;
    glGenBuffers(1, &vboID);
    [vbos addObject: [NSNumber numberWithInteger:vboID]];
    // Bind the VBO just created
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    int countBytes = dLength * sizeof(unsigned short);
    glBufferData(GL_ARRAY_BUFFER, countBytes, indices, GL_STATIC_DRAW);
}

/**
 Desallocate memory
 */
- (void) dealloc {
    
    vaos = nil;
    vbos = nil;
    textures = nil;
}

@end
