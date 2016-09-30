import Foundation

public class TerrainRender : NSObject {
    
    /**
    * Reference to the shader manager
    */
    var tShader : TerrainShaderManager!;
    
    /**
    * Initializer of the terrain render
    *
    * @param aShader           Shader manager
    * @param projectionMatrix  The projection matrix of the render
    */
    public init( aShader : TerrainShaderManager, projectionMatrix : GLTransformation) {
        self.tShader = aShader;
        self.tShader.start();
        self.tShader.loadProjectionMatrix(projectionMatrix);
        self.tShader.stop();
    }
    
    /**
    * Get the transformation matrix of one terrain
    *
    * @param terrain
    *            Entity for which is to create the transformation matrix
    *
    * @return The transformation matrix that put the terrain in its right
    *         position
    */
    private func getTransformationMatrix(terrain : Terrain) -> GLTransformation {
        let matrix : GLTransformation  = GLTransformation();
        matrix.glLoadIdentity();
        matrix.glTranslate(terrain.x, ty: terrain.y, tz: terrain.z);
        let terrainRotation : Float = 0.0;
        matrix.glRotate(terrainRotation, x: 1.0, y: 0.0, z: 0.0)
        matrix.glRotate(terrainRotation, x: 0.0, y: 1.0, z: 0.0)
        matrix.glRotate(terrainRotation, x: 0.0, y: 0.0, z: 1.0)
        return matrix;
    }
    
    /**
    * Render the terrains in the scene
    *
    * @param skyColor
    * 			Color of the sky
    * @param sun
    *            The source of light of the scene
    * @param viewMatrix
    *            View matrix to render the scene
    * @param terrains
    *            List of terrains of the scene
    */
    public func render(skyColor : Vector3f, sun : Light, viewMatrix : GLTransformation, terrains : Array<Terrain>) {
        tShader.start();
        tShader.loadSkyColor(skyColor);
        tShader.loadLight(sun);
        tShader.loadViewMatrix(viewMatrix);
        tShader.connectTextureUnits();
        
        self.render(terrains);
        tShader.stop();
    }
    
    /**
    * Render one list of terrains
    *
    * @param terrains
    *            Terrains to render
    */
    private func render (terrains : Array<Terrain>) {
        if (terrains.isEmpty) {
            return;
        } else {
            for terrain in terrains {
                self.prepareTerrain(terrain);
                self.prepareInstance(terrain);
                self.render_terrain(terrain);
                self.unbindTexturedModel();
            }
        }
    }
    
    /**
    * Bind the attributes of openGL
    *
    * @param terrain
    *      Terrain to get prepared
    */
    private func prepareTerrain(terrain : Terrain) {
        let model : RawModel = terrain.model;
        
        
        glBindVertexArrayOES(GLuint(model.vaoId));
        
        glEnableVertexAttribArray(GLuint(TTerrainAttribute.position.rawValue));
        glEnableVertexAttribArray(GLuint(TTerrainAttribute.textureCoords.rawValue));
        glEnableVertexAttribArray(GLuint(TTerrainAttribute.normal.rawValue));
        
        
        // bind several textures of the terrain
        self.bindTextures(terrain);
        
        // Bind the light properties
        tShader.loadShineVariables(1.0, reflectivity: 0.0);
    }
    
    /**
    * When loads one texture defines that by default should zoom in/out it
    */
    private func defineTextureFunctionFilters() {
        glActiveTexture(GLuint(GL_TEXTURE0));
        
        // The texture minifying function is used whenever the pixel being
        // textured maps to an area greater than one texture element
        glTexParameteri(GLuint(GL_TEXTURE_2D), GLuint(GL_TEXTURE_MIN_FILTER), GL_LINEAR);
        
        // The texture magnification function is used when the pixel being
        // textured maps to an area less than or equal to one texture element
        glTexParameteri(GLuint(GL_TEXTURE_2D), GLuint(GL_TEXTURE_MAG_FILTER), GL_LINEAR);
        
        // Sets the wrap parameter for texture coordinate s
        glTexParameteri(GLuint(GL_TEXTURE_2D), GLuint(GL_TEXTURE_WRAP_S), GL_REPEAT);
        
        // Sets the wrap parameter for texture coordinate t
        glTexParameteri(GLuint(GL_TEXTURE_2D), GLuint(GL_TEXTURE_WRAP_T), GL_REPEAT);
    }
    
    /**
    * Bind the several textures of the terrain
    *
    * @param terrain Terrain that is going to get the textures bound
    */
    private func bindTextures(terrain : Terrain) {
        let texturesPackage : TerrainTexturesPack = terrain.texturePack;
        
        
        let backgroundTextureId : Int = texturesPackage.backgroundTextureId;
        let mudTextureId : Int = texturesPackage.mudTextureId;
        let grassTextureId : Int = texturesPackage.grassTextureId;
        let pathTextureId : Int = texturesPackage.pathTextureId;
        let weightMapTextureId : Int = texturesPackage.weightMapTextureId;
        
        
        
        let texturesMatching : Dictionary<Int32, Int> = [
            GL_TEXTURE0 : backgroundTextureId,
            GL_TEXTURE1 : mudTextureId,
            GL_TEXTURE2 : grassTextureId,
            GL_TEXTURE3 : pathTextureId,
            GL_TEXTURE4 : weightMapTextureId,
        ];
        for (key, iTexture) in texturesMatching {
            glActiveTexture(GLenum(key));
            glBindTexture(GLenum(GL_TEXTURE_2D), GLuint(iTexture));
            
            // Set filtering of the texture
            self.defineTextureFunctionFilters();
        }
    }
    
    /**
    * Render the terrain itself
    *
    * @param terrain
    *            the terrain to render
    */
    private func   prepareInstance(terrain : Terrain) {
        // Load the transformation matrix
        self.tShader.loadTransformationMatrix(self.getTransformationMatrix(terrain));
    }
    
    /**
    * Call the render of the triangles to the terrain itself
    *
    * @param terrain A reference to the terrain to get render
    */
    private func render_terrain(terrain : Terrain) {
        let rawModel : RawModel = terrain.model;
        
        glDrawElements(GLenum(GL_TRIANGLES), rawModel.indicesCount, GLenum(GL_UNSIGNED_SHORT), rawModel.indicesData);
    }
    
    /**
    * UnBind the previous binded elements
    */
    private func unbindTexturedModel() {
        glDisableVertexAttribArray(GLuint(TTerrainAttribute.position.rawValue));
        glDisableVertexAttribArray(GLuint(TTerrainAttribute.textureCoords.rawValue));
        glDisableVertexAttribArray(GLuint(TTerrainAttribute.normal.rawValue));
        glBindVertexArrayOES(0);
    }
    
    /**
    * Clean up because we need to clean up when we finish the program
    */
    deinit {
        tShader = nil;
    }
}