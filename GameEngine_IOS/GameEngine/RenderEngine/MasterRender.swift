import Foundation
import GLKit


/// Groups the entities in a hash map like this the same entity will be just put in different positions
open class MasterRender {
    
    
    /// Width of the screen
    fileprivate var width : Int;
    
    
    /// Height of the screen
    fileprivate var height : Int;
    
    
    /// Reference to the render of the entities
    fileprivate var entityRender : EntityRender!;
    
    
    /// Reference to the render of the terrains
    fileprivate var terrainRender : TerrainRender!;
    
    
    /// Reference to the render of the sky box
    fileprivate var skyBoxRender : SkyBoxRender!;
    
    
    /// Reference to the render of GUIs
    fileprivate var guiRender : GuiRender!;
    
    /**
     * Reference to the camera from where the user is going to see the 3D world
     */
    fileprivate var camera : ThirdPersonCamera!;
    
    /**
     * Entities of the world that are going to be rendered
     */
    fileprivate var entities : Dictionary<String, Array<Entity>>!;
    
    /**
     * List of terrains of the world that are going to be render
     */
    fileprivate var terrains : Array<Terrain>!;
    
    /**
     * The player that is going to be show in the scene
     */
    fileprivate var player : Player!;
    
    /**
     * List of GUIs to show the status of the user
     */
    fileprivate var GUIs : Array<GuiTexture>!;
    
    /**
     * The sky box that is going to use during the render
     */
    fileprivate var skyBox : SkyBox!;
    
    /**
     * Used to track how long tacks to render a frame
     */
    fileprivate var startDate : Date!;
    
    /**
     * The time to render one last frame in seconds
     * Variable used to be frame rate independent when moves the entities around
     */
    fileprivate var timeToRender : Float = 0.0;
    
    /* Constants of the camera */
    fileprivate static let FOV : Float = 45.0;
    fileprivate static let NEAR_PLANE : Float = 0.1;
    fileprivate static let FAR_PLANE : Float = 1000.0;
    
    
    /*Components of the color of the sky*/
    fileprivate static let SKY_R : Float = 0.5;
    fileprivate static let SKY_G : Float = 0.5;
    fileprivate static let SKY_B : Float = 0.5;
    fileprivate static let SKY_A : Float = 1.0;
    
