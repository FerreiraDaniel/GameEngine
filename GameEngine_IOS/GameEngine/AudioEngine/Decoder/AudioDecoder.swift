import Foundation
import AudioToolbox

/// Decodes a file in a supported format
open class AudioDecoder {
    
    fileprivate let GET_DATA_NAME = "getData"
    
    /// Inicializer of the audio decoder
    public init() {
    }
    
    
    /// Get the data out of an audio file
    ///
    /// - Parameter inFileURL: The input url from which to read the audio file
    /// - Returns: The data describing the audio thats been read
    open func getData(_ inFileURL: URL) -> AudioData? {
        var err: OSStatus = noErr
        var fileLengthInFrames: Int64 = 0
        var fileFormat: AudioStreamBasicDescription = AudioStreamBasicDescription()
        var propertySize: UInt32 = UInt32(MemoryLayout.stride(ofValue: fileFormat))
        var extRef: ExtAudioFileRef? = nil
        var data: UnsafeMutableRawPointer? = nil
        var outputFormat: AudioStreamBasicDescription = AudioStreamBasicDescription()
        
        // Open a file with ExtAudioFileOpen()
        err = ExtAudioFileOpenURL(inFileURL as CFURL, &extRef)
        if err != noErr
        {
            print("\(GET_DATA_NAME): ExtAudioFileOpenURL FAILED, Error = \(err)");
            return nil
        }
        
        // Get the audio data format
        err = ExtAudioFileGetProperty(extRef!, kExtAudioFileProperty_FileDataFormat, &propertySize, &fileFormat)
        if err != noErr
        {
            print("\(GET_DATA_NAME): ExtAudioFileGetProperty(kExtAudioFileProperty_FileDataFormat) FAILED, Error = \(err)");
            return nil
        }
        if fileFormat.mChannelsPerFrame > 2 {
            print("\(GET_DATA_NAME): Unsupported Format, channel count is greater than stereo");
            return nil
        }
        
        // Set the client format to 16 bit signed integer (native-endian) data
        // Maintain the channel count and sample rate of the original source format
        outputFormat.mSampleRate = fileFormat.mSampleRate
        outputFormat.mChannelsPerFrame = fileFormat.mChannelsPerFrame
        
        outputFormat.mFormatID = kAudioFormatLinearPCM
        outputFormat.mBytesPerPacket = 2 * outputFormat.mChannelsPerFrame
        outputFormat.mFramesPerPacket = 1
        outputFormat.mBytesPerFrame = 2 * outputFormat.mChannelsPerFrame
        outputFormat.mBitsPerChannel = 16
        outputFormat.mFormatFlags = kAudioFormatFlagsNativeEndian | kAudioFormatFlagIsPacked | kAudioFormatFlagIsSignedInteger
        
        // Set the desired client (output) data format
        err = ExtAudioFileSetProperty(extRef!, kExtAudioFileProperty_ClientDataFormat, UInt32(MemoryLayout.stride(ofValue: outputFormat)), &outputFormat)
        if err != noErr
        {
            print("\(GET_DATA_NAME): ExtAudioFileSetProperty(kExtAudioFileProperty_ClientDataFormat) FAILED, Error = \(err)");
            return nil
        }
        
        // Get the total frame count
        propertySize = UInt32(MemoryLayout.stride(ofValue: fileLengthInFrames))
        err = ExtAudioFileGetProperty(extRef!, kExtAudioFileProperty_FileLengthFrames, &propertySize, &fileLengthInFrames)
        if err != noErr
        {
            print("\(GET_DATA_NAME): ExtAudioFileGetProperty(kExtAudioFileProperty_FileLengthFrames) FAILED, Error = \(err)");
            return nil
        }
        
        // Read all the data into memory
        let dataSize = UInt32(fileLengthInFrames) * outputFormat.mBytesPerFrame
        data = UnsafeMutableRawPointer.allocate(bytes: Int(dataSize), alignedTo: 1)
        if data == nil
        {
            return nil
        }
        else {
            var theDataBuffer: AudioBufferList = AudioBufferList()
            theDataBuffer.mNumberBuffers = 1
            theDataBuffer.mBuffers.mDataByteSize = dataSize
            theDataBuffer.mBuffers.mNumberChannels = outputFormat.mChannelsPerFrame
            theDataBuffer.mBuffers.mData = data!
            
            // Read the data into an AudioBufferList
            var ioNumberFrames: UInt32 = UInt32(fileLengthInFrames)
            err = ExtAudioFileRead(extRef!, &ioNumberFrames, &theDataBuffer)
            if err == noErr {
                // success
                let audioData = AudioData()
                audioData.channels = Int(outputFormat.mChannelsPerFrame)
                audioData.data = theDataBuffer.mBuffers.mData
                audioData.dataSize = Int32(dataSize)
                audioData.rate = outputFormat.mSampleRate
                return audioData
            } else {
                // failure
                if(data != nil)
                {
                    data!.deallocate(bytes: Int(dataSize), alignedTo: 1)
                }
                data = nil // make sure to return NULL
                print("\(GET_DATA_NAME): ExtAudioFileRead FAILED, Error = \(err)");
                return nil;
            }
        }
    }
    
    
    /// Dispose the ExtAudioFileRef, it is no longer needed
    ///
    /// - Parameter data: The the audio data to be release
    open func clean(data : AudioData?) {
        if(data != nil) {
            if data!.data != nil {
                data!.data!.deallocate(bytes: Int(data!.dataSize), alignedTo: 1)
            }
        }
    }
    
}
