package com.tradinos.dealat2.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by developer on 20.02.18.
 */

public class ImageDecoder {

    private final int smallFactor = 150, largeFactor = 500;

    public Bitmap decodeLargeImage(String path) {
        //  return BitmapFactory.decodeFile(path);
        return decodeImage(path, this.largeFactor);
    }

    public Bitmap decodeSmallImage(String path) {

        return decodeImage(path, this.smallFactor);
    }

    private Bitmap decodeImage(String path, int factor) {
        Bitmap bm;
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

    private int calculateInSampleSize(BitmapFactory.Options options, int factor) {
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

    public String getVideoPath(Uri uri, ContentResolver contentResolver){
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));

        return filePath;
    }

    public File ConvertBitmapToFile(String path) {

        File f = new File(path);

        ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
        try {
            bmpStream.flush();//to avoid out of memory error
            bmpStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = decodeLargeImage(path);

        String extension = path.substring(path.lastIndexOf(".") + 1);
        if (extension.equals("png"))
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bmpStream);
        else
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpStream);

        byte[] bmpPicByteArray = bmpStream.toByteArray();

        FileOutputStream fo;

        try {
            fo = new FileOutputStream(f);
            fo.write(bmpPicByteArray);
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }
}
