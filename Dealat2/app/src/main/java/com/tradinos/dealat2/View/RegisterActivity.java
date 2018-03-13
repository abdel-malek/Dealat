package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.HashMap;

/**
 * Created by developer on 18.02.18.
 */

public class RegisterActivity extends MasterActivity {


    private EditText editTextPhone, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
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
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        editTextName = (EditText) findViewById(R.id.editName);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){

            case R.id.buttonTrue: //Register

                if (isNetworkAvailable() && checkInput()){
                    HashMap<String, String> parameters = new HashMap<>();

                    parameters.put("phone", stringInput(editTextPhone));
                    parameters.put("name", stringInput(editTextName));
                    parameters.put("lang", MyApplication.getLocale().toString());
                    parameters.put("city_id", MyApplication.getCity());

                    ShowProgressDialog();
                    UserController.getInstance(mController).registerUser(parameters, new SuccessCallback<User>() {
                        @Override
                        public void OnSuccess(User result) {
                            HideProgressDialog();
                            MyApplication.saveUserState(User.PENDING);
                            new CurrentAndroidUser(mContext).Save(result);

                            Intent intent = new Intent(mContext, VerificationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                break;

            case R.id.buttonFalse: //Skip
                intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buttonGmail:
                break;

            case R.id.buttonFacebook:
                break;
        }
    }

    private boolean checkInput(){
        if (inputIsEmpty(editTextName)){
            editTextName.setError(getString(R.string.errorRequired));
            editTextName.requestFocus();
        }
        else if (inputIsEmpty(editTextPhone)){
            editTextPhone.setError(getString(R.string.errorRequired));
            editTextPhone.requestFocus();
        }
        else if (stringInput(editTextPhone).length() != 9){
            editTextPhone.setError(getString(R.string.errorPhoneLength));
            editTextPhone.requestFocus();
        }
        else
            return true;

        return false;
    }
}
