import Foundation

import OpenAL

/**
 * Class responsible for handling the creation, setup and close of audio
 */
public class AudioManager {
    
    private static var context: COpaquePointer = nil
    private static var device: COpaquePointer = nil
    
    /**
     * Before using any openAL methods we need to initialize openAL
     *
     * @return False -> One or more errors occurred True -> Everything was all
     *         right
     */
    public static func initOpenAL() -> Bool {
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
            context = alcCreateContext(device, nil)
            if context == nil
            {
                return false;
            } else {
                // Make the new context the Current OpenAL Context
                alcMakeContextCurrent(context)
                
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
    
    /**
     * Releases the resources used by openAL
     */
    public static func teardownOpenAL() {
        
        //Release context
        if(context != nil) {
            alcDestroyContext(context)
            context = nil
        }
        //Close device
        if(device != nil) {
            alcCloseDevice(device)
            device = nil
        }
    }
}
