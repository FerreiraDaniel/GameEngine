import Foundation

/**
 * Contains methods used by the wavefront loaders
 */
open class GenericLoader {
    
    internal static let SPLIT_TOKEN = " ";
    internal static let EMPTY_STRING = "";
    
    /**
     *  Max size of a line in the object file
     */
    internal static let MAX_LINE_LENGTH : Int = 512;
    
    /**
     * Parse two strings and return the equivalent vector2f
     *
     * @param xStr
     *            X component
     * @param yStr
     *            Y component
     *
     * @return The parsed vector
     */
    internal static func parseVector2f(_ xStr : String, yStr : String) -> Vector2f {
        let x : Float = Float(xStr) ?? 0.0
        let y : Float = Float(yStr) ?? 0.0
        
        return Vector2f(x: x, y: y);
    }
    
    
    /**
     * Parse three strings and return the equivalent vector3f
     *
     * @param xStr
     *            X component
     * @param yStr
     *            Y component
     * @param zStr
     *            Z component
     *
     * @return The parsed vector
     */
    internal static func parseVector3f(_ xStr : String, yStr : String, zStr : String) -> Vector3f {
        let x : Float = Float(xStr) ?? 0.0
        let y : Float = Float(yStr) ?? 0.0
        let z : Float = Float(zStr) ?? 0.0
        
        return Vector3f(x: x, y: y, z: z);
    }
    
    /**
     *
     * @param line
     *            The line of the file with all the information
     * @param currentLine
     *            Array of strings with differents parts of line
     *
     * @return A a string defined by in one line
     */
    internal static func parseStringComponent(_ line : String, _ currentLine: [String]) -> String {
        let startIndex : Int = currentLine[0].characters.count
        let index = line.characters.index(line.startIndex, offsetBy: startIndex)
        let fileName = line.substring(from: index)
        return fileName.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines);
    }
}
