import Foundation

public class RawModel : RawModel1 {
    
    
    public init!(vaoId: Int32, indicesData: UnsafeMutablePointer<UInt16>, indicesCount: Int32) {
        super.init(vaoId, indicesData, indicesCount);
    }
}