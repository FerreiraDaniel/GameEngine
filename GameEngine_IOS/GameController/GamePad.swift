import Foundation


/// Used handle the entering of data from the user
open class GamePad {
    
    
    /// List of booleans to indicate if the key was pressed or not
    fileprivate static var keysAreDown : Array<Bool> = Array<Bool>(repeating: false, count: GamePadKey.numOfKeys.rawValue)
    
    
    /// Checks if a key was pressed or not
    ///
    /// - Parameter key: Key to check
    /// - Returns: Flag that indicates if the key was pressed or not
    open static func isDown(key : GamePadKey) -> Bool {
        let value = keysAreDown[key.rawValue];
        GamePad.set(key: key, clicked: false);
        return value;
    }
    
    
    /// Called when the user presses on of the virtual buttons
    ///
    /// - Parameters:
    ///   - key: The key that the user have clicked
    ///   - clicked: Flag that indicates if is to indicate clicked or not
    open static func set(key : GamePadKey, clicked : Bool) {
        keysAreDown[key.rawValue] = clicked;
    }
}
