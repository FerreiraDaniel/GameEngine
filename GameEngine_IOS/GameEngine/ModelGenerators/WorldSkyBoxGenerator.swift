import Foundation

/**
* Responsible for creating the sky of the 3D world
*/
public class WorldSkyBoxGenerator : NSObject {
    
    /**
    * The sky of the 3D scene
    *
    * @param loader object that is going to read the textures of the sky
    *
    * @return the reference to the sky box created
    */
    public static func getSky(loader : Loader) -> SkyBox {
        let skyBoxShape : IShape = SkyBoxShape()
        let rawModel : RawModel = loader.load3DPositionsToVAO(skyBoxShape.getVertices(), positionsLength: Int(skyBoxShape.countVertices()));
        
        let TEXTURE_FILES : Array<String> = [
            "sky_right", "sky_left", "sky_top", "sky_bottom", "sky_back", "sky_front"
        ];
        
        let textureId = loader.loadTCubeMap(TEXTURE_FILES);
        
        return SkyBox(textureId: textureId, model: rawModel)
    }
    
}
