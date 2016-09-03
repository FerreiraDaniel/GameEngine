package com.dferreira.commons;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.newdawn.slick.opengl.PNGDecoder;

import com.dferreira.commons.models.TextureData;

public class LoadUtils {

	/**
	 * Number of components that the texture has (RGBA)
	 */
	private final static int NUMBER_OF_COMPONENTS = 4;

	/**
	 * Reads a string from a certain resource
	 *
	 * @param fileName
	 *            id of the resource where the text exists
	 * @return The text that exists in the resource
	 */
	public static String readTextFromRawResource(String fileName) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		String str = null;

		int bytesRead;
		try {
			InputStream inputStream = new FileInputStream(fileName);
			bytesRead = inputStream.read();
			while (bytesRead != -1) {
				byteArrayOutputStream.write(bytesRead);
				bytesRead = inputStream.read();
			}
			inputStream.close();
			str = byteArrayOutputStream.toString();
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Load texture from resource
	 *
	 * @param context
	 *            Context where this method will be called
	 * @param resourceId
	 *            id of the resource where the texture exists
	 * @return Id from the texture that was bounded in openGL
	 */
	public static TextureData loadTexture(String fileName) {
		int width = 0;
		int height = 0;

		ByteBuffer buffer = null;

		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(NUMBER_OF_COMPONENTS * width * height);
			decoder.decode(buffer, NUMBER_OF_COMPONENTS * width, PNGDecoder.RGBA);
			buffer.flip();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new TextureData(buffer, width, height);
	}
}
