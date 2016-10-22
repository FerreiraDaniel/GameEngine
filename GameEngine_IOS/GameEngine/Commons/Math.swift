import Foundation
import GLKit

/**
 * Contains a set of methods to help in the migration from Java to Swift project when using math
 * Class
 */
public class Math {
    
    /**
     *  Transforms an angle in the degrees to radians
     */
    public static func toRadians(degreesAngle : Float) -> Float
    {
        let radiansAngle = GLKMathDegreesToRadians(degreesAngle);
        return radiansAngle;
    }
    
    /**
     * Return the cosine of the angle passed
     */
    public static func cos(radianAngle : Float) -> Float
    {
        return cosf(radianAngle);
    }
    
    /**
    * Return the sine of the angle passed
    */
    public static func sin(radianAngle : Float) -> Float
    {
        return sinf(radianAngle);
    }
}