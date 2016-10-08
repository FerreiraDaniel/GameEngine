import Foundation

public class RawModel : RawModel1 {
    
    /**
    * Identifier of the vertex array object of the raw model
    */
    var vaoId : Int
    
    /**
    * Number of indices of the raw model
    */
    var indicesCount : Int
    
    /**
    Initiator of the raw model
    *
    * @param aVaoId the
    *            vaoId assigned by openGL
    * @param aIndicesData
    Data of the indices
    * @param aIndicesCount the
    *            number of vertex
    */
    public init!(vaoId: Int, indicesData: UnsafeMutablePointer<UInt16>, indicesCount: Int) {
        self.vaoId = vaoId;
        self.indicesCount = indicesCount;
        super.init(indicesData, Int32(indicesCount));
    }
}