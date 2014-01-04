package headers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import utils.CRC;

/**
 * The IEND chunk must appear LAST. It marks the end of the PNG datastream. The chunk's data field is empty.
 * @author Maciej
 *
 */
public class IEND extends Chunk {

	public IEND() {
		super(0, IEND.class.getSimpleName().getBytes(), new byte[0]);
		updateCRC();
	}

//	@Override
//	protected void updateCRC() {
//		ByteBuffer b = ByteBuffer
//				.allocate(super.chType.length + super.chLength);
//		b.order(ByteOrder.BIG_ENDIAN);
//		b.put(this.chType);
//		b.put(this.chData);
//		CRC c = CRC.getInstance();
//		chCRC = c.updateCRC(0, b.array());
//
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IEND [chLength=").append(chLength).append(", chType=")
				.append(Arrays.toString(chType)).append(", chData=")
				.append(Arrays.toString(chData)).append(", chCRC=")
				.append(chCRC).append("]");
		return builder.toString();
	}

}
