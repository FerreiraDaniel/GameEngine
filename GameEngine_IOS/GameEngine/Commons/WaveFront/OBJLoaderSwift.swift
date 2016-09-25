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
    * @param faces 	List of face from the waveFront shape
    
    * @return The waveFront element as an IShape
    */
    private static func createShape(vertices : Array<Vector3f>, normals : Array<Vector3f>, textures : Array<Vector2f>, faces : Array<PolygonalFaceSwift>) -> IShape! {
        
        // This are the format of data accepted by the loader
        // We setup the arrays now that we know the size of them
        let numberVertices = vertices.count;
        let numberFaces = faces.count;
        
        //Vertices
        let verticesLength = (numberVertices * COORDINATES_BY_VERTEX);
        var aVertices : Array<Float> = Array<Float>(count: verticesLength, repeatedValue: 0.0);
        
        //Normals
        let normalsLength = numberVertices * COORDINATES_BY_NORMAL;
        var normalsArray : Array<Float> = Array<Float>(count: normalsLength, repeatedValue: 0.0);
        
        //Textures
        let textureLength = numberVertices * COORDINATES_BY_TEXTURE;
        var texturesArray : Array<Float> = Array<Float>(count: textureLength, repeatedValue: 0.0);
        
        //Indices
        var indicesArray : Array<Int> = Array<Int>(count: numberFaces, repeatedValue: 0);
        
        for (var j : Int = 0; j < numberFaces; j++) {
            let face = faces[j];
            
            let vertexIndex = face.vertexIndex;
            let normalIndex = face.normalIndex;
            let textureIndex = face.textureIndex;
            
            // Point to loaded structure
            
            let  vertice : Vector3f = vertices[vertexIndex];
            let  currentNorm : Vector3f = normals[normalIndex];
            let  currentTexture : Vector2f! = textures[textureIndex];
            
            // Build index lists
            indicesArray[j] =  vertexIndex
            
            
            // Uses the (faces and vertices list to build the final vertices array
            
            aVertices[vertexIndex * COORDINATES_BY_VERTEX + 0] = vertice.x;
            aVertices[vertexIndex * COORDINATES_BY_VERTEX + 1] = vertice.y;
            aVertices[vertexIndex * COORDINATES_BY_VERTEX + 2] = vertice.z;
            
            // Build the normals list
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 0] = currentNorm.x;
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 1] = currentNorm.y;
            normalsArray[vertexIndex * COORDINATES_BY_NORMAL + 2] = currentNorm.z;
            
            
            
            // Build the texture coordinates list
            if (currentTexture != nil) {
                texturesArray [vertexIndex * COORDINATES_BY_TEXTURE + 0] = currentTexture.x;
                texturesArray [vertexIndex * COORDINATES_BY_TEXTURE + 1] = currentTexture.y;
            }
        }
        
        
        return WfObject(aVertices: aVertices, aTextureCoordinates: texturesArray, aNormals: normalsArray, aIndices: indicesArray);
    }
    
    /**
    * Process one vertex
    */
    private static func processVertex(vertexData : Array<String>) -> PolygonalFaceSwift {
        let vertexIndex : Int = (Int(vertexData[TComponentPosition.vertexPos.rawValue]) ?? 0) - 1;
        let textureIndex : Int = (Int(vertexData[TComponentPosition.texturPos.rawValue]) ?? 0) - 1;
        let normalIndex : Int = (Int(vertexData[TComponentPosition.normalPos.rawValue]) ?? 0) - 1;
        
        
        return PolygonalFaceSwift(vertexIndex: vertexIndex, textureIndex: textureIndex, normalIndex: normalIndex);
    }
    
    /**
    * Parses one waveFront file
    *
    * @param objFileName
    *            Name of the file without extension
    *
    * @return The shape with information about the waveFront file read
    */
    public static func loadObjModel(objFileName : String) -> IShape {
        //Declares the arrays where the elements of the obj file are going to be store
        var vertices : Array<Vector3f> = Array<Vector3f>();
        var textures : Array<Vector2f> = Array<Vector2f>();
        var normals : Array<Vector3f> = Array<Vector3f>();
        var faces : Array<PolygonalFaceSwift> = Array<PolygonalFaceSwift>();
        
        //Open the obj file from the disk
        let objPath : String = NSBundle.mainBundle().pathForResource(objFileName, ofType: OBJ_EXTENSION)!
        var file : UnsafeMutablePointer<FILE> = fopen(objPath, "r")
        
        if(file != nil) {
            
            var buffer : UnsafeMutablePointer<Int8> = UnsafeMutablePointer<Int8>(calloc(MAX_LINE_LENGTH, sizeof(CChar)));
            //Read the obj file line by line
            while(fgets(buffer, Int32(sizeof(CChar) * MAX_LINE_LENGTH), file) != nil) {
                let line = String(UTF8String: UnsafePointer<CChar>(buffer))
                let currentLine : Array<String> = line!.componentsSeparatedByString(" ");
                let prefix : String = currentLine[0];
                switch(prefix) {
                case VERTEX_PREFIX:
                    print("Vertex")
                    break
                case TEXTURE_PREFIX:
                    print("texture")
                    break
                case NORMAL_PREFIX:
                    print("normal")
                    break
                case FACE_PREFIX:
                    print("face")
                    break
                default:
                    break
                }
                
            }
            
            free(buffer);
            fclose(file);
        }
        
        /*
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
        }*/
        
        return self.createShape(vertices, normals: normals, textures: textures, faces: faces);
        
    }
    
}