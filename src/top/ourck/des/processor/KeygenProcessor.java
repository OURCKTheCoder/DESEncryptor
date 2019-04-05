package top.ourck.des.processor;

/**
 * 64-bit key -> 48-bit key.
 * @author OURCK
 *
 */
public class KeygenProcessor extends DESKeyGenerator {

	private static final int[] PC1_LMTX = {
		57,49,41,33,25,17,9,
		1,58,50,42,34,26,18,
		10,2,59,51,43,35,27,
		19,11,3,60,52,44,36
	};
	
	private static final int[] PC1_RMTX = {
		63,55,47,39,31,23,15,
		7,62,54,46,38,30,22,
		14,6,61,53,45,37,29,
		21,13,5,28,20,12,4
	};
	
	private static final int[] PC2_MTX = {
		14,17,11,24,1,5,
		3,28,15,6,21,10,
		23,19,12,4,26,8,
		16,7,27,20,13,2,
		41,52,31,37,47,55,
		30,40,51,45,33,48,
		44,49,39,56,34,53,
		46,42,50,36,29,32
	};
	
	private static final int[] SHIFT_TABLE = {
		1,1,2,2,
		2,2,2,2,
		1,2,2,2,
		2,2,2,1
	};
	
	private static final int INPUT_BITS = 64;
	private static final int OUTPUT_BITS = 48;
	private static final int VALID_BITS = 56;
	private static final int MAX_LOOPS = 16;
	
	@Override
	protected boolean isInputValid(byte[] k) {
		return k.length == INPUT_BITS ? true : false;
	}

	@Override
	protected byte[][] handleProcess(byte[] k) {
		byte[] l = new byte[VALID_BITS / 2];
		byte[] r = new byte[VALID_BITS / 2];
		byte[][] result = new byte[MAX_LOOPS][OUTPUT_BITS];
		
		// TODO Consider using HashMap!
		int count = 0;
		for(int i = 0; i < PC1_LMTX.length; i++) l[count++] = k[PC1_LMTX[i] - 1];
		count = 0;
		for(int i = 0; i < PC1_RMTX.length; i++) r[count++] = k[PC1_RMTX[i] - 1];
		
		for(int i = 0; i < MAX_LOOPS; i++) {
			l = loopLeftShift(l, SHIFT_TABLE[i]);
			r = loopLeftShift(r, SHIFT_TABLE[i]);

			for(int j = 0; j < OUTPUT_BITS; j++) {
				int targetIndex = PC2_MTX[j] - 1;
				if(targetIndex < VALID_BITS / 2) result[i][j] = l[targetIndex];
				else							 result[i][j] = r[targetIndex - VALID_BITS / 2];
			}
		}
		
		return result;
	}
	
	// TODO LinkedList!!!
	private byte[] loopLeftShift(byte[] target, int shiftTime) {
		byte[] temp = new byte[shiftTime];
		for(int j = 0; j < shiftTime; j++) temp[j] = target[j];
		for(int j = 0; j < VALID_BITS / 2 - shiftTime; j++) target[j] = target[j + shiftTime]; // TODO BE CAREFUL NOT TO OVERFLOW!
		for(int j = 0; j < shiftTime; j++) target[j + target.length - shiftTime] = temp[j];
		
		return target;
	}

	public static void main(String[] args) throws Exception {
		byte[] key = new byte[] {0,0,0,1,0,0,1,1,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,0,0,0,1};
		
		byte[][] ciphers = new KeygenProcessor().process(key);
		for(byte[] cipher : ciphers) {
			for(byte b : cipher) System.out.print(b);
			System.out.println();
		}
	}
}
