package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.ParentController;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by developer on 12.03.18.
 */

public class VerificationActivity extends MasterActivity {

    private EditText editTextCode;
    private User user;
    private ImageButton buttonEditNumber;
    private Button buttonMessage, buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_verification);
        super.onCreate(savedInstanceState);

        long currentDate = new Date().getTime();
        long codeRequestDate = MyApplication.getCodeRequestDate();

        long difference = currentDate - codeRequestDate;
        long startTime = 3600000 - difference;

        if (startTime <= 0) {
            afterAnHour();
        } else { // this counter because a user can request another code only after an hour from first request
            // and so they can edit their phone number
            new CountDownTimer(startTime, 1000) {

                @Override
                public void onTick(long l) {
                    int seconds = (int) (l / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;

                    buttonMessage.setText(String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds));
                }

                @Override
                public void onFinish() {
                    afterAnHour();
                }
            }.start();
        }
    }

    @Override
    public void getData() {
        user = new CurrentAndroidUser(mContext).Get();
    }

    @Override
    public void showData() {
        if (user != null)
            ((TextView) findViewById(R.id.textViewPhone)).setText(getPhoneNumber(user.getPhone()));

        buttonSkip.setText(underlineString(getString(R.string.buttonSkip)));
    }

    @Override
    public void assignUIReferences() {
        editTextCode = (EditText) findViewById(R.id.edit_query);
        buttonMessage = findViewById(R.id.buttonMessage);
        buttonEditNumber = findViewById(R.id.buttonEdit);
        buttonSkip = findViewById(R.id.buttonFalse);
    }

    @Override
    public void assignActions() {
        buttonEditNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                MyApplication.saveUserState(User.LOCATED);

                startActivity(intent);
                finish();
            }
        });

        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    HashMap<String, String> parameters = new HashMap<>();

                    parameters.put("phone", user.getPhone());
                    parameters.put("name", user.getName());
                    parameters.put("lang", MyApplication.getLocale().toString());
                    parameters.put("city_id", MyApplication.getCity());

                    ShowProgressDialog();
                    UserController.getInstance(mController).registerUser(parameters, new SuccessCallback<User>() {
                        @Override
                        public void OnSuccess(User result) {
                            HideProgressDialog();
                            MyApplication.saveCodeRequestDate(new Date());

                            recreate();
                        }
                    });
                }
            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) {
            if (isNetworkAvailable() && checkInput() && user != null) {

                final HashMap<String, String> parameters = new HashMap<>();
                parameters.put("phone", user.getPhone());
                parameters.put("verification_code", stringInput(editTextCode));
                parameters.put("is_multi", "0");

                if (TextUtils.isEmpty(user.getServerKey()))
                    verify(parameters);
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    parameters.put("is_multi", "1");
                            }
                            verify(parameters);
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
                    builder.setMessage(R.string.areYouSureRegister).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                            .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                }
            }
        }
    }

    private void verify(HashMap<String, String> parameters) {
        ShowProgressDialog();
        UserController.getInstance(mController).verifyUser(parameters, new SuccessCallback<User>() {
            @Override
            public void OnSuccess(User result) {

                MyApplication.saveUserState(User.REGISTERED);
                new CurrentAndroidUser(mContext).Save(result);

                final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                UserController.getInstance(new ParentController(mContext, new FaildCallback() {
                    @Override
                    public void OnFaild(Code errorCode, String Message, String data) {
                        HideProgressDialog();

                        Intent intent = new Intent(mContext, EditProfileActivity.class);
                        intent.putExtra("action", "home");
                        startActivity(intent);
                        finish();
                    }
                })).saveUserToken(refreshedToken, new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        HideProgressDialog();

                        Intent intent = new Intent(mContext, EditProfileActivity.class);
                        intent.putExtra("action", "home");
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    private boolean checkInput() {
        if (inputIsEmpty(editTextCode)) {
            editTextCode.setError(getString(R.string.errorRequired));
            editTextCode.requestFocus();
        } else if (stringInput(editTextCode).length() != 6) {
            editTextCode.setError(getString(R.string.errorCodeLenght));
            editTextCode.requestFocus();
        } else
            return true;
        return false;
    }

    private void afterAnHour() {
        buttonMessage.setText(underlineString(getString(R.string.buttonReRequestCode)));

        buttonEditNumber.setVisibility(View.VISIBLE);
    }
}
