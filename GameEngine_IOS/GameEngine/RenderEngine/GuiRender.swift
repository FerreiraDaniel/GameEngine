import Foundation
import OpenGLES

/**
 * Class responsible to render the GUIs in the screen
 */
open class GuiRender {
    
    /**
     * Reference to the shader manager
     */
    fileprivate var gShader : GuiShaderManager!;
    
    
    /**
     * Initializer of the entity render
     *
     * @param gManager
     *            Shader manager
     */
    public init(_ gManager : GuiShaderManager) {
        self.gShader = gManager;
    }
    
    /**
     * Get the transformation matrix of one entity
     *
     * @param gui
     *            GUI for which is to create the transformation matrix
     *
     * @return The transformation matrix that put the entity in its right
     *         position
     */
    fileprivate func getTransformationMatrix(_ gui : GuiTexture) -> GLTransformation {
        let matrix : GLTransformation  = GLTransformation();
        matrix.loadIdentity();
        
        matrix.translate(x: gui.position.x, y: gui.position.y, z: 0.0);
        
        matrix.scale(x: gui.scale.x, y: gui.scale.y, z: 1.0);
        return matrix;
        
    }
    
    /**
     * Render the GUIs in the scene
     *
     *
     * @param quad 	Reference to to quad where the GUIs are going to be Render
     * @param guis	List of GUIs to render
     */
    open func render(_ guis : Array<GuiTexture>) {
        gShader.start();
        
        self.lrender(guis);
        gShader.stop();
    }
    
    
    /**
     * Render one hashMap of entities where each key is a group of similar
     * entities to be render
     *
     * @param guis
     *            List of GUIs to render
     */
    fileprivate func lrender(_ guis: Array<GuiTexture> ) {
        if (guis.isEmpty) {
            return;
        } else {
            glEnable(GLuint(GL_BLEND));
            glBlendFunc(GLuint(GL_SRC_ALPHA), GLuint(GL_ONE_MINUS_SRC_ALPHA));
            glDisable(GLuint(GL_DEPTH_TEST));
            
            for gui in guis {
                prepareModel(gui.rawModel);
                prepareInstance(gui);
                render(gui.rawModel);
                
                unbindTexturedModel();
            }
            glEnable(GLuint(GL_DEPTH_TEST));
            glDisable(GLuint(GL_BLEND));
            
        }
    }
    
    
    
    /**
     * Bind the attributes of openGL
     *
     * @param quad
     Contains the positions to render the guis
     */
    fileprivate func prepareModel(_ quad : RawModel) {
        glBindVertexArrayOES(GLuint(quad.vaoId));
        glEnableVertexAttribArray(GLuint(TGuiAttribute.position.rawValue));
    }
    
    /**
     * Load the transformation matrix of the GUI
     *
     * @param gui
     *            Entity that is to get prepared to be loaded
     */
    fileprivate func prepareInstance(_ gui: GuiTexture) {
        glActiveTexture(GLenum(GL_TEXTURE0));
        glBindTexture(GLenum(GL_TEXTURE_2D), GLuint(gui.textureId));
        // Load the transformation matrix
        gShader.loadTransformationMatrix(getTransformationMatrix(gui));
    }
    
    /**
     * Call the render of the triangles to the entity itself
     *
     * @param entity
     *            Entity to get render
     */
    fileprivate func render(_ quad :RawModel) {
        glDrawArrays(GLenum(GL_TRIANGLE_STRIP), 0, GLsizei(quad.indicesCount));
    }
    
    /**
     * UnBind the previous binded elements
     */
    fileprivate func unbindTexturedModel() {
        glDisableVertexAttribArray(GLuint(TGuiAttribute.position.rawValue));
        //Unbind vbo
        glBindBuffer(GLuint(GL_ARRAY_BUFFER), 0);
    }
    
    /**
     * Clean up because we need to clean up when we finish the program
     */
    deinit {
        gShader = nil;
    }
    
}
