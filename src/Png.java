import headers.Chunk;
import headers.IDAT;
import headers.IEND;
import headers.IHDR;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import utils.RgbMatrix;

public class Png {
	// FileOutputStream stream = null;
	static byte[] neccessaryHeader = { (byte) 137, 80, 78, 71, 13, 10, 26, 10 };
	public final static String PATH = "src/testPNG";
	ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
	RgbMatrix rgbMatrix;

	public Png(RgbMatrix rgbMatrix) {
		super();
		this.rgbMatrix = rgbMatrix;
	}

	private String getNecesseryHeader() {
		StringBuilder sb = new StringBuilder();
		for (byte b : neccessaryHeader) {
			sb.append(b);
		}
		return sb.toString();
	}

	private void saveToFile() {
		Path path = Paths.get(PATH);
		Path path_png = Paths.get(PATH + ".png");
		// TODO: Poprawiæ allokowanie danych
		ByteBuffer b = ByteBuffer.allocate(100000);
		int limit = 0;
		b.put(neccessaryHeader);
		limit += neccessaryHeader.length;
		for (Chunk c : chunkList) {
			byte[] chunkByte = c.byteToWrite();
			b.put(chunkByte);
			limit += chunkByte.length;
		}
		// b.limit(limit);

		// byte[] arrayB = b.array();
		// ;

		// ByteBuffer newB = ByteBuffer.allocate(limit);
		// newB.put(b);

		try {
			Files.write(path, Arrays.copyOf(b.array(), limit));
			Files.write(path_png, Arrays.copyOf(b.array(), limit));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createPNG(byte type) {
		chunkList.add(new IHDR(rgbMatrix.getWidth(), rgbMatrix.getHeight()));
		IDAT data = new IDAT(rgbMatrix, type);
		data.compress();
		chunkList.add(data);
		chunkList.add(new IEND());
		System.out.print(getNecesseryHeader());
		for (Chunk c : chunkList) {
			System.out.print(c.stringToWrite());
		}
		saveToFile();

	}

}
