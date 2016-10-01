import Foundation

/**
* Represents a 2D Vector with their x,y,z coordinates
*/
public class Vector2f : NSObject {
    
    var x : Float;
    var y : Float;
    
    /**
    * The initialize of the vector
    *
    * @param aX
    *            coordinate
    * @param aY
    *            coordinate
    */
    public init(x : Float, y : Float) {
        self.x = x;
        self.y = y;
    }
}