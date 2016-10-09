import Foundation

public class Utils : NSObject {

    /***
    * Allocate space for one array native pointer
    * and copy the array to it
    */
    public static func arrayToPointer(lArray : Array<Float>) -> UnsafeMutablePointer<Float> {
        //Allocate  memory
        let pointer = UnsafeMutablePointer<Float>(calloc(lArray.count, sizeof(Float)));
        
        //Copy elements one by one
        for(var i = 0;i < lArray.count;i++) {
            pointer[i] = lArray[i];
        }
        
        return pointer;
    }
    
    
    /***
    * Allocate space for one array native pointer
    * and copy the array to it
    */
    public static func arrayToPointer(lArray : Array<ushort>) -> UnsafeMutablePointer<ushort> {
        //Allocate  memory
        let pointer = UnsafeMutablePointer<ushort>(calloc(lArray.count, sizeof(ushort)));
        
        //Copy elements one by one
        for(var i = 0;i < lArray.count;i++) {
            pointer[i] = lArray[i];
        }
        
        return pointer;
    }
}