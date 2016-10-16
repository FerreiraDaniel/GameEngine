import Foundation
import OpenGLES

/**
* Class responsible to render the entities in the screen
*/
public class EntityRender : NSObject {
    
    /**
    * Reference to the shader manager
    */
    private var eShader : EntityShaderManager!;
    
    /**
    * Initializer of the entity render
    *
    * @param aShader           Shader manager
    * @param projectionMatrix  The projection matrix of the render
    */
    public init( aShader : EntityShaderManager, projectionMatrix : GLTransformation) {
        self.eShader = aShader;
        self.eShader.start();
        self.eShader.loadProjectionMatrix(projectionMatrix);
        self.eShader.stop();
    }
    
    /**
    * Render the entities in the scene
    *
    * @param skyColor
    * 			Color of the sky
    *
    * @param sun
    *            The source of light of the scene
    * @param viewMatrix
    *            View matrix to render the scene
    * @param entities
    *            List of entities of the scene
    */
    public func render(skyColor : Vector3f, sun : Light, viewMatrix : GLTransformation , entities : Dictionary<String, Array<Entity>>) {
        // Render the object
        eShader.start();
        //Load the elements of the scene
        eShader.loadSkyColor(skyColor);
        eShader.loadLight(sun);
        eShader.loadViewMatrix(viewMatrix);
        
        // Render the entities
        self.render(entities);
        
        eShader.stop();
    }
    
    /**
    * Render the entities in the scene
    *
    * @param skyColor   Color of the sky
    * @param sun        The source of light of the scene
    * @param viewMatrix View matrix to render the scene
    * @param player     The player of the scene
    */
    public func render(skyColor : Vector3f, sun : Light, viewMatrix : GLTransformation ,player : Player) {
        eShader.start();
        eShader.loadSkyColor(skyColor);
        eShader.loadLight(sun);
        eShader.loadViewMatrix(viewMatrix);
        
        self.renderPlayer(player);
        eShader.stop();
    }
    
    /**
    * Get the transformation matrix of one entity
    *
    * @param entity
    *            Entity for which is to create the transformation matrix
    *
    * @return The transformation matrix that put the entity in its right
    *         position
    */
    private func getTransformationMatrix(entity : Entity) -> GLTransformation {
        let matrix : GLTransformation  = GLTransformation();
        matrix.glLoadIdentity();
        let entityPosition : Vector3f = entity.position;
        
        matrix.glTranslate(entityPosition.x, ty: entityPosition.y, tz: entityPosition.z);
        matrix.glRotate(entity.rotX, x: 1.0, y: 0.0, z: 0.0)
        matrix.glRotate(entity.rotY, x: 0.0, y: 1.0, z: 0.0)
        matrix.glRotate(entity.rotZ, x: 0.0, y: 0.0, z: 1.0)
        
        matrix.glScale(entity.scale, sy: entity.scale, sz: entity.scale);
        return matrix;
    }
    
    /**
    * Render one hashMap of entities where each key is a group of similar
    * entities to be render
    *
    * @param entities
    *            HashMap of entities to render
    */
    private func render(entities : Dictionary<String, Array<Entity>>){
        if (!entities.isEmpty) {
            for (_, entities) in entities {
                let entity : Entity = entities.first!;
                let model : TexturedModel = entity.model;
                self.prepareTexturedModel(model)
                for entity in entities {
                    self.renderEntity(entity);
                }
                self.unbindTexturedModel();
                //Restore the state if has transparency
                if(!model.hasTransparency) {
                    self.disableCulling();
                }
            }
        }
    }
    
    /**
    * Call the render of the triangles to the entity itself
    *
    * @param entity
    * 			Entity to get render
    */
    private func renderEntity(entity : Entity) {
        let model : TexturedModel = entity.model;
        let rawModel : RawModel = model.rawModel;
        self.prepareInstance(entity);
        
        glDrawElements(GLenum(GL_TRIANGLES), GLsizei(rawModel.indicesCount), GLenum(GL_UNSIGNED_SHORT), rawModel.indicesData);
    }
    
    //----------------------------------------------------------------------------------
    
    /**
    * Bind the attributes of openGL
    *
    * @param texturedModel Model that contains the model of the entity with textures
    */
    private func prepareTexturedModel(texturedModel : TexturedModel) {
        let model : RawModel = texturedModel.rawModel;
        let modelTexture : ModelTexture = texturedModel.texture;
        
        if(!texturedModel.hasTransparency) {
            self.enableCulling();
        };
        glBindVertexArrayOES(GLuint(model.vaoId));
        
        
        glEnableVertexAttribArray(GLuint(TEntityAttribute.position.rawValue));
        glEnableVertexAttribArray(GLuint(TEntityAttribute.textureCoords.rawValue));
        glEnableVertexAttribArray(GLuint(TEntityAttribute.normal.rawValue));
        
        
        //Activate the texture of the entity
        glActiveTexture(GLuint(GL_TEXTURE0));
        glBindTexture(GLuint(GL_TEXTURE_2D), GLuint(modelTexture.textureId));
        
        // Load if should put the normals of the entity point up or not
        eShader.loadNormalsPointingUp(texturedModel.normalsPointingUp);
        
        // Load the the light properties
        eShader.loadShineVariables(modelTexture.shineDamper,  reflectivity: modelTexture.reflectivity);
        
    }
    
    /**
    * Load the transformation matrix of the entity
    *
    * @param entity
    * 			Entity that is to get prepared to be loaded
    */
    private func prepareInstance(entity : Entity) {
        // Load the transformation matrix
        eShader.loadTransformationMatrix(self.getTransformationMatrix(entity));
    }
    
    
    /**
    * Enable culling of faces to get better performance
    */
    private func enableCulling() {
        //Enable the GL cull face feature
        glEnable(GLuint(GL_CULL_FACE));
        //Avoid to render faces that are away from the camera
        glCullFace(GLuint(GL_BACK));
    }
    
    /**
    * Disable the culling of the faces vital for transparent model
    */
    private func disableCulling() {
        glDisable(GLuint(GL_CULL_FACE));
    }
    
    /**
    * Render one player of the scene
    *
    * @param player the player that is to render in the scene
    */
    private func renderPlayer(player : Player) {
        prepareTexturedModel(player.model);
        prepareInstance(player);
        self.renderEntity(player);
        // Restore the state if has transparency
        if (!player.model.hasTransparency) {
            disableCulling();
        }
        unbindTexturedModel();
    }
    
    /**
    * UnBind the previous binded elements
    */
    private func unbindTexturedModel() {
        
        glDisableVertexAttribArray(GLuint(TEntityAttribute.position.rawValue));
        glDisableVertexAttribArray(GLuint(TEntityAttribute.textureCoords.rawValue));
        glDisableVertexAttribArray(GLuint(TEntityAttribute.normal.rawValue));
        
        // UnBind the current VBO
        glBindBuffer(GLuint(GL_ARRAY_BUFFER), 0);
    }
    
    /**
    * Clean up because we need to clean up when we finish the program
    */
    deinit {
        eShader = nil;
    }
}
