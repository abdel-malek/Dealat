package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by developer on 18.02.18.
 */

public class RegisterActivity extends MasterActivity {


    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private EditText editTextPhone, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
      //  FacebookSdk.sdkInitialize(this);
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

              /*  accessTokenTracker= new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

                    }
                };

                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

                    }
                };*/

                // uncomment FacebookSdk.sdkInitialize(this) in onCreate
                // might be removed later if not needed
             /*   accessTokenTracker.startTracking();
                profileTracker.startTracking();
                callbackManager = CallbackManager.Factory.create();

                LoginManager manager = LoginManager.getInstance();
                manager.logInWithReadPermissions(RegisterActivity.this ,Arrays.asList("public_profile", "email", "user_birthday"));
                manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        // Application code
                                        try {
                                            String birthday="";
                                            if(object.has("birthday")){
                                                birthday = object.getString("birthday"); // 01/31/1980 format
                                            }

                                            String gender = object.getString("gender");

                                            User user = new User();
                                            user.setId(object.getString("id"));
                                            user.setName(object.getString("first_name") + " "+ object.getString("last_name"));
                                            user.setEmail(object.getString("email"));
                                            user.setImageUrl("https://graph.facebook.com/"+ object.getString("id")+"/picture?type=large");


                                            new CurrentAndroidUser(mContext).Save(user);
                                            MyApplication.saveUserState(User.REGISTERED);

                                            Intent intent = new Intent(mContext, HomeActivity.class);
                                            startActivity(intent);
                                            finish();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });*/

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     //   callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
//        accessTokenTracker.stopTracking();
    //    profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
   //     Profile profile = Profile.getCurrentProfile();

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
