public class WorldSkyBoxGenerator : NSObject {


    public static func getSky(loader : Loader) -> SkyBox {
        let skyBoxShape : IShape = SkyBoxShape()
        let rawModel : RawModel = loader.load3DPositionsToVAO(skyBoxShape.getVertices(), positionsLength: Int(skyBoxShape.countVertices()));
        
        let TEXTURE_FILES : Array<String> = [
            "sky_right", "sky_left", "sky_top", "sky_bottom", "sky_back", "sky_front"
        ];
        
        let textureId = loader.loadTCubeMap(TEXTURE_FILES);
        
        return SkyBox(Int32(textureId), rawModel)
}

}
