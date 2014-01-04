package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Filter {
	private RgbMatrix matrix;
	private int bpp = 3;

	public Filter(RgbMatrix matrix) {
		super();
		this.matrix = matrix;
	}

	/**
	 * The Sub() filter transmits the difference between each byte and the value
	 * of the corresponding byte of the prior pixel.<br/>
	 * To compute the Sub() filter, apply the following formula to each byte of
	 * the scanline:<br/>
	 * Sub(x) = Raw(x) - Raw(x-bpp)<br/>
	 * where x ranges from zero to the number of bytes representing the scanline
	 * minus one, Raw() refers to the raw data byte at that byte position in the
	 * scanline, and bpp is defined as the number of bytes per complete pixel,
	 * rounding up to one. For example, for color type 2 with a bit depth of 16,
	 * bpp is equal to 6 (three samples, two bytes per sample); for color type 0
	 * with a bit depth of 2, bpp is equal to 1 (rounding up); for color type 4
	 * with a bit depth of 16, bpp is equal to 4 (two-byte grayscale sample,
	 * plus two-byte alpha sample). <br/>
	 * Note this computation is done for each byte, regardless of bit depth. In
	 * a 16-bit image, each MSB is predicted from the preceding MSB and each LSB
	 * from the preceding LSB, because of the way that bpp is defined. <br/>
	 * Unsigned arithmetic modulo 256 is used, so that both the inputs and
	 * outputs fit into bytes. The sequence of Sub values is transmitted as the
	 * filtered scanline. <br/>
	 * For all x < 0, assume Raw(x) = 0.
	 */
	private byte[][] Sub() {
		byte[][] data = None();
		byte[][] newDate = data.clone();
		int c = 0;
		for (byte[] i : data) {
			for (int j = 0; j < i.length; j++) {// = bpp){
				byte tmp = ((j - bpp) < 0) ? (byte) 0 : i[j - bpp];
				newDate[c][j] = (byte) (i[j] - tmp);
			}
			c++;
		}
		return newDate;
	}

	/**
	 * The Up() filter is just like the Sub() filter except that the pixel
	 * immediately above the current pixel, rather than just to its left, is
	 * used as the predictor. <br/>
	 * To compute the Up() filter, apply the following formula to each byte of
	 * the scanline: <br/>
	 * Up(x) = Raw(x) - Prior(x)<br/>
	 * where x ranges from zero to the number of bytes representing the scanline
	 * minus one, Raw() refers to the raw data byte at that byte position in the
	 * scanline, and Prior(x) refers to the unfiltered bytes of the prior
	 * scanline. <br/>
	 * Note this is done for each byte, regardless of bit depth. Unsigned
	 * arithmetic modulo 256 is used, so that both the inputs and outputs fit
	 * into bytes. The sequence of Up values is transmitted as the filtered
	 * scanline. <br/>
	 * On the first scanline of an image (or of a pass of an interlaced image),
	 * assume Prior(x) = 0 for all x.
	 */
	private void Up() {

	}

	/**
	 * The Average() filter uses the average of the two neighboring pixels (left
	 * and above) to predict the value of a pixel. <br/>
	 * To compute the Average() filter, apply the following formula to each byte
	 * of the scanline: <br/>
	 * Average(x) = Raw(x) - floor((Raw(x-bpp)+Prior(x))/2)<br/>
	 * where x ranges from zero to the number of bytes representing the scanline
	 * minus one, Raw() refers to the raw data byte at that byte position in the
	 * scanline, Prior() refers to the unfiltered bytes of the prior scanline,
	 * and bpp is defined as for the Sub() filter. <br/>
	 * Note this is done for each byte, regardless of bit depth. The sequence of
	 * Average values is transmitted as the filtered scanline. <br/>
	 * The subtraction of the predicted value from the raw byte must be done
	 * modulo 256, so that both the inputs and outputs fit into bytes. However,
	 * the sum Raw(x-bpp)+Prior(x) must be formed without overflow (using at
	 * least nine-bit arithmetic). floor() indicates that the result of the
	 * division is rounded to the next lower integer if fractional; in other
	 * words, it is an integer division or right shift operation. <br/>
	 * For all x < 0, assume Raw(x) = 0. On the first scanline of an image (or
	 * of a pass of an interlaced image), assume Prior(x) = 0 for all x.
	 */
	private void Averege() {

	}

	/**
	 * The Paeth() filter computes a simple linear function of the three
	 * neighboring pixels (left, above, upper left), then chooses as predictor
	 * the neighboring pixel closest to the computed value. This technique is
	 * due to Alan W. Paeth <a href=
	 * "http://www.libpng.org/pub/png/spec/1.2/PNG-References.html#B.PAETH"
	 * >[PAETH]</a>. <br/>
	 * To compute the Paeth() filter, apply the following formula to each byte
	 * of the scanline: <br/>
	 * Paeth(x) = Raw(x) - PaethPredictor(Raw(x-bpp), Prior(x), Prior(x-bpp))<br/>
	 * where x ranges from zero to the number of bytes representing the scanline
	 * minus one, Raw() refers to the raw data byte at that byte position in the
	 * scanline, Prior() refers to the unfiltered bytes of the prior scanline,
	 * and bpp is defined as for the Sub() filter. <br/>
	 * Note this is done for each byte, regardless of bit depth. Unsigned
	 * arithmetic modulo 256 is used, so that both the inputs and outputs fit
	 * into bytes. The sequence of Paeth values is transmitted as the filtered
	 * scanline. <br/>
	 * The PaethPredictor() function is defined by the following pseudocode:
	 * <p>
	 * function PaethPredictor (a, b, c)<br/>
	 * begin<br/>
	 * ; a = left, b = above, c = upper left<br/>
	 * p := a + b - c ; initial estimate<br/>
	 * pa := abs(p - a) ; distances to a, b, c<br/>
	 * pb := abs(p - b)<br/>
	 * pc := abs(p - c)<br/>
	 * ; return nearest of a,b,c,<br/>
	 * ; breaking ties in order a,b,c.<br/>
	 * if pa <= pb AND pa <= pc then return a<br/>
	 * else if pb <= pc then return b<br/>
	 * else return c<br/>
	 * end
	 * </p>
	 * The calculations within the PaethPredictor() function must be performed
	 * exactly, without overflow. Arithmetic modulo 256 is to be used only for
	 * the final step of subtracting the function result from the target byte
	 * value. <br/>
	 * <b>Note that the order in which ties are broken is critical and must not
	 * be altered. </b>The tie break order is: pixel to the left, pixel above,
	 * pixel to the upper left. (This order differs from that given in Paeth's
	 * article.) <br/>
	 * For all x < 0, assume Raw(x) = 0 and Prior(x) = 0. On the first scanline
	 * of an image (or of a pass of an interlaced image), assume Prior(x) = 0
	 * for all x.
	 */
	private void Peath() {

	}

	/**
	 * The encoder can choose which of these filter algorithms to apply on a
	 * scanline-by-scanline basis. In the image data sent to the compression
	 * step, each scanline is preceded by a filter-type byte that specifies the
	 * filter algorithm used for that scanline. <br/>
	 * Filtering algorithms are applied to bytes, not to pixels, regardless of
	 * the bit depth or color type of the image. The filtering algorithms work
	 * on the byte sequence formed by a scanline that has been represented as
	 * described in Image layout. If the image includes an alpha channel, the
	 * alpha data is filtered in the same way as the image data. <br/>
	 * For all filters, the bytes "to the left of" the first pixel in a scanline
	 * must be treated as being zero. For filters that refer to the prior
	 * scanline, the entire prior scanline must be treated as being zeroes for
	 * the first scanline of an image (or of a pass of an interlaced image).
	 */
	public byte[] applyFilter(byte type) {
		switch (type) {
		case (byte) 1:
			return transormToArray(type, Sub());
		case (byte) 2:
			Up();
			break;
		case (byte) 3:
			Averege();
			break;
		case (byte) 4:
			Peath();
			break;
		case (byte) 0:
			return transormToArray(type, None());
		default:
			throw new RuntimeException("Nie ma takiego filtru!!");
		}
		return null;

	}

	private byte[] transormToArray(byte type, byte[][] multidimData) {
		ByteBuffer b = ByteBuffer.allocate((matrix.getHeight() + 1)
				* multidimData[0].length);
		for (byte[] i : multidimData) {
			b.put(type);
			b.put(i);
		}
		return b.array();
	}

	/**
	 * With the None() filter, the scanline is transmitted unmodified; it is
	 * necessary only to insert a filter-type byte before the data.
	 */
	private byte[][] None() {
		byte[][] newData = new byte[matrix.getHeight()][];
		for (int i = 0; i < matrix.getHeight(); i++) {
			ByteBuffer b = ByteBuffer.allocate(matrix.getWidth() * 3);
			b.order(ByteOrder.BIG_ENDIAN);
			for (byte[] j : matrix.getData()[i]) {
				for (byte k : j) {
					b.put(k);
				}
			}
			newData[i] = b.array();
		}
		return newData;
	}
}
