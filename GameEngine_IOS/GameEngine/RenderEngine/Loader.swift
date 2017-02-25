import Foundation
import GLKit
import OpenAL
import AudioToolbox

/*
 * Load the elements to make the scene
 */
open class Loader  {
    
/*    func BUFFER_OFFSET(_ i: Int) -> UnsafeRawPointer {
        let p: UnsafeRawPointer? = nil
        return p.advancedBy(i)
    }
  */  

    func BUFFER_OFFSET(_ i: Int) -> UnsafeRawPointer? {
        return UnsafeRawPointer(bitPattern: i)
    }


    
    /**
     * List of the vertex array objects loaded
     */
    fileprivate var vaos : Array<GLuint>!;
    
    /**
     * List of the vertex buffer objects loaded
     */
    fileprivate var vbos : Array<GLuint>!;
    
    /**
     * List of the textures that make part of the game engine
     */
    fileprivate var textures : Array<GLuint>!;
    
    /**
     * Decoder for audio files
     */
    fileprivate var audioDecoder : AudioDecoder;
    
    /**
     * Extension of the png files
     */
    fileprivate let PNG_EXTENSION : String  = "png";
    
    fileprivate let NUMBER_CUBE_FACES : Int = 6;
    

    /// Initiator of the loader
    public init() {
        self.vaos = Array<GLuint>();
        self.vbos = Array<GLuint>();
        self.textures = Array<GLuint>();
        self.audioDecoder = AudioDecoder()
    }
    
    /**
     *
     * @return If possible one audio source with everything set
     */
    fileprivate func genAudioSource() -> AudioSource! {
        var sourceId : ALuint = 0;
        
        alGenSources(1, &sourceId)
        let error = alGetError()
        if(error == AL_NO_ERROR)
        {
            return AudioSource(sourceId: sourceId)
        } else
        {
            return nil
        }
    }
    
    /**
     *
     * @param numberOfSources
     *            Number of audio sources to generate
     *
     * @return A list of the audio sources generated
     */
    open func genAudioSources(_ numberOfSources : Int) -> [AudioSource] {
        var sourceLst : [AudioSource] = [AudioSource]();
        
        for _ in 0 ..< numberOfSources {
            let audioSource : AudioSource! = genAudioSource();
            if audioSource != nil {
                sourceLst.append(audioSource)
            }
        }
        
        return sourceLst
    }
    
