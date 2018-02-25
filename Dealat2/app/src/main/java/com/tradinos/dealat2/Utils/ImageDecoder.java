package com.tradinos.dealat2.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by developer on 20.02.18.
 */

public class ImageDecoder {

    private final int factor = 150;

    public Bitmap decodeFile(String path){
        return BitmapFactory.decodeFile(path);
    }

    public Bitmap decodeSampledBitmapFromUri(String path) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > factor || width > factor) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)factor);
            } else {
                inSampleSize = Math.round((float)width / (float)factor);
            }
        }
        return inSampleSize;
    }

  /*  File ConvertBitmapToFile(String path) throws IOException {


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
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpStream);
       // byte[] bmpPicByteArray = bmpStream.toByteArray();

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

    }*/
}
