import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.RgbMatrix;

public class Bmp {
	public static final String SCIEZKA = "src/test.bmp";
	private RgbMatrix rgbMatrix;

	public void compute() {
		BufferedImage img;
		RgbMatrix data;
		try {
			img = ImageIO.read(new File(SCIEZKA));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		data = new RgbMatrix(img.getHeight(), img.getWidth());
		// int h = img.getHeight();
		// int w = img.getWidth();
		// short[][][] pixele = new short[w][h][3];
		int[] rgb;

		for (int i = 0; i < data.getWidth(); i++) {
			for (int j = 0; j < data.getHeight(); j++) {
				rgb = getPixelData(img, i, j);
				// od razu odwracam sobie kolory, ktore mozesz sobie
				// zakomentowac jak niepotrzebujesz
				setPixelData(img, i, j, new Color(rgb[2], rgb[1], rgb[0]));
				for (int k = 0; k < rgb.length; k++) {
					data.getData()[j][i][k] = (short) rgb[k];
				}
			}
		}
		rgbMatrix = data;
		// return pixele;
	}

	public static void main(String[] args) throws IOException {

		// sciezka = "F:\\workspace\\BmpToPng\\src\\test.bmp";
		BufferedImage img = ImageIO.read(new File(SCIEZKA));
		int h = img.getHeight();
		int w = img.getWidth();
		int[][][] pixele = new int[w][h][3];
		int[] rgb;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				rgb = getPixelData(img, i, j);
				// od razu odwracam sobie kolory, ktore mozesz sobie
				// zakomentowac jak niepotrzebujesz
				setPixelData(img, i, j, new Color(rgb[2], rgb[1], rgb[0]));
				for (int k = 0; k < rgb.length; k++) {
					pixele[i][j][k] = rgb[k];
				}
			}
		}
		// System.out.println(pixele[20][19][0]);
		// System.out.println(pixele[20][19][1]);
		// System.out.println(pixele[20][19][2]);
		System.out.println(h + "px - wysokosc");
		System.out.println(w + "px - szerokosc");
		System.out.println(h * w + " - liczba pixeli");
		File outputfile = new File("saved.png");
		ImageIO.write(img, "png", outputfile);

	}

	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		int rgb[] = new int[] { (argb >> 16) & 0xff, // r
				(argb >> 8) & 0xff, // g
				(argb) & 0xff // b
		};

		// System.out.println(x + "x" + y + " r: " + rgb[0] + " g: " + rgb[1]
		// + " b: " + rgb[2]);
		return rgb;
	}

	private static void setPixelData(BufferedImage img, int x, int y, Color c) {
		img.setRGB(x, y, c.getRGB());
	}

	public RgbMatrix getRgbMatrix() {
		return rgbMatrix;
	}

}