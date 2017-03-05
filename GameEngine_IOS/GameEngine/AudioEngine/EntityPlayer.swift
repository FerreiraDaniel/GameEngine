import Foundation


/// Is going to demand the reproduction of audio due to the control of the entities
open class EntityPlayer : GenericPlayer {
    
    
    /// If the entity is far away from the player more that the threshold is not going to be listened
    fileprivate let SOUND_THRESHOLD : Float = 50.0
    
    /// First if going to make a rough approximation only after is going to check if exists a crash
    fileprivate let ROUGH_THRESHOLD : Float = 10.0
    fileprivate let CRASH_THRESHOLD : Float = 2.0
    
    
    /// List of sound sources available to the entity player
    fileprivate var sourcesAvailable : [AudioSource]
    
    
    /// The player can always play audio
    fileprivate var playerSource : AudioSource!
    
    
    /// Sources assigned to the entities
    fileprivate var sourcesAssign : [Entity : AudioSource]
    
    /// Initializer of the entity player
    ///
    /// - Parameter sourcesAvailable: The list of sources available
    public init(sourcesAvailable : [AudioSource]) {
        self.sourcesAvailable = sourcesAvailable;
        if ((!self.sourcesAvailable.isEmpty)) {
            playerSource = self.sourcesAvailable.removeLast()
        } else {
            playerSource = nil
        }
        self.sourcesAssign = [Entity : AudioSource]();
    }
    
    
    /// Set the listener position and reproduces the sound of the player
    ///
    /// - Parameters:
    ///   - library: Set of sounds that can use to provide sound feedback to the user
    ///   - player: The player of the scene and also the listener
    fileprivate func playPlayer(library : [TAudioEnum: AudioBuffer], player : Player) {
        self.positioningListener(position: player.position);
        
        // If the player is running plays the sound of the footsteps
        if (player.getCurrentSpeed() == 0) {
            if (self.isPlaying(source: playerSource)) {
                self.pause(source: playerSource);
            }
        } else {
            let pitch = player.getCurrentSpeed() / 15.0
            if (self.isPaused(source: playerSource)) {
                self.continuePlaying(source: playerSource);
            } else {
                if (!self.isPlaying(source: playerSource)) {
                    let footStepsBuffer : AudioBuffer = library[TAudioEnum.footsteps]!;
                    self.setPitch(source: playerSource, pitch: pitch);
                    self.setVolume(source: playerSource, volume: 1.0);
                    self.play(source: playerSource, buffer: footStepsBuffer);
                    self.setLoop(source: self.playerSource, loop: true);
                }
            }
            self.setPosition(source: playerSource, position: player.position);
        }
    }
    
    /// Makes one aproximation component by compoenent (Faster that usual distance)
    ///
    /// - Parameters:
    ///   - position1: The position one to take in account
    ///   - position2: The position two entity to take in account
    ///   - threshold: The threshold that is going to use to the match
    /// - Returns:  False = Not passed in the rough approximation
    ///             True  = Passed in the rough approximation
    fileprivate func roughApproximation(position1 : Vector3f, position2 : Vector3f, threshold : Float) -> Bool {
        return (abs(position1.x - position2.x) < threshold) &&
            (abs(position1.y - position2.y) < threshold) &&
            (abs(position1.z - position2.z) < threshold);
    }
    
    
    /// Computes the distance between two points
    ///
    /// - Parameters:
    ///   - position1: The position one to take in account
    ///   - position2: The position two entity to take in account
    /// - Returns: The square of the distance between two positions
    fileprivate func squareDistance(position1 : Vector3f, position2 : Vector3f) -> Float {
        let xDiff : Float = abs(position1.x - position2.x);
        let yDiff : Float = abs(position1.y - position2.y);
        let zDiff : Float = abs(position1.z - position2.z);
        
        return (xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff);
    }
    
    
    /// Indicates if an entity passes the threshold to be considered
    ///
    /// - Parameters:
    ///   - pPosition: The position of player to take in account
    ///   - ePosition: The position of entity to take in account
    /// - Returns: False the player can not listen the entity
    ///             True the entity can be listen by the player
    fileprivate func passesThreshold(pPosition : Vector3f, ePosition : Vector3f) -> Bool {
        return roughApproximation(position1: pPosition, position2: ePosition, threshold: SOUND_THRESHOLD);
    }
    

