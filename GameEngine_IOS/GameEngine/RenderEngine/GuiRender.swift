import Foundation
import OpenGLES

/**
 * Class responsible to render the GUIs in the screen
 */
public class GuiRender {
    
    /**
     * Reference to the shader manager
     */
    private var gShader : GuiShaderManager!;
    
    
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
    private func getTransformationMatrix(gui : GuiTexture) -> GLTransformation {
        let matrix : GLTransformation  = GLTransformation();
        matrix.glLoadIdentity();
        
        matrix.glTranslate(gui.position.x, ty: gui.position.y, tz: 0.0);
        
        matrix.glScale(gui.scale.x, sy: gui.scale.y, sz: 1.0);
        return matrix;
        
    }
    
    /**
     * Render the GUIs in the scene
     *
     *
     * @param quad 	Reference to to quad where the GUIs are going to be Render
     * @param guis	List of GUIs to render
     */
    public func render(guis : Array<GuiTexture>) {
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
    private func lrender(guis: Array<GuiTexture> ) {
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
    private func prepareModel(quad : RawModel) {
        glBindVertexArrayOES(GLuint(quad.vaoId));
        glEnableVertexAttribArray(GLuint(TGuiAttribute.position.rawValue));
    }
    
    /**
     * Load the transformation matrix of the GUI
     *
     * @param gui
     *            Entity that is to get prepared to be loaded
     */
    private func prepareInstance(gui: GuiTexture) {
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
    private func render(quad :RawModel) {
        glDrawArrays(GLenum(GL_TRIANGLE_STRIP), 0, GLsizei(quad.indicesCount));
    }
    
    /**
     * UnBind the previous binded elements
     */
    private func unbindTexturedModel() {
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
