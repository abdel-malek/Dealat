package com.tradinos.dealat2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.ParentController;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.View.CityActivity;
import com.tradinos.dealat2.View.HomeActivity;
import com.tradinos.dealat2.View.LanguageActivity;
import com.tradinos.dealat2.View.MasterActivity;
import com.tradinos.dealat2.View.RegisterActivity;
import com.tradinos.dealat2.View.VerificationActivity;

public class SplashActivity extends MasterActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);

        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/all_android");

        /* New Handler to
         * close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent;

                switch (MyApplication.getUserState()) {
                    case User.Languaged:
                        mainIntent = new Intent(SplashActivity.this, CityActivity.class);
                        startActivity(mainIntent);
                        finish();
                        break;

                    case User.LOCATED:
                        mainIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                        startActivity(mainIntent);
                        finish();
                        break;

                    case User.PENDING:
                        mainIntent = new Intent(SplashActivity.this, VerificationActivity.class);
                        startActivity(mainIntent);
                        finish();
                        break;

                    case User.REGISTERED:

                        UserController.getInstance(mController).getUserInfo(new SuccessCallback<User>() {
                            @Override
                            public void OnSuccess(User result) {
                                new CurrentAndroidUser(mContext).Save(result);
                                MyApplication.saveCity(result.getCityId());

                                final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                UserController.getInstance(new ParentController(mContext, new FaildCallback() {
                                    @Override
                                    public void OnFaild(Code errorCode, String Message, String data) {

                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })).saveUserToken(refreshedToken, new SuccessCallback<String>() {
                                    @Override
                                    public void OnSuccess(String result) {

                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });

                        break;

                    default: //NOT_REGISTERED
                        mainIntent = new Intent(SplashActivity.this, LanguageActivity.class);
                        startActivity(mainIntent);
                        finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
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

    }
}
