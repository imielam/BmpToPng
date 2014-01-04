package utils;

public class CRC {
	private final static CRC instance = new CRC();
	private final long[] crcTable = new long[256];

	private CRC() {
		long c;
		int n, k;

		for (n = 0; n < 256; n++) {
			c = (long) n;
			for (k = 0; k < 8; k++) {
				if ((c & 1) == 1)
					c = 0xedb88320L ^ (c >> 1);
				else
					c = c >> 1;
			}
			crcTable[n] = c;
		}
	}

	public static CRC getInstance() {
		return instance;
	}

	public int updateCRC(long crc, byte[] buf) {
		long result = crc;

		for (byte c : buf) {
			result = crcTable[(int) ((result ^ c) & 0xff)] ^ (result >> 8);
		}
		return (int) result;
	}

}
