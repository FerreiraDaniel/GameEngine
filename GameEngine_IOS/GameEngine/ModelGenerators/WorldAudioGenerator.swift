

import Foundation

/**
 * Generates the sounds that is going to be used by the audio engine of the game
 */
public class WorldAudioGenerator {
        
    /**
     * Creates one dictionary with buffers to the audio engine
     */
    public static func getBuffers(loader : Loader) -> [TAudioEnum : AudioBuffer] {
        
        var audioLibrary : [TAudioEnum : AudioBuffer] = [TAudioEnum :  AudioBuffer]();
        for tAudio in TAudioEnum.allValues {
            let fileName : String = tAudio.rawValue
            let audioBuffer : AudioBuffer! = loader.loadSound(fileName);
            if(audioBuffer != nil) {
                audioLibrary[tAudio] = audioBuffer
            }
            
        }
        
        return audioLibrary;
    }
    
}
