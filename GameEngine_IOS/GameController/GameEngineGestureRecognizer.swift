import Foundation
import UIKit

/**
 * Handles the touch in the view
 */
open class GameEngineGestureRecognizer : UIGestureRecognizer {
    
    fileprivate static var _pressed : Bool = false;
    fileprivate static var _glX : Float = 0.0;
    fileprivate static var _glY : Float = 0.0;
    
    
    /**
     * @param xScreenPosition The position in the coordinates of the screen
     * @return the value in gl format between -1.0 and 1
     */
    fileprivate func getGLXPosition(_ xScreenPosition : Float) -> Float {
        let width = Float((view?.bounds.width)!);
        return ((xScreenPosition / width) * 2.0) - 1.0;
    }
    
    /**
     * @param yScreenPosition The position in the coordinates of the screen
     * @return the value in gl format between -1.0 and 1
     */
    fileprivate func getGLYPosition(_ yScreenPosition : Float) -> Float {
        let height = Float((view?.bounds.height)!);
        return 1.0 - ((yScreenPosition / height) * 2.0);
    }
    
    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param touches     All the touches that should be handle
     * @param event       The MotionEvent object containing full information about the event.
     */
    func touchesBegan(_ touches: Set<NSObject>!, withEvent event: UIEvent!) {
        if(touches != nil) {
            for gtouch in touches {
                let touch = gtouch as! UITouch
                let position : CGPoint = touch.location(in: view)
                let positionX = position.x;
                let positionY = position.y;
                GameEngineGestureRecognizer._glX = getGLXPosition(Float(positionX));
                GameEngineGestureRecognizer._glY = getGLYPosition(Float(positionY));
                GameEngineGestureRecognizer._pressed = true;
            }
        }
    }
    
    /**
     * Called when a touch event is ended to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param touches     All the touches that should be handle
     * @param event       The MotionEvent object containing full information about the event.
     */
    func touchesEnded(_ touches: Set<NSObject>!, withEvent event: UIEvent!) {
        GameEngineGestureRecognizer._pressed = false;
    }
    
    /**
     * @return Indicates if the the interface is pressed
     */
    open static func getIsPressed() -> Bool {
        return _pressed;
    }
    
    /**
     * @return The position that is was pressed in the x-coordinate
     */
    open static func getGlX() -> Float {
        return _glX;
    }
    
    /**
     * @return The position that is was pressed in the y-coordinate
     */
    open static func getGlY() -> Float {
        return _glY;
    }
}
