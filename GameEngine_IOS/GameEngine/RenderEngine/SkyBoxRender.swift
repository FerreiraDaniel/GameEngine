import Foundation
import OpenGLES

public class SkyBoxRender : NSObject {
    
    /**
    * Reference to the shader manager
    */
    private var sbShader : SkyBoxShaderManager!;
    
    
    /**
    * Initializer of the sky box render
    *
    * @param aShader           Shader manager
    * @param projectionMatrix  The projection matrix of the render
    */
    public init(aShader : SkyBoxShaderManager , projectionMatrix : GLTransformation) {
        sbShader = aShader;
        sbShader.start();
        sbShader.loadProjectionMatrix(projectionMatrix);
        sbShader.stop();
    }
    
    
    //---------------------------------------------------------------------------------
    //Render method
    public func render(viewMatrix : GLTransformation, skyBox : SkyBox)
    {
        // Render the object
        sbShader.start();
        //Load the elements of the scene
        sbShader.loadViewMatrix(viewMatrix);
        
        self.prepareSkyBox(skyBox);
        
        // Render the sky box
        self.render(skyBox);
        
        self.unbindTexture();
        
        sbShader.stop();
        
    }
    //---------------------------------------------------------------------------------
    
    
    /**
    * Bind the attributes of openGL
    *
    * @param skyBox
    * 			The sky box description that should be prepared
    */
    private func prepareSkyBox(skyBox : SkyBox) {
        let model : RawModel = skyBox.model;
        
        
        glBindVertexArrayOES(GLuint(model.vaoId));
        
        glEnableVertexAttribArray(GLuint(TSkyBoxAttribute.position.rawValue));
        
        
        // bind several textures of the sky box
        self.bindTextures(skyBox);
    }
    
    /**
    * Bind the cube texture of the skyBox
    */
    private func bindTextures(skyBox : SkyBox) {
        glActiveTexture(GLenum(GL_TEXTURE0));
        glBindTexture(GLenum(GL_TEXTURE_CUBE_MAP), GLuint(skyBox.textureId));
    }
    
    
    
    /**
    * Call the render of the triangles to the skyBox itself
    *
    * @param skyBox
    * 			The sky box to be render
    */
    private func render(skyBox :  SkyBox) {
        let rawModel : RawModel  = skyBox.model;
        glDrawArrays(GLenum(GL_TRIANGLES), 0, GLsizei(rawModel.indicesCount));
    }
    
    
    
    
    
    /**
    * UnBind the previous binded elements
    */
    public func  unbindTexture() {
        glDisableVertexAttribArray(GLuint(TSkyBoxAttribute.position.rawValue));
        // UnBind the current VBO
        glBindBuffer(GLenum(GL_ARRAY_BUFFER), 0);
    }
    
    /**
    * Clean up because we need to clean up when we finish the program
    */
    deinit {
        sbShader = nil;
    }
}