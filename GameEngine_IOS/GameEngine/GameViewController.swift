import Foundation

import UIKit
import GLKit

//A view controller that manages an OpenGL ES rendering loop
open class GameViewController: GLKViewController {
    
    
    /// Number of audio sources available
    fileprivate let POOL_SOURCES_SIZE : Int = 32;
    
    /// The master render is going put all the elements together in order to render the scene
    fileprivate var renderer : MasterRender? = nil;
    
    
    /// Array of entities to render
    fileprivate var entities : Array<Entity> = [];
    
    
    /// Array of terrains to render
    fileprivate var terrains : Array<Terrain> = [];
    
    
    /// Array of GUIs to render
    fileprivate var guis : Array<GuiTexture>? = [];
    
    
    /// Position of the light in scene
    fileprivate var light : Light! = nil;
    
    
    /// SkyBox of the 3D world
    fileprivate var skyBox : SkyBox! = nil;
    
    
    /// The player that is going to be show in the scene
    fileprivate var player : Player! = nil;
    
    
    /// Dictionary of sounds supported by the game
    fileprivate var audioLibrary : [TAudioEnum :  AudioBuffer]! = nil;
    
    
    /// Reference to the player of sounds of the game
    fileprivate var masterPlayer : MasterPlayer! = nil;
    
    /// Manages an OpenGL ES rendering context
    /// The state information, commands, and resources needed to draw using OpenGL ES
    fileprivate var context:  EAGLContext! = nil;
    
    /// Method should set up your context and any drawable properties and can perform other resource allocation and initialization
    open override func viewDidLoad() {
        super.viewDidLoad()
        
        self.context = EAGLContext(api: .openGLES2)
        
        if !(self.context != nil) {
            print("Failed to create ES context")
        }
        
        let view = self.view as! GLKView
        view.context = self.context!
        view.drawableDepthFormat = GLKViewDrawableDepthFormat.format24
        
        //Handle events registration
        let gestureRecognizer = GameEngineGestureRecognizer()
        view.addGestureRecognizer(gestureRecognizer)
        
        self.setupAudioEngine()
        self.setupGL()
        
        
        // Initializes the main variables responsible to render the 3D world
        let loader : Loader = Loader()
        
        
        // Prepares the terrains that is going to render
        self.terrains = WorldTerrainsGenerator.getTerrains(loader);
        
        // Prepares the entities that is going to be render
        self.entities = WorldEntitiesGenerator.getEntities(loader, terrain: terrains[0]);
        
        
        // Load the lights that is going to render
        self.light = WorldEntitiesGenerator.getLight();
        
        // Prepares the guis that is going to render
        self.guis = WorldGUIsGenerator.getGUIs(loader);
        
        // Load the sky box that is going to render
        self.skyBox = WorldSkyBoxGenerator.getSky(loader);
        
        //Prepares the player that is going to be used in the scene
        self.player = WorldPlayersGenerator.getPlayer(loader);
    }
    
    
    
    
    /// Sent to the view controller when the app receives a memory warning
    open override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
        if self.isViewLoaded && (self.view.window != nil) {
            self.view = nil
            
            self.tearDownGL()
            AudioManager.teardownOpenAL()
            
            if EAGLContext.current() === self.context {
                EAGLContext.setCurrent(nil)
            }
            self.context = nil
        }
    }
    
    
    /// Setup everything required to use the open GL elements
    fileprivate func setupGL() {
        EAGLContext.setCurrent(self.context);
        
        self.renderer =  MasterRender(width: Int(self.view.bounds.size.width), height: Int(self.view.bounds.size.height))
    }
    
    ///Setup all the elements to reproduce audio
    fileprivate func setupAudioEngine() {
        let audioIniatialized = AudioManager.initOpenAL()
        
        if audioIniatialized
        {
            /* Initiailizes the main variable responsible to the audio of the 3D world*/
            let audioLoader = AudioLoader();
            
            /* Prepares the sounds to be used by the engine*/
            self.audioLibrary = WorldAudioGenerator.getBuffers(audioLoader);
            
            /*Sounds player*/
            let sourceLst : [AudioSource] = audioLoader.genSources(count: POOL_SOURCES_SIZE);
            self.masterPlayer = MasterPlayer(sourcesAvailable: sourceLst);
        }
    }
    
    
    fileprivate func tearDownGL() {
        EAGLContext.setCurrent(self.context);
        self.renderer = nil;
    }
    
    // Makes all the necessary calls to update the scene
    fileprivate func renderFrame() {
        // game logic
        if(renderer == nil)
        {
            print("error renderFrame: renderer should not be null")
        } else
        {
            renderer!.startFrameRender();
            renderer!.processTerrains(terrains);
            renderer!.processEntities(entities);
            renderer!.processPlayer(player);
            renderer!.processSkyBox(skyBox);
            
            if(self.guis != nil)
            {
                renderer!.processGUIs(self.guis!);
                
            }
            
            renderer!.render(light);
            renderer!.endFrameRender();
        }
    }
    
    
    /// Calls everything necessary to play the sounds of the game
    fileprivate func playAudio() {
        masterPlayer.play(library: self.audioLibrary, entities: self.entities, player: self.player)
    }
    
    /// Draw the entities of the scene
    open func update() {
        renderFrame()
        playAudio()
    }
    
    /// A deinitializer is called immediately before a class instance is deallocated
    deinit {
    }
}
