package headers;

import java.util.Arrays;
import java.util.zip.Deflater;

import utils.Filter;
import utils.RgbMatrix;

/**
 * The IDAT chunk contains the actual image data. To create this data: <il> <li>
 * Begin with image scanlines represented as described in Image layout; the
 * layout and total size of this raw data are determined by the fields of IHDR.
 * <li>Filter the image data according to the filtering method specified by the
 * IHDR chunk. (Note that with filter method 0, the only one currently defined,
 * this implies prepending a filter-type byte to each scanline.) <li>Compress
 * the filtered data using the compression method specified by the IHDR chunk.
 * </il> The IDAT chunk contains the output datastream of the compression
 * algorithm.<br/>
 * 
 * To read the image data, reverse this process.<br/>
 * 
 * There can be multiple IDAT chunks; if so, they must appear consecutively with
 * no other intervening chunks. The compressed datastream is then the
 * concatenation of the contents of all the IDAT chunks. The encoder can divide
 * the compressed datastream into IDAT chunks however it wishes. (Multiple IDAT
 * chunks are allowed so that encoders can work in a fixed amount of memory;
 * typically the chunk size will correspond to the encoder's buffer size.) It is
 * important to emphasize that IDAT chunk boundaries have no semantic
 * significance and can occur at any point in the compressed datastream. A PNG
 * file in which each IDAT chunk contains only one data byte is valid, though
 * remarkably wasteful of space. (For that matter, zero-length IDAT chunks are
 * valid, though even more wasteful.)
 * 
 * @author Maciej
 * 
 */
public class IDAT extends Chunk {
	/**
	 * <table>
	 * <tr>
	 * <td>Type</td/>
	 * <td>Name</td>
	 * </tr>
	 * <tr>
	 * <td>0</td>
	 * <td>None</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>Sub</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>Up</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>Average</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td>Paeth</td>
	 * </tr>
	 * </table>
	 */
	private byte filterType = 0;

	private Filter filter;
	private Deflater compressor = new Deflater();

	public IDAT(RgbMatrix data) {
		this(data, (byte) 0);
	}

	public IDAT(RgbMatrix data, byte type) {
		super(0, IDAT.class.getSimpleName().getBytes());
		filterType = type;
		filter = new Filter(data);
		super.updateCRC();
	}

	/**
	 * <a href=
	 * "http://stackoverflow.com/questions/6173920/zlib-compression-using-deflate-and-inflate-classes-in-java"
	 * >Tutorial on how to use defleate</a>
	 */
	private byte[] encode(byte[] dataToCompress) {
		byte[] result = new byte[dataToCompress.length];
		compressor.setInput(dataToCompress);
		compressor.finish();
		// buffor
		super.chLength = compressor.deflate(result);
		result = Arrays.copyOfRange(result, 0, chLength);
		return result;
	}

	public void compress() {
		super.chData = encode(filter.applyFilter(filterType));
		super.updateCRC();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IDAT [filterType=").append(filterType)
				.append(", filter=").append(filter).append(", compressor=")
				.append(compressor).append(", chLength=").append(chLength)
				.append(", chType=").append(Arrays.toString(chType))
				.append(", chData=").append(Arrays.toString(chData))
				.append(", chCRC=").append(chCRC).append("]");
		return builder.toString();
	}

	// @Override
	// protected void updateCRC() {
	// // TODO Auto-generated method stub
	//
	// }

}
