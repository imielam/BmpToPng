package headers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

public abstract class Chunk {
	/**
	 * A 4-byte unsigned integer giving the number of bytes in the chunk's data
	 * field. The length counts only the data field, not itself, the chunk type
	 * code, or the CRC. Zero is a valid length. Although encoders and decoders
	 * should treat the length as unsigned, its value must not exceed 231-1
	 * bytes.
	 */
	public int chLength;
	/**
	 * A 4-byte chunk type code. For convenience in description and in examining
	 * PNG files, type codes are restricted to consist of uppercase and
	 * lowercase ASCII letters (A-Z and a-z, or 65-90 and 97-122 decimal).
	 * However, encoders and decoders must treat the codes as fixed binary
	 * values, not character strings. For example, it would not be correct to
	 * represent the type code IDAT by the EBCDIC equivalents of those letters.
	 * Additional naming conventions for chunk types are discussed in the next
	 * section.
	 */
	public byte[] chType = new byte[0];
	/**
	 * The data bytes appropriate to the chunk type, if any. This field can be
	 * of zero length.
	 */
	public byte[] chData = new byte[0];
	/**
	 * 
	 A 4-byte CRC (Cyclic Redundancy Check) calculated on the preceding bytes
	 * in the chunk, including the chunk type code and chunk data fields, but
	 * not including the length field. The CRC is always present, even for
	 * chunks containing no data
	 */
	public int chCRC;

	public Chunk(int chLength, byte[] chType, byte[] chData, int chCRC) {
		super();
		this.chLength = chLength;
		this.chType = chType;
		this.chData = chData;
		this.chCRC = chCRC;
	}

	public Chunk(int chLength, byte[] chType, byte[] chData) {
		super();
		this.chLength = chLength;
		this.chType = chType;
		this.chData = chData;
	}

	public Chunk(int chLength, byte[] chType) {
		super();
		this.chLength = chLength;
		this.chType = chType;
	}

	protected void updateCRC() {
		ByteBuffer b = ByteBuffer.allocate(chType.length + chLength);
		b.order(ByteOrder.BIG_ENDIAN);
		b.put(chType);
		b.put(chData);
		// CRC c = CRC.getInstance();
		// chCRC = c.updateCRC(0, b.array());
		CRC32 crc = new CRC32();
		crc.update(b.array());
		chCRC = (int) crc.getValue();
	}

	public String stringToWrite() {
		StringBuilder sb = new StringBuilder();
		ByteBuffer b = ByteBuffer.allocate(4 + 4 + chType.length
				+ chData.length);
		b.order(ByteOrder.BIG_ENDIAN);
		b.putInt(chLength);
		b.put(chType);
		b.put(chData);
		b.putInt(chCRC);
		byte[] result = b.array();
		for (byte i : result) {
			sb.append(i);
		}
		return sb.toString();
	}

	public byte[] byteToWrite() {
		ByteBuffer b = ByteBuffer.allocate(4 + 4 + chType.length
				+ chData.length);
		b.order(ByteOrder.BIG_ENDIAN);
		b.putInt(chLength);
		b.put(chType);
		b.put(chData);
		b.putInt(chCRC);
		return b.array();
	}
}