    /// Assign audio sources to the entities
    ///
    /// - Parameters:
    ///   - player: The player of the scene and also the listener
    ///   - entities: Set of entities that should provide sound feedback
    fileprivate func assignSources(player : Player, entities : [Entity]) {
        let pPosition : Vector3f = player.position;
        for entity in entities {
            if (passesThreshold(pPosition: pPosition, ePosition: entity.position)) {
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
                    let aSource = sourcesAssign.removeValue(forKey: entity)!;
                    self.stop(source: aSource);
                    self.sourcesAvailable.append(aSource);
                }
            }
        }
    }
    

    /// Check is an entity crashed against the player
    ///
    /// - Parameters:
    ///   - library: Set of sounds that can use to provide sound feedback to the user
    ///   - entity: The entity to check
    ///   - player: The position of the player that is going to check the crash
    /// - Returns:  False = No crash was detected
    ///             True = A crash was detected and the sound was played
    fileprivate func playCrash(library : [TAudioEnum : AudioBuffer], entity : Entity, player : Vector3f) -> Bool {
        if (roughApproximation(position1: player, position2: entity.position, threshold: ROUGH_THRESHOLD)
            && (squareDistance(position1: player, position2: entity.position) < CRASH_THRESHOLD)) {
            let source : AudioSource = sourcesAssign[entity]!;
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
                if (!isPlaying(source: source) && (source.getBuffer() == nil)) {
                    let breakingWood : AudioBuffer = library[TAudioEnum.breakingWood]!;
                    self.setPitch(source: source, pitch: 1.0);
                    self.setVolume(source: source, volume: 1.0);
                    self.setPosition(source: source, position: entity.position);
                    self.play(source: source, buffer: breakingWood);
                    self.setLoop(source: source, loop: false);
                }
                break;
            case .player:
                break;
            case .tree:
                let breakingWood : AudioBuffer! = library[TAudioEnum.breakingWood];
                if (!isPlaying(source: source) || ((source.getBuffer() == nil) || (source.getBuffer() != breakingWood))) {
                    self.setPitch(source: source, pitch: 1.0);
                    self.setVolume(source: source, volume: 1.0);
                    self.setPosition(source: source, position: entity.position);
                    self.play(source: source, buffer: breakingWood);
                    self.setLoop(source: source, loop: false);
                }
                break;
            }
            
            return true;
        } else {
            return false;
        }
    }
    

    /// Reproduces the sound of the entities
    ///
    /// - Parameters:
    ///   - library: Set of sounds that can use to provide sound feedback to the user
    ///   - entities: Set of entities that should provide sound feedback
    ///   - player: The player of the scene and also the listener
    fileprivate func playEntities(library : [TAudioEnum : AudioBuffer], entities : [Entity]!, player : Player) {
        if (entities != nil) {
            let pPosition : Vector3f = player.position;
            
            for entity in entities {
                // Check is has one source
                let source : AudioSource! = sourcesAssign[entity];
                if (source == nil) {
                    continue;
                }
                
                if (!playCrash(library: library, entity: entity, player: pPosition)) {
                    // If not played a crash
                    if (entity.genericEntity.objectType != TEntity.tree) {
                        continue;
                    }
                    
                    // Make birds to play in each tree
                    let breakingWood : AudioBuffer = library[TAudioEnum.breakingWood]!;
                    if ((source.getBuffer() != nil) && (source.getBuffer() == breakingWood)) {
                        continue;
                    }
                    if (!isPlaying(source: source)) {
                        let falconBuffer : AudioBuffer = library[TAudioEnum.falcon]!;
                        self.setPitch(source: source, pitch: 1.0);
                        self.setVolume(source: source, volume: 1.0);
                        self.setPosition(source: source, position: entity.position);
                        self.play(source: source, buffer: falconBuffer);
                        self.setLoop(source: source, loop: true);
                    }
                }
                
            }
        }
    }
    

    /// Plays all the sounds related with entities
    ///
    /// - Parameters:
    ///   - library: Set of sounds that can use to provide sound feedback to the user
    ///   - entities: Set of entities that should provide sound feedback
    ///   - player: The player of the scene and also the listener
    open func play(library : [TAudioEnum: AudioBuffer] , entities : [Entity], player : Player) {
        self.playPlayer(library: library, player: player);
        self.assignSources(player: player, entities: entities);
        let entitiesPlaying : [Entity] = Array(self.sourcesAssign.keys);
        
        self.playEntities(library: library, entities: entitiesPlaying, player: player);
        
    }
    

    /// Clean up because we need to clean up when we finish the program
    open func cleanUp() {
    }
}
