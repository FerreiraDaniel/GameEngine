import GLKit

/**
* Define constants used in the render
*/
open class RenderConstants {
    
    /**
    * Number of components per generic vertex attribute
    */
    open static let vertex : GLint = 3
    
    /**
    * Number of components that compose the coordinates of the texture
    */
    open static let texture : GLint = 2
    
    /**
    * Number of components that compose the normal vector
    */
    open static let normal : GLint = 3
    
    /**Specifies whether fixed-point data values should be normalized (GL_TRUE) or converted directly as fixed-point values (GL_FALSE) when they are accessed.*/
    open static let vertexNormalized : GLboolean = GLboolean(GL_FALSE)
    
    /**Offset between following vertices*/
    open static let STRIDE : Int32  = 0
    
    /**Offset of start of data*/
    open static let START_OFFSET = 0
}
