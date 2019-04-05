package top.ourck.des.processor;

public class InitialDisplacementProcessor extends DESProcessor {

	private static final int[] IP_MTX = {
			58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6,
			64, 56, 48, 40, 32, 24, 16, 8,
			57, 49, 41, 33, 25, 17, 9, 1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45, 37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7
	};
	
	private static final int[] INVERSEIP_MTX = {
			40,8,48,16,56,24,64,32,
			39,7,47,15,55,23,63,31,
			38,6,46,14,54,22,62,30,
			37,5,45,13,53,21,61,29,
			36,4,44,12,52,20,60,28,
			35,3,43,11,51,19,59,27,
			34,2,42,10,50,18,58,26,
			33,1,41, 9,49,17,57,25
	};
	
	private static final int INPUT_BITS = 64;
	private static final int OUTPUT_BITS = 64;
	
	private boolean inversed = false;
	
	public InitialDisplacementProcessor(boolean inversed) {
		this.inversed = inversed;
	}
	
	@Override
	protected byte[] handleProcess(byte[] m) {
		byte[] r = new byte[OUTPUT_BITS];
		int[] mtx = null;
		if(!inversed) 	mtx = IP_MTX;
		else			mtx = INVERSEIP_MTX;
		
		for(int i = 0; i < r.length; i++) {
			r[i] = m[mtx[i] - 1];
		}
		
		return r;
	}

	@Override
	public boolean isInputValid(byte[] m) {
		return m.length == INPUT_BITS ? true : false;
	}

	public static void main(String[] args) throws Exception {
		byte[] msg = new byte[64];
		msg[0] = 1; msg[1] = 1; //msg[3] = 1;
		
		byte[] cipher = new InitialDisplacementProcessor(false).process(msg);
		for(byte b : cipher) System.out.print(b);
		
		System.out.println();
		
		byte[] reverse = new InitialDisplacementProcessor(true).process(msg);
		for(byte b : reverse) System.out.print(b);
	}
}
