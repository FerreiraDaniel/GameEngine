import Foundation

/**
 * Holds the data of the pixels of a texture as well as the width and height
 */
open class TextureData : NSObject {
    
    // struct of components of the image (RGBA)
    struct RGBA {
        var r: UInt8
        var g: UInt8
        var b: UInt8
        var a: UInt8
    }
    
    
    /*Max allowed value of the a component of a pixel*/
    fileprivate static let MAX_COMPONENT : Int = 255;
    
    /*Maximum value that the component RGB has*/
    open static let MAX_PIXEL_COLOR : Float = Float(MAX_COMPONENT * MAX_COMPONENT * MAX_COMPONENT);
    
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
    var buffer : UnsafeMutableRawPointer?;
    
    /**
     * The initialize of the texture data
     
     * @param buffer
     *            The buffer with data about the pixels of the image
     * @param width
     *            Width of the texture
     * @param height
     *            Height of the texture
     
     */
    public init(buffer : UnsafeMutableRawPointer?, width : Int, height : Int) {
        self.buffer = buffer
        self.width = width
        self.height = height
    }
    
    /**
     * Get the component of the image
     *
     * @param x               x-coordinate
     * @param y               y-coordinate
     *
     * @return  The value representing the component read
     */
    fileprivate func getLRgb(_ x : Int, y : Int) -> RGBA! {
        // First check if the coordinates are in the range of the image
        if ((self.buffer == nil) || (x < 0) || (x >= self.height) || (y < 0) || (y >= self.height)) {
            // No in the range
            return nil;
        } else {
            let index = ((y * self.width) + x);
            let rgbaptr = buffer!.assumingMemoryBound(to: RGBA.self)
            let rgba = rgbaptr[index];
            return rgba;
        }
    }
    
    /**
     * @param x the x-coordinate
     * @param y the y-coordinate
     *
     * @return One integer representing the RGB colors of the pixel in the
     * position passed
     */
    open func getRGB(_ x : Int, y : Int) -> Int? {
        let rgb = getLRgb(x, y: y);
        let r = UInt((rgb?.r)!)
        let g = UInt((rgb?.g)!)
        let b = UInt((rgb?.b)!)
        
        return Int((b << 16) + (g << 8) + (r));
    }
}
