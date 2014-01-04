public class Main {
	public static final byte FILER_TYPE = 1;

	public static void main(String[] args) {
		Bmp bmp = new Bmp();
		bmp.compute();
		Png png = new Png(bmp.getRgbMatrix());
		png.createPNG(FILER_TYPE);
	}
}
