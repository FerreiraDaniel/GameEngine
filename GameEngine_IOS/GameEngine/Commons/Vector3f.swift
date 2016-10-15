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
}
