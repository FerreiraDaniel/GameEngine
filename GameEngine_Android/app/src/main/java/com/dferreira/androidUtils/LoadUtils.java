package com.dferreira.androidUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dferreira.commons.models.TextureData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Set of util methods to help load the application
 */
class LoadUtils {

    private final static String TAG = "LoadUtils";

    /**
     * Number of components that the texture has (RGBA)
     */
    private final static int NUMBER_OF_COMPONENTS = 4;


    /**
     * Load texture from resource
     *
     * @param resourceId id of the resource where the texture exists
     * @return The data of the texture
     */
    static TextureData decodeTextureFile(Context context, int resourceId) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        int bufferI = bitmap.getWidth() * bitmap.getHeight();
        int[] buffer = new int[bufferI];

        bitmap.getPixels(buffer, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        //ARGB -> RGBA
        for (int i = 0; i < bufferI; i++) {
            int pixel = buffer[i];
            buffer[i] = (((pixel >> 16) & 0xFF) << 24) + //R
                    (((pixel >> 8) & 0xFF) << 16) + //G
                    ((pixel & 0xFF) << 8) +  //B
                    ((pixel >> 24) & 0xFF); //A

        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferI * NUMBER_OF_COMPONENTS);
        byteBuffer.asIntBuffer().put(buffer).position(0);
        TextureData textureData = new TextureData(byteBuffer, bitmap.getWidth(), bitmap.getHeight());
        bitmap.recycle();
        return textureData;
    }


    /**
     * Reads a string from a certain resource
     *
     * @param rawResourceId id of the resource where the text exists
     * @return The text that exists in the resource
     */
    static String readTextFromRawResource(Context context, int rawResourceId) {
        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String str = null;

        int bytesRead;
        try {
            bytesRead = inputStream.read();
            while (bytesRead != -1) {
                byteArrayOutputStream.write(bytesRead);
                bytesRead = inputStream.read();
            }
            inputStream.close();
            str = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Read string failed", e);
        }
        return str;
    }
}
