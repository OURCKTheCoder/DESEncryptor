package top.ourck.utils;

public class TypeConvert {
	
//	private static char byteToChar(byte b1, byte b2) {
//        char c = (char) (((b1 & 0xFF) << 8) | (b2 & 0xFF));
//        return c;
//    }
//	
//	/**
//	 * Turn a byte[]'s String representation into a byte[].
//	 * Just like this: "1010" -> {1,0,1,0}
//	 * @param str String
//	 * @return byte[]
//	 */
//	public static byte[] parseByteAryStr(String str) {
//		char[] mcs = str.toCharArray();
//		byte[] mbs = new byte[mcs.length];
//		for(int i = 0; i < mbs.length; i++) {
//			mbs[i] = (byte)(mcs[i] - '0'); // Unsafe!
//		}
//		return mbs;
//	}
//	
//	/**
//	 * Turn a byte[] into a String.
//	 * Just like this: {1,0,1,0} -> "1010"
//	 * @param b byte[]
//	 * @return String
//	 */
//	public static String parseByteAry(byte[] b) {
//		StringBuilder stb = new StringBuilder();
//		for(int i = 0; i < b.length; i++) {
//			stb.append(String.valueOf(b[i]));
//		}
//		return stb.toString();
//	}
//
//	/**
//	 * Turn a byte[]'s String value representation into a String.
//	 * Just like this: "1010" -> "AA"
//	 * @param byteStr a byte[]'s String value representation
//	 * @return String object.
//	 * TODO What if we input a single-byte string?
//	 */
//	public static String byteValStrToStr(String byteStr) {
//		byte[] b = parseByteAryStr(byteStr);
//		return byteValToStr(b);
//	}
//	
//	/**
//	 * Turn a byte[]'s value into a String. <br>
//	 * Just like this: {1,0,1,0} -> "AA"
//	 * @param b byte[]
//	 * @return String object.
//	 * TODO What if we input a single-byte string?
//	 */
//	public static String byteValToStr(byte[] b) {
//		StringBuilder stb = new StringBuilder();
//		for(int i = 0; i < b.length; i += 2) {
//			stb.append(byteToChar(b[i], b[i + 1]));
//		}
//		return stb.toString();
//	}
//	
//	public static String strToByteValStr(String str) {
//		byte[] bVal = str.getBytes();
//		byte[] bits = new byte[64]; // TODO 64x Hard-coded.
//		for(int j = 0; j < bVal.length; j++) {
//			int val = bVal[j];
//			for(int i = 0; i < 8; i++) {
//				bits[8 - 1 - i] = (byte)(val & 1);
//				val >>>= 1;
//			}
//		}
//		
//		return parseByteAry(bits);
//	}
	
	public static String bytes2Str(byte[] bs) {
		return new String(bs);
	}
	
	public static byte[] str2Bytes(String str) {
		return str.getBytes();
	}
	
	public static byte[] bytes2Bits(byte[] bs) {
		byte[] bits = new byte[bs.length * 8]; // TODO 64x Hard-coded.
		for(int j = 0; j < bs.length; j++) {
			int val = bs[j];
			for(int i = 0; i < 8; i++) {
				bits[8 - 1 - i + j * 8] = (byte)(val & 1);
				val >>>= 1;
			}
		}
		
		return bits;
	}

	public static byte[] bits2Bytes(byte[] bits) {
		byte[] bs = new byte[bits.length / 8];
		for(int i = 0; i < bs.length; i++) {
			for(int j = 0; j < 8; j++)
				bs[i] += bits[i * 8 + j] * Math.pow(2, 7 - j);
		}
		
		return bs;
	}
	
	public static byte[] str2Bits(String str) {
		return bytes2Bits(str2Bytes(str));
	}
	
	public static String bits2Str(byte[] bits) {
		return bytes2Str(bits2Bytes(bits));
	}
	
	
	// Convenience method.
	/**
	 * Turn a byte[]'s String representation into bits.
	 * Just like this: "1010" -> {1,0,1,0}
	 * @param bitsStr String
	 * @return Bits.
	 */
	public static byte[] parseStr2Bits(String bitsStr) {
		char[] mcs = bitsStr.toCharArray();
		byte[] mbs = new byte[mcs.length];
		for(int i = 0; i < mbs.length; i++) {
			mbs[i] = (byte)(mcs[i] - '0'); // Unsafe!
		}
		return mbs;
	}

	/**
	 * Turn a byte[]'s String representation into bits.
	 * Just like this: "1010" -> {1,0,1,0}
	 * @param bitsStr String
	 * @return Bits.
	 */
	public static String parseBits2Str(byte[] bits) {
		StringBuilder stb = new StringBuilder();
		for(int i = 0; i < bits.length; i++) {
			stb.append(String.valueOf(bits[i]));
		}
		return stb.toString();
	}
	
	public static void main(String[] args) {
		byte[] bits = {1,0,0,0,0,1,0,1};
		bits = bytes2Bits(bits2Bytes(bits));
		for(byte b : bits) System.out.print(b);
	}
}
