package com.dealat.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.dealat.BuildConfig;
import com.dealat.Controller.DealatController;
import com.dealat.Model.About;
import com.dealat.R;
import com.tradinos.core.network.SuccessCallback;

/**
 * Created by developer on 19.04.18.
 */

public class AboutActivity extends MasterActivity {

   // private final int REQUEST_CALL = 6;
    private About about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        ShowProgressDialog();
        DealatController.getInstance(mController).getAbout(new SuccessCallback<About>() {
            @Override
            public void OnSuccess(About result) {
                HideProgressDialog();
                about = result;

                ((TextView) findViewById(R.id.textDesc)).setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

                if (about.getFacebookLink() != null)
                    findViewById(R.id.buttonFacebook).setVisibility(View.VISIBLE);

                if (about.getLinkedInLink() != null)
                    findViewById(R.id.buttonLinkedIn).setVisibility(View.VISIBLE);

                if (about.getYoutubeLink() != null)
                    findViewById(R.id.buttonYoutube).setVisibility(View.VISIBLE);

                if (about.getTwitterLink() != null)
                    findViewById(R.id.buttonTwitter).setVisibility(View.VISIBLE);

                if (about.getInstagramLink() != null)
                    findViewById(R.id.buttonInstagram).setVisibility(View.VISIBLE);

                ((TextView) findViewById(R.id.email)).setText(about.getEmail());

                ((TextView) findViewById(R.id.textView)).setText(htmlContent(about.getContent()));

                if (about.getPhone() != null) {

                    ((TextView) findViewById(R.id.textViewPhone)).setText(htmlContent(about.getPhone()));
                    findViewById(R.id.textViewPhone).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {

    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CALL) {
//
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:" + getPhoneNumber(about.getPhone())));
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
//                startActivity(callIntent);
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFacebook:
                openUrl(about.getFacebookLink());
                break;

            case R.id.buttonTwitter:
                openUrl(about.getTwitterLink());
                break;

            case R.id.buttonYoutube:
                openUrl(about.getYoutubeLink());
                break;

            case R.id.buttonLinkedIn:
                openUrl(about.getLinkedInLink());
                break;

            case R.id.buttonInstagram:
                openUrl(about.getInstagramLink());
                break;

//            case R.id.textViewPhone:
//
//                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mContext,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(AboutActivity.this,
//                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//                } else {
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//
//                    callIntent.setData(Uri.parse("tel:" + getPhoneNumber(about.getPhone())));
//                    startActivity(callIntent);
//                }
//                break;
        }
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.parentPanel), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });

        snackbar.show();
    }

    private void openUrl(String url) {
        if (url != null) {
            Uri webpage = Uri.parse(url);

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            if (intent.resolveActivity(mContext.getPackageManager()) != null)
                startActivity(intent);
        }
    }
}
