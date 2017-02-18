import Foundation

import OpenAL


/**
 * Is going to demand the reproduction of audio due to the control of the
 * entities
 */
public class EntityPlayer : GenericPlayer {
    
    /**
     * If the entity is far away from the player more that the threshold is not
     * going to be listened
     */
    private let SOUND_THRESHOLD : Float = 50.0;
    
    /**
     * First if going to make a rough approximation only after is going to check
     * if exists a crash
     */
    private let ROUGH_THRESHOLD : Float = 10.0;
    private let CRASH_THRESHOLD : Float = 2.0;
    
    /**
     * List of sound sources available to the entity player
     *
     */
    private var sourcesAvailable : [AudioSource];
    
    /**
     * The player can always play audio;
     */
    private var playerSource : AudioSource!;
    
    /**
     * Sources assign to the entityes
     */
    private var sourcesAssign : [Entity : AudioSource];
    
    /**
     * Constructor of the entity player
     */
    public init(sourcesAvailable : [AudioSource]) {
        self.sourcesAvailable = sourcesAvailable;
        if ((!self.sourcesAvailable.isEmpty)) {
            playerSource = self.sourcesAvailable.removeLast()
        } else {
            playerSource = nil
        }
        self.sourcesAssign = [Entity : AudioSource]();
    }
    
    /**
     * The the properties of the listener of the scene
     *
     * @param listenerPos
     *            Position of the listener in the 3D world
     */
    private func setListenerData(listenerPos : Vector3f) {
        alListener3f(AL_POSITION, listenerPos.x, listenerPos.y, listenerPos.z);
        // Second sets the velocity of the listener (for the moment idle
        let  listenerVel : Vector3f = Vector3f(x: 0.0, y: 0.0, z: 0.0);
        alListener3f(AL_VELOCITY, listenerVel.x, listenerVel.y, listenerVel.z);
    }
    
    /**
     * Set the listener position and reproduces the sound of the player
     *
     * @param audioLibrary
     *            Set of sounds that can use to provide sound feedback to the
     *            user
     * @param player
     *            The player of the scene and also the listener
     */
    private func playPlayer(audioLibrary : [TAudioEnum: AudioBuffer], player : Player) {
        self.setListenerData(player.position);
        
        // If the player is running plays the sound of the footsteps
        let playerSrcId = self.playerSource.getSourceId();
        if (player.getCurrentSpeed() == 0) {
            if (self.isPlaying(playerSrcId)) {
                self.pause(playerSrcId);
            }
        } else {
            if (self.isPaused(playerSrcId)) {
                self.continuePlaying(playerSrcId);
            } else {
                if (!self.isPlaying(playerSrcId)) {
                    let footStepsBuffer : AudioBuffer = audioLibrary[TAudioEnum.footsteps]!;
                    self.setPitch(playerSrcId, pitch: 2.0);
                    self.setVolume(playerSrcId, volume: 1.0);
                    self.play(playerSource, audioBuffer: footStepsBuffer);
                    self.setLoop(playerSrcId, loop: true);
                }
            }
            self.setPosition(playerSrcId, position: player.position);
        }
    }
    
    /**
     *
     * @param position1
     *            The position one to take in account
     * @param position2
     *            The position two entity to take in account
     * @param threshold
     *            The threshold that is going to use to the match
     *
     * @return False = Not passed in the rough approximation True = Passed in
     *         the rough approximation
     */
    private func roughApproximation(position1 : Vector3f, position2 : Vector3f, threshold : Float) -> Bool {
        let xDiff : Float = abs(position1.x - position2.x);
        let yDiff : Float = abs(position1.y - position2.y);
        let zDiff : Float = abs(position1.z - position2.z);
        
        return (xDiff < threshold) && (yDiff < threshold) && (zDiff < threshold);
    }
    
    /**
     *
     * @param position1
     *            The position one to take in account
     * @param position2
     *            The position two entity to take in account
     *
     *
     * @return The square of the distance between two positions
     */
    private func squareDistance(position1 : Vector3f, position2 : Vector3f) -> Float {
        let xDiff : Float = abs(position1.x - position2.x);
        let yDiff : Float = abs(position1.y - position2.y);
        let zDiff : Float = abs(position1.z - position2.z);
        
        return (xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff);
    }
    
    /**
     *
     * @param pPosition
     *            The position of player to take in account
     * @param ePosition
     *            The position of entity to take in account
     *
     * @return False the player can not listen the entity True the entity can be
     *         listen by the player
     */
    private func passesThreshold(pPosition : Vector3f, ePosition : Vector3f) -> Bool {
        return roughApproximation(pPosition, position2: ePosition, threshold: SOUND_THRESHOLD);
    }
    
