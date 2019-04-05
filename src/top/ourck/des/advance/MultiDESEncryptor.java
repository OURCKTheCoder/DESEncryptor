package top.ourck.des.advance;

import top.ourck.des.frontend.DESDecryptor;
import top.ourck.des.frontend.DESEncryptor;

/**
 * An advanced DES encryptor. <br>
 * Accepts 1 message and N keys. Use keys in turn to encrypt / decrypt message.
 * @author OURCK
 */
public class MultiDESEncryptor implements AdvancedDESEncryptor {

	private DESEncryptor enc = new DESEncryptor();
	private DESDecryptor dec = new DESDecryptor();
	private final int procTimes;
	
	public MultiDESEncryptor(int procTimes) {
		this.procTimes = procTimes;
	}
	
	@Override
	public byte[] encrypt(byte[] m, byte[]... k) {
		try {
			for(int i = 0; i < procTimes; i++) {
				if((i & 1) == 0)	m = enc.encrypt(m, k[i % k.length]); // TODO % may be too slow!
				else				m = dec.decrypt(m, k[i % k.length]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return m;
	}

	@Override
	public byte[] decrypt(byte[] c, byte[]... k) {
		try {
			for(int i = procTimes - 1; i >= 0; i--) {
				if((i & 1) == 0)	c = dec.decrypt(c, k[i % k.length]); // TODO % may be too slow!
				else				c = enc.encrypt(c, k[i % k.length]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return c;
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
					1,1,1,0,1,1,0,1
			};
			byte[][] keys = new byte[2][];
			keys[0] = new byte[] {
					0,0,0,1,0,0,1,1,
					0,0,1,1,0,1,0,0,
					0,1,0,1,0,1,1,1,
					0,1,1,1,1,0,0,1,
					1,0,0,1,1,0,1,1,
				 	1,0,1,1,1,1,0,0,
					1,1,0,1,1,1,1,1,
					1,1,1,1,0,0,0,1
			};
			keys[1] = new byte[64]; keys[1][0] = 1;
			
			for(int i = 0; i < message.length; i++) {
				System.out.print(message[i]);
				if((1 + i & 7) == 0) System.out.print(" ");
			}
			System.out.println();
			
			AdvancedDESEncryptor aDesEnc = new MultiDESEncryptor(3);
			byte[] cipher = aDesEnc.encrypt(message, keys);
			for(int i = 0; i < cipher.length; i++) {
				System.out.print(cipher[i]);
				if((1 + i & 7) == 0) System.out.print(" ");
			}
			System.out.println();
			
			byte[] restored = aDesEnc.decrypt(cipher, keys);
			for(int i = 0; i < restored.length; i++) {
				System.out.print(restored[i]);
				if((1 + i & 7) == 0) System.out.print(" ");
			}
	}

}
