package com.dferreira.commons;

import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.utils.Utils;

import org.newdawn.slick.opengl.PNGDecoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class LoadUtils {

    /**
     * Number of components that the texture has (RGBA)
     */
    private final static int NUMBER_OF_COMPONENTS = 4;

    /**
     * Used to go from the path of classes to the path of the resources
     */
    private final static String RESOURCES_PATH_RELATIVE_TO_CLASSES = "../../resources/main/";

    /**
     * Due to the differences how resources are saved during debug
     * phase and when they are inside jar a file can return null just because needs
     * a different way to access (This method handles that)
     *
     * @param fileName Name of the file to open the sream
     * @return The stream got it if possible
     */
    public static InputStream getInputStreamToResource(String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            //Tries another way around
            URL url = LoadUtils.class.getClassLoader().getResource(fileName);
            if (url == null) {
                //Probably is working in the EDI
                URL baseUrl = LoadUtils.class.getClassLoader().getResource(Utils.EMPTY_STRING);
                if (baseUrl != null) {
                    try {
                        FileInputStream file = new FileInputStream(baseUrl.getPath() + RESOURCES_PATH_RELATIVE_TO_CLASSES + fileName);
                        return file;
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                try {
                    inputStream = url.openStream();
                } catch (IOException e2) {
                    return null;
                }
            }
        }
        return inputStream;
    }

    /**
     * Reads a string from a certain resource
     *
     * @param fileName id of the resource where the text exists
     * @return The text that exists in the resource
     */
    public static String readTextFromRawResource(String fileName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String str = null;

        int bytesRead;
        try {
            InputStream inputStream = getInputStreamToResource(fileName);
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
     * @param fileName
     * @return
     */
    public static TextureData loadTexture(String fileName) {
        int width = 0;
        int height = 0;

        ByteBuffer buffer = null;

        try {
            InputStream in = getInputStreamToResource(fileName);
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
