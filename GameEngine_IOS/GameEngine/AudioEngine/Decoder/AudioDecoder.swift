import Foundation
import AudioToolbox

public class AudioDecoder {
    
    public init() {
    }
    
    /**
     * Get the data out of an audio file
     *
     * @param inFileURL The input url from which to read the audio file
     * @return The data describing the audio thats been read
     */
    public func getData(inFileURL: NSURL) -> AudioData? {
        var err: OSStatus = noErr
        var theFileLengthInFrames: Int64 = 0
        var theFileFormat: AudioStreamBasicDescription = AudioStreamBasicDescription()
        var thePropertySize: UInt32 = UInt32(strideofValue(theFileFormat))
        var extRef: ExtAudioFileRef = nil
        var theData: UnsafeMutablePointer<Void>? = nil
        var theOutputFormat: AudioStreamBasicDescription = AudioStreamBasicDescription()
        
        // Open a file with ExtAudioFileOpen()
        err = ExtAudioFileOpenURL(inFileURL, &extRef)
        if err != noErr
        {
            print("MyGetOpenALAudioData: ExtAudioFileOpenURL FAILED, Error = \(err)");
            return nil
        }
        
        // Get the audio data format
        err = ExtAudioFileGetProperty(extRef, kExtAudioFileProperty_FileDataFormat, &thePropertySize, &theFileFormat)
        if err != noErr
        {
            print("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileDataFormat) FAILED, Error = \(err)");
            return nil
        }
        if theFileFormat.mChannelsPerFrame > 2 {
            print("MyGetOpenALAudioData - Unsupported Format, channel count is greater than stereo");
            return nil
        }
        
        // Set the client format to 16 bit signed integer (native-endian) data
        // Maintain the channel count and sample rate of the original source format
        theOutputFormat.mSampleRate = theFileFormat.mSampleRate
        theOutputFormat.mChannelsPerFrame = theFileFormat.mChannelsPerFrame
        
        theOutputFormat.mFormatID = kAudioFormatLinearPCM
        theOutputFormat.mBytesPerPacket = 2 * theOutputFormat.mChannelsPerFrame
        theOutputFormat.mFramesPerPacket = 1
        theOutputFormat.mBytesPerFrame = 2 * theOutputFormat.mChannelsPerFrame
        theOutputFormat.mBitsPerChannel = 16
        theOutputFormat.mFormatFlags = kAudioFormatFlagsNativeEndian | kAudioFormatFlagIsPacked | kAudioFormatFlagIsSignedInteger
        
        // Set the desired client (output) data format
        err = ExtAudioFileSetProperty(extRef, kExtAudioFileProperty_ClientDataFormat, UInt32(strideofValue(theOutputFormat)), &theOutputFormat)
        if err != noErr
        {
            print("MyGetOpenALAudioData: ExtAudioFileSetProperty(kExtAudioFileProperty_ClientDataFormat) FAILED, Error = \(err)");
            return nil
        }
        
        // Get the total frame count
        thePropertySize = UInt32(strideofValue(theFileLengthInFrames))
        err = ExtAudioFileGetProperty(extRef, kExtAudioFileProperty_FileLengthFrames, &thePropertySize, &theFileLengthInFrames)
        if err != noErr
        {
            print("MyGetOpenALAudioData: ExtAudioFileGetProperty(kExtAudioFileProperty_FileLengthFrames) FAILED, Error = \(err)");
            return nil
        }
        
        // Read all the data into memory
        let dataSize = UInt32(theFileLengthInFrames) * theOutputFormat.mBytesPerFrame
        theData = UnsafeMutablePointer.alloc(Int(dataSize))
        if theData == nil
        {
            return nil
        }
        else {
            var theDataBuffer: AudioBufferList = AudioBufferList()
            theDataBuffer.mNumberBuffers = 1
            theDataBuffer.mBuffers.mDataByteSize = dataSize
            theDataBuffer.mBuffers.mNumberChannels = theOutputFormat.mChannelsPerFrame
            theDataBuffer.mBuffers.mData = theData!
            
            // Read the data into an AudioBufferList
            var ioNumberFrames: UInt32 = UInt32(theFileLengthInFrames)
            err = ExtAudioFileRead(extRef, &ioNumberFrames, &theDataBuffer)
            if err == noErr {
                // success
                let audioData = AudioData()
                audioData.channels = Int(theOutputFormat.mChannelsPerFrame)
                audioData.data = theDataBuffer.mBuffers.mData
                audioData.dataSize = Int32(dataSize)
                audioData.rate = theOutputFormat.mSampleRate
                return audioData
            } else {
                // failure
                if(theData != nil)
                {
                    theData!.dealloc(Int(dataSize))
                }
                theData = nil // make sure to return NULL
                print("MyGetOpenALAudioData: ExtAudioFileRead FAILED, Error = \(err)");
                return nil;
            }
        }
    }
    
    
    /**
     *
     *  Dispose the ExtAudioFileRef, it is no longer needed
     */
    public func cleanUp(audioData : AudioData?) {
        if(audioData != nil) {
            if audioData!.data != nil {
                audioData!.data!.dealloc(Int(audioData!.dataSize))
            }
        }
    }
    
}
