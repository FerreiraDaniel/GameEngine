import Foundation

import OpenAL


/// Contains a set of methods that are useful for all the players of the application
open class GenericPlayer {
    
    
    /// Set the properties of the listener of the scene
    ///
    /// - Parameter position: Position of the listener in the 3D world
    internal func positioningListener(position : Vector3f) {
        alListener3f(AL_POSITION, position.x, position.y, position.z);
        // Second sets the velocity of the listener (for the moment idle
        let  listenerVel : Vector3f = Vector3f(x: 0.0, y: 0.0, z: 0.0);
        alListener3f(AL_VELOCITY, listenerVel.x, listenerVel.y, listenerVel.z);
    }
    
    /// Plays one buffer in a certain source
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - buffer: Buffer to play
    internal func play(source : AudioSource, buffer : AudioBuffer) {
        
        let sourceId : ALuint = source.getSourceId();
        let bufferId : ALint = buffer.getBufferId();
        
        self.stop(source: source);
        source.set(buffer: buffer);
        // Associate the buffer with the source (Put the CD into the player)
        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcePlay(sourceId);
    }
    

    /// Pauses the reproduction of a sound in a source if it is playing something
    ///
    /// - Parameter source: The source of audio
    internal func pause(source : AudioSource) {
        let sourceId = source.getSourceId()
        alSourcePause(sourceId);
    }
    

    /// Continues the reproduction of a sound if it is in pause
    ///
    /// - Parameter source: Source of audio
    internal func continuePlaying(source : AudioSource) {
        let sourceId = source.getSourceId()
        alSourcePlay(sourceId);
    }
    
    
    /// Stop the reproduction of a sound if is playing
    ///
    /// - Parameter source: The source of audio
    internal func stop(source : AudioSource) {
        source.set(buffer: nil);
        alSourceStop(source.getSourceId());
    }
    
    
    /// Indicates if the source should play over and over again the associated buffer
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - loop: The flag that indicate if should use looping or not
    internal func setLoop(source : AudioSource, loop : Bool) {
        let sourceId = source.getSourceId()
        alSourcei(sourceId, AL_LOOPING, (loop) ? AL_TRUE : AL_FALSE);
    }
    
    
    /// Reads the state of the source
    ///
    /// - Parameter source: Source of audio
    /// - Returns: The state of the source
    fileprivate func getSourceiState(source : AudioSource) -> ALint {
        var state : ALint = 0
        let sourceId = source.getSourceId()
        alGetSourcei(sourceId, AL_SOURCE_STATE, &state);
        return state;
    }
    
    /// Get if the source is playing or not
    ///
    /// - Parameter source: Source of audio
    /// - Returns: If the source is playing or not
    internal func isPlaying(source : AudioSource) -> Bool {
        return getSourceiState(source: source) == AL_PLAYING;
    }
    
    
    /// Get if the source is paused or not
    ///
    /// - Parameter source: Source of audio
    /// - Returns: If the source is paused or not
    internal func isPaused(source : AudioSource) -> Bool {
        return getSourceiState(source: source) == AL_PAUSED;
    }
    
    
    /// Set the velocity of the source
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - velocity: The velocity to set
    internal func setVelocity(source : AudioSource, velocity : Vector3f) {
        // Set the velocity of the source
        let sourceId = source.getSourceId()
        alSource3f(sourceId, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }
    
    
    /// Set the volume of the source
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - volume: volume of the source between 0.0f and 1.0f
    internal func  setVolume(source : AudioSource, volume : ALfloat) {
        // Set the gain of the source
        let sourceId = source.getSourceId()
        alSourcef(sourceId, AL_GAIN, volume);
    }
    
    
    /// Set the pitch of the source (Speed of play)
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - pitch: The pitch to set in the source
    internal func  setPitch(source : AudioSource, pitch : ALfloat) {
        // Set the pitch of the source
        let sourceId = source.getSourceId()
        alSourcef(sourceId, AL_PITCH, pitch)
    }
    
    
    /// Set the position of the source
    ///
    /// - Parameters:
    ///   - source: Source of audio
    ///   - position: The position to set
    internal func  setPosition(source : AudioSource, position : Vector3f) {
        // Set the position of the source
        let sourceId = source.getSourceId()
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }
}
