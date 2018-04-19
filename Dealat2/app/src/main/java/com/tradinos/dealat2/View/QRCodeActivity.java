package com.tradinos.dealat2.View;
/*
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by developer on 16.04.18.
 */
/*
public class QRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        setContentView(mScannerView);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(QRCodeActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRCodeActivity.this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == REQUEST_CAMERA)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mScannerView.startCamera();
            }
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        // Prints scan results
        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        // Prints the scan format (qrcode, pdf417 etc.)
        // Logger.verbose("result", result.getBarcodeFormat().toString());

        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        //   Intent intent = new Intent();
        //  intent.putExtra(AppConstants.KEY_QR_CODE, result.getText());
        //  setResult(RESULT_OK, intent);
        //  finish();
    }
}
*/