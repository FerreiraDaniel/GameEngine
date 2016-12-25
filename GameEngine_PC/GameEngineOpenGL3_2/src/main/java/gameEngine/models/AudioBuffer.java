package gameEngine.models;

/**
 * Audio buffer to get played
 */
public class AudioBuffer {

	/**
	 * Identifier of the buffer to play in openAL
	 */
	private int bufferId;

	/**
	 * @param bufferId
	 *            Identifier of the buffer to play in openAL
	 */
	public AudioBuffer(int bufferId) {
		super();
		this.bufferId = bufferId;
	}

	/**
	 * @return the bufferId Identifier of the buffer to play in openAL
	 */
	public int getBufferId() {
		return bufferId;
	}

	/**
	 * @param bufferId
	 *            Identifier of the buffer to play in openAL
	 */
	public void setBufferId(int bufferId) {
		this.bufferId = bufferId;
	}

}
