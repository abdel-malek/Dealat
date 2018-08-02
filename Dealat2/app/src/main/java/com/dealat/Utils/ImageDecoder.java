package com.dealat.Utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by developer on 20.02.18.
 */

public class ImageDecoder {

    private final int smallFactor = 120, largeFactor = 400; // 150 // 500

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

    public String getVideoPath(Uri uri, ContentResolver contentResolver) {
        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));

        return filePath;
    }

    public File ConvertBitmapToFile(String path) {

        // create new file to write the compressed bitmap to, so that the original image keeps its quality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "Compressed_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Dealat");
        if (!storageDir.exists())
            storageDir.mkdirs();

        try {
            File f = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */
                    storageDir      /* directory */);

            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            try {
                bmpStream.flush();//to avoid out of memory error
                bmpStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = decodeLargeImage(path);

            //Hint to the compressor, 0-100. 0 meaning compress for small size,
            // 100 meaning compress for max quality.
            // Some formats, like PNG which is lossless, will ignore the quality setting
            String extension = path.substring(path.lastIndexOf(".") + 1);
            if (extension.equals("png"))
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bmpStream);
            else
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bmpStream);

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        // File f = new File(path);

        return null;
    }
}
