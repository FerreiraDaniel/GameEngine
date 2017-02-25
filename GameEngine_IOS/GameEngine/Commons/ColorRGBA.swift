import Foundation

/**
 * Represents one color with (Red, Green, Blue, Alpha blend) components
 */
open class ColorRGBA : ColorRGB {
    
    /**
     * Alpha blend component
     */
    var a : Float;
    
    /**
     * @param r
     *            Red component
     * @param g
     *            Green component
     * @param b
     *            Blue component
     * @param a
     *            Alpha blend
     */
    public init(r : Float, g : Float, b : Float, a : Float) {
        self.a = a;
        super.init(r: r, g: g, b: b)
    }
    
    /**
     *
     * @param color	Creates one rgba color from a RGB
     */
    public init(color : ColorRGB) {
        self.a = 1.0;
        super.init(r: color.r, g: color.g, b: color.b)
    }
}
