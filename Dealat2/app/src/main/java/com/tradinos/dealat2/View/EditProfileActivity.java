package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.SplashActivity;
import com.tradinos.dealat2.Utils.ImageDecoder;
import com.tradinos.dealat2.Utils.SelectDateFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class EditProfileActivity extends MasterActivity implements SelectDateFragment.OnDialogClosed {

    private final int REQUEST_SELECT_IMG = 1;

    private final String NULL = "-1";

    private String action = "finish"; // by default user enter EditProfileActivity from MyProfileActivity
    private final String HOME = "home"; // after VerificationActivity user will enter their info
    // and then complete to HomeActivity
    private boolean enabled = false;
    private CurrentAndroidUser currentAndroidUser;
    private File image;

    //Views
    private EditText editTextName, editWhatsApp, editEmail, editBirthday;
    private AppCompatSpinner spinnerCity, spinnerGender;
    private ImageView imageViewPersonal;
    private Button buttonRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_profile);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        if (getIntent().hasExtra("action"))
            action = getIntent().getStringExtra("action");

        currentAndroidUser = new CurrentAndroidUser(this);

        List<Item> genders = new ArrayList<>();
        genders.add(Item.getNoItem());
        genders.add(new Item("1", getString(R.string.male)));
        genders.add(new Item("2", getString(R.string.female)));
        spinnerGender.setAdapter(new ItemAdapter(mContext, genders));

        ShowProgressDialog();
        UserController.getInstance(mController).getCities(new SuccessCallback<List<Item>>() {
            @Override
            public void OnSuccess(List<Item> result) {
                HideProgressDialog();
                enabled = true;

                final List<Item> cities = result;
                spinnerCity.setAdapter(new ItemAdapter(mContext, result));

                UserController.getInstance(mController).getUserInfo(new SuccessCallback<User>() {
                    @Override
                    public void OnSuccess(User result) {
                        findViewById(R.id.buttonTrue).setEnabled(true);
                        findViewById(R.id.buttonEdit).setEnabled(true);

                        currentAndroidUser.Save(result);
                        MyApplication.saveCity(result.getCityId());

                        editTextName.setText(result.getName());

                        ((TextView) findViewById(R.id.textView)).setText(result.getPhone());
                        spinnerCity.setSelection(getItemIndex(cities, result.getCityId()));

                        spinnerGender.setSelection(result.getGender());

                        if (!TextUtils.isEmpty(result.getWhatsAppNumber()))
                            editWhatsApp.setText(result.getWhatsAppNumber());

                        if (!TextUtils.isEmpty(result.getEmail()))
                            editEmail.setText(result.getEmail());

                        if (!TextUtils.isEmpty(result.getBirthday())) {
                            editBirthday.setText(result.getBirthday()); //formattedDate
                            buttonRemove.setVisibility(View.VISIBLE);
                        }

                        if (!TextUtils.isEmpty(result.getImageUrl())) {
                            ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                            mImageLoader.get(MyApplication.getBaseUrl() + result.getImageUrl(), ImageLoader.getImageListener(imageViewPersonal,
                                    R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void showData() {
    }

    @Override
    public void assignUIReferences() {
        imageViewPersonal = (ImageView) findViewById(R.id.imageView);
        editTextName = (EditText) findViewById(R.id.editName);
        editWhatsApp = (EditText) findViewById(R.id.editWhatsApp);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editBirthday = (EditText) findViewById(R.id.editBirthday);
        spinnerCity = (AppCompatSpinner) findViewById(R.id.spinner);
        spinnerGender = (AppCompatSpinner) findViewById(R.id.spinnerGender);
        buttonRemove = (Button) findViewById(R.id.buttonRemove);

        findViewById(R.id.buttonEdit).setEnabled(false);
    }

    @Override
    public void assignActions() {
        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(getSupportFragmentManager());
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBirthday.setText("");
                buttonRemove.setVisibility(View.GONE);
            }
        });
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

                    if (spinnerCity.getSelectedItem() != null) //city_id
                        parameters.put("city_id", ((Item) spinnerCity.getSelectedItem()).getId());

                    if (inputIsEmpty(editEmail))
                        parameters.put("email", NULL);
                    else
                        parameters.put("email", stringInput(editEmail));

                    if (inputIsEmpty(editWhatsApp))
                        parameters.put("whatsup_number", NULL);
                    else
                        parameters.put("whatsup_number", stringInput(editWhatsApp));

                    if (inputIsEmpty(editBirthday))
                        parameters.put("birthday", NULL);
                    else
                        parameters.put("birthday", stringInput(editBirthday));

                    Item item = (Item) spinnerGender.getSelectedItem();
                    if (item.isNothing())
                        parameters.put("user_gender", NULL);
                    else
                        parameters.put("user_gender", item.getId());

                    if (image == null)
                        parameters.put("image", NULL);

                    ShowProgressDialog();
                    UserController.getInstance(mController).editUserInfo(image, parameters, new SuccessCallback<User>() {
                        @Override
                        public void OnSuccess(User result) {
                            HideProgressDialog();

                            MyApplication.saveCity(result.getCityId());
                            currentAndroidUser.Save(result);

                            showMessageInToast(R.string.toastSaved);

                            setResult(RESULT_OK);

                            continueToHome();
                            finish();
                        }
                    });
                }

                break;

            case R.id.buttonEdit:
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_profile_image);

                dialog.findViewById(R.id.buttonTrue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, SelectImagesActivity.class);
                        intent.putExtra("counter", 7); //just to ensure user picks only on pic as personal pic
                        startActivityForResult(intent, REQUEST_SELECT_IMG);
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.buttonFalse).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image = null;
                        imageViewPersonal.setImageDrawable(null);
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;

            case R.id.buttonDelete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                ShowProgressDialog();
                                UserController.getInstance(mController).deactivateAccount(new SuccessCallback<String>() {
                                    @Override
                                    public void OnSuccess(String result) {
                                        HideProgressDialog();
                                        MyApplication.saveUserState(User.NOT_REGISTERED);
                                        new CurrentAndroidUser(mContext).clearUser();

                                        Intent intent = new Intent(mContext, SplashActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
                builder.setMessage(R.string.areYouSureDeactivate).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();

                break;

            case R.id.buttonFalse:
                continueToHome();
                finish();
                break;
        }
    }

    private void continueToHome() {
        if (action.equals(HOME)) {
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_IMG) {
            List<Image> newImages = (List<Image>) data.getSerializableExtra("images");

            if (newImages.size() > 0) {
                imageViewPersonal.setImageBitmap(new ImageDecoder().decodeSmallImage(newImages.get(0).getPath()));
                image = new File(newImages.get(0).getPath());
            }
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

    private void showDatePickerDialog(FragmentManager supportFragmentManager) {
        DialogFragment dateFragment = new SelectDateFragment();
        Bundle arg = new Bundle();
        dateFragment.setArguments(arg);
        dateFragment.setCancelable(true);

        dateFragment.show(supportFragmentManager, "datePicker");
    }

    @Override
    public void OnDialogClosed(int year, int month, int day) {
        if (year == 0 && month == 0 && day == 0) {
        } else {
            String mDate = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
            editBirthday.setText(mDate);
            buttonRemove.setVisibility(View.VISIBLE);
        }
    }
}
