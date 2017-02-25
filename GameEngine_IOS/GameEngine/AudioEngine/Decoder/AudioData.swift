import Foundation


/// Data describing the sounds in an audio file
public class AudioData {
    
    /// The data that has been read from the audio file
    public var data : UnsafeMutablePointer<Void>?;
    
    /// The number of bytes that are available in data pointer
    public var dataSize: Int32
    
    
    /// The sampling rate
    public var rate : Double;
    
    
    /// The number of channels in the sound file
    public var channels : Int;
    
    
    /// Initialializer of the audio file description
    public init() {
        data = nil
        dataSize = 0
        rate = 0
        channels = 0
    }
}
