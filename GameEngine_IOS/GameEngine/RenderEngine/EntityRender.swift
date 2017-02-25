import Foundation
import OpenGLES


/// Responsible to render the entities in the screen
open class EntityRender {
    

    /// Reference to the shader manager
    fileprivate var eShader : EntityShaderManager!;
    

    /// Initializer of the entity render
    ///
    /// - Parameters:
    ///   - aShader: Shader manager
    ///   - projectionMatrix: The projection matrix of the render
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
     * @param player     The player of the scene
     */
    open func render(_ skyColor : ColorRGBA, sun : Light, viewMatrix : GLTransformation , entities : Dictionary<String, Array<Entity>>, player : Player) {
        // Render the object
        eShader.start();
        //Load the elements of the scene
        eShader.loadSkyColor(skyColor);
        eShader.loadLight(sun);
        eShader.loadViewMatrix(viewMatrix);
        
        // Render the entities
        self.render(entities);
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
    fileprivate func getTransformationMatrix(_ entity : Entity) -> GLTransformation {
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
    fileprivate func render(_ entities : Dictionary<String, Array<Entity>>){
        if (!entities.isEmpty) {
            for (_, batch) in entities {
                let firstEntity : Entity = batch.first!;
                let genericEntity : GenericEntity = firstEntity.genericEntity
                let groupsOfMaterials : Dictionary<String, MaterialGroup>  = genericEntity.groupsOfMaterials;
                for(_, materialGroup) in groupsOfMaterials {
                    for rawModelMaterial in materialGroup.materials {
                        let model : RawModel = rawModelMaterial.rawModel
                        let material : Material? = rawModelMaterial.material
                        if(material != nil)
                        {
                            prepareMaterial(material!);
                        }
                        
                        prepareModel(model);
                        for entity in batch {
                            loadEntityTransformation(entity);
                            self.render(model);
                        }
                        unPrepareModel();
                        if(material != nil)
                        {
                            unPrepareMaterial(material!);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Render one player of the scene
     *
     * @param player the player that is to render in the scene
     */
    fileprivate func renderPlayer(_ player : Player) {
        let genericEntity : GenericEntity = player.genericEntity;
        let groupsOfMaterials : Dictionary<String, MaterialGroup> = genericEntity.groupsOfMaterials;
        for(_, materialGroup) in groupsOfMaterials {
            for rawModelMaterial in materialGroup.materials {
                let model : RawModel = rawModelMaterial.rawModel
                let material : Material? = rawModelMaterial.material
                if(material != nil)
                {
                    prepareMaterial(material!);
                }
                prepareModel(model);
                loadEntityTransformation(player);
                render(model);
                unPrepareModel();
                if(material != nil)
                {
                    unPrepareMaterial(material!);
                }
            }
        }
    }
    
    /**
     * Call the render of the triangles to the model
     *
     * @param model Raw model to get render
     */
    fileprivate func render(_ model: RawModel) {
        if(model.indicesData != nil)
        {
            glDrawElements(GLenum(GL_TRIANGLES), GLsizei(model.indicesCount), GLenum(GL_UNSIGNED_SHORT), model.indicesData!);
        }
    }
    
    //----------------------------------------------------------------------------------
    
    /**
     * Bind the model to render the entity with openGL
     *
     * @param model Model that contains the model of the entity with textures
     */
    fileprivate func prepareModel(_ model : RawModel) {
        glBindVertexArrayOES(GLuint(model.vaoId));
        //Enable the attributes to bind
        glEnableVertexAttribArray(GLuint(TEntityAttribute.position.rawValue));
        glEnableVertexAttribArray(GLuint(TEntityAttribute.textureCoords.rawValue));
        glEnableVertexAttribArray(GLuint(TEntityAttribute.normal.rawValue));
    }
    
    /**
     * Bind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    fileprivate func prepareMaterial(_ material : Material) {
        if(!material.hasTransparency) {
            self.enableCulling();
        };
        
        
        if(material.textureId > 0) {
            //Enable the specific texture
            glActiveTexture(GLuint(GL_TEXTURE0));
            glBindTexture(GLuint(GL_TEXTURE_2D), GLuint(material.textureId));
        }
        
        // Load if should put the normals of the entity point up or not
        eShader.loadNormalsPointingUp(material.normalsPointingUp);
        
        // Load the the light properties
        eShader.loadShineVariables(material.shineDamper,  reflectivity: material.reflectivity);
        
        // Load the texture weight of the material
        eShader.loadTextureWeight(material.textureWeight);
        
        // Load the diffuse color of the material
        eShader.loadDiffuseColor(material.diffuseColor);
    }
    
    /**
     * Load the transformation matrix of the entity
     *
     * @param entity
     * 			Entity that is to get prepared to be loaded
     */
    fileprivate func loadEntityTransformation(_ entity : Entity) {
        // Load the transformation matrix
        eShader.loadTransformationMatrix(self.getTransformationMatrix(entity));
    }
    
    
    /**
     * Enable culling of faces to get better performance
     */
    fileprivate func enableCulling() {
        //Enable the GL cull face feature
        glEnable(GLuint(GL_CULL_FACE));
        //Avoid to render faces that are away from the camera
        glCullFace(GLuint(GL_BACK));
    }
    
    /**
     * Disable the culling of the faces vital for transparent model
     */
    fileprivate func disableCulling() {
        glDisable(GLuint(GL_CULL_FACE));
    }
    
    
    
    
    /**
     * UnBind the previous bound elements
     */
    fileprivate func unPrepareModel() {
        
        glDisableVertexAttribArray(GLuint(TEntityAttribute.position.rawValue));
        glDisableVertexAttribArray(GLuint(TEntityAttribute.textureCoords.rawValue));
        glDisableVertexAttribArray(GLuint(TEntityAttribute.normal.rawValue));
        
        // UnBind the current VBO
        glBindBuffer(GLuint(GL_ARRAY_BUFFER), 0);
    }
    
    /**
     * UnBind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    fileprivate func unPrepareMaterial(_ material : Material) {
        // Restore the state if has transparency
        if (!material.hasTransparency) {
            disableCulling();
        }
    }
    
    /**
     * Clean up because we need to clean up when we finish the program
     */
    deinit {
        eShader = nil;
    }
}
