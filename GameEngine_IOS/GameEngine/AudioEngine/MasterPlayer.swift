import Foundation

/**
 * Play all the different sound used by the audio engine
 */
public class MasterPlayer {
    
    /**
     * Reference to the render of the entities
     */
    private var entityPlayer : EntityPlayer;
    
    
    /**
     * Constructor of the master player
     *
     * @param sourcesAvailable	Pool of sources to be used by the players
     */
    public init(sourcesAvailable : [AudioSource]) {
        self.entityPlayer = EntityPlayer(sourcesAvailable: sourcesAvailable);
    }
    
    /**
     *
     * @param audioLibrary	Set of sounds that the players can use to
     * @param entities      Entities of the world that are going to be rendered
     * @param player        The player that is going to be show in the scene
     * give audio feedback to the user
     */
    public func play(audioLibrary : [TAudioEnum: AudioBuffer], entities : [Entity], player : Player) {
        self.entityPlayer.play(audioLibrary, entities: entities, player: player);
    }
    
    /**
     * Clean up because we need to clean up when we finish the program
     */
    public func cleanUp() {
        self.entityPlayer.cleanUp();
    }
}
