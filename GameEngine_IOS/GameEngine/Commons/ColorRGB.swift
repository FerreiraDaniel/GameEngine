import Foundation

/**
 * Represents one color with (Red, Green and Blue) components
 */
public class ColorRGB {
    
    /**
     * Red component
     */
    var r: Float;
    
    /**
     * Green component
     */
    var g: Float;
    
    /**
     * Blue component
     */
    var b: Float;
    
    /**
     * @param r
     *            Red component
     * @param g
     *            Green component
     * @param b
     *            Blue component
     */
    public init(r : Float, g : Float, b: Float) {
        self.r = r;
        self.g = g;
        self.b = b;
    }
    
}
