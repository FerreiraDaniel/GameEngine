import Foundation
import GLKit

public class LoadUtils : NSObject {
    
    /**
    * Reads a string from a certain resource
    *
    * @param fileName
    *            Name of the resource where the text exists
    * @return The text that exists in the resource
    */
    public static func readTextFromRawResource(fileName: String)  -> UnsafePointer<Int8> {
        var source: UnsafePointer<Int8>
        do {
            source = try NSString(contentsOfFile: fileName, encoding: NSUTF8StringEncoding).UTF8String
        } catch {
            print("Failed to load text file \(fileName)")
            return nil
        }
        return source;
    }
    
    
    /**
    * Load texture from resource
    *
    * @param fileName
    *            Name of the where the texture exists
    */
    public static func loadTexture(path : String) -> TextureData! {
        
        let spriteImage : CGImageRef! = (UIImage(named: path)?.CGImage);
        
        if(spriteImage == nil) {
            print("Error loading file: \(path)")
            return nil;
        } else {
            let width = CGImageGetWidth(spriteImage);
            let height = CGImageGetHeight(spriteImage);
            let spriteData = calloc(width * height * 4, sizeof(GLubyte));
            
            
            let bitsPerComponent = CGImageGetBitsPerComponent(spriteImage); //Should be 8
            let bytesPerRow = CGImageGetBytesPerRow(spriteImage); //width * 4
            let space = CGImageGetColorSpace(spriteImage);
            let bitmapInfo = CGImageAlphaInfo.PremultipliedLast.rawValue;
            let spriteContext : CGContextRef! = CGBitmapContextCreate(spriteData, width, height, bitsPerComponent, bytesPerRow, space, bitmapInfo);
            if(spriteContext == nil) {
                print("Impossible to get the sprint context");
                return nil;
            } else {
                CGContextDrawImage(spriteContext, CGRectMake(0, 0, CGFloat(width), CGFloat(height)), spriteImage);
            }
            return TextureData(buffer: spriteData, width: width, height: height);
        }
    }
}

