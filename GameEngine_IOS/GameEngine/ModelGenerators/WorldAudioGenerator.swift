import Foundation


/// Generates the sounds that is going to be used by the audio engine of the game
open class WorldAudioGenerator {
    
    /// Creates one dictionary with buffers to the audio engine
    ///
    /// - Parameter loader: The loader of the sounds
    /// - Returns: An dictionary with elements that were possible to read
    open static func getBuffers(_ loader : AudioLoader) -> [TAudioEnum : AudioBuffer] {
        
        var audioLibrary : [TAudioEnum : AudioBuffer] = [TAudioEnum :  AudioBuffer]();
        for tAudio in TAudioEnum.allValues {
            let fileName : String = tAudio.rawValue
            let audioBuffer : AudioBuffer! = loader.load(fileName);
            if(audioBuffer != nil) {
                audioLibrary[tAudio] = audioBuffer
            }
        }
        
        return audioLibrary;
    }
    
}
