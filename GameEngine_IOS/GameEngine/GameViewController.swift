import Foundation

import UIKit
import GLKit


public class GameViewController: GLKViewController {
    
    /**
     * The master render is going put all the elements together in order to
     * render the scene
     */
    private var renderer : MasterRender! = nil;
    
    /**
     * Array of entities to render
     */
    private var entities : Array<Entity> = [];
    
    /**
     * Array of terrains to render
     */
    private var terrains : Array<Terrain> = [];

    /**
     * Array of GUIs to render
     */
    private var guis : Array<GuiTexture> = [];
    
    /**
     * Position of the light in scene
     */
    private var light : Light! = nil;
    
    /**
     * SkyBox of the 3D world
     */
    private var skyBox : SkyBox! = nil;
    
    /**
     * The player that is going to be show in the scene
     */
    private var player : Player! = nil;
    

    
    private var context:  EAGLContext! = nil;
    
    public override func viewDidLoad() {
        super.viewDidLoad()
        
        self.context = EAGLContext(API: .OpenGLES2)
        
        if !(self.context != nil) {
            print("Failed to create ES context")
        }
        
        let view = self.view as! GLKView
        view.context = self.context!
        view.drawableDepthFormat = .Format24
        
        //Handle events registration
        let gestureRecognizer = GameEngineGestureRecognizer()
        view.addGestureRecognizer(gestureRecognizer)
        
        self.setupGL()
        
        /* Initializes the main variables responsible to render the 3D world */
        let loader : Loader = Loader()
        
        /* Prepares the terrains that is going to render */
        self.terrains = WorldTerrainsGenerator.getTerrains(loader);
        
        /* Prepares the entities that is going to be render */
        self.entities = WorldEntitiesGenerator.getEntities(loader, terrain: terrains[0]);
        
        
        /* Load the lights that is going to render*/
        self.light = WorldEntitiesGenerator.getLight();
        
        /* Prepares the guis that is going to render*/
        self.guis = WorldGUIsGenerator.getGUIs(loader);
        
        /* Load the sky box that is going to render*/
        self.skyBox = WorldSkyBoxGenerator.getSky(loader);
        
        /*Prepares the player that is going to be used in the scene*/
        self.player = WorldPlayersGenerator.getPlayer(loader);
        
                
    }
    
    
    
    
    public override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
        if self.isViewLoaded() && (self.view.window != nil) {
            self.view = nil
            
            self.tearDownGL()
            
            if EAGLContext.currentContext() === self.context {
                EAGLContext.setCurrentContext(nil)
            }
            self.context = nil
        }
    }
    
    private func setupGL() {
        EAGLContext.setCurrentContext(self.context);
        
        self.renderer =  MasterRender(width: Int(self.view.bounds.size.width), height: Int(self.view.bounds.size.height))
    }
    
    private func tearDownGL() {
        EAGLContext.setCurrentContext(self.context);
        self.renderer = nil;
    }
    
    public func update() {
        /*NSDate* startDate = [NSDate date];*/
        // game logic
        renderer.startFrameRender();
        renderer.processTerrains(terrains);
        renderer.processEntities(entities);
        renderer.processPlayer(player);
        renderer.processSkyBox(skyBox);
        renderer.processGUIs(self.guis);
        
        renderer.render(light);
        renderer.endFrameRender();
        
        // Logs frames/s
        /*NSDate* endDate = [NSDate date];
        
        NSTimeInterval secondsBetween = [endDate timeIntervalSinceDate:startDate];
        //printf("%f Frames/s\n", (1.0f / secondsBetween));*/
    }
    
    @IBAction func leftPressed(sender: AnyObject) {
        GamePad.setKey(GamePadKey.left, clicked: true)
    }
    
    
    @IBAction func rightPressed(sender: AnyObject) {
        GamePad.setKey(GamePadKey.right, clicked: true)
    }
    
    @IBAction func downPressed(sender: AnyObject) {
        GamePad.setKey(GamePadKey.down, clicked: true)
    }
    
    @IBAction func upPressed(sender: AnyObject) {
        GamePad.setKey(GamePadKey.up, clicked: true)
    }
    
    deinit {
    }
    
    
    
}
