import Foundation

/**
* Parses files formated in wavefront format
*/
public class OBJLoader {
    
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
        
        return Vector2f(x: x, y: y);
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
        
        return Vector3f(x: x, y: y, z: z);
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
    private static func createShape(vertices : Array<Vector3f>, normals : Array<Vector3f>, textures : Array<Vector2f>, faces : Array<PolygonalFace>) -> IShape! {
        
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
        
        for j : Int in 0 ..< numberFaces {
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
    private static func processVertex(vertexData : Array<String>) -> PolygonalFace {
        let vertexIndex : Int = (Int(vertexData[TComponentPosition.vertexPos.rawValue]) ?? 0) - 1;
        let textureIndex : Int = (Int(vertexData[TComponentPosition.texturPos.rawValue]) ?? 0) - 1;
        let normalIndex : Int = (Int(vertexData[TComponentPosition.normalPos.rawValue]) ?? 0) - 1;
        
        
        return PolygonalFace(vertexIndex: vertexIndex, textureIndex: textureIndex, normalIndex: normalIndex);
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
        var faces : Array<PolygonalFace> = Array<PolygonalFace>();
        
        //Open the obj file from the disk
        let objPath : String = NSBundle.mainBundle().pathForResource(objFileName, ofType: OBJ_EXTENSION)!
        let file : UnsafeMutablePointer<FILE> = fopen(objPath, "r")
        
        if(file != nil) {
            
            let buffer : UnsafeMutablePointer<Int8> = UnsafeMutablePointer<Int8>(calloc(MAX_LINE_LENGTH, sizeof(CChar)));
            //Read the obj file line by line
            let bytesToRead : Int32 = Int32(sizeof(CChar) * MAX_LINE_LENGTH);
            while(fgets(buffer, bytesToRead, file) != nil) {
                let linen = String(UTF8String: UnsafePointer<CChar>(buffer))
                //.stringByReplacingOccurrencesOfString(" ", withString: "+")
                let line = linen?.stringByReplacingOccurrencesOfString("\n", withString: "");
                let currentLine : Array<String> = line!.componentsSeparatedByString(" ");
                let prefix : String = currentLine[0];
                switch(prefix) {
                case VERTEX_PREFIX:
                    // Parses the vertices
                    let vertex : Vector3f = self.parseVector3f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue], zStr: currentLine[TCoordinatePosition.posZ.rawValue]);
                    vertices.append(vertex);
                    break
                case TEXTURE_PREFIX:
                    // Parses the texture coordinates
                    let texture : Vector2f = self.parseVector2f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue]);
                    textures.append(texture);
                    break
                case NORMAL_PREFIX:
                    // Parses the normals
                    let normal : Vector3f = self.parseVector3f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue], zStr: currentLine[TCoordinatePosition.posZ.rawValue]);
                    normals.append(normal);
                    break
                case FACE_PREFIX:
                    // Parses the faces
                    for i : Int in 1 ..< 4 {
                        let fVertexStr : Array<String> = currentLine[i].componentsSeparatedByString("/");
                        let pFace : PolygonalFace = self.processVertex(fVertexStr);
                        faces.append(pFace);
                    }
                default:
                    break
                }
            }
            
            free(buffer);
            fclose(file);
        }
        
        return self.createShape(vertices, normals: normals, textures: textures, faces: faces);
        
    }
    
}
