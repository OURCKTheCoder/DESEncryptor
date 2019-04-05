package top.ourck.des.processor;

/**
 * Implements replacement processing.<br>
 * This class's process() method accepts a <b>32-bit key</b> and return a <b>32-bit cipher</b>.
 * @author OURCK
 */
public class ReplacementProcessor extends DESProcessor {

	private static final int[] REP_MTX = {
		16, 7, 20, 21,
		29, 12, 28, 17,
		1, 15, 23, 26,
		5, 18, 31, 10,
		2, 8, 24, 14,
		32, 27, 3, 9,
		19, 13, 30, 6,
		22, 11, 4, 25	
	};
	
	private static final int INPUT_BITS = 32;
	private static final int OUTPUT_BITS = 32;
	
	@Override
	protected boolean isInputValid(byte[] m) {
		return m.length == INPUT_BITS ? true : false;
	}

	@Override
	protected byte[] handleProcess(byte[] m) {
		byte[] r = new byte[OUTPUT_BITS];
		for(int i = 0; i < OUTPUT_BITS; i++) r[i] = m[REP_MTX[i] - 1];
		return r;
	}
	
	public static void main(String[] args) throws Exception{
		byte[] msg = new byte[32];
		msg[0] = 1;
		
		byte[] cipher = new ReplacementProcessor().process(msg);
		for(byte b : cipher) System.out.print(b);
	}
}
