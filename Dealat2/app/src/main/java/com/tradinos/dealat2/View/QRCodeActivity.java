package com.tradinos.dealat2.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.Result;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.ParentController;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by developer on 16.04.18.
 */

public class QRCodeActivity extends MasterActivity implements ZXingScannerView.ResultHandler {

    private final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private TextView textViewCode;

    @Override
    public void onCreate(Bundle state) {

        setContentView(R.layout.activity_qr);
        super.onCreate(state);

        // Programmatically initialize the scanner view
        //  mScannerView = new ZXingScannerView(this);
        // Set the scanner view as the content view
        // setContentView(mScannerView);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(QRCodeActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRCodeActivity.this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else
            mScannerView.startCamera();
    }

    @Override
    public void getData() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        mScannerView = findViewById(R.id.imageView);
        textViewCode = findViewById(R.id.textView);
    }

    @Override
    public void assignActions() {

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScannerView.startCamera();
            }
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here

        ShowProgressDialog();
        UserController.getInstance(new ParentController(mContext, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message, String data) {

                HideProgressDialog();

                showMessageInToast(Message);
                //If you would like to resume scanning, call this method below:
                mScannerView.resumeCameraPreview(QRCodeActivity.this);
            }
        })).sendQrCode(result.getText(), new SuccessCallback<String>() {
            @Override
            public void OnSuccess(String result) {
                HideProgressDialog();
                textViewCode.setText(result);

                textViewCode.setVisibility(View.VISIBLE);
            }
        });

        // Prints the scan format (qrcode, pdf417 etc.)
        // Logger.verbose("result", result.getBarcodeFormat().toString());

        //   Intent intent = new Intent();
        //  intent.putExtra(AppConstants.KEY_QR_CODE, result.getText());
        //  setResult(RESULT_OK, intent);
        //  finish();
    }

    @Override
    public void onClick(View view) {

    }
}
