


/// Set of util methods to help load the application
public class Utils {
    
    /// Allocate space for one array native pointer and copy the array to it
    ///
    /// - Parameter array: Array that is to convert into a pointer
    /// - Returns: The pointer that is the end result of the conversion
    public static func toPointer<Type>(array : Array<Type>) -> UnsafeMutablePointer<Type> {
        //Allocate  memory
        let pointer = UnsafeMutablePointer<Type>.alloc(array.count);
        
        //Copy elements one by one
        for i in 0 ..< array.count {
            pointer[i] = array[i];
        }
        
        return pointer;
    }
}
