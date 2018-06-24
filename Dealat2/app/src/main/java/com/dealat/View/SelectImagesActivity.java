package com.dealat.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.GridView;

import com.dealat.Adapter.ImageAdapter;
import com.dealat.Model.Image;
import com.dealat.MyApplication;
import com.dealat.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by developer on 20.02.18.
 */

public class SelectImagesActivity extends MasterActivity {

    private final int REQUEST_CAMERA = 12,
            REQUEST_PERMISSION_READ = 13, REQUEST_PERMISSION_WRITE = 14, REQUEST_CAMERA_PERM = 15;

    private ImageAdapter adapter;

    //views
    private GridView gridView;

    File photoFile;
    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_images);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        Image.ImageCounter = getIntent().getIntExtra("counter", 0);
    }

    @Override
    public void showData() {

        // if device has no camera
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            findViewById(R.id.container).setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(SelectImagesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SelectImagesActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
        } else {
            adapter = new ImageAdapter(mContext, getAllShownImagesPath());
            gridView.setAdapter(adapter);
        }
    }

    @Override
    public void assignUIReferences() {
        gridView = findViewById(R.id.gridView);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container: // camera

                if (Image.ImageCounter >= Image.MAX_IMAGES)
                    showMessageInToast(getString(R.string.toastMaxImages));

                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(SelectImagesActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectImagesActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(SelectImagesActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SelectImagesActivity.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERM);

                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            // N is for Nougat Api 24 Android 7
                            if (Build.VERSION_CODES.N <= android.os.Build.VERSION.SDK_INT) {
                                photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName()
                                        + ".provider", photoFile);
                            } else
                                photoUri = Uri.fromFile(photoFile);

                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                        }
                    }
                }

                break;

            case R.id.buttonTrue: //done

                Intent intent = new Intent();
                intent.putExtra("images", (ArrayList) adapter.getSelectedImages());
                setResult(RESULT_OK, intent);
                finish();

                break;

            case R.id.buttonFalse: //cancel
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                adapter.addCapturedImage(new Image(MyApplication.getImagePath()));
                adapter.notifyDataSetChanged();

            } else if (resultCode == RESULT_CANCELED) {

                if (photoFile != null)
                    if (photoFile.delete())
                        MediaScannerConnection.scanFile(mContext, new String[]{photoFile.getPath()}, new String[]{"image/*"}, null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_READ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                adapter = new ImageAdapter(mContext, getAllShownImagesPath());
                gridView.setAdapter(adapter);
            } else { // to avoid NullPointerException in case Done or camera button is clicked
                adapter = new ImageAdapter(mContext, new ArrayList<Image>());
            }
        }
    }

    private ArrayList<Image> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<Image> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(0, new Image(absolutePathOfImage));
        }
        cursor.close();

        return listOfAllImages;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Dealat");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        MediaScannerConnection.scanFile(mContext, new String[]{image.getPath()}, new String[]{"image/*"}, null);

        // Save a file: path for use with ACTION_VIEW intents
        MyApplication.saveImagePath(image.getAbsolutePath());

        return image;
    }
}
