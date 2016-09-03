#import <Foundation/Foundation.h>

/**
 * Contains the identifiers to the several textures of a terrain
 */
@interface TerrainTexturesPack : NSObject
    
    /**
     * The identifier of the weight map texture
     */
    @property int weightMapTextureId;
    
    /**
     * The identifier of the background texture
     */
    @property  int backgroundTextureId;
    
    /**
     * Identifier of the mud texture
     */
    @property  int mudTextureId;
    
    /**
     * Identifier of the grass texture
     */
    @property  int grassTextureId;
    
    /**
     * Identifier of the path texture
     */
    @property  int pathTextureId;
    
@end
