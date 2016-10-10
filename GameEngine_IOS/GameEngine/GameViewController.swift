import Foundation

import UIKit
import GLKit


public class GameViewController: GLKViewController {
    
    private var renderer : MasterRender! = nil;
    private var entities : Array<Entity> = [];
    private var terrains : Array<Terrain> = [];
    private var light : Light! = nil;
    private var skyBox : SkyBox! = nil;
    
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
        
        self.setupGL()
        
        /* Initializes the main variables responsible to render the 3D world */
        let loader : Loader = Loader()
        
        
        /* Prepares the entities that is going to be render */
        self.entities = WorldEntitiesGenerator.getEntities(loader);
        
        /* Prepares the terrains that is going to render */
        self.terrains = WorldTerrainsGenerator.getTerrains(loader);
        
        /* Load the lights that is going to render*/
        self.light = WorldEntitiesGenerator.getLight();
        
        /* Load the sky box that is going to render*/
        self.skyBox = WorldSkyBoxGenerator.getSky(loader);

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
        renderer.processTerrains(terrains);
        renderer.processEntities(entities);
        renderer.processSkyBox(skyBox);
        
        renderer.render(light);
        
        // Logs frames/s
        /*NSDate* endDate = [NSDate date];
        
        NSTimeInterval secondsBetween = [endDate timeIntervalSinceDate:startDate];
        //printf("%f Frames/s\n", (1.0f / secondsBetween));*/
    }
    
    deinit {
    }
}