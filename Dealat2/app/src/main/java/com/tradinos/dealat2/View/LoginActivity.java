package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.User;
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

      /*  User user = new User();
        user.setId("1");
        user.setUsername("097367429");
        user.setName("ola");
        user.setPassword("3dfe48960147c43eab5e8731a6a45a07");
        new CurrentAndroidUser(mContext).Save(user);

        ShowProgressDialog();
        AdController.getInstance(mController).test(new SuccessCallback<String>() {
            @Override
            public void OnSuccess(String result) {
                HideProgressDialog();
            }
        });*/
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
