import Foundation

/**
 * Parses files formated in wavefront format
 */
public class OBJLoader : GenericLoader {
    
    /**
     * Extension of the obj files
     */
    private static let OBJ_EXTENSION : String = "obj";
    
    
    private static let INITIAL_ARRAY_CAPACITY : Int = 1000;
    
    /* Character for split material and group */
    private static let MAT_GROUP_SPLIT : String = "@";
    
    /**
     * Number of components depending upon the type of element
     */
    private static let COORDINATES_BY_VERTEX : Int = 3;
    private static let COORDINATES_BY_TEXTURE : Int = 2;
    private static let COORDINATES_BY_NORMAL : Int = 3;
    
    /**
     *
     * @param face
     *            reference to a Polygonal face
     *
     * @return A key to use in the dictionary of indices
     */
    private static func getKey(face : PolygonalFace) -> String {
        let materialName :  String = (face.materialName == EMPTY_STRING) ? EMPTY_STRING : face.materialName;
        let groupName : String = (face.groupName == EMPTY_STRING) ? EMPTY_STRING : face.groupName;
        return materialName + MAT_GROUP_SPLIT + groupName;
    }
    
    /**
     * Uses the hash map to create a list of objects
     *
     * @param verticesArray
     *            Array of vertices
     * @param normalsArray
     *            Array of normals
     * @param texturesArray
     *            Array of textures positions
     * @param indicesArrayMap
     *            Dictionary with indices that make the faces
     * @param materials
     *            HashMap of materials that make part of the object (if any)
     *
     * @return List of objects created
     */
    private static func buildShapesLst(verticesArray : [Float], _ normalsArray : [Float], _ texturesArray : [Float],
                                       _ indicesArrayMap : Dictionary<String, [Int]>, _ materials : Dictionary<String, IExternalMaterial>?) -> [IShape] {
        var wfObjectList : [IShape] = [IShape]();
        
        for (key, indicesList) in indicesArrayMap {
            let materialName = key.componentsSeparatedByString(MAT_GROUP_SPLIT)[0];
            let groupName = key.componentsSeparatedByString(MAT_GROUP_SPLIT)[1];
            var material : IExternalMaterial? = nil;
            
            if((materials != nil) && (materials![materialName] != nil)){
                material = materials![materialName];
            }
            let shape : IShape = WfObject(verticesArray, texturesArray, normalsArray, indicesList, groupName, material);
            wfObjectList.append(shape)
        }
        return wfObjectList;
    }
    
    /**
     * Uses the list of elements of the waveFront file to create one shape
     *
     * @param vertices	List of vertices from the waveFront shape
     * @param normals	List of normals from the waveFront shape
     * @param textures	List of texture coordinates from the waveFront shape
     * @param faces 	List of face from the waveFront shape
     * @param materials HashMap of materials that make part of the object (if any)
     
     * @return The waveFront element as an IShape
     */
    private static func createShapes(vertices : Array<Vector3f>,
                                     _ normals : Array<Vector3f>,
                                       _ textures : Array<Vector2f>,
                                         _ faces : Array<PolygonalFace>,
                                           _ materials : Dictionary<String, IExternalMaterial>?) -> [IShape] {
        
        // This are the format of data accepted by the loader
        // We setup the arrays now that we know the size of them
        let numberVertices = vertices.count;
        
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
        var indicesArrayMap : Dictionary<String, [Int]> = Dictionary<String, [Int]>();
        
        for face in faces {
            
            let vertexIndex = face.vertexIndex;
            let normalIndex = face.normalIndex;
            let textureIndex = face.textureIndex;
            
            // Point to loaded structure
            
            let  vertice : Vector3f = vertices[vertexIndex];
            let  currentNorm : Vector3f = normals[normalIndex];
            let  currentTexture : Vector2f! = textures[textureIndex];
            
            // Build index lists
            let indicesKey = getKey(face)
            
            if(indicesArrayMap[indicesKey] == nil)
            {
                var indicesArray : [Int] = [Int]();
                indicesArray.append(vertexIndex);
                indicesArrayMap[indicesKey] = indicesArray;
            } else
            {
                var indicesArray : [Int] = indicesArrayMap[indicesKey]!;
                indicesArray.append(vertexIndex);
                indicesArrayMap[indicesKey] = indicesArray
            }
            
            
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
                texturesArray [vertexIndex * COORDINATES_BY_TEXTURE + 1] = 1.0 - currentTexture.y;
            }
        }
        
