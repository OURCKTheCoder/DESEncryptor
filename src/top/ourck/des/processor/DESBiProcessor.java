package top.ourck.des.processor;

public abstract class DESBiProcessor {

	/**
	 * Perform actions to encrypt message.
	 * @param m Message
	 * @param k Key
	 * @return byte[] as a result.
	 * @throws Exception If any processor failed to encrypt, or k & m is invalid.
	 */
	public byte[] process(byte[] m, byte[] k) throws Exception {
		assertValid(m, k);
		return handleProcess(m, k);
	}
	
	/**
	 * Used for testing message & key.
	 * @param m Message
	 * @param k Key
	 * @return If m & k has proper bits, return true. Else return false.
	 */
	protected abstract boolean isInputValid(byte[] m, byte[] k);
	
	/**
	 * The processor's main working process. 
	 * @param m Message
	 * @param k Key
	 * @return byte[] as a result.
	 */
	protected abstract byte[] handleProcess(byte[] m, byte[] k);
	
	/**
	 * Test if m & k is valid using isInputValid().
	 * @param m Message
	 * @param k Key
	 * @throws Exception If m & k don't have proper bits.
	 */
	protected void assertValid(byte[] m, byte[] k) throws Exception {
		if(!this.isInputValid(m, k)) throw new Exception("Input is invalid!");
	}
}
