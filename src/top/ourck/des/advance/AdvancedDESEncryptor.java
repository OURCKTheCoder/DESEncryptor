package top.ourck.des.advance;

public interface AdvancedDESEncryptor {

	byte[] encrypt(byte[] m, byte[]... k);
	byte[] decrypt(byte[] c, byte[]... k);
}
