import Foundation


/// Play all the different sounds used by the audio engine
open class MasterPlayer {
    

    /// Reference to the render of the entities
    fileprivate var entityPlayer : EntityPlayer
    

    /// Constructor of the master player
    ///
    /// - Parameter sourcesAvailable: Pool of sources to be used by the players
    public init(sourcesAvailable : [AudioSource]) {
        self.entityPlayer = EntityPlayer(sourcesAvailable: sourcesAvailable);
    }
    

    /// Give audio feedback to the user
    ///
    /// - Parameters:
    ///   - library: Set of sounds that the players can use to
    ///   - entities: Entities of the world that are going to be rendered
    ///   - player: The player that is going to listening the audio
    open func play(library : [TAudioEnum: AudioBuffer], entities : [Entity], player : Player) {
        self.entityPlayer.play(library: library, entities: entities, player: player);
    }
    

    /// Clean up because we need to clean up when we finish the program
    open func cleanUp() {
        self.entityPlayer.cleanUp();
    }
}
