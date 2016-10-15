import Foundation

/**
* Represents one raw model of one entity
*/
public class RawModel {
    
    /**
    * Buffer of indices of the model
    */
    var indicesData : UnsafeMutablePointer<ushort>
    
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
        
        if(indicesData == nil)
        {
            self.indicesData = nil;
        } else {
            //Allocate and fill the vertices memory
            self.indicesData = UnsafeMutablePointer<ushort>(calloc(indicesCount, sizeof(ushort)));
            
            //Copy vertices one by one
            for(var i = 0;i < indicesCount;i++) {
                self.indicesData[i] = indicesData[i];
            }
        }
        
    }
}