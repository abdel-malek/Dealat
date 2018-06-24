package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dealat.R;

/**
 * Created by developer on 23.04.18.
 */

public class PublicNotificationActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_public_notification);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("txt");

        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.textView)).setText(text);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonTrue:
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonFalse:
                finish();
                break;
        }
    }
}
