import Foundation

import OpenAL


/// Audio buffer to get played
open class AudioBuffer : NSObject {
    
    
    ///Identifier of the buffer to play in openAL
    fileprivate var bufferId : ALint;
    
    
    /// Initializer of the audio buffer
    ///
    /// - Parameter bufferId: Identifier of the buffer to play in openAL
    public init(_ bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
    
    /// Get the identifier of the associated buffer
    ///
    /// - Returns: The Identifier of the buffer to play in openAL
    open func getBufferId() -> ALint {
        return bufferId;
    }
    
    
    /// Identifier of the buffer to play in openAL
    ///
    /// - Parameter bufferId: The identifier of the buffer
    open func setBufferId(_ bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
}
