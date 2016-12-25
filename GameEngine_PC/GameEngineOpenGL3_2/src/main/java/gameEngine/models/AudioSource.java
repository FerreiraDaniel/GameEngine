package gameEngine.models;

/**
 * Source represents the object that can play sounds for instance the CD player
 */
public class AudioSource {

	/**
	 * Identifier of the source in openAL
	 */
	private int sourceId;
	
	/**
	 * Reference to the buffer currently in the source if any
	 */
	private AudioBuffer buffer;

	/**
	 * @param sourceId
	 *            Identifier of the source in openAL
	 */
	public AudioSource(int sourceId) {
		super();
		this.sourceId = sourceId;
	}

	/**
	 * @return the sourceId Identifier of the source in openAL
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            Identifier of the source in openAL to set
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the buffer currently associated with source
	 */
	public AudioBuffer getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set in the source
	 */
	public void setBuffer(AudioBuffer buffer) {
		this.buffer = buffer;
	}

	

}
