import Foundation


/// Data describing the sounds in an audio file
open class AudioData {
    
    /// The data that has been read from the audio file
    open var data : UnsafeMutableRawPointer?;
    
    /// The number of bytes that are available in data pointer
    open var dataSize: Int32
    
    
    /// The sampling rate
    open var rate : Double;
    
    
    /// The number of channels in the sound file
    open var channels : Int;
    
    
    /// Initialializer of the audio file description
    public init() {
        data = nil
        dataSize = 0
        rate = 0
        channels = 0
    }
}
