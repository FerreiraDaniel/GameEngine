import Foundation

/**
 * Represents a 3D Vector with their x,y,z coordinates
 */
public class Vector3f : Vector2f {
    
    var z : Float;
    
    /**
     * The Initialize of the vector
     *
     * @param x
     *            X-coordinate
     * @param y
     *            Y-coordinate
     * @param z
     *            Z-coordinate
     */
    public init(x : Float , y : Float, z : Float) {
        self.z = z;
        super.init(x: x, y: y);
    }
    
    /**
     * Transforms a 3D vector into a string
     */
    var description : String {
        return String(format: "(%f,%f,%f)", x, y, z)
    }
    
    /**
     * Normalizes the components of the vector
     */
    public func normalise() {
        let magnitude : Float = Math.sqrt((self.x * self.x) + (self.y * self.y) + (self.z * self.z));
        self.x = (x / magnitude);
        self.y = (y / magnitude);
        self.z = (z / magnitude);
    }
}
