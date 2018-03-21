package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class EditProfileActivity extends MasterActivity {

    private boolean enabled = false;
    private CurrentAndroidUser user;

    private EditText editTextName;
    private AppCompatSpinner spinnerCity;
    private ImageButton buttonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_profile);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        user = new CurrentAndroidUser(this);

        ShowProgressDialog();
        UserController.getInstance(mController).getCities(new SuccessCallback<List<Item>>() {
            @Override
            public void OnSuccess(List<Item> result) {
                HideProgressDialog();
                enabled = true;

                spinnerCity.setAdapter(new ItemAdapter(mContext, result));

                if (user.IsLogged())
                    spinnerCity.setSelection(getItemIndex(result, user.Get().getCityId()));
            }
        });
    }

    @Override
    public void showData() {
        if (user.IsLogged())
            editTextName.setText(user.Get().getName());
    }

    @Override
    public void assignUIReferences() {
        editTextName = (EditText) findViewById(R.id.editName);
        spinnerCity = (AppCompatSpinner) findViewById(R.id.spinner);
        buttonImage = (ImageButton) findViewById(R.id.buttonEdit);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTrue:

                if (inputIsEmpty(editTextName)) {
                    editTextName.setError(getString(R.string.errorRequired));
                    editTextName.requestFocus();
                } else {
                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("name", stringInput(editTextName));

                    if (spinnerCity.getSelectedItem() != null)
                        parameters.put("location_id", ((Item) spinnerCity.getSelectedItem()).getId());
                    //  parameters.put("personal_image")

                    ShowProgressDialog();
                    UserController.getInstance(mController).editUserInfo(parameters, new SuccessCallback<User>() {
                        @Override
                        public void OnSuccess(User result) {
                            HideProgressDialog();

                            MyApplication.saveCity(result.getCityId());
                            user.Save(result);

                            showMessageInToast(R.string.toastSaved);
                            finish();
                        }
                    });
                }

                break;

            case R.id.buttonFalse:
                finish();
                break;
        }
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container3), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });

        if (enabled)
            showMessageInToast(message);
        else
            snackbar.show();
    }
}
