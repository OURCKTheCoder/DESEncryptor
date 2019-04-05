package top.ourck.des.processor;

public abstract class DESProcessor {
	
	/**
	 * Perform actions to encrypt message.
	 * @param m Message
	 * @return byte[] as a result.
	 * @throws Exception If any processor failed to encrypt, or m is invalid.
	 */
	public byte[] process(byte[] m) throws Exception {
		assertValid(m);
		return handleProcess(m);
	}
	
	/**
	 * Used for testing message.
	 * @param m Message
	 * @return If m & k has proper bits, return true. Else return false.
	 */
	protected abstract boolean isInputValid(byte[] m);
	
	/**
	 * The processor's main working process. 
	 * @param m Message
	 * @return byte[] as a result.
	 */
	protected abstract byte[] handleProcess(byte[] m);
	
	/**
	 * Test if message is valid using isInputValid().
	 * @param m Message
	 * @throws Exception If m & k don't have proper bits.
	 */
	protected void assertValid(byte[] m) throws Exception {
		if(!this.isInputValid(m)) throw new Exception("Input is invalid!");
	}
}
