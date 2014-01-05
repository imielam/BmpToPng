public class Main {

	/**
	 * For images of color type 3 (indexed color), filter type 0 (None) is
	 * usually the most effective. Note that color images with 256 or fewer
	 * colors should almost always be stored in indexed color format; truecolor
	 * format is likely to be much larger. <br/>
	 * Filter type 0 is also recommended for images of bit depths less than 8.
	 * For low-bit-depth grayscale images, it may be a net win to expand the
	 * image to 8-bit representation and apply filtering, but this is rare. <br/>
	 * For truecolor and grayscale images, any of the five filters may prove the
	 * most effective. If an encoder uses a fixed filter, the Paeth filter is
	 * most likely to be the best. <br/>
	 * For best compression of truecolor and grayscale images, we recommend an
	 * adaptive filtering approach in which a filter is chosen for each
	 * scanline. The following simple heuristic has performed well in early
	 * tests: compute the output scanline using all five filters, and select the
	 * filter that gives the smallest sum of absolute values of outputs.
	 * (Consider the output bytes as signed differences for this test.) This
	 * method usually outperforms any single fixed filter choice. However, it is
	 * likely that much better heuristics will be found as more experience is
	 * gained with PNG. <br/>
	 * Filtering according to these recommendations is effective on interlaced
	 * as well as noninterlaced images.
	 */
	public static final byte FILER_TYPE = 4;
	// public static final String SCIEZKA = "src/test.bmp";
	// public static final String SCIEZKA = "src/testBMP_MI.bmp";

	public static final String SCIEZKA = "src/blackbuck (1).bmp";

	public static void main(String[] args) {
		Bmp bmp = new Bmp(SCIEZKA);
		bmp.compute();
		Png png = new Png(bmp.getRgbMatrix());
		png.createPNG(FILER_TYPE);
		System.out.println("zakonczy³em konwersje.");
	}
}
