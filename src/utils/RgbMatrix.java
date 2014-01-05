package utils;

import java.util.Arrays;

public class RgbMatrix {
	private byte[][][] data;
	private int height;
	private int width;
	public final int numberOfSamplesPerPixel = 3;
	private static final int SIZE_OF_SAMPLE = 1;

	public int getBPP() {
		return numberOfSamplesPerPixel * SIZE_OF_SAMPLE;
	}

	public RgbMatrix(int height, int width) {
		super();
		this.height = height;
		this.width = width;
		data = new byte[height][width][numberOfSamplesPerPixel];
	}

	public byte[][][] getData() {
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
