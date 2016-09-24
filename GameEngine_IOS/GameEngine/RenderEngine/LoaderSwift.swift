import Foundation

public class LoaderSwift : Loader {
    
    /**
    * List of the vertex array objects loaded
    */
    private var vaos : Array<GLuint>!;
    
    /**
    * List of the vertex buffer objects loaded
    */
    private var vbos : Array<GLuint>!;
    
    /**
    * List of the textures that make part of the game engine
    */
    private var textures : Array<GLuint>!;
    
    /**
    * Extension of the png files
    */
    private let PNG_EXTENSION : String  = "png";
    
    private let NUMBER_CUBE_FACES : Int = 6;
    
    /**
    Initiator of the loader
    */
    public override init() {
        super.init();
        
        self.vaos = Array<GLuint>();
        self.vbos = Array<GLuint>();
        self.textures = Array<GLuint>();
        /*
        self = [super init];
        if (self) {
            vaos = [NSMutableArray array];
            vbos = [NSMutableArray array];
            textures = [NSMutableArray array];
        }
        return self;
*/
    }
    
    /**
    * Create a vertex array object
    *
    * @return the identifier of the VAO created
    */
    private func createVAO() -> Int {
        var vaoID : GLuint = 0;
        glGenVertexArraysOES(1, &vaoID);
        vaos.append(vaoID);
        glBindVertexArrayOES(vaoID);
        return Int(vaoID);
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
       /*private func  storeDataInAttributeList(attributeNumber : Int, coordinateSize : Int, data : UnsafePointer<Void>, dLength : Int) {
     
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

    }*/
    
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
    /*private func storeDataInAttributeList(attributeNumber : Int, coordinateSize : Int, data :UnsafePointer<Void> , dLength : Int) {
        
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

    }*/
    
    /**
    * Load a shape in a VAO (Vertex array object)
    *
    * @param shape
    *            The shape to load
    *
    * @return A row model with information loaded
    */
    public func loadToVAO(shape : IShape) -> RawModel{
        
        let vaoID = self.createVAO();
        
        let indicesLength = shape.countIndices();
        let vertexLength = shape.countVertices();
        let textureLength = shape.countTextureCoords();
        let normalsLength = shape.countNormals();
        //Buffers
        let indicesData = shape.getIndices();
        let vertexData = shape.getVertices();
        let textureData = shape.getTextureCoords();
        let normalData = shape.getNormals();
        
        self.bindIndicesBuffer(indicesData, dLength: Int(indicesLength));
        
        super.storeDataInAttributeList( Int32(TEntityAttribute.position.rawValue) , VERTEX_SIZE, vertexData, vertexLength);
        super.storeDataInAttributeList( Int32(TEntityAttribute.textureCoords.rawValue), COORD_SIZE, textureData, textureLength);
        super.storeDataInAttributeList( Int32(TEntityAttribute.normal.rawValue), NORMAL_SIZE, normalData, normalsLength);
        
        
        self.unbindVAO();
        return RawModel(Int32(vaoID) , indicesData , indicesLength);
        
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
    /*private func  loadPositionsToVAO(positions : UnsafePointer<Void>, positionsLength : Int, dimensions : Int) -> RawModel {
        
    int vaoID = [self createVAO];
    [self storeDataInAttributeList: LOCATION_ATTR_ID : dimensions : positions: positionsLength];
    [self unbindVAO];
    return [[RawModel alloc] init : vaoID : nil : positionsLength/dimensions];

    }*/
    
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
    public func load3DPositionsToVAO(positions : UnsafeMutablePointer<Float>, positionsLength : Int) -> RawModel{
        return super.load3DPositionsToVAO1(positions, Int32(positionsLength));
        /*
        int dimensions = 3;
        return [self loadPositionsToVAO : positions : positionsLength : dimensions];
        */
    }
    
    /**
    * When loads one texture defines that by default should zoom in/out it
    *
    * @param itarget
    *            the target of the filter
    */
    private func defineTextureFunctionFilters (itarget : Int32) {
        let target : GLenum = GLenum(itarget);
        
    glTexParameteri(target, GLenum(GL_TEXTURE_MIN_FILTER), GL_LINEAR);
    glTexParameteri(target, GLenum(GL_TEXTURE_MAG_FILTER), GL_LINEAR);
    glTexParameteri(target, GLenum(GL_TEXTURE_WRAP_S), GL_CLAMP_TO_EDGE);
    glTexParameteri(target, GLenum(GL_TEXTURE_WRAP_T), GL_CLAMP_TO_EDGE);

    }
    
    /**
    *  Load one texture from a file and set it in openGL
    *
    * @param fileName
    *            Name of the file to load without the .png extension in the end
    *
    * @return Identifier of the texture loaded
    */
    public func loadTexture(fileName : String) -> Int {
        let imagePath : String! = NSBundle.mainBundle().pathForResource(fileName, ofType: PNG_EXTENSION)
        
        if(imagePath == nil) {
            print("Impossible to get the patch to \(fileName)");
            return -1;
        }
        let textureData : TextureDataSwift! = LoadUtils.loadTexture(imagePath);
        
        if (textureData == nil) {
            print("Impossible to get the image \(imagePath)");
            return -1;
        } else {
            var textureId : GLuint = 0;
            glGenTextures(1, &textureId);
            glBindTexture(GLenum(GL_TEXTURE_2D), textureId);
            
            let width : GLsizei = GLsizei(textureData.width)
            let height : GLsizei = GLsizei(textureData.height)
            let buffer : UnsafeMutablePointer<Void> = textureData.buffer
            
            
            glTexImage2D(GLenum(GL_TEXTURE_2D), 0, GL_RGBA,  width, height, 0,
                GLenum(GL_RGBA), GLenum(GL_UNSIGNED_BYTE),  buffer);
            
            self.defineTextureFunctionFilters(GL_TEXTURE_2D);
            self.textures.append(textureId);
            return Int(textureId);
            
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
    public func loadTCubeMap(fileNames : Array<String>!) -> Int {        
        if (fileNames == nil) {
            return -1;
        } else {
            let cubicTextureTargets : Array<Int32> = [GL_TEXTURE_CUBE_MAP_POSITIVE_X,
                GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
                GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
                GL_TEXTURE_CUBE_MAP_NEGATIVE_Z ];
            
            var textureId : GLuint = 0;
            glGenTextures(1, &textureId);
            glActiveTexture(GLenum(GL_TEXTURE0));
            glBindTexture(GLenum(GL_TEXTURE_CUBE_MAP), textureId);
            
            for (var i = 0; i < NUMBER_CUBE_FACES; i++) {
                let fileName = fileNames[i];
                
                let imagePath : String! = NSBundle.mainBundle().pathForResource(fileName, ofType: PNG_EXTENSION)
                let textureData = LoadUtils.loadTexture(imagePath);
                
                if (textureData == nil) {
                    return -1;
                } else {
                    let target = cubicTextureTargets[i];
                    let width : GLsizei = GLsizei(textureData.width)
                    let height : GLsizei = GLsizei(textureData.height)
                    let buffer : UnsafeMutablePointer<Void> = textureData.buffer

                    glTexImage2D(GLenum(target), 0, GL_RGBA,  width, height, 0,
                        GLenum(GL_RGBA), GLenum(GL_UNSIGNED_BYTE),  buffer);
                }
            }
            self.defineTextureFunctionFilters(GL_TEXTURE_CUBE_MAP);
            self.textures.append(textureId);
            return Int(textureId);

        }
    }
    
    /**
    * UnBind the current vertex array object
    */
    private func unbindVAO() {
        glBindVertexArrayOES(0);
    }
    
    /**
    *
    * @param indices
    *            the indices to vertex buffer object
    * @para dLength
    *            Number of indices that the data has
    */
    private func bindIndicesBuffer(indices : UnsafePointer<Void>, dLength : Int) {
        
        
        var vboID : GLuint = 0;
        glGenBuffers(1, &vboID);
        vbos.append(vboID);
        
        // Bind the VBO just created
        glBindBuffer(GLenum(GL_ARRAY_BUFFER), vboID)
        let countBytes = dLength * sizeof(CUnsignedShort);
        glBufferData(GLenum(GL_ARRAY_BUFFER), countBytes, indices, GLenum(GL_STATIC_DRAW))
        
    }
    
    /**
    Desallocate memory
    */
    deinit {
    
    vaos = nil;
    vbos = nil;
    textures = nil;
    }
    
}