import Foundation

/**
 * Enumeration for all the audio supported by the game
 */
public enum TAudioEnum : String {
    
    case
    
    /**
     * Used when the player jumps
     */
    bounce = "bounce",
    
    /**
     * Used when the player goes against one tree
     */
    breakingWood = "breaking wood",
    
    /**
     * Used to represent on bird singing in the a tree
     */
    falcon = "falcon",
    
    /**
     * Used to indicate that the player is moving
     */
    footsteps = "footsteps",
    
    /**
     * Used to indicate that the user is in one height level so with more wind
     */
    wind = "wind"
    
    static let allValues = [bounce, breakingWood, falcon, footsteps, wind]
}
