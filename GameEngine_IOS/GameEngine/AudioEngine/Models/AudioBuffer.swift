import Foundation

import OpenAL

/**
 * Audio buffer to get played
 */
public class AudioBuffer : NSObject {
    
    /**
     * Identifier of the buffer to play in openAL
     */
    private var bufferId : ALint;
    
    /**
     * @param bufferId
     *            Identifier of the buffer to play in openAL
     */
    public init(bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
    /**
     * @return the bufferId Identifier of the buffer to play in openAL
     */
    public func getBufferId() -> ALint {
        return bufferId;
    }
    
    /**
     * @param bufferId
     *            Identifier of the buffer to play in openAL
     */
    public func setBufferId(bufferId : ALint) {
        self.bufferId = bufferId;
    }
    
}
