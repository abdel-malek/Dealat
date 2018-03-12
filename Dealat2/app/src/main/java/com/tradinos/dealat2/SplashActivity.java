package com.tradinos.dealat2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.View.CityActivity;
import com.tradinos.dealat2.View.HomeActivity;
import com.tradinos.dealat2.View.LoginActivity;
import com.tradinos.dealat2.View.VerificationActivity;

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;

                switch (MyApplication.getUserState()){

                    case User.PENDING:
                        mainIntent = new Intent(SplashActivity.this, VerificationActivity.class);
                        break;

                    case User.REGISTERED:
                        mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        break;
                    default: //NOT_REGISTERED
                        mainIntent = new Intent(SplashActivity.this, CityActivity.class);
                }


                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
