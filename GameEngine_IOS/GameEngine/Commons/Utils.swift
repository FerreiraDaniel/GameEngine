


/// Set of util methods to help load the application
open class Utils {
    
    /// Allocate space for one array native pointer and copy the array to it
    ///
    /// - Parameter array: Array that is to convert into a pointer
    /// - Returns: The pointer that is the end result of the conversion
    open static func toPointer<Type>(_ array : Array<Type>) -> UnsafeMutablePointer<Type> {
        //Allocate  memory
        let pointer = UnsafeMutablePointer<Type>.allocate(capacity: array.count);
        
        //Copy elements one by one
        for i in 0 ..< array.count {
            pointer[i] = array[i];
        }
        
        return pointer;
    }
}
