#import "OBJLoader.h"

/**
 * Parses files formated in wavefront format
 */
@implementation OBJLoader
{
@private
    
    /**
     *	Positions where the coordinates are when each line of wavefront
     *is parsed
     */
    enum
    {
        posX = 1,
        posY,
        posZ
    } CoordinatePositions;
    
    enum {
        vertexPos = 0,
        texturPos,
        normalPos
    };
    
}

/**
 * Vertices positions in the model
 */
const NSString *VERTEX_PREFIX = @"v";

/**
 * Normal vertices
 */
const NSString *NORMAL_PREFIX = @"vn";

/**
 * Vertices texture coordinates in our model
 */
const NSString *TEXTURE_PREFIX = @"vt";

/**
 * Face each face represents one triangle
 */
const NSString *FACE_PREFIX = @"f";

/**
 * Extension of the obj files
 */
NSString* OBJ_EXTENSION = @"obj";

/**
 *  Max size of a line in the object file
 */
const int MAX_LINE_LENGTH = 512;

const int INITIAL_ARRAY_CAPACITY = 1000;

/**
 * Number of components depending upon the type of element
 */
const int COORDINATES_BY_VERTEX = 3;
const int COORDINATES_BY_TEXTURE = 2;
const int COORDINATES_BY_NORMAL = 3;

/**
 * Parse two strings and return the equivalent vector2f
 *
 * @param xStr
 *            X component
 * @param yStr
 *            Y component
 *
 * @return The parsed vector
 */
+ (Vector2f*) parseVector2f : (NSString*) xStr : (NSString*) yStr {
    float x = [xStr floatValue];
    float y = [yStr floatValue];
    
    return [[Vector2f alloc] init: x : y];
}


/**
 * Parse three strings and return the equivalent vector3f
 *
 * @param xStr
 *            X component
 * @param yStr
 *            Y component
 * @param zStr
 *            Z component
 *
 * @return The parsed vector
 */
+ (Vector3f*) parseVector3f :(NSString*) xStr : (NSString*) yStr :  (NSString*) zStr {
    float x = [xStr floatValue];
    float y = [yStr floatValue];
    float z = [zStr floatValue];
    
    return [[Vector3f alloc] init: x : y : z];
}

/**
 * Uses the list of elements of the waveFront file to create one shape
 *
 * @param vertices	List of vertices from the waveFront shape
 * @param normals	List of normals from the waveFront shape
 * @param textures	List of texture coordinates from the waveFront shape
 * @param facesLst 	List of face from the waveFront shape
 
 * @return The waveFront element as an IShape
 */
+ (id<IShape>) createShape : (NSMutableArray<Vector3f*>*) vertices :
(NSMutableArray<Vector3f*>*) normals :
(NSMutableArray<Vector2f*>*) textures :
(NSMutableArray<PolygonalFace*>*) facesLst {
    // This are the format of data accepted by the loader
    // We setup the arrays now that we know the size of them
    int numberVertices = [vertices count];
    int numberFaces = [facesLst count];
    float verticesArray[numberVertices * COORDINATES_BY_VERTEX];
    float normalsArray[numberVertices * COORDINATES_BY_NORMAL];
    float texturesArray[numberVertices * COORDINATES_BY_TEXTURE];
    unsigned short indicesArray[numberFaces];
    
    for (int j = 0; j < numberFaces; j++) {
        PolygonalFace* face = [facesLst objectAtIndex:j];
        
        int vertexIndex = [face vertexIndex];
        int normalIndex = [face normalIndex];
        int textureIndex = [face textureIndex];
        
        // Point to loaded structure

        Vector3f* vertice = [vertices objectAtIndex: vertexIndex];
        Vector3f* currentNorm = [normals objectAtIndex: normalIndex];
        Vector2f* currentTexture = [textures objectAtIndex: textureIndex];
        
        // Build index lists
        indicesArray[j] = vertexIndex;
        
        // Uses the (faces and vertices list to build the final vertices
        // array
        verticesArray[vertexIndex * COORDINATES_BY_VERTEX] = vertice.x;
        verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 1] = vertice.y;
        verticesArray[vertexIndex * COORDINATES_BY_VERTEX + 2] = vertice.z;
        
        
        // Build the normals list
        normalsArray[vertexIndex * COORDINATES_BY_NORMAL] = currentNorm.x;
        normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 1] = currentNorm.y;
        normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 2] = currentNorm.z;
        
        // Build the texture coordinates list
        if (currentTexture != nil) {
            texturesArray[vertexIndex * COORDINATES_BY_TEXTURE] = currentTexture.x;
            texturesArray[vertexIndex * COORDINATES_BY_TEXTURE + 1] = currentTexture.y;
        }
    }
    
    
    return [[WfObject alloc] initWithAVertices:verticesArray
                                     aCountVertices: (numberVertices * COORDINATES_BY_VERTEX)
                                aTextureCoordinates: texturesArray
                           aCountTextureCoordinates:(numberVertices * COORDINATES_BY_TEXTURE)
                                           aNormals: normalsArray
                                      aCountNormals: (numberVertices * COORDINATES_BY_NORMAL)
                                           aIndices: indicesArray
                                      aCountIndices: numberFaces];
    

}

