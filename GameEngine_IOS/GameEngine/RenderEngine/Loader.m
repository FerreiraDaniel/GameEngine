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
- (RawModel*) load3DPositionsToVAO1 : (float*) positions : (int) positionsLength {
    int dimensions = 3;
    return [self loadPositionsToVAO : positions : positionsLength : dimensions];
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
