package com.tradinos.dealat2.View;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;

import com.tradinos.dealat2.Adapter.ImageAdapter;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.R;

import java.util.ArrayList;

/**
 * Created by developer on 20.02.18.
 */

public class SelectImagesActivity extends MasterActivity {

    private final int REQUEST_SUBMIT = 1, REQUEST_CAMERA = 2;

    private Category selectedCategory;

    private ImageAdapter adapter;

    //views
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_images);
        super.onCreate(savedInstanceState);

        Image.ImageCounter = 0;
    }

    @Override
    public void getData() {
        selectedCategory = (Category) getIntent().getSerializableExtra("category");
    }

    @Override
    public void showData() {
        adapter = new ImageAdapter(mContext, getAllShownImagesPath());
        gridView.setAdapter(adapter);
    }

    @Override
    public void assignUIReferences() {
        gridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container: // camera


                break;

            case R.id.buttonTrue: //done
                Intent intent = new Intent(mContext, ItemInfoActivity.class);

                intent.putExtra("images", (ArrayList) adapter.getSelectedImages());
                intent.putExtra("category", selectedCategory);
                startActivityForResult(intent, REQUEST_SUBMIT);
                break;

            case R.id.buttonFalse: //cancel
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == REQUEST_SUBMIT)
                finish();
        else if (requestCode == REQUEST_CAMERA){

            }
    }

    private ArrayList<Image> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<Image> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage = null;
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

            listOfAllImages.add(new Image(absolutePathOfImage));
        }
        return listOfAllImages;
    }
}
