import Foundation

/**
 * Used handle the entering of data from the user
 **/
open class GamePad {
    
    fileprivate static var keysAreDown : Array<Bool> = Array<Bool>(repeating: false, count: GamePadKey.numOfKeys.rawValue)
    
    /**
     *
     * @param selectedKey key to check
     *
     * @return  Flag that indicates if the key was pressed or not
     */
    open static func isKeyDown(_ selectedKey : GamePadKey) -> Bool {
        let value = keysAreDown[selectedKey.rawValue];
        GamePad.setKey(selectedKey, clicked: false);
        return value;
    }
    
    /**
     * Called when the user presses on of the virtual buttons
     *
     * @param selectedKey the key that the user have clicked
     * @param clicked       Flag that indicates if is to indicate clicked or not
     */
    open static func setKey(_ selectedKey : GamePadKey, clicked : Bool) {
        keysAreDown[selectedKey.rawValue] = clicked;
    }
}