        return buildShapesLst(aVertices, normalsArray, texturesArray, indicesArrayMap, materials);
    }
    
    /**
     * Process one vertex
     */
    private static func processVertex(vertexData : Array<String>, _ groupName : String, _ materialName : String) -> PolygonalFace {
        let vertexIndex : Int = (Int(vertexData[TComponentPosition.vertexPos.rawValue]) ?? 0) - 1;
        let textureIndex : Int = (Int(vertexData[TComponentPosition.texturPos.rawValue]) ?? 0) - 1;
        let normalIndex : Int = (Int(vertexData[TComponentPosition.normalPos.rawValue]) ?? 0) - 1;
        
        
        return PolygonalFace(vertexIndex: vertexIndex,
                             textureIndex: textureIndex,
                             normalIndex: normalIndex,
                             groupName: groupName,
                             materialName: materialName
        );
    }
    
    /**
     * Parses one waveFront file
     *
     * @param objFileName
     *            Name of the file without extension
     *
     * @return The shape with information about the waveFront file read
     */
    public static func loadObjModel(objFileName : String) -> [IShape] {
        //Declares the arrays where the elements of the obj file are going to be store
        var materials : Dictionary<String, IExternalMaterial>?  = nil;
        var vertices : Array<Vector3f> = Array<Vector3f>();
        var textures : Array<Vector2f> = Array<Vector2f>();
        var normals : Array<Vector3f> = Array<Vector3f>();
        var faces : Array<PolygonalFace> = Array<PolygonalFace>();
        var currentGroupName : String = EMPTY_STRING;
        var currentMaterialName : String = EMPTY_STRING;
        
        
        //Open the obj file from the disk
        let objPath : String = NSBundle.mainBundle().pathForResource(objFileName, ofType: OBJ_EXTENSION)!
        let file : UnsafeMutablePointer<FILE> = fopen(objPath, "r")
        
        if(file != nil) {
            
            let buffer : UnsafeMutablePointer<Int8> = UnsafeMutablePointer<Int8>.alloc(MAX_LINE_LENGTH);
            //Read the obj file line by line
            let bytesToRead : Int32 = Int32(sizeof(CChar) * MAX_LINE_LENGTH);
            while(fgets(buffer, bytesToRead, file) != nil) {
                let linen = String(UTF8String: UnsafePointer<CChar>(buffer))
                let line = linen?.stringByReplacingOccurrencesOfString("\n", withString: EMPTY_STRING);
                let currentLine : Array<String> = line!.componentsSeparatedByString(GenericLoader.SPLIT_TOKEN);
                let prefix : String = currentLine[0];
                switch(prefix) {
                // The comments in the OBJ do nothing
                case OBJPrefix.COMMENT:
                    break;
                // Definition of the list of materials of the model
                case OBJPrefix.MATERIALS:
                    let materialsFileName = parseStringComponent(line!, currentLine)
                    materials = MtlLoader.loadObjModel(materialsFileName);
                    break;
                    // Parses the information that is to update the current material
                // used
                case OBJPrefix.USE_MATERIAL:
                    currentMaterialName = parseStringComponent(line!, currentLine);
                    break;
                /// Define the name of the object or the group
                case OBJPrefix.OBJECT_NAME:
                    currentGroupName = parseStringComponent(line!, currentLine);
                    break;
                case OBJPrefix.GROUP:
                    currentGroupName = parseStringComponent(line!, currentLine);
                    break;
                /// Defines the smooth shading (Not done yet)
                case OBJPrefix.SMOOTH_SHADING:
                    break;
                // Parses the vertices
                case OBJPrefix.VERTEX:
                    // Parses the vertices
                    let vertex : Vector3f = self.parseVector3f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue], zStr: currentLine[TCoordinatePosition.posZ.rawValue]);
                    vertices.append(vertex);
                    break;
                // Parses the texture coordinates
                case OBJPrefix.TEXTURE:
                    // Parses the texture coordinates
                    let texture : Vector2f = self.parseVector2f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue]);
                    textures.append(texture);
                    break;
                // Parses the normals
                case OBJPrefix.NORMAL:
                    // Parses the normals
                    let normal : Vector3f = self.parseVector3f(currentLine[TCoordinatePosition.posX.rawValue], yStr: currentLine[TCoordinatePosition.posY.rawValue], zStr: currentLine[TCoordinatePosition.posZ.rawValue]);
                    normals.append(normal);
                    break;
                // Parses the faces
                case OBJPrefix.FACE:
                    // Parses the faces
                    for i : Int in 1 ..< 4 {
                        let fVertexStr : Array<String> = currentLine[i].componentsSeparatedByString("/");
                        let pFace : PolygonalFace = self.processVertex(fVertexStr, currentGroupName, currentMaterialName);
                        faces.append(pFace);
                    }
                    break;
                default:
                    print("Impossible to parse:\(line)")
                    break
                }
            }
            
            free(buffer);
            fclose(file);
        }
        
        return self.createShapes(vertices, normals, textures, faces, materials);
        
    }
    
}
