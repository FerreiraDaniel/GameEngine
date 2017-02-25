import Foundation

import OpenAL


/// Audio buffer to get played
public class AudioBuffer : NSObject {
    
    
    ///Identifier of the buffer to play in openAL
    private var bufferId : ALint;
    
    
    /// Initializer of the audio buffer
    ///
    /// - Parameter bufferId: Identifier of the buffer to play in openAL
    public init(_ bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
    
    /// Get the identifier of the associated buffer
    ///
    /// - Returns: The Identifier of the buffer to play in openAL
    public func getBufferId() -> ALint {
        return bufferId;
    }
    
    
    /// Identifier of the buffer to play in openAL
    ///
    /// - Parameter bufferId: The identifier of the buffer
    public func setBufferId(bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
}
