package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tradinos.dealat2.R;

/**
 * Created by developer on 18.02.18.
 */

public class LoginActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {

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
        Intent intent;

        switch (view.getId()){

            case R.id.buttonTrue: //Register
                break;

            case R.id.buttonFalse: //Skip
                intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonGmail:
                break;

            case R.id.buttonFacebook:
                break;
        }
    }
}
