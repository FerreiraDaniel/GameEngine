import Foundation

/**
* Groups the entities in a hash map like this
* the same entity will be just put in different positions
*/
public class MasterRender : NSObject{
    
    
    /**
    * Width of the screen
    */
    private var width : Int;
    
    /**
    * Height of the screen
    */
    private var height : Int;
    
    /**
    * Reference to the render of the entities
    */
    private var entityRender : EntityRender!;
    
    /**
    * Reference to the render of the terrains
    */
    private var terrainRender : TerrainRender!;
    
    /**
    * Reference to the render of the sky box
    */
    private var skyBoxRender : SkyBoxRender!;
    
    
    /**
    * Reference to the camera from where the user is going to see the 3D world
    */
    private var camera : Camera!;
    
    /**
    * Entities of the world that are going to be rendered
    */
    private var entities : Dictionary<String, Array<Entity>>!;
    
    /**
    * List of terrains of the world that are going to be render
    */
    private var terrains : Array<Terrain>!;
    
    /**
    * The sky box that is going to use during the render
    */
    private var skyBox : SkyBox!;
    
    /* Constants of the camera */
    private static let FOV : Float = 65.0;
    private static let NEAR_PLANE : Float = 0.1;
    private static let FAR_PLANE : Float = 1000.0;
    
    
    /*Components of the color of the sky*/
    private static let SKY_R : Float = 0.5;
    private static let SKY_G : Float = 0.5;
    private static let SKY_B : Float = 0.5;
    
    /**
    * Create the projection matrix with parameters of the camera
    *
    * @return A projection matrix
    */
    private static func createProjectionMatrix(width : Int, height : Int) -> GLTransformation {
        let matrix : GLTransformation = GLTransformation();
        matrix.glLoadIdentity();
        let aspect : Float = fabs( ((1.0) * Float(width)) / Float(height));
        matrix.gluPerspective(FOV, aspect: aspect, nearZ: NEAR_PLANE, farZ: FAR_PLANE);
        
        return matrix;
    }
    
    
    /**
    * Create the view matrix from the data that has about the camera
    *
    * @param aCamera
    *            the camera to which is to create the view matrix
    *
    * @return The view matrix
    */
    private static func createViewMatrix(aCamera : Camera) -> GLTransformation {
        let matrix : GLTransformation = GLTransformation();
        matrix.glLoadIdentity();
        matrix.glRotate(aCamera.pitch, x: 1.0, y: 0.0, z: 0.0);
        matrix.glRotate(aCamera.yaw, x:0.0, y:1.0, z: 0.0);
        
        
        let camPosition : Vector3f = aCamera.position;
        matrix.glTranslate(-camPosition.x, ty: -camPosition.y, tz: -camPosition.z);
        return matrix;
    }
    
    
    /**
    * Calls the methods to update the camera and updates the matrix that
    * describe the camera in the scene
    */
    private func updateCamera() -> GLTransformation {
        camera.move();
        camera.rotate();
        
        // Matrix update
        let viewMatrix : GLTransformation = MasterRender.createViewMatrix(camera);
        return viewMatrix;
    }
    
    /**
    *   Initiator of the master render
    *   @param width Width of the view to render the 3D world
    *   @param height Height of the view to render the 3D world
    */
    public init(aWidth : Int, aHeight : Int) {
        
        
        self.width = aWidth;
        self.height = aHeight;
        
        
        //Initializes the projection matrix
        let projectionMatrix : GLTransformation = MasterRender.createProjectionMatrix(aWidth, height: aHeight);
        
        
        //Initializes the entity render
        let  eShader : EntityShaderManager = EntityShaderManager();
        self.entityRender = EntityRender(aShader: eShader, projectionMatrix: projectionMatrix);
        
        // Initializes the entities to render
        self.entities = Dictionary<String, Array<Entity>>();
        
        // Initializes the terrain render
        let tShader : TerrainShaderManager = TerrainShaderManager();
        self.terrainRender = TerrainRender(aShader: tShader, projectionMatrix: projectionMatrix);
        
        // Initializes the terrains to render
        self.terrains = Array<Terrain>();
        
        // Initializes the sky box render
        let sbManager : SkyBoxShaderManager = SkyBoxShaderManager();
        
        self.skyBoxRender = SkyBoxRender(aShader: sbManager, projectionMatrix: projectionMatrix);
        
        // Initializes the camera
        self.camera = Camera();
        
        super.init();
        
    }
    
    /**
    * Put one entity in the list of entities to render
    *
    * @param entity
    *            the entity to add to the render
    */
    private func processEntity(entity : Entity) {
        let entityModel : TexturedModel = entity.model;
        let key : String = String(entityModel.id);
        var batch : Array<Entity>! = entities[key];
        
        
        if (batch == nil) {
            batch = Array<Entity>();
        }
        
        batch.append(entity);
        entities[key] = batch;
    }
    
    /**
    * Put the entities to process in the hash map dedicated to process entities
    * by group
    *
    * @param lEntities
    *            list of entities to get render in the next frame
    */
    public func processEntities(lEntities : Array<Entity>!) {
        self.entities.removeAll();
        if ((lEntities != nil) && (!lEntities.isEmpty)) {
            for entity in lEntities {
                self.processEntity(entity);
            }
        }
    }
    
    /**
    * Put a terrain in the list of terrains to render
    *
    * @param terrain
    *            the terrain to render
    */
    private func  processTerrain(terrain : Terrain) {
        self.terrains.append(terrain);
    }
    
    /**
    * Put the terrains to process in the list of terrains to process
    *
    * @param lTerrains
    *            list of terrains to process
    */
    public func  processTerrains(lTerrains : Array<Terrain>!) {
        self.terrains.removeAll();
        for terrain in lTerrains {
            self.processTerrain(terrain);
        }
    }
    
    /**
    * Set the sky box the use during the render
    */
    public func  processSkyBox(aSkyBox : SkyBox) {
        self.skyBox = aSkyBox;
    }
    
    /**
    * Render the entire scene (Called by each frame)
    *
    * @param sun
    *            Sun of the scene
    */
    public func render(sun : Light) {
        self.prepare();
        let viewMatrix : GLTransformation = self.updateCamera();
        let skyColor : Vector3f = Vector3f(MasterRender.SKY_R, MasterRender.SKY_G, MasterRender.SKY_B);
        entityRender.render(skyColor, sun: sun, viewMatrix: viewMatrix, entities: self.entities);
        terrainRender.render(skyColor, sun: sun, viewMatrix: viewMatrix, terrains: self.terrains);
        skyBoxRender.render(viewMatrix, skyBox: self.skyBox);
    }
    
    /**
    * Clean the data of the previous frame
    */
    private func prepare()
    {
        glEnable(GLenum(GL_DEPTH_TEST));
        
        // Set the color clear value
        glClearColor(0, 0.3, 0, 1);
        
        // Clear the color and depth buffers
        glClear(GLbitfield(GL_COLOR_BUFFER_BIT) | GLbitfield(GL_DEPTH_BUFFER_BIT))
    }
    
    /**
    *  Releases the resources used by the class
    */
    deinit {
        self.entityRender = nil;
        self.entities = nil;
        self.terrainRender = nil;
        self.terrains = nil;
        self.skyBoxRender = nil;
        self.camera = nil;
    }
}