    /**
     * Create the projection matrix with parameters of the camera
     *
     * @return A projection matrix
     */
    fileprivate static func createProjectionMatrix(_ width : Int, height : Int) -> GLTransformation {
        let matrix : GLTransformation = GLTransformation();
        matrix.loadIdentity();
        let aspect : Float = fabs( ((1.0) * Float(width)) / Float(height));
        matrix.perspective(yViewAngle: FOV, aspect: aspect, nearZ: NEAR_PLANE, farZ: FAR_PLANE);
        
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
    fileprivate static func createViewMatrix(camera : Camera) -> GLTransformation {
        let matrix : GLTransformation = GLTransformation();
        matrix.loadIdentity();
        matrix.rotate(angle: camera.pitch, x: 1.0, y: 0.0, z: 0.0);
        matrix.rotate(angle: camera.yaw, x:0.0, y:1.0, z: 0.0);
        
        
        let camPosition : Vector3f = camera.position;
        matrix.translate(x: -camPosition.x, y: -camPosition.y, z: -camPosition.z);
        return matrix;
    }
    
    
    /**
     * Calls the methods to update the camera and updates the matrix that
     * describe the camera in the scene
     */
    fileprivate func updateCamera() -> GLTransformation {
        
        //Update the camera taking in account the position of the player
        if(player != nil) {
            camera.update(Player: player, terrain: terrains[0]);
        }
        
        // Matrix update
        let viewMatrix : GLTransformation = MasterRender.createViewMatrix(camera: camera);
        return viewMatrix;
    }
    
    /**
     * Uses the GUIs to update the game pad of the game
     */
    fileprivate func updateGamePad() {
        if ((self.GUIs != nil) && (!self.GUIs.isEmpty)) {
            for guiTexture in GUIs {
                if(guiTexture.gamePadKey != nil) {
                    let keyPressed = guiTexture.containsLocation(GameEngineGestureRecognizer.getGlX(), GameEngineGestureRecognizer.getGlY());
                    GamePad.setKey(guiTexture.gamePadKey, clicked: keyPressed && GameEngineGestureRecognizer.getIsPressed());
                }
            }
        }
    }
    
    
    /// Call the method to update the player position
    fileprivate func updatePlayer() {
        if (self.player != nil) {
            self.player.move(self.timeToRender, terrain: terrains[0]);
        }
    }
    
    
    /// Initiator of the master render
    ///
    /// - Parameters:
    ///   - width: Width of the view to render the 3D world
    ///   - height: Height of the view to render the 3D world
    public init(width : Int, height : Int) {
        
        
        self.width = width;
        self.height = height;
        
        
        //Initializes the projection matrix
        let projectionMatrix : GLTransformation = MasterRender.createProjectionMatrix(width, height: height);
        
        
        //Initializes the entity render
        let  eShader : EntityShaderManager = EntityShaderManager();
        self.entityRender = EntityRender(aShader: eShader, projectionMatrix: projectionMatrix);
        
        // Initializes the entities to render
        self.entities = Dictionary<String, Array<Entity>>();
        
        // Initializes the terrain render
        let tShader : TerrainShaderManager = TerrainShaderManager();
        self.terrainRender = TerrainRender(aShader: tShader, projectionMatrix: projectionMatrix);
        
        let gShader : GuiShaderManager = GuiShaderManager();
        self.guiRender = GuiRender(gShader);
        
        // Initializes the terrains to render
        self.terrains = Array<Terrain>();
        
        // Initializes the sky box render
        let sbManager : SkyBoxShaderManager = SkyBoxShaderManager();
        
        self.skyBoxRender = SkyBoxRender(aShader: sbManager, projectionMatrix: projectionMatrix);
        
        // Initializes the GUIs to render
        self.GUIs = Array<GuiTexture>();
        
        
        // Initializes the camera
        self.camera = ThirdPersonCamera();
        
    }
    
    /**
     * Put one entity in the list of entities to render
     *
     * @param entity
     *            the entity to add to the render
     */
    fileprivate func processEntity(_ entity : Entity) {
        let genericEntity : GenericEntity = entity.genericEntity;
        let key : String = genericEntity.id;
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
    open func processEntities(_ lEntities : Array<Entity>!) {
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
    fileprivate func  processTerrain(_ terrain : Terrain) {
        self.terrains.append(terrain);
    }
    
    /**
     * Put the terrains to process in the list of terrains to process
     *
     * @param lTerrains
     *            list of terrains to process
     */
    open func  processTerrains(_ lTerrains : Array<Terrain>!) {
        self.terrains.removeAll();
        for terrain in lTerrains {
            self.processTerrain(terrain);
        }
    }
    
    /**
     * Put a GUI in the list of GUIs to render
     *
     * @param gui
     *            the GUI to render
     */
    fileprivate func processGUI(_ gui : GuiTexture) {
        self.GUIs.append(gui);
    }
    
    /**
     * Put the GUIs to process in the list of GUIs to process
     *
     * @param lGuis
     *            array of GUIs to process
     */
    open func processGUIs(_ lGuis : Array<GuiTexture>) {
        self.GUIs.removeAll();
        
        if (!lGuis.isEmpty) {
            for gui in lGuis {
                
                processGUI(gui);
            }
        }
    }
    
    /**
     * Set the sky box the use during the render
     */
    open func  processSkyBox(_ aSkyBox : SkyBox) {
        self.skyBox = aSkyBox;
    }
    
    /**
     * Set the player that is going to use during the render
     *
     * @param player The player that is going to set
     */
    open func processPlayer(_ player : Player) {
        self.player = player;
    }
    
    /**
     * Render the entire scene (Called by each frame)
     *
     * @param sun
     *            Sun of the scene
     */
    open func render(_ sun : Light) {
        self.prepare();
        self.updateGamePad();
        self.updatePlayer();
        let viewMatrix : GLTransformation = self.updateCamera();
        let skyColor : ColorRGBA = ColorRGBA(r: MasterRender.SKY_R, g: MasterRender.SKY_G, b: MasterRender.SKY_B, a: MasterRender.SKY_A);
        self.entityRender.render(skyColor, sun: sun, viewMatrix: viewMatrix, entities: self.entities, player: self.player);
        self.terrainRender.render(skyColor, sun: sun, viewMatrix: viewMatrix, terrains: self.terrains);
        self.skyBoxRender.render(viewMatrix, skyBox: self.skyBox);
        self.guiRender.render(self.GUIs);
    }
    
    /**
     * Indicates that is going to start the rendering of a new frame Like that
     * the master render can compute how long tacks to render the frame
     */
    open func startFrameRender() {
        self.startDate = Date();
    }
    
    /**
     * Indicates that is going to end the rendering of a frame Like that the
     * master render can compute how long tacks to render the frame
     */
    open func endFrameRender() {
        // Logs frames/s
        let endDate = Date();
        let interval : TimeInterval = endDate.timeIntervalSince(self.startDate);
        self.timeToRender = Float(interval);
    }
    
    
    /// Clean the data of the previous frame
    fileprivate func prepare()
    {
        glEnable(GLenum(GL_DEPTH_TEST));
        
        // Set the color clear value
        glClearColor(0, 0.3, 0, 1);
        
        // Clear the color and depth buffers
        glClear(GLbitfield(GL_COLOR_BUFFER_BIT) | GLbitfield(GL_DEPTH_BUFFER_BIT))
    }
    
    
    /// Releases the resources used by the class
    deinit {
        self.entityRender = nil;
        self.entities = nil;
        self.terrainRender = nil;
        self.skyBoxRender = nil;
        self.guiRender = nil;
        self.terrains = nil;
        self.player = nil;
        self.camera = nil;
        self.GUIs = nil;
        self.skyBox = nil;
    }
}
