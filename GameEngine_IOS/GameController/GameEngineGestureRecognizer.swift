import Foundation
import UIKit


/// Handles the touch in the view
open class GameEngineGestureRecognizer : UIGestureRecognizer {
    
    /// Indicates if there was a press or not
    fileprivate static var pressed : Bool = false;
    /// Indicates where in the x-axle the screen was pressed values between (-1.0 and 1)
    fileprivate static var normalX : Float = 0.0;
    /// Indicates where in the Y-axle the screen was pressed values between (-1.0 and 1)
    fileprivate static var normalY : Float = 0.0;
    
    
    /// Get the value of the screen in X-axle normalized
    ///
    /// - Parameter xScreenPosition: The position in the coordinates of the screen
    /// - Returns: The value in format between -1.0 and 1
    fileprivate func getNormalXPosition(_ xScreenPosition : Float) -> Float {
        let width = Float((view?.bounds.width)!);
        return ((xScreenPosition / width) * 2.0) - 1.0;
    }
    
    
    /// Get the value of the screen in Y-axle normalized
    ///
    /// - Parameter yScreenPosition: The position in the coordinates of the screen
    /// - Returns: The value in format between -1.0 and 1
    fileprivate func getNormalYPosition(_ yScreenPosition : Float) -> Float {
        let height = Float((view?.bounds.height)!);
        return 1.0 - ((yScreenPosition / height) * 2.0);
    }
    
    
    /// Called when a touch event is dispatched to a view. This allows listeners to
    /// get a chance to respond before the target view.
    ///
    /// - Parameters:
    ///   - touches: All the touches that should be handle
    ///   - event: The MotionEvent object containing full information about the event.
    func touchesBegan(_ touches: Set<NSObject>!, withEvent event: UIEvent!) {
        if(touches != nil) {
            for gtouch in touches {
                let touch = gtouch as! UITouch
                let position : CGPoint = touch.location(in: view)
                let positionX = Float(position.x);
                let positionY = Float(position.y);
                GameEngineGestureRecognizer.normalX = getNormalXPosition(positionX);
                GameEngineGestureRecognizer.normalY = getNormalYPosition(positionY);
                GameEngineGestureRecognizer.pressed = true;
            }
        }
    }
    
    
    /// Called when a touch event is ended to a view. This allows listeners to
    /// get a chance to respond before the target view.
    ///
    /// - Parameters:
    ///   - touches: All the touches that should be handle
    ///   - event: The MotionEvent object containing full information about the event.
    func touchesEnded(_ touches: Set<NSObject>!, withEvent event: UIEvent!) {
        GameEngineGestureRecognizer.pressed = false;
    }
    
    /// Indicates if the the interface is pressed
    ///
    /// - Returns: Bool that indicates if there was a screen press or not
    open static func isPressed() -> Bool {
        return pressed;
    }
    
    
    /// The position that is was pressed in the x-coordinate
    ///
    /// - Returns: The position that is was pressed in the x-coordinate
    open static func getNormalX() -> Float {
        return normalX;
    }
    
    
    /// The position that is was pressed in the x-coordinate
    ///
    /// - Returns: The position that is was pressed in the y-coordinate
    open static func getNormalY() -> Float {
        return normalY;
    }
}
