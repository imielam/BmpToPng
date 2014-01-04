package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class RgbMatrix {
	private short[][][] data;
	private int height;
	private int width;

	public RgbMatrix(int height, int width) {
		super();
		this.height = height;
		this.width = width;
		data = new short[height][width][3];
	}

	public short[][][] getData() {
		return data;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RgbMatrix [data=").append(Arrays.deepToString(data))
				.append(", height=").append(height).append(", width=")
				.append(width).append("]");
		return builder.toString();
	}

}
