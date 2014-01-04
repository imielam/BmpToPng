package utils;

public class CRC {
	private final static CRC instance = new CRC();
	private final int[] crcTable = new int[256];

	private CRC() {
		int c;
		int n, k;

		for (n = 0; n < 256; n++) {
			c = n;
			for (k = 0; k < 8; k++) {
				if ((c & 1) == 1)
					c = 0xedb88320 ^ (c >> 1);
				else
					c = c >> 1;
			}
			crcTable[n] = c;
		}
	}

	public static CRC getInstance() {
		return instance;
	}

	public int updateCRC(int crc, byte[] buf) {
		int result = crc;

		for (byte c : buf) {
			result = crcTable[(int) ((result ^ c) & 0xff)] ^ (result >> 8);
		}
		return result;
	}

}
