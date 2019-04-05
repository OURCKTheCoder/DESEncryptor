package top.ourck.des.frontend;

import java.util.Arrays;

import top.ourck.des.processor.IncrementalProcessor;
import top.ourck.des.processor.InitialDisplacementProcessor;
import top.ourck.des.processor.KeygenProcessor;

public class DESDecryptor {

	private static final int INC_TIMES = 16;
	
	private KeygenProcessor kgProcessor = new KeygenProcessor();
	private InitialDisplacementProcessor ipProcessor = new InitialDisplacementProcessor(false);
	private InitialDisplacementProcessor iipProcessor = new InitialDisplacementProcessor(true);
	private IncrementalProcessor incProcessor = new IncrementalProcessor();
	
	public byte[] decrypt(byte[] c, byte[] k) throws Exception {
		if(c.length != 64) throw new Exception("Message must be of 64 bits!");
		if(k.length != 64) throw new Exception("Key must be of 64 bits!");

		byte[][] cpKeys = kgProcessor.process(k);
		byte[] iped = ipProcessor.process(c);
		
		byte[] inced = iped;
		for(int i = 0; i < INC_TIMES; i++) {
			inced = incProcessor.process(inced, cpKeys[INC_TIMES - 1 - i]);
		}
		// DO NOT FORGET TO REVERSE 1 MORE TIME!!!!
		byte[] l = Arrays.copyOfRange(inced, 0, inced.length / 2);
		byte[] r = Arrays.copyOfRange(inced, inced.length / 2, inced.length);
		System.arraycopy(r, 0, inced, 0, r.length);
		System.arraycopy(l, 0, inced, r.length, l.length);
		
		byte[] cipher = iipProcessor.process(inced);
		
		return cipher;
	}
	
	public static void main(String[] args) throws Exception {
		byte[] cipher = new byte[64];
		byte[] key = new byte[64];
		key[0] = 1;
		cipher[1] = 1;
		
		for(byte b : cipher) System.out.print(b);
		System.out.println();
		byte[] message = new DESDecryptor().decrypt(cipher, key);
		for(byte b : message) System.out.print(b);
	}
	
}
