package com.tradinos.dealat2.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by developer on 20.02.18.
 */

public class ImageDecoder {

    private final int smallFactor = 150, largeFactor = 300;

    public Bitmap decodeLargeImage(String path) {
        //  return BitmapFactory.decodeFile(path);
        return decodeImage(path, this.largeFactor);
    }

    public Bitmap decodeSmallImage(String path) {

        return decodeImage(path, this.smallFactor);
    }

    public Bitmap decodeImage(String path, int factor) {
        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, factor);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int factor) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > factor || width > factor) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) factor);
            } else {
                inSampleSize = Math.round((float) width / (float) factor);
            }
        }
        return inSampleSize;
    }
/*
    public File ConvertBitmapToFile(String path) throws IOException {

        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
        try {
            bmpStream.flush();//to avoid out of memory error
            bmpStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = decodeLargeImage(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpStream);
        byte[] bmpPicByteArray = bmpStream.toByteArray();

        FileOutputStream fo;

        try {
            fo = new FileOutputStream(f);
            fo.write(bmpStream.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    public Bitmap codec(String path){
        File file = new File(path);

        FileInputStream fileInputStream = null;
        try {
             fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap original = BitmapFactory.decodeStream(fileInputStream);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.JPEG, 3, os);
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }*/
}
