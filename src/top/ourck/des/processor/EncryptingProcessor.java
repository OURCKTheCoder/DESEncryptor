package top.ourck.des.processor;

/**
 * Implements encrypting processing(with 48-bit key) & compacting processing.<br>
 * This class's process() method accepts a <b>48-bit key</b> and a <b>48-bit message</b>, return a <b>32-bit cipher</b>.
 * @author OURCK
 */
public class EncryptingProcessor extends DESBiProcessor {

	private static class SBoxs {
		private static final int S_BOX_TABLE[] = { // 8 * 4 * 16
			// S1 
			14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
			0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
			4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
			15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13,
			//S2
			15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
			3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
			0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
			13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9,
			//S3
			10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
			13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
			13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
			1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12,
			//S4
			7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
			13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
			10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
			3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14,
			//S5
			2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
			14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
			4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
			11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3,
			//S6
			12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
			10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
			9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
			4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13,
			//S7
			4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
			13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
			1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
			6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12,
			//S8
			13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
			1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
			7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
			2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11
		};
		//private static final int INPUT_BITS = 6;
		private static final int OUTPUT_BITS = 4;
		
		private SBoxs() {}
		private static final int getIndex(int nBox, int r, int c) {
			return S_BOX_TABLE[(nBox - 1) * 4 * 16
			                   + r * 16
			                   + c];
		}
		
		public static final byte[] getOutput(byte[] m, int nBox) { // nBox starts from 1!
			int r = m[0] * 2 + m[5];
			int c = m[1] * 8 + m[2] * 4 + m[3] * 2 + m[4];
			int val = getIndex(nBox, r, c);
			byte[] cipher = new byte[OUTPUT_BITS];
			for(int i = 0; i < OUTPUT_BITS; i++, val >>>= 1) { // mod 2 & reverse.
				cipher[OUTPUT_BITS - 1 - i] = (byte)(val & 1);
			}
			return cipher;
		}
	}
	
	private static final int INPUT_BITS = 48;
	private static final int OUTPUT_BITS = 32;
	private static final int KEY_BITS = 48;
	
	/**
	 * Accepts a 48-bit message & 48-bit key, return a 32-bit cipher.
	 * @param m Message
	 * @param k Key
	 * @return A 32-bit cipher.
	 * TODO m & k is modified! Unsafe!
	 */
	@Override
	protected byte[] handleProcess(byte[] m, byte[] k) {
		for(int i = 0; i < m.length; i++) m[i] ^= k[i]; // Encrypting with 48-bit key.
		
		// TODO The process below is hard-coded!
		byte[] result = new byte[OUTPUT_BITS];
		byte[] buf = new byte[6];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 6; j++) buf[j] = m[i * 6 + j];
			byte[] group = SBoxs.getOutput(buf, i + 1);
			for(int j = 0; j < 4; j++) result[i * 4 + j] = group[j];
		}
		return result;
	}

	@Override
	public boolean isInputValid(byte[] m, byte[] k) {
		return m.length == INPUT_BITS && k.length == KEY_BITS ? true : false;
	}

	public static void main(String[] args) throws Exception {
		byte[] key = new byte[48];
		byte[] msg = new byte[48];
		key[0] = 1;
		msg[0] = 1;
		
		byte[] cipher = new EncryptingProcessor().process(msg, key);
		for(byte b : cipher) System.out.print(b);
	}
}
