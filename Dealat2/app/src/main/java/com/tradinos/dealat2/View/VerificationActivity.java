package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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

import java.util.HashMap;

/**
 * Created by developer on 12.03.18.
 */

public class VerificationActivity extends MasterActivity {

    private EditText editTextCode;
    private CurrentAndroidUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_verification);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        user = new CurrentAndroidUser(mContext);
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        editTextCode = (EditText) findViewById(R.id.edit_query);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonFalse) { // Skip
            Intent intent = new Intent(mContext, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (view.getId() == R.id.buttonTrue) {
            if (isNetworkAvailable() && checkInput() && user.IsLogged()) {

                final HashMap<String, String> parameters = new HashMap<>();
                parameters.put("phone", user.Get().getPhone());
                parameters.put("verification_code", stringInput(editTextCode));
                parameters.put("is_multi", "0");

                if (TextUtils.isEmpty(user.Get().getServerKey()))
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
        if (inputIsEmpty(editTextCode)){
            editTextCode.setError(getString(R.string.errorRequired));
            editTextCode.requestFocus();
        }
        else if (stringInput(editTextCode).length() != 6){
            editTextCode.setError(getString(R.string.errorCodeLenght));
            editTextCode.requestFocus();
        }
        else
            return true;
        return false;
    }
}
