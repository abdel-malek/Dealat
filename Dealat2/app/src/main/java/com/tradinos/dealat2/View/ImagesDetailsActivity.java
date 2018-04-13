package com.tradinos.dealat2.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.tradinos.dealat2.Adapter.ImageDetailsAdapter;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.DownloadRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by developer on 01.04.18.
 */

public class ImagesDetailsActivity extends MasterActivity {

    private final int REQUEST_PERMISSION_WRITE = 1;
    private Ad currentAd;

    //Views
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_images_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        currentAd = (Ad) getIntent().getSerializableExtra("ad");
    }

    @Override
    public void showData() {
        viewPager.setAdapter(new ImageDetailsAdapter(getSupportFragmentManager(), currentAd.getImagesPaths(),
                currentAd.getMainVideoUrl(), currentAd.getTemplate()));
        int page = getIntent().getIntExtra("page", 0);
        viewPager.setCurrentItem(page);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void assignUIReferences() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) {
            if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(ImagesDetailsActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                downloadImage();
            } else {
                ActivityCompat.requestPermissions(ImagesDetailsActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
            }
        }
    }

    private void downloadImage() {
        int page = viewPager.getCurrentItem();

        DownloadRequest request = new DownloadRequest(Request.Method.GET, MyApplication.getBaseUrl() + currentAd.getImagePath(page), new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                if (response != null) {
                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Dealat");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                    String name = "Ad_" + currentAd.getId() + "_" + timeStamp + ".jpg";

                    File file = new File(directory, name);
                    if (file.exists()) {
                        file.delete();
                    }

                    FileOutputStream outputStream;

                    try {
                        outputStream = new FileOutputStream(file);
                        // outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                        outputStream.write(response);
                        outputStream.close();

                        MediaScannerConnection.scanFile(mContext, new String[]{file.getPath()}, new String[]{"image/*"}, null);

                        showMessageInToast(R.string.toastImageSave);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, null);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImage();
            }
        }
    }

}
