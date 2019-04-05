package top.ourck.des.frontend;

import java.util.Arrays;

import top.ourck.des.processor.IncrementalProcessor;
import top.ourck.des.processor.InitialDisplacementProcessor;
import top.ourck.des.processor.KeygenProcessor;

public class DESEncryptor {

	private static final int INC_TIMES = 16;
	
	private KeygenProcessor kgProcessor = new KeygenProcessor();
	private InitialDisplacementProcessor ipProcessor = new InitialDisplacementProcessor(false);
	private InitialDisplacementProcessor iipProcessor = new InitialDisplacementProcessor(true);
	private IncrementalProcessor incProcessor = new IncrementalProcessor();
	
	public byte[] encrypt(byte[] m, byte[] k) throws Exception {
		if(m.length != 64) throw new Exception("Message must be of 64 bits!");
		if(k.length != 64) throw new Exception("Key must be of 64 bits!");

		byte[][] cpKeys = kgProcessor.process(k);
		byte[] iped = ipProcessor.process(m);
		
		byte[] inced = iped;
		for(int i = 0; i < INC_TIMES; i++) {
			inced = incProcessor.process(inced, cpKeys[i]);
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
		byte[] message = new byte[] {
				0,0,0,0,0,0,0,1,
				0,0,1,0,0,0,1,1,
				0,1,0,0,0,1,0,1,
				0,1,1,0,0,1,1,1,
				1,0,0,0,1,0,0,1,
				1,0,1,0,1,0,1,1,
				1,1,0,0,1,1,0,1,
				1,1,1,0,1,1,1,1
		};
		byte[] key = new byte[] {
				0,0,0,1,0,0,1,1,
				0,0,1,1,0,1,0,0,
				0,1,0,1,0,1,1,1,
				0,1,1,1,1,0,0,1,
				1,0,0,1,1,0,1,1,
				1,0,1,1,1,1,0,0,
				1,1,0,1,1,1,1,1,
				1,1,1,1,0,0,0,1
		};
		
		for(int i = 0; i < message.length; i++) {
			System.out.print(message[i]);
			if((1 + i & 7) == 0) System.out.print(" ");
		}
		System.out.println();
		
		byte[] cipher = new DESEncryptor().encrypt(message, key);
		for(int i = 0; i < cipher.length; i++) {
			System.out.print(cipher[i]);
			if((1 + i & 7) == 0) System.out.print(" ");
		}
		System.out.println();
		
		byte[] restored = new DESDecryptor().decrypt(cipher, key);
		for(int i = 0; i < restored.length; i++) {
			System.out.print(restored[i]);
			if((1 + i & 7) == 0) System.out.print(" ");
		}
	}
	
}
