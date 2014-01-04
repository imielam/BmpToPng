import headers.Chunk;
import headers.IDAT;
import headers.IEND;
import headers.IHDR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.lang.model.element.NestingKind;

import utils.RgbMatrix;


public class Png {
//	FileOutputStream stream = null;
	static byte[] neccessaryHeader = {(byte) 137,80,78,71,13,10,26,10};
	public final static String PATH = "src/testPNG";
	ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
	RgbMatrix rgbMatrix;
		
	public Png(RgbMatrix rgbMatrix) {
		super();
		this.rgbMatrix = rgbMatrix;
	}
	
	private String getNecesseryHeader(){
		StringBuilder sb = new StringBuilder();
		for (byte b : neccessaryHeader){
			sb.append(b);
		}
		return sb.toString();
	}
	
	private void saveToFile(){
		Path path = Paths.get(PATH);
		ByteBuffer b = ByteBuffer.allocate(100000);
		int limit = 0;
		b.put(neccessaryHeader);
		limit += neccessaryHeader.length;
		for (Chunk c : chunkList){
			byte[] chunkByte = c.byteToWrite();
			b.put(chunkByte);
			limit += chunkByte.length;
		}
		b.limit(limit);
		try {
			Files.write(path, b.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void createPNG(){	
//		FileOutputStream stream = null;
//		try {
//			stream = new FileOutputStream("src/testPNG");
//			stream.write(neccessaryHeader);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				stream.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		chunkList.add(new IHDR(rgbMatrix.getWidth(), rgbMatrix.getHeight()));
		IDAT data = new IDAT(rgbMatrix);
		data.compress();
		chunkList.add(data);
		chunkList.add(new IEND());
		System.out.print(getNecesseryHeader());
		for (Chunk c : chunkList){
			System.out.print(c.stringToWrite());
		}
		saveToFile();
		
	}

}
