import Foundation

import UIKit
import GLKit

public class GameEngineView : UIView {
    
    private var renderer : MasterRender! = nil;
    private var entities : Array<Entity> = [];
    private var terrains : Array<Terrain> = [];
    private var light : Light! = nil;
    private var skyBox : SkyBox! = nil;
    
    private var context:  EAGLContext! = nil;
    
    /* Class Methods
    ------------------------------------------*/
    

    /* Lifecycle
    ------------------------------------------*/
    
    required public init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        self.context = EAGLContext(API: .OpenGLES2)
        
        if !(self.context != nil) {
            print("Failed to create ES context")
        }        
    }
}