package top.ourck.des.processor;

public abstract class DESKeyGenerator {
	/**
	 * Perform actions to generate key.
	 * @param k Key
	 * @param loopT Current loop times.
	 * @return byte[] as a result of key.
	 * @throws Exception If any processor failed to encrypt, or k is invalid.
	 */
	public byte[][] process(byte[] k) throws Exception {
		assertValid(k);
		return handleProcess(k);
	}
	
	/**
	 * Used for testing if input is valid
	 * @param k Key
	 * @param loopT Current loop times.
	 * @return If m & k has proper bits, return true. Else return false.
	 */
	protected abstract boolean isInputValid(byte[] k);
	
	/**
	 * The processor's main working process. 
	 * @param k Key
	 * @param loopT Current loop times.
	 * @return byte[] as a result of key.
	 */
	protected abstract byte[][] handleProcess(byte[] k);
	
	/**
	 * Test if input is valid using isInputValid().
	 * @param k Key
	 * @param loopT Current loop times.
	 * @throws Exception If k don't have proper bits or loopT is invalid.
	 */
	protected void assertValid(byte[] k) throws Exception {
		if(!this.isInputValid(k)) throw new Exception("Input is invalid!");
	}
}
