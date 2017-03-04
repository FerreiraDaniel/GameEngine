import Foundation
import GLKit

/**
 * Contains a set of methods to help in the migration from Java to Swift project when using math
 * Class
 */
open class Math {
    
    /// Transforms an angle in the degrees to radians
    ///
    /// - Parameter degrees: The angle to conver in degrees
    /// - Returns: The angle transformed in radian
    open static func toRadians(degrees : Float) -> Float
    {
        let radiansAngle = GLKMathDegreesToRadians(degrees);
        return radiansAngle;
    }
    
    /**
     * Return the cosine of the angle passed
     */
    open static func cos(_ radianAngle : Float) -> Float
    {
        return cosf(radianAngle);
    }
    
    /**
     * Return the sine of the angle passed
     */
    open static func sin(_ radianAngle : Float) -> Float
    {
        return sinf(radianAngle);
    }
    
    /*
     *  Returns the correctly rounded positive square root of a double value.
     */
    open static func sqrt(_ a : Float) -> Float
    {
        return sqrtf(a)
    }
    
    /**
     * Returns the largest (closest to positive infinity)
     * {@code double} value that is less than or equal to the
     * argument and is equal to a mathematical integer. Special cases:
     * <ul><li>If the argument value is already equal to a
     * mathematical integer, then the result is the same as the
     * argument.  <li>If the argument is NaN or an infinity or
     * positive zero or negative zero, then the result is the same as
     * the argument.</ul>
     *
     * @param   a   a value.
     * @return  the largest (closest to positive infinity)
     *          floating-point value that less than or equal to the argument
     *          and is equal to a mathematical integer.
     */
    open static func floor(_ a : Float) -> Float {
        return floorf(a);
    }
    
    /**
     * In geometry, the barycentric coordinate system is a coordinate system in
     * which the location of a point of a triangle is specified as the center of
     * mass, or barycenter, of usually unequal masses placed at its vertices
     *
     * @param v1
     *            First of the vertices of the triangle
     * @param v2
     *            Second of the vertices of the triangle
     * @param v3
     *            Third of the vertices of the triangle
     * @param pos
     *            Position the is to determine
     *
     * @return The location determined
     */
    open static func barryCentric(_ v1 : Vector3f, v2 : Vector3f, v3 : Vector3f, pos : Vector2f) -> Float {
        let det0 : Float = ((v2.z - v3.z) * (v1.x - v3.x))
        let det1 : Float = ((v3.x - v2.x) * (v1.z - v3.z))
        let det : Float =  det0 + det1;
        // Compute dot products
        let l10 : Float = (v2.z - v3.z) * (pos.x - v3.x);
        let l11 : Float = (v3.x - v2.x) * (pos.y - v3.z);
        let l1 : Float = (l10 + l11) / det;
        //
        let l20 : Float = (v3.z - v1.z) * (pos.x - v3.x)
        let l21 : Float = (v1.x - v3.x) * (pos.y - v3.z)
        let l2 : Float = ((l20 + l21) / det);
        //
        let l3 : Float = (1.0 - l1 - l2);
        
        let yPos : Float = Float(l1 * v1.y + l2 * v2.y + l3 * v3.y);
        
        return yPos;
    }
}
