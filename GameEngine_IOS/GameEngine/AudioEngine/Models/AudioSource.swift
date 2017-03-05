import Foundation

import OpenAL


/// Source represents the object that can play sounds for instance the CD player
open class AudioSource {
    

    /// Identifier of the source in openAL
    fileprivate var sourceId : ALuint;
    
    /// Reference to the buffer currently in the source if any
    fileprivate var buffer : AudioBuffer!;
    
    /// Initializer of the audio source
    ///
    /// - Parameter sourceId: Identifier of the source in openAL
    public init(sourceId : ALuint) {
        self.sourceId = sourceId;
        self.buffer = nil;
    }
    
    /// Getter of the source identifier
    ///
    /// - Returns: The sourceId Identifier of the source in openAL
    open func getSourceId() -> ALuint {
    return sourceId;
    }
    
    
    /// Getter of the associated buffer
    ///
    /// - Returns: The buffer currently associated with source
    open func getBuffer() -> AudioBuffer! {
        return self.buffer;
    }
    

    /// Associated a buffer to the source
    ///
    /// - Parameter buffer: <#buffer description#>
    open func set(buffer : AudioBuffer!) {
        self.buffer = buffer;
    }
    
    
    
}
