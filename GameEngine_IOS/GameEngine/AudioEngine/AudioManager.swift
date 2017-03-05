import Foundation

import OpenAL

/// Responsible for handling the creation, setup and close of audio
open class AudioManager {
    
    fileprivate static var context: OpaquePointer? = nil
    fileprivate static var device: OpaquePointer? = nil
    
    /// Before using any openAL methods we need to initialize openAL
    /// - Returns:  False = One or more errors occurred
    ///             True = Everything was all
    open static func initOpenAL() -> Bool {
        var error: ALenum = AL_NO_ERROR
        
        
        // Create a new OpenAL Device
        // Pass NULL to specify the system’s default output device
        device = alcOpenDevice(nil)
        if device == nil
        {
            return false
        }else {
            // Create a new OpenAL Context
            // The new context will render to the OpenAL Device just created
            context = alcCreateContext(device!, nil)
            if context == nil
            {
                return false
            } else {
                // Make the new context the Current OpenAL Context
                alcMakeContextCurrent(context!)
                
                error = alGetError()
                
                if error == AL_NO_ERROR
                {
                    return true
                } else {
                    NSLog("Error Generating Buffers: \(error)")
                    return false
                }
            }
        }
        
    }
    
    
    /// Releases the resources used by openAL
    open static func teardownOpenAL() {
        
        //Release context
        if(context != nil) {
            alcDestroyContext(context!)
            context = nil
        }
        //Close device
        if(device != nil) {
            alcCloseDevice(device!)
            device = nil
        }
    }
}
