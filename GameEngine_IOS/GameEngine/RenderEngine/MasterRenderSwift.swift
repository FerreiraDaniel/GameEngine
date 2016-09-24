import Foundation

/**
* Groups the entities in a hash map like this
* the same entity will be just put in different positions
*/
public class MasterRenderSwift : NSObject{
    
    
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
    private var entityRender : EntityRenderSwift;
    
    /**
    * Reference to the render of the terrains
    */
    private var terrainRender : TerrainRenderSwift;
    
    /**
    * Reference to the render of the sky box
    */
    private var skyBoxRender : SkyBoxRenderSwift;
    
    
    /**
    * Reference to the camera from where the user is going to see the 3D world
    */
    private var camera : Camera;
    
    /**
    * Entities of the world that are going to be rendered
    */
    private var entities : Array<Entity>;
    
    /**
    * List of terrains of the world that are going to be render
    */
    private var terrains : Array<Terrain>;
    
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
    private func updateCameraSwift() -> GLTransformation {
        camera.move();
        camera.rotate();
        
        // Matrix update
        let viewMatrix : GLTransformation = MasterRenderSwift.createViewMatrix(camera);
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
        let projectionMatrix : GLTransformation = MasterRenderSwift.createProjectionMatrix(aWidth, height: aHeight);
        
        
        //Initializes the entity render
        let  eShader : EntityShaderManager = EntityShaderManager();
        self.entityRender = EntityRenderSwift(aShader: eShader, projectionMatrix: projectionMatrix);
        
        // Initializes the entities to render
        self.entities = Array<Entity>();
        
        // Initializes the terrain render
        let tShader : TerrainShaderManager = TerrainShaderManager();
        self.terrainRender = TerrainRenderSwift(aShader: tShader, projectionMatrix: projectionMatrix);
        
        // Initializes the terrains to render
        self.terrains = Array<Terrain>();
        
        // Initializes the sky box render
        let sbManager : SkyBoxShaderManager = SkyBoxShaderManager();
        
        self.skyBoxRender = SkyBoxRenderSwift(aShader: sbManager, projectionMatrix: projectionMatrix);
        
        // Initializes the camera
        self.camera = Camera();
        
        super.init();
        
    }
    
    
    /**
    * Put the entities to process in the hash map dedicated to process entities
    * by group
    *
    * @param lEntities
    *            list of entities to get render in the next frame
    */
    public func processEntities(lEntities : Array<Entity>) {
    }
    
    
    /**
    * Put the terrains to process in the list of terrains to process
    *
    * @param lTerrains
    *            list of terrains to process
    */
    public func  processTerrains(lTerrains : Array<Terrain>) {
    }
    
    /**
    * Set the sky box the use during the render
    */
    public func  processSkyBox(aSkyBox : SkyBox) {
    }
    
    /**
    * Render the entire scene (Called by each frame)
    *
    * @param sun
    *            Sun of the scene
    */
    public func render(sun : Light) {
    }
    
    
}