    /**
     * Assign audio sources to the entities
     *
     * @param player
     *            The player of the scene and also the listener
     * @param entities
     *            Set of entities that should provide sound feedback
     */
    private func assignSources(player : Player, entities : [Entity]) {
        let pPosition : Vector3f = player.position;
        for entity in entities {
            if (passesThreshold(pPosition, ePosition: entity.position)) {
                // Can not assign nothing
                if (sourcesAvailable.isEmpty) {
                    //Is going to continue searching maybe there is anothers that do not pass the threshold
                    continue;
                }
                // Check is a source was already assign
                if (sourcesAssign[entity] == nil) {
                    let aSource : AudioSource = sourcesAvailable.removeLast();
                    sourcesAssign[entity] = aSource;
                }
            } else {
                // Not passed threshold
                if (sourcesAssign[entity] != nil) {
                    let aSource = sourcesAssign.removeValueForKey(entity)!;
                    self.stop(aSource);
                    self.sourcesAvailable.append(aSource);
                }
            }
        }
    }
    
    /**
     * Check is an entity crashed against the player
     *
     * @param audioLibrary
     *            Set of sounds that can use to provide sound feedback to the
     *            user
     * @param entity
     *            The entity to check
     * @param pPosition
     *            The position of the player that is going to check the crash
     *
     * @return False = No crash was detected True = A crash was detected and the
     *         sound was played
     */
    private func playCrash(audioLibrary : [TAudioEnum : AudioBuffer], entity : Entity, pPosition : Vector3f) -> Bool {
        if (roughApproximation(pPosition, position2: entity.position, threshold: ROUGH_THRESHOLD)
            && (squareDistance(pPosition, position2: entity.position) < CRASH_THRESHOLD)) {
            let audioSource : AudioSource = sourcesAssign[entity]!;
            let sourceId : ALuint = audioSource.getSourceId();
            let tEntity : TEntity = entity.genericEntity.objectType;
            
            switch(tEntity) {
            case .banana_tree:
                break;
            case .fern:
                break;
            case .flower:
                break;
            case .grass:
                break;
            case .marble:
                if (!isPlaying(sourceId) && (audioSource.getBuffer() == nil)) {
                    let breakingWood : AudioBuffer = audioLibrary[TAudioEnum.breakingWood]!;
                    self.setPitch(sourceId, pitch: 1.0);
                    self.setVolume(sourceId, volume: 1.0);
                    self.setPosition(sourceId, position: entity.position);
                    self.play(audioSource, audioBuffer: breakingWood);
                    self.setLoop(sourceId, loop: false);
                }
                break;
            case .player:
                break;
            case .tree:
                let breakingWood : AudioBuffer! = audioLibrary[TAudioEnum.breakingWood];
                if (!isPlaying(sourceId) || ((audioSource.getBuffer() == nil) || (audioSource.getBuffer() != breakingWood))) {
                    self.setPitch(sourceId, pitch: 1.0);
                    self.setVolume(sourceId, volume: 1.0);
                    self.setPosition(sourceId, position: entity.position);
                    self.play(audioSource, audioBuffer: breakingWood);
                    self.setLoop(sourceId, loop: false);
                }
                break;
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Reproduces the sound of the entities
     *
     * @param audioLibrary
     *            Set of sounds that can use to provide sound feedback to the
     *            user
     * @param entities
     *            Set of entities that should provide sound feedback
     * @param player
     *            The player of the scene and also the listener
     */
    private func playEntities(audioLibrary : [TAudioEnum : AudioBuffer], entities : [Entity]!, player : Player) {
        if (entities != nil) {
            let pPosition : Vector3f = player.position;
            
            for entity in entities {
                // Check is has one source
                let audioSource : AudioSource! = sourcesAssign[entity];
                if (audioSource == nil) {
                    continue;
                }
                
                if (!playCrash(audioLibrary, entity: entity, pPosition: pPosition)) {
                    // If not played a crash
                    if (entity.genericEntity.objectType != TEntity.tree) {
                        continue;
                    }
                    
                    // Make birds to play in each tree
                    let breakingWood : AudioBuffer = audioLibrary[TAudioEnum.breakingWood]!;
                    if ((audioSource.getBuffer() != nil) && (audioSource.getBuffer() == breakingWood)) {
                        continue;
                    }
                    let sourceId : ALuint = audioSource.getSourceId()
                    if (!isPlaying(sourceId)) {
                        let falconBuffer : AudioBuffer = audioLibrary[TAudioEnum.falcon]!;
                        self.setPitch(sourceId, pitch: 1.0);
                        self.setVolume(sourceId, volume: 1.0);
                        self.setPosition(sourceId, position: entity.position);
                        self.play(audioSource, audioBuffer: falconBuffer);
                        self.setLoop(sourceId, loop: true);
                    }
                }
                
            }
        }
    }
    
    /**
     * Plays all the sounds related with entities
     *
     * @param audioLibrary
     *            Set of sounds that can use to provide sound feedback to the
     *            user
     * @param entities
     *            Set of entities that should provide sound feedback
     * @param player
     *            The player of the scene and also the listener
     */
    public func play(audioLibrary : [TAudioEnum: AudioBuffer] , entities : [Entity], player : Player) {
        self.playPlayer(audioLibrary, player: player);
        self.assignSources(player, entities: entities);
        let entitiesPlaying : [Entity] = Array(self.sourcesAssign.keys);
        
        self.playEntities(audioLibrary, entities: entitiesPlaying, player: player);
        
    }
    
    /**
     * Clean up because we need to clean up when we finish the program
     */
    public func cleanUp() {
    }
}
