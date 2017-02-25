import Foundation
import GLKit

open class LoadUtils {
    
    /**
    * Reads a string from a certain resource
    *
    * @param fileName
    *            Name of the resource where the text exists
    * @return The text that exists in the resource
    */
    open static func readText(_ fileName: String)  -> NSString? {
        do {
            return try NSString(contentsOfFile: fileName, encoding: String.Encoding.utf8.rawValue)
        } catch {
            print("Failed to load text file \(fileName)")
            return nil
        }
    }
    
    
    /**
    * Load texture from resource
    *
    * @param fileName
    *            Name of the where the texture exists
    */
    open static func loadTexture(_ path: String) -> TextureData! {
        
        let spriteImage : CGImage! = (UIImage(named: path)?.cgImage);
        
        if(spriteImage == nil) {
            print("Error loading file: \(path)")
            return nil;
        } else {
            let width = spriteImage.width;
            let height = spriteImage.height;
            let spriteData = UnsafeMutablePointer<UInt8>.allocate(capacity: width * height * 4);
            
            for index in 0..<(width * height * 4) {
                spriteData[index] = 0
            }
            
            let bitsPerComponent = spriteImage.bitsPerComponent; //Should be 8
            let bytesPerRow = spriteImage.bytesPerRow; //width * 4
            let space = spriteImage.colorSpace;
            let bitmapInfo = CGImageAlphaInfo.premultipliedLast.rawValue;
            let spriteContext : CGContext! = CGContext(data: spriteData, width: width, height: height, bitsPerComponent: bitsPerComponent, bytesPerRow: bytesPerRow, space: space!, bitmapInfo: bitmapInfo);
            if(spriteContext == nil) {
                print("Impossible to get the sprint context");
                return nil;
            } else {
                spriteContext.draw(spriteImage, in: CGRect(x: 0, y: 0, width: CGFloat(width), height: CGFloat(height)));
            }
            return TextureData(buffer: spriteData, width: width, height: height);
        }
    }
}

