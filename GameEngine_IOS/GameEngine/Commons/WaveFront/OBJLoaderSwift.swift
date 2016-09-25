import Foundation

/**
* Parses files formated in wavefront format
*/
public class OBJLoaderSwift : NSObject {

    /**
    * Vertices positions in the model
    */
    private static let VERTEX_PREFIX : String = "v";
    
    /**
    * Normal vertices
    */
    private static let NORMAL_PREFIX : String = "vn";
    
    /**
    * Vertices texture coordinates in our model
    */
    private static let TEXTURE_PREFIX : String = "vt";
    
    /**
    * Face each face represents one triangle
    */
    private static let FACE_PREFIX : String = "f";
    
    /**
    * Extension of the obj files
    */
    private static let OBJ_EXTENSION : String = "obj";
    
    /**
    *  Max size of a line in the object file
    */
    private static let MAX_LINE_LENGTH : Int = 512;
    
    private static let INITIAL_ARRAY_CAPACITY : Int = 1000;
    
    /**
    * Number of components depending upon the type of element
    */
    private static let COORDINATES_BY_VERTEX : Int = 3;
    private static let COORDINATES_BY_TEXTURE : Int = 2;
    private static let COORDINATES_BY_NORMAL : Int = 3;
    
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
    private static func parseVector2f(xStr : String, yStr : String) -> Vector2f {
        let x : Float = Float(xStr) ?? 0.0
        let y : Float = Float(yStr) ?? 0.0
        
        return Vector2f(x, y);
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
    private static func parseVector3f(xStr : String, yStr : String, zStr : String) -> Vector3f {
        let x : Float = Float(xStr) ?? 0.0
        let y : Float = Float(yStr) ?? 0.0
        let z : Float = Float(zStr) ?? 0.0
        
        return Vector3f(x, y, z);
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
    private static func createShape(vertices : Array<Vector3f>, normals : Array<Vector3f>, textures : Array<Vector2f>) -> IShape! {
        return nil;
    }
    
    /*
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
    
    
    return [[WfObject alloc] init:
    verticesArray :
    (numberVertices * COORDINATES_BY_VERTEX) :
    texturesArray :
    (numberVertices * COORDINATES_BY_TEXTURE) :
    normalsArray :
    (numberVertices * COORDINATES_BY_NORMAL) :
    indicesArray :
    numberFaces];
    }*/
}