package top.ourck.des.processor;

/**
 * Implements extending processing.<br>
 * This class's process() method accepts a <b>32-bit key</b> and return a <b>48-bit cipher</b>.
 * @author OURCK
 */
public class ExtendProcessor extends DESProcessor {

	private static final int[] EXT_MTX = {
			32,	1,	2,	3,	4,	5,
			4,	5,	6,	7,	8,	9,
			8,	9,	10,	11,	12,	13,
			12,	13,	14,	15,	16,	17,
			16,	17,	18,	19,	20,	21,
			20,	21,	22,	23,	24,	25,
			24,	25,	26,	27,	28,	29,
			28,	29,	30,	31,	32,	1
	};
	
	private static final int INPUT_BITS = 32;
	private static final int OUTPUT_BITS = 48;
	
	@Override
	protected byte[] handleProcess(byte[] m) {
		byte[] r = new byte[OUTPUT_BITS];
		for(int i = 0; i < r.length; i++) {
			r[i] = m[EXT_MTX[i] - 1];
		}
		return r;
	}

	@Override
	public boolean isInputValid(byte[] m) {
		return m.length == INPUT_BITS ? true : false;
	}
	
	public static void main(String[] args) throws Exception {
		byte[] msg = new byte[32];
		msg[0] = 1;
		
		byte[] cipher = new ExtendProcessor().process(msg);
		for(byte b : cipher) System.out.print(b);
	}
}