    /**
     * Create a vertex array object
     *
     * @return the identifier of the VAO created
     */
    fileprivate func createVAO() -> Int {
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
    fileprivate func storeDataInAttributeList(_ attributeNumber : Int, coordinateSize : GLint, data :UnsafeRawPointer? , dLength : Int) {
        
        if(data == nil)
        {
            print("something went wrong data nil not expected")
        } else
        {
            var vboID : GLuint = 0;
            
            glEnableVertexAttribArray(GLuint(attributeNumber));
            glGenBuffers(1, &vboID);
            self.vbos.append(vboID);
            
            // Bind the VBO just created
            glBindBuffer(GLenum(GL_ARRAY_BUFFER), vboID);
            let countBytes : Int = dLength * MemoryLayout<CFloat>.size;
            glBufferData(GLenum(GL_ARRAY_BUFFER), countBytes, data!, GLenum(GL_STATIC_DRAW));
            glVertexAttribPointer(GLuint(attributeNumber), coordinateSize, GLenum(GL_FLOAT), RenderConstants.vertexNormalized, RenderConstants.STRIDE, BUFFER_OFFSET(0));
            // UnBind the current VBO
            glBindBuffer(GLenum(GL_ARRAY_BUFFER), 0);
        }
    }
    
    /**
     * Load a shape in a VAO (Vertex array object)
     *
     * @param shape
     *            The shape to load
     *
     * @return A row model with information loaded
     */
    open func loadToVAO(_ shape : IShape) -> RawModel{
        
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
        
        self.storeDataInAttributeList(TEntityAttribute.position.rawValue, coordinateSize: RenderConstants.vertex, data: vertexData, dLength: Int(vertexLength))
        self.storeDataInAttributeList(TEntityAttribute.textureCoords.rawValue, coordinateSize: RenderConstants.texture, data: textureData, dLength: Int(textureLength))
        self.storeDataInAttributeList(TEntityAttribute.normal.rawValue, coordinateSize: RenderConstants.normal, data: normalData, dLength: Int(normalsLength))
        
        self.unbindVAO();
        return RawModel(vaoId: vaoID , indicesData: indicesData , indicesCount: Int(indicesLength));
        
    }
    
    /**
     *
     * @param externalMaterial
     *            A reference to an external material with all the information
     *            needed to create a material
     *
     * @return The material loaded
     */
    open func loadMaterial(_ shape : IShape) -> Material? {
        let externalMaterial : IExternalMaterial? = shape.getMaterial()
        
        
        if (externalMaterial == nil) {
            return nil;
        } else {
            var textureId : Int;
            var textureWeight : Float;
            var diffuseColor : ColorRGBA;
            
            if ((externalMaterial!.getDiffuseTextureFileName() == nil)
                || (externalMaterial!.getDiffuseTextureFileName() == "")) {
                textureId = 0;
                textureWeight = 0.0;
                diffuseColor = (externalMaterial!.getDiffuseColor() == nil) ? ColorRGBA(r: 0.0, g: 0.0, b: 0.0, a: 1.0) : ColorRGBA(color: externalMaterial!.getDiffuseColor()!);
            } else {
                textureId = self.loadTexture(externalMaterial!.getDiffuseTextureFileName()!);
                textureWeight = 1.0;
                diffuseColor = ColorRGBA(r: 0.0, g: 0.0, b: 0.0, a: 1.0);
            }
            let material : Material = Material(textureId);
            material.textureWeight = textureWeight
            material.diffuseColor = diffuseColor;
            
            return material;
        }
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
    fileprivate func  loadPositionsToVAO(_ positions : UnsafeRawPointer?, positionsLength : Int, dimensions : Int) -> RawModel {
        
        let vaoID = self.createVAO();
        self.storeDataInAttributeList(TEntityAttribute.position.rawValue, coordinateSize: GLint(dimensions), data: positions, dLength: positionsLength);
        self.unbindVAO();
        
        return RawModel(vaoId: vaoID , indicesData: nil , indicesCount: (positionsLength/dimensions));
        
    }
    
    /**
     * Load a list of 2D positions to VAO
     *
     * @param positions
     *            Positions to load
     * @para positionsLength
     *            Number of positions to load in the vertex array object
     *
     * @return The rawModel pointing to the created VAO
     */
    open func load2DPositionsToVAO(_ positions : UnsafeMutablePointer<Float>?, positionsLength : Int) -> RawModel{
        let dimensions = 2;
        return self.loadPositionsToVAO(positions, positionsLength: positionsLength, dimensions: dimensions);
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
    open func load3DPositionsToVAO(_ positions : UnsafeMutablePointer<Float>?, positionsLength : Int) -> RawModel{
        let dimensions = 3;
        return self.loadPositionsToVAO(positions, positionsLength: positionsLength, dimensions: dimensions);
    }
    
    /**
     * When loads one texture defines that by default should zoom in/out it
     *
     * @param itarget
     *            the target of the filter
     */
    fileprivate func defineTextureFunctionFilters (_ itarget : Int32) {
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
    open func loadTexture(_ fileName : String) -> Int {
        var imagePath : String! = Bundle.main.path(forResource: fileName, ofType: PNG_EXTENSION)
        
        if(imagePath == nil) {
            let fName = fileName.components(separatedBy: ".")
            imagePath = Bundle.main.path(forResource: fName[0], ofType: PNG_EXTENSION)
        }
        
        if(imagePath == nil) {
            print("Impossible to get the patch to \(fileName)");
            return -1;
        }
        let textureData : TextureData! = LoadUtils.loadTexture(imagePath);
        
        if (textureData == nil) {
            print("Impossible to get the image \(imagePath)");
            return -1;
        } else {
            var textureId : GLuint = 0;
            glGenTextures(1, &textureId);
            glBindTexture(GLenum(GL_TEXTURE_2D), textureId);
            
            let width : GLsizei = GLsizei(textureData.width)
            let height : GLsizei = GLsizei(textureData.height)
            let buffer : UnsafeMutableRawPointer? = textureData.buffer
            
            
            glTexImage2D(GLenum(GL_TEXTURE_2D), 0, GL_RGBA,  width, height, 0,
                         GLenum(GL_RGBA), GLenum(GL_UNSIGNED_BYTE),  buffer!);
            
            self.defineTextureFunctionFilters(GL_TEXTURE_2D);
            self.textures.append(textureId);
            return Int(textureId);
            
        }
    }
    
    
    /**
     *  Loads the data of a texture without bind
     *
     * @param fileName
     *            Name of the file to load without the .png extension in the end
     *
     * @return The texture read from the file without any openGL bind
     */
    open func getTextureData(_ fileName : String) -> TextureData! {
        let imagePath : String! = Bundle.main.path(forResource: fileName, ofType: PNG_EXTENSION)
        
        if(imagePath == nil) {
            print("Impossible to get the patch to \(fileName)");
            return nil;
        } else {
            let textureData : TextureData! = LoadUtils.loadTexture(imagePath);
            return textureData;
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
    open func loadTCubeMap(_ fileNames : Array<String>!) -> Int {
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
            
            for i in 0 ..< NUMBER_CUBE_FACES {
                let fileName = fileNames[i];
                
                let imagePath : String! = Bundle.main.path(forResource: fileName, ofType: PNG_EXTENSION)
                let textureData = LoadUtils.loadTexture(imagePath);
                
                if (textureData == nil) {
                    return -1;
                } else {
                    let target = cubicTextureTargets[i];
                    let width : GLsizei = GLsizei(textureData!.width)
                    let height : GLsizei = GLsizei(textureData!.height)
                    let buffer : UnsafeMutableRawPointer? = textureData?.buffer
                    
                    if(buffer == nil)
                    {
                        print("loadTCubeMap unexpected nil buffer")
                    } else
                    {
                        glTexImage2D(GLenum(target), 0, GL_RGBA,  width, height, 0,
                                     GLenum(GL_RGBA), GLenum(GL_UNSIGNED_BYTE),  buffer!);
                    }
                }
            }
            self.defineTextureFunctionFilters(GL_TEXTURE_CUBE_MAP);
            self.textures.append(textureId);
            return Int(textureId);
            
        }
    }
    
    
    
    
    /**
     * Load one audio file in a buffer
     *
     * @param fileName
     *            The file name of the file to load
     *
     * @return The identifier of the buffer return by openAL
     */
    open func loadSound(_ fileName : String) -> AudioBuffer? {
        var audioBuffer : AudioBuffer! = nil;
        
        var bufferId : ALuint = 0
        
        alGenBuffers(1, &bufferId)
        
        
        
        /**
         * Was not possible to one buffer return AL false
         */
        if (alGetError() != AL_NO_ERROR) {
            return nil;
        }
        
        let filePath : String! = Bundle.main.path(forResource: fileName, ofType: "aac")
        if(filePath == nil) {
            print("Impossible to get the patch to audio \(fileName) ");
            return nil;
        }
        
        
        let fileURL : URL = URL(fileURLWithPath: filePath)
        

        let audioFile = self.audioDecoder.getData(fileURL)
        
        if let aFile = audioFile
        {
            //data = MyGetOpenALAudioData(fileURL, &size, &format, &freq)
            
            let format : ALenum = (aFile.channels > 1) ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16
            
            
            alBufferData(bufferId, format, aFile.data!, aFile.dataSize, ALsizei(aFile.rate))
            self.audioDecoder.cleanUp(audioFile);
            
            audioBuffer = AudioBuffer(ALint(bufferId));
            
            return audioBuffer;
        } else
        {
            print("Impossible to read the file \(fileURL)")
            return nil;
        }
    }
    
    /**
     * UnBind the current vertex array object
     */
    fileprivate func unbindVAO() {
        glBindVertexArrayOES(0);
    }
    
    /**
     *
     * @param indices
     *            the indices to vertex buffer object
     * @para dLength
     *            Number of indices that the data has
     */
    fileprivate func bindIndicesBuffer(_ indices : UnsafeRawPointer?, dLength : Int) {
        
        if(indices == nil)
        {
            print("Unexpected bindIndicesBuffer: indices should not be nil")
        } else {
            var vboID : GLuint = 0;
            glGenBuffers(1, &vboID);
            vbos.append(vboID);
            
            // Bind the VBO just created
            glBindBuffer(GLenum(GL_ARRAY_BUFFER), vboID)
            let countBytes = dLength * MemoryLayout<CUnsignedShort>.size;
            glBufferData(GLenum(GL_ARRAY_BUFFER), countBytes, indices!, GLenum(GL_STATIC_DRAW))
        }
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
