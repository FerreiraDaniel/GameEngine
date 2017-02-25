import Foundation

import OpenAL

/**
 * Source represents the object that can play sounds for instance the CD player
 */
open class AudioSource {
    
    /**
     * Identifier of the source in openAL
     */
    fileprivate var sourceId : ALuint;
    
    /**
     * Reference to the buffer currently in the source if any
     */
    fileprivate var buffer : AudioBuffer!;
    
    /**
     * @param sourceId
     *            Identifier of the source in openAL
     */
    public init(sourceId : ALuint) {
        self.sourceId = sourceId;
        self.buffer = nil;
    }
    
    /**
     * @return the sourceId Identifier of the source in openAL
     */
    open func getSourceId() -> ALuint {
    return sourceId;
    }
    
    /**
     * @param sourceId
     *            Identifier of the source in openAL to set
     */
    open func setSourceId(_ sourceId : ALuint) {
        self.sourceId = sourceId;
    }
    
    /**
     * @return the buffer currently associated with source
     */
    open func getBuffer() -> AudioBuffer! {
        return self.buffer;
    }
    
    /**
     * @param buffer the buffer to set in the source
     */
    open func setBuffer(_ buffer : AudioBuffer!) {
        self.buffer = buffer;
    }
    
    
    
}
