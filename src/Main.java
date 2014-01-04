import java.util.Arrays;

import headers.IHDR;


public class Main {
	public static void main(String[] args) {
		Bmp bmp = new Bmp();
		bmp.compute();
//		System.out.println(bmp.getRgbMatrix());
//		byte[] tmp = IHDR.class.getSimpleName().getBytes();
//		for (byte b : tmp) {
//			System.out.print((char)b);
//		}
////		System.out.println(Arrays.toString(tmp));
		Png png = new Png(bmp.getRgbMatrix());
		png.createPNG();
	}
}
