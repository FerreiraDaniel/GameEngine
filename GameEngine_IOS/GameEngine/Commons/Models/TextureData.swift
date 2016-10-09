import Foundation

/**
* Holds the data of the pixels of a texture as well as the width and height
*/
public class TextureData : NSObject {
    
    /**
    * Width of the texture
    */
    var width : Int;
    
    /**
    * Height of the texture
    */
    var height : Int;
    
    /**
    * The buffer with data about the pixels of the image
    */
    var buffer : UnsafeMutablePointer<Void>;
    
    /**
    * The initialize of the texture data
    
    * @param buffer
    *            The buffer with data about the pixels of the image
    * @param width
    *            Width of the texture
    * @param height
    *            Height of the texture
    
    */
    public init(buffer : UnsafeMutablePointer<Void>, width : Int, height : Int) {
        self.buffer = buffer
        self.width = width
        self.height = height
    }
}