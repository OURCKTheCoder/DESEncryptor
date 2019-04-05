package top.ourck.des.processor;

import java.util.Arrays;

/**
 * Implements incremental processing.<br>
 * This class comprehensively and recursively use {@link ExtendProcessor}, {@link EncryptingProcessor}, {@link KeygenProcessor} and {@link ReplacementProcessor}
 * to perform encrypting.<br>
 * This class's process() method accepts a <b>48-bit key</b> and a <b>64-bit message</b>.
 * @author OURCK
 */
public class IncrementalProcessor extends DESBiProcessor {

	private static final int INPUT_BITS = 64;
	private static final int OUTPUT_BITS = 64;
	private static final int KEY_BITS = 48;
	
	// TODO Chain of responsibility?
	private ExtendProcessor extProcessor = new ExtendProcessor();
	private EncryptingProcessor encProcessor = new EncryptingProcessor();
	private ReplacementProcessor repProcessor = new ReplacementProcessor();
	
	@Override
	public byte[] process(byte[] m, byte[] k) throws Exception {
		assertValid(m, k);
		try { return handleProcess(m, k); }
		catch(RuntimeException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	protected byte[] handleProcess(byte[] m, byte[] k) {
		byte[] l = Arrays.copyOfRange(m, 0, INPUT_BITS / 2);
		byte[] r = Arrays.copyOfRange(m, INPUT_BITS / 2, OUTPUT_BITS);
		byte[] oldR = Arrays.copyOf(r, r.length);
		
		try {
			r = extProcessor.process(r);
			r = encProcessor.process(r, k);
			r = repProcessor.process(r);
			
			// L xor R
			for(int j = 0; j < r.length; j++)
				r[j] ^= l[j];
			l = oldR;
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		byte[] cipher = new byte[OUTPUT_BITS];
		System.arraycopy(l, 0, cipher, 0, l.length);
		System.arraycopy(r, 0, cipher, l.length, r.length);
		
//		for(int i = 0; i < cipher.length; i++) {
//			System.out.print(cipher[i]);
//			if((1 + i & 7) == 0) System.out.print(" ");
//		}
//		System.out.println();
		return cipher;
	}

	@Override
	public boolean isInputValid(byte[] m, byte[] k) {
		return m.length == INPUT_BITS && k.length == KEY_BITS ? true : false;
	}
	
	public static void main(String[] args) throws Exception {
		byte[] key = new byte[48];
		byte[] msg = new byte[64];
		key[0] = 1;
		msg[0] = 1;
		
		byte[] cipher = new IncrementalProcessor().process(msg, key);
		for(byte b : cipher) System.out.print(b);
	}
}
