import Foundation

import OpenAL

/**
 * Source represents the object that can play sounds for instance the CD player
 */
public class AudioSource {
    
    /**
     * Identifier of the source in openAL
     */
    private var sourceId : ALuint;
    
    /**
     * Reference to the buffer currently in the source if any
     */
    private var buffer : AudioBuffer!;
    
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
    public func getSourceId() -> ALuint {
    return sourceId;
    }
    
    /**
     * @param sourceId
     *            Identifier of the source in openAL to set
     */
    public func setSourceId(sourceId : ALuint) {
        self.sourceId = sourceId;
    }
    
    /**
     * @return the buffer currently associated with source
     */
    public func getBuffer() -> AudioBuffer! {
        return self.buffer;
    }
    
    /**
     * @param buffer the buffer to set in the source
     */
    public func setBuffer(buffer : AudioBuffer!) {
        self.buffer = buffer;
    }
    
    
    
}
