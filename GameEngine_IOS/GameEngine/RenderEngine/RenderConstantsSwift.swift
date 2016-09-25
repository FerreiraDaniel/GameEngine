import Foundation

/**
* Define constants used in the render
*/
public class RenderConstantsSwift {
    public static let vertex : GLint = 3;
    public static let texture : GLint = 2;
    public static let normal : GLint = 3;
    
    //Specifies whether fixed-point data values should be normalized (GL_TRUE) or converted directly as fixed-point values (GL_FALSE) when they are accessed.
    public static let vertexNormalized : GLboolean = GLboolean(GL_FALSE)
    
    //Offset between following vertices
    let STRIDE  = 0
    
    //Offset of start of data
    public static let START_OFFSET = 0
    
    //number of components per generic vertex attribute
    let NUMBER_COMPONENTS_PER_VERTEX_ATTR  = 2
    
    
    /**
    * Number of components that compose the normal vector
    */
    let NUMBER_COMPONENTS_PER_NORMAL_ATTR = 3
}