import Foundation

public class Vector3fSwift : Vector2fSwift {
    
    var z : Float;
    
    /**
    * The Initialize of the vector
    *
    * @param aX
    *            X-coordinate
    * @param aY
    *            Y-coordinate
    * @param aZ
    *            Z-coordinate
    */
    public init(x : Float , y : Float, z : Float) {
        self.z = z;
        super.init(x: x, y: y);
    }
}