/**
 * Parses one waveFront file
 *
 * @param objFileName
 *            Name of the file without extension
 *
 * @return The shape with information about the waveFront file read
 */
+ (id<IShape>) loadObjModel : (NSString*) objFileName {
    
    //Declares the arrays where the elements of the obj file are going to be store
    NSMutableArray<Vector3f*> *vertices = [[NSMutableArray alloc]initWithCapacity: INITIAL_ARRAY_CAPACITY];
    NSMutableArray<Vector2f*> *textures =  [[NSMutableArray alloc]initWithCapacity: INITIAL_ARRAY_CAPACITY];
    NSMutableArray<Vector3f*> *normals =  [[NSMutableArray alloc]initWithCapacity: INITIAL_ARRAY_CAPACITY];
    NSMutableArray<PolygonalFace*> *facesLst =  [[NSMutableArray alloc]initWithCapacity: INITIAL_ARRAY_CAPACITY];
    
    //Open the obj file from the disk
    NSString* objPath = [[NSBundle mainBundle] pathForResource:objFileName ofType: OBJ_EXTENSION];
    FILE *file = fopen([objPath UTF8String], "r");
    char buffer[MAX_LINE_LENGTH];
    //Read the obj file line by line
    while ((fgets(buffer, sizeof(char) * MAX_LINE_LENGTH, file) != NULL)){
        NSString* line = [NSString stringWithUTF8String:buffer];
        NSArray *currentLine = [line componentsSeparatedByString:@" "];
        //Switch of the type element to read
        NSString *prefix = [currentLine objectAtIndex: 0];
        typedef void (^CaseElementTypeBlock)();
        
        NSDictionary *d = @{
                            VERTEX_PREFIX:
                                ^{
                                    // Parses the vertices
                                    Vector3f *vertex = [self parseVector3f: [currentLine objectAtIndex: posX] :
                                                        [currentLine objectAtIndex: posY] : [currentLine objectAtIndex: posZ]];
                                    [vertices addObject: vertex];
                                },
                            TEXTURE_PREFIX:
                                ^{
                                    // Parses the texture coordinates
                                    Vector2f *texture = [self parseVector2f: [currentLine objectAtIndex: posX] :
                                                         [currentLine objectAtIndex: posY]];
                                    [textures addObject: texture];
                                },
                            NORMAL_PREFIX:
                                ^{
                                    // Parses the normals
                                    Vector3f *normal = [self parseVector3f: [currentLine objectAtIndex: posX] :
                                                        [currentLine objectAtIndex: posY] : [currentLine objectAtIndex: posZ]];
                                    [normals addObject: normal];
                                },
                            FACE_PREFIX:
                                ^{
                                    // Parses the faces
                                    for (int i = 1; i < 4; i++) {
                                        NSArray *fVertexStr = [[currentLine objectAtIndex: i] componentsSeparatedByString:@"/"];
                                        PolygonalFace *pFace = [self processVertex : fVertexStr];
                                        
                                        [facesLst addObject:pFace];
                                    }
                                }
                            };
        if([d objectForKey: prefix] == nil) {
            //Default block
        } else {
            ((CaseElementTypeBlock)d[[currentLine objectAtIndex: 0]])(); // invoke the correct block of code
        }
    }
    fclose(file);
    
    return [self createShape : vertices : normals : textures : facesLst];
}

/**
 * Process one vertex
 */
+ (PolygonalFace*) processVertex : (NSArray*) vertexData {
    unsigned short vertexIndex = [[vertexData objectAtIndex: vertexPos] integerValue] - 1;
    unsigned short textureIndex = [[vertexData objectAtIndex: texturPos] integerValue] - 1;
    unsigned short normalIndex = [[vertexData objectAtIndex: normalPos] integerValue] - 1;
    
    
    return [[PolygonalFace alloc] init : vertexIndex : textureIndex : normalIndex];
}

@end
