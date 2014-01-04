package headers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import utils.CRC;

public class IHDR extends Chunk{
	/**
	 * <0:2^31-1>
	 */
	private int width;
	/**
	 * <0:2^31-1>
	 */
	private int height; //
	/**
	 * (1;2;4;8;16)
	 */
	private byte depth = 16; //
	/**
	 * (0;2;3;4;6) Color type codes represent sums of the following values:
	 * <ul>
	 * <li/>1 (palette used)
	 * <li/>2 (color used)
	 * <li/>4 (alpha channel used)
	 * </ul>
	 */
	private byte type = 2; //
	/**
	 * Compression method is a single-byte integer that indicates the method
	 * used to compress the image data. At present, only compression method 0
	 * (deflate/inflate compression with a sliding window of at most 32768
	 * bytes) is defined. All standard PNG images must be compressed with this
	 * scheme. The compression method field is provided for possible future
	 * expansion or proprietary variants. Decoders must check this byte and
	 * report an error if it holds an unrecognized code. See Deflate/Inflate
	 * Compression for details.
	 */
	private static final byte compressionMethod = 0;
	/**
	 * Filter method is a single-byte integer that indicates the preprocessing
	 * method applied to the image data before compression. At present, only
	 * filter method 0 (adaptive filtering with five basic filter types) is
	 * defined. As with the compression method field, decoders must check this
	 * byte and report an error if it holds an unrecognized code.
	 */
	private static final byte filterMethod = 0;
	/**
	 * Interlace method is a single-byte integer that indicates the transmission
	 * order of the image data. Two values are currently defined:
	 * <ul>
	 * <li/>0 (no interlace)
	 * <li/>1 (Adam7 interlace)
	 * </ul>
	 */
	private byte interlanceMethor = 0;
	
	public IHDR(int width,
			int height){
		this(0, width,
			height, (byte)16, (byte)2, (byte) 0);
	}
		
	public IHDR(int chLength, int width,
			int height, byte depth, byte type, byte interlanceMethor) {
		
		super(chLength, IHDR.class.getSimpleName().getBytes());
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.type = type;
		this.interlanceMethor = interlanceMethor;
		updateCRC();
	}

	protected void updateCRC() {
		createCunkData();
		super.updateCRC();
	}

	private void createCunkData() {
		ByteBuffer b = ByteBuffer.allocate(13);
		b.order(ByteOrder.BIG_ENDIAN);
		b.putInt(width);
		b.putInt(height);
		b.put(depth);
		b.put(type);
		b.put(compressionMethod);
		b.put(filterMethod);
		b.put(interlanceMethor);
		super.chData = b.array();
		super.chLength = super.chData.length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte getDepth() {
		return depth;
	}

	public byte getType() {
		return type;
	}

	public static byte getCompressionmethod() {
		return compressionMethod;
	}

	public static byte getFiltermethod() {
		return filterMethod;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IHDR [width=").append(width).append(", height=")
				.append(height).append(", depth=").append(depth)
				.append(", type=").append(type).append(", interlanceMethor=")
				.append(interlanceMethor).append(", chLength=")
				.append(chLength).append(", chType=")
				.append(Arrays.toString(chType)).append(", chData=")
				.append(Arrays.toString(chData)).append(", chCRC=")
				.append(chCRC).append("]");
		return builder.toString();
	}

	public byte getInterlanceMethor() {
		return interlanceMethor;
	}
	
	

}
