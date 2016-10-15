import Foundation
import GLKit

/**
*	Represents one shader program in that can be running in the Graphic Boards
*/
public class  ShaderProgram {
    
    /**
    * The id of the program
    */
    var programId : GLuint
    
    /**
    * The id of the vertex shader
    */
    var vertexShaderId: GLuint
    
    /**
    * The id of the fragment shader
    */
    var fragmentShaderId : GLuint
    
    public init() {
        self.programId = 0;
        self.vertexShaderId = 0;
        self.fragmentShaderId = 0;
    }
    
}
