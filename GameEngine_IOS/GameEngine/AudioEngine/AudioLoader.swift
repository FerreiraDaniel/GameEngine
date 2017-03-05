import Foundation
import OpenAL
import AudioToolbox

/// Loader of audio elements
open class AudioLoader {
    
    /// Decoder for audio files
    fileprivate var audioDecoder : AudioDecoder;

    
    /// Initiator of the loader
    public init() {
        self.audioDecoder = AudioDecoder()
    }
    
    /// Generate one audio source
    ///
    /// - Returns: If possible one audio source with everything set
    fileprivate func genAudioSource() -> AudioSource! {
        var sourceId : ALuint = 0;
        
        alGenSources(1, &sourceId)
        let error = alGetError()
        if(error == AL_NO_ERROR)
        {
            return AudioSource(sourceId: sourceId)
        } else
        {
            return nil
        }
    }
    
    /// Generates a list of sources
    ///
    /// - Parameter count: Number of audio sources to generate
    /// - Returns: A list of the audio sources generated
    open func genSources(count : Int) -> [AudioSource] {
        var sourceLst : [AudioSource] = [AudioSource]();
        
        for _ in 0 ..< count {
            let audioSource : AudioSource! = genAudioSource();
            if audioSource != nil {
                sourceLst.append(audioSource)
            }
        }
        
        return sourceLst
    }
    
    /// Load one audio file in a buffer
    ///
    /// - Parameter fileName: The file name of the file to load
    /// - Returns: The identifier of the buffer return by openAL
    open func load(_ fileName : String) -> AudioBuffer? {
        var audioBuffer : AudioBuffer! = nil;
        
        var bufferId : ALuint = 0
        
        alGenBuffers(1, &bufferId)
        
        
        //Was not possible to one buffer return AL false
        if (alGetError() != AL_NO_ERROR) {
            return nil;
        }
        
        let filePath : String! = Bundle.main.path(forResource: fileName, ofType: "aac")
        if(filePath == nil) {
            print("Impossible to get the patch to audio \(fileName) ");
            return nil;
        }
        
        
        let fileURL : URL = URL(fileURLWithPath: filePath)
        
        
        let audioFile = self.audioDecoder.getData(fileURL)
        
        if let aFile = audioFile
        {
            //data = MyGetOpenALAudioData(fileURL, &size, &format, &freq)
            
            let format : ALenum = (aFile.channels > 1) ? AL_FORMAT_STEREO16 : AL_FORMAT_MONO16
            
            
            alBufferData(bufferId, format, aFile.data!, aFile.dataSize, ALsizei(aFile.rate))
            self.audioDecoder.clean(data: audioFile);
            
            audioBuffer = AudioBuffer(ALint(bufferId));
            
            return audioBuffer;
        } else
        {
            print("Impossible to read the file \(fileURL)")
            return nil;
        }
    }
}
