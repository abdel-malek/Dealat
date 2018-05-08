package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AutoCompleteAdapter;
import com.tradinos.dealat2.Adapter.CityAdapter;
import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.AdElectronic;
import com.tradinos.dealat2.Model.AdFashion;
import com.tradinos.dealat2.Model.AdIndustry;
import com.tradinos.dealat2.Model.AdJob;
import com.tradinos.dealat2.Model.AdKid;
import com.tradinos.dealat2.Model.AdMobile;
import com.tradinos.dealat2.Model.AdProperty;
import com.tradinos.dealat2.Model.AdSport;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.City;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;
import com.tradinos.dealat2.Utils.ScalableImageView;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by developer on 14.03.18.
 */

public class EditAdActivity extends MasterActivity {

    private final int REQUEST_SELECT = 7, REQUEST_SELECT_VIDEO = 8;

    private boolean enabled = false;

    private final String NULL = "-1";
    private Ad currentAd;
    private String videoServerPath;

    private Category currentCategory;
    private Item selectedLocation;
    private List<Type> categoryBrands = new ArrayList<>();
    private List<Item> years = new ArrayList<>(), capacities = new ArrayList<>();

    private HashMap<String, String> parameters = new HashMap<>();

    private HorizontalAdapter adapter;
    private JSONArray imagesJsonArray, deletedImgsJsonArray = new JSONArray(), deletedVideosJsonArray = new JSONArray();

    private Bitmap popupBitmap;

    //views
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    private TextView textBrand, textModel, textTransmission, textCapacity,
            textDate,
            textEdu, textSch, textCertificate, textGender,
            textState,
            textFurn;

    private EditText editTitle, editDesc, editCategory, editPrice,
            editKilo,
            editSize,
            editSpace, editRooms, editFloors, editNumberFloors, editState,
            editEx, editSalary;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerPeriod, spinnerCity,
            spinnerBrand, spinnerModel, spinnerYear, spinnerTransmission, spinnerCapacity,
            spinnerEdu, spinnerCertificate, spinnerSch, spinnerGender,
            spinnerState,
            spinnerFurn;

    private SwitchCompat switchNegotiable, switchFeatured;

    private CheckBox checkPhone;

    private TextInputLayout containerPrice, containerKilometer, containerSize,
            containerSpace, containerRooms, containerFloors, containerNumberFloors, containerState,
            containerEx, containerSalary;

    // views for upload video
    private ProgressBar progressBarVideo;
    private ImageButton imageButtonCheck, imageButtonVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_ad);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        int startYear = AdVehicle.START_YEAR;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add(Item.getNoItem());
        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));
        spinnerYear.setAdapter(new ItemAdapter(mContext, years));


        capacities.add(Item.getNoItem());
        for (int i = AdVehicle.CAPACITY_MIN; i <= AdVehicle.CAPACITY_MAX; i = i + 100)
            capacities.add(new Item(String.valueOf(i), String.valueOf(i)));
        spinnerCapacity.setAdapter(new ItemAdapter(mContext, capacities));


        List<Item> usageOptions = new ArrayList<>();
        usageOptions.add(new Item("0", getString(R.string.old)));
        usageOptions.add(new Item("1", getString(R.string.newU)));
        spinnerState.setAdapter(new ItemAdapter(mContext, usageOptions));


        List<Item> transmissionOptions = new ArrayList<>();
        transmissionOptions.add(new Item("0", getString(R.string.manual)));
        transmissionOptions.add(new Item("1", getString(R.string.labelAutomatic)));
        spinnerTransmission.setAdapter(new ItemAdapter(mContext, transmissionOptions));


        List<Item> furnOptions = new ArrayList<>();
        furnOptions.add(new Item("0", getString(R.string.no)));
        furnOptions.add(new Item("1", getString(R.string.yes)));
        spinnerFurn.setAdapter(new ItemAdapter(mContext, furnOptions));


        List<Item> genders = new ArrayList<>();
        genders.add(Item.getNoItem());
        genders.add(new Item("1", getString(R.string.male)));
        genders.add(new Item("2", getString(R.string.female)));
        spinnerGender.setAdapter(new ItemAdapter(mContext, genders));


        currentCategory = MyApplication.getCategoryById(currentAd.getCategoryId());
        editCategory.setText(currentCategory.getFullName());

        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {

                currentAd = result;

                videoServerPath = result.getMainVideoUrl();

                adapter = new HorizontalAdapter(mContext, linearLayout);
                List<Image> images = new ArrayList<>();
                Image image;
                for (int i = 0; i < currentAd.getImagesPaths().size(); i++) {

                    image = new Image();
                    image.setServerPath(currentAd.getImagePath(i));

                    if (i == 0)
                        image.markAsMain();

                    images.add(image);
                }

                adapter.loadViews(images);

                AdController.getInstance(mController).getTemplatesData(new SuccessCallback<TemplatesData>() {
                    @Override
                    public void OnSuccess(TemplatesData result) {
                        enabled = true;
                        findViewById(R.id.buttonTrue).setEnabled(true);
                        findViewById(R.id.buttonEdit).setEnabled(true);

                        spinnerPeriod.setAdapter(new ItemAdapter(mContext, result.getShowPeriods()));

                        spinnerCity.setAdapter(new CityAdapter(mContext, result.getCities()));

                        categoryBrands = getCategoryBrands(result.getBrands());
                        spinnerBrand.setAdapter(new TypeAdapter(mContext, categoryBrands));

                        result.getEducations().add(0, Item.getNoItem());
                        spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                        result.getSchedules().add(0, Item.getNoItem());
                        spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

                        result.getCertificates().add(0, Item.getNoItem());
                        spinnerCertificate.setAdapter(new ItemAdapter(mContext, result.getCertificates()));

                        HideProgressDialog();

                        showTemplate();

                        if (videoServerPath != null) {
                            imageButtonCheck.setVisibility(View.VISIBLE);
                            imageButtonVideo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_play_arrow_gray));
                        }

                        fillTemplate(result);
                    }
                });
            }
        });
    }

    @Override
    public void showData() {
        ((TextView) findViewById(R.id.textView)).setText(getString(R.string.selectImages) + " " + String.valueOf(Image.MAX_IMAGES) +
                " " + getString(R.string.images));

        User user = new CurrentAndroidUser(mContext).Get();
        if (user != null)
            checkPhone.setText(getString(R.string.labelShowPhone) + " " + user.getPhone());
    }

    @Override
    public void assignUIReferences() {
        findViewById(R.id.buttonEdit).setEnabled(false);

        linearLayout = (LinearLayout) findViewById(R.id.layout);

        textBrand = (TextView) findViewById(R.id.textBrand);
        textModel = (TextView) findViewById(R.id.textModel);
        textDate = (TextView) findViewById(R.id.textDate);
        textTransmission = (TextView) findViewById(R.id.textTransmission);
        textCapacity = findViewById(R.id.textCapacity);
        textEdu = (TextView) findViewById(R.id.textEdu);
        textCertificate = findViewById(R.id.textCertificate);
        textSch = (TextView) findViewById(R.id.textSch);
        textGender = findViewById(R.id.textGender);
        textState = (TextView) findViewById(R.id.textState);
        textFurn = (TextView) findViewById(R.id.textFurn);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editCategory = (EditText) findViewById(R.id.editCategory);
        editDesc = (EditText) findViewById(R.id.editDesc);
        editPrice = (EditText) findViewById(R.id.editPrice);

        editKilo = (EditText) findViewById(R.id.editKilo);

        editSize = (EditText) findViewById(R.id.editSize);

        editSalary = (EditText) findViewById(R.id.editSalary);
        editEx = (EditText) findViewById(R.id.editEx);

        editSpace = (EditText) findViewById(R.id.editSpace);
        editRooms = (EditText) findViewById(R.id.editRooms);
        editFloors = (EditText) findViewById(R.id.editFloors);
        editNumberFloors = findViewById(R.id.editNumberFloors);
        editState = (EditText) findViewById(R.id.editState);

        autoCompleteLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocation);

        spinnerCity = (AppCompatSpinner) findViewById(R.id.spinner);
        spinnerPeriod = (AppCompatSpinner) findViewById(R.id.spinnerPeriod);

        spinnerBrand = (AppCompatSpinner) findViewById(R.id.spinnerBrand);
        spinnerModel = (AppCompatSpinner) findViewById(R.id.spinnerModel);
        spinnerYear = (AppCompatSpinner) findViewById(R.id.spinnerYear);
        spinnerTransmission = (AppCompatSpinner) findViewById(R.id.spinnerTransmission);
        spinnerCapacity = findViewById(R.id.spinnerCapacity);

        spinnerEdu = (AppCompatSpinner) findViewById(R.id.spinnerEdu);
        spinnerCertificate = findViewById(R.id.spinnerCertificate);
        spinnerSch = (AppCompatSpinner) findViewById(R.id.spinnerSch);
        spinnerGender = findViewById(R.id.spinnerGender);

        spinnerState = findViewById(R.id.spinnerState);

        spinnerFurn = (AppCompatSpinner) findViewById(R.id.spinnerFurn);

        switchNegotiable = (SwitchCompat) findViewById(R.id.switchNegotiable);
        switchFeatured = (SwitchCompat) findViewById(R.id.switchFeatured);

        checkPhone = findViewById(R.id.checkPhone);

        containerPrice = (TextInputLayout) findViewById(R.id.containerPrice);

        containerKilometer = (TextInputLayout) findViewById(R.id.containerKilometer);

        containerSize = (TextInputLayout) findViewById(R.id.containerSize);

        containerSpace = (TextInputLayout) findViewById(R.id.containerSpace);
        containerRooms = (TextInputLayout) findViewById(R.id.containerRooms);
        containerFloors = (TextInputLayout) findViewById(R.id.containerFloors);
        containerNumberFloors = findViewById(R.id.containerNumberFloors);
        containerState = (TextInputLayout) findViewById(R.id.containerState);

        containerEx = (TextInputLayout) findViewById(R.id.containerEx);
        containerSalary = (TextInputLayout) findViewById(R.id.containerSalary);

        progressBarVideo = (ProgressBar) findViewById(R.id.progressBar);
        imageButtonCheck = (ImageButton) findViewById(R.id.imageCheck);
        imageButtonVideo = (ImageButton) findViewById(R.id.buttonVideo);

    }

    @Override
    public void assignActions() {
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City city = ((CityAdapter) spinnerCity.getAdapter()).getItem(i);
                autoCompleteLocation.setAdapter(new AutoCompleteAdapter(mContext, city.getLocations()));

                //in general case, clear autoCompleteLocation
                autoCompleteLocation.setText("");
                selectedLocation = null;

                //now set previously selected location
                //no need to check null getCityId because it's required
                if (currentAd.getCityId() != null && currentAd.getCityId().equals(city.getId()))
                    if (currentAd.getLocationId() != null) {
                        int loc = getItemIndex(city.getLocations(), currentAd.getLocationId());
                        selectedLocation = ((AutoCompleteAdapter) autoCompleteLocation.getAdapter()).getItem(loc);
                        autoCompleteLocation.setText(selectedLocation.getName());
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        autoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLocation = ((AutoCompleteAdapter) autoCompleteLocation.getAdapter()).getItem(i);
                autoCompleteLocation.setText(selectedLocation.getName());
                autoCompleteLocation.setError(null);

                if (selectedLocation.isNothing())
                    selectedLocation = null;
            }
        });

        autoCompleteLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (selectedLocation != null)
                    if (charSequence.length() != selectedLocation.getName().length()) {
                        selectedLocation = null;
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        autoCompleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteLocation.getText().toString().equals(""))
                    autoCompleteLocation.showDropDown();
            }
        });

        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCategory.setError(getString(R.string.errorCategory));
                editCategory.requestFocus();
            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Type selectedType = ((TypeAdapter) spinnerBrand.getAdapter()).getItem(i);

                spinnerModel.setAdapter(new ItemAdapter(mContext, selectedType.getModels()));

                if (currentAd.getTemplate() == Category.VEHICLES) //it means there're models with brands
                    // and to ensure casting to AdVehicle
                    if (((AdVehicle) currentAd).getTypeId() != null &&
                            ((AdVehicle) currentAd).getTypeId().equals(selectedType.getId()))
                        spinnerModel.setSelection(getItemIndex(selectedType.getModels(), ((AdVehicle) currentAd).getModelId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                startActivityForResult(intent, REQUEST_SELECT_VIDEO);
            }
        });

        imageButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedVideosJsonArray.put(videoServerPath);

                imageButtonVideo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_video_call_white_36dp));
                videoServerPath = null;
                imageButtonCheck.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonTrue: // Save
                parameters.clear();

                if (checkGeneralInput()) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    getTemplateInput();

                                    ShowProgressDialog();
                                    AdController.getInstance(mController).editAd(parameters, new SuccessCallback<String>() {
                                        @Override
                                        public void OnSuccess(String result) {
                                            HideProgressDialog();
                                            showMessageInToast(getString(R.string.toastSaved));
                                            finish();
                                        }
                                    });
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
                    builder.setMessage(R.string.areYouSureEdit).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                            .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                }
                break;

            case R.id.buttonEdit: // Select Images
                if (adapter.getCount() >= Image.MAX_IMAGES)
                    showMessageInToast(getString(R.string.toastMaxImages));
                else {
                    Intent intent = new Intent(mContext, SelectImagesActivity.class);
                    intent.putExtra("counter", adapter.getCount());
                    startActivityForResult(intent, REQUEST_SELECT);
                }

                break;

            case R.id.layoutHorizontal:
                final int position = Integer.parseInt(view.getTag().toString());
                final Image clickedImage = adapter.getItem(position);

                if (!clickedImage.isLoading()) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //Inflate the view from a predefined XML layout
                    View layout = inflater.inflate(R.layout.popup_layout,
                            (ViewGroup) findViewById(R.id.popupLayout));

                    ScalableImageView imageView = layout.findViewById(R.id.imageView);

                    if (!clickedImage.isPreviouslyLoaded()) {
                        popupBitmap = new ImageDecoder().decodeLargeImage(clickedImage.getPath());
                        imageView.setImageBitmap(popupBitmap);
                    } else {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                        mImageLoader.get(MyApplication.getBaseUrl() + clickedImage.getServerPath(),
                                ImageLoader.getImageListener(imageView,
                                        R.drawable.others, R.drawable.others));
                    }

                    popupWindow = new PopupWindow(layout, MATCH_PARENT, MATCH_PARENT);

                    layout.findViewById(R.id.buttonTrue).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            adapter.replaceMain(position);

                            if (popupBitmap != null)
                                popupBitmap.recycle();
                            popupWindow.dismiss();
                            popupWindow = null;
                            popupBitmap = null;
                        }
                    });

                    layout.findViewById(R.id.buttonFalse).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adapter.deleteImage(position);
                            deletedImgsJsonArray.put(clickedImage.getServerPath());

                            if (popupBitmap != null)
                                popupBitmap.recycle();
                            popupWindow.dismiss();
                            popupWindow = null;
                            popupBitmap = null;
                        }
                    });

                    popupWindow.showAtLocation(findViewById(R.id.container2), Gravity.CENTER, 0, 0);
                }
        }
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container2), message, Snackbar.LENGTH_INDEFINITE)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT) {
                List<Image> newImages = (List<Image>) data.getSerializableExtra("images");

                int base = adapter.getCount();
                adapter.setViews(newImages);

                // uploading images
                for (int i = 0; i < newImages.size(); i++)
                    new UploadImage(i + base).execute(newImages.get(i));
            } else if (requestCode == REQUEST_SELECT_VIDEO) {
                String path = new ImageDecoder().getVideoPath(data.getData(), getContentResolver());
                Bitmap bm = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                imageButtonVideo.setImageBitmap(bm);
                progressBarVideo.setVisibility(View.VISIBLE);
                new UploadVideo().execute(path);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            if (popupBitmap != null)
                popupBitmap.recycle();
            popupWindow.dismiss();
            popupWindow = null;
            popupBitmap = null;
        } else
            super.onBackPressed();
    }

    private boolean checkGeneralInput() {
        if (inputIsEmpty(editTitle)) {
            editTitle.setError(getString(R.string.errorRequired));
            editTitle.requestFocus();

        } else if (currentAd.getTemplate() == Category.PROPERTIES && selectedLocation == null) {
            autoCompleteLocation.setError(getString(R.string.errorRequired));
            autoCompleteLocation.requestFocus();

        } else if (currentAd.getTemplate() != Category.JOBS && inputIsEmpty(editPrice)) {
            editPrice.setError(getString(R.string.errorRequired));
            editPrice.requestFocus();

        } else if (adapter.isLoading())
            showMessageInToast(getString(R.string.toastWaitTillUploading));
        else {

            parameters.put("ad_id", currentAd.getId());

            if (inputIsEmpty(editDesc))
                parameters.put("description", NULL);
            else
                parameters.put("description", stringInput(editDesc));

            parameters.put("title", stringInput(editTitle));
            parameters.put("show_period", ((Item) spinnerPeriod.getSelectedItem()).getId());
            parameters.put("city_id", ((City) spinnerCity.getSelectedItem()).getId());

            if (selectedLocation != null)
                parameters.put("location_id", selectedLocation.getId());
            else
                parameters.put("location_id", NULL);

            if (currentAd.getTemplate() != Category.JOBS)
                parameters.put("price", String.valueOf(doubleEditText(editPrice)));

            if (switchNegotiable.isChecked())
                parameters.put("is_negotiable", "1");
            else
                parameters.put("is_negotiable", "0");

            if (switchFeatured.isChecked())
                parameters.put("is_featured", "1");
            else
                parameters.put("is_featured", "0");

            imagesJsonArray = new JSONArray();
            Image image;

            if (adapter.getCount() > 0) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    image = adapter.getItem(i);
                    if (image.isMarkedAsMain())
                        parameters.put("main_image", image.getServerPath());
                    else
                        imagesJsonArray.put(image.getServerPath());
                }

                if (imagesJsonArray.length() > 0)
                    parameters.put("images", imagesJsonArray.toString());
            } else
                parameters.put("main_image", NULL);


            if (deletedImgsJsonArray.length() > 0)
                parameters.put("deleted_images", deletedImgsJsonArray.toString());

            if (deletedVideosJsonArray.length() > 0)
                parameters.put("deleted_videos", deletedVideosJsonArray.toString());

            if (videoServerPath == null)
                parameters.put("main_video", NULL);
            else
                parameters.put("main_video", videoServerPath);

            parameters.put("edit_status", String.valueOf(currentAd.getStatus()));

            if (checkPhone.isChecked())
                parameters.put("ad_visible_phone", "1");
            else
                parameters.put("ad_visible_phone", "0");

            return true;
        }

        return false;
    }

    private void getTemplateInput() {

        Item item;
        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:
                if (inputIsEmpty(editSpace))
                    parameters.put("space", NULL);
                else
                    parameters.put("space", String.valueOf(doubleEditText(editSpace)));

                if (inputIsEmpty(editRooms))
                    parameters.put("rooms_num", NULL);
                else
                    parameters.put("rooms_num", String.valueOf(doubleEditText(editRooms)));

                if (inputIsEmpty(editFloors))
                    parameters.put("floor", NULL);
                else
                    parameters.put("floor", String.valueOf(doubleEditText(editFloors)));

                if (inputIsEmpty(editNumberFloors))
                    parameters.put("floors_number", NULL);
                else
                    parameters.put("floors_number", String.valueOf(doubleEditText(editNumberFloors)));

                if (inputIsEmpty(editState))
                    parameters.put("state", NULL);
                else
                    parameters.put("state", stringInput(editState));

                item = (Item) spinnerFurn.getSelectedItem();
                if (item != null)
                    parameters.put("with_furniture", item.getId());

                break;

            case Category.JOBS:

                if (inputIsEmpty(editEx))
                    parameters.put("experience", NULL);
                else
                    parameters.put("experience", stringInput(editEx));

                if (inputIsEmpty(editSalary))
                    parameters.put("salary", NULL);
                else
                    parameters.put("salary", String.valueOf(doubleEditText(editSalary)));

                item = ((Item) spinnerEdu.getSelectedItem());
                if (item != null)
                    parameters.put("education_id", item.getId());

                item = (Item) spinnerCertificate.getSelectedItem();
                if (item != null)
                    parameters.put("certificate_id", item.getId());

                item = ((Item) spinnerSch.getSelectedItem());
                if (item != null)
                    parameters.put("schedule_id", item.getId());

                item = (Item) spinnerGender.getSelectedItem();
                if (item != null)
                    parameters.put("gender", item.getId());

                break;

            case Category.VEHICLES:
                if (inputIsEmpty(editKilo))
                    parameters.put("kilometer", NULL);
                else
                    parameters.put("kilometer", String.valueOf(doubleEditText(editKilo)));

                item = ((Item) spinnerBrand.getSelectedItem());
                if (item != null)
                    parameters.put("type_id", item.getId());

                item = ((Item) spinnerModel.getSelectedItem());
                if (item != null)
                    parameters.put("type_model_id", item.getId());

                item = ((Item) spinnerYear.getSelectedItem());
                if (item != null)
                    parameters.put("manufacture_date", item.getId());

                item = (Item) spinnerCapacity.getSelectedItem();
                if (item != null)
                    parameters.put("engine_capacity", item.getId());

                item = (Item) spinnerTransmission.getSelectedItem();
                if (item != null)
                    parameters.put("is_automatic", item.getId());

                item = (Item) spinnerState.getSelectedItem();
                if (item != null)
                    parameters.put("is_new", item.getId());

                break;

            case Category.ELECTRONICS:

                if (inputIsEmpty(editSize))
                    parameters.put("size", NULL);
                else
                    parameters.put("size", String.valueOf(doubleEditText(editSize)));

            case Category.MOBILES:

                item = ((Item) spinnerBrand.getSelectedItem());
                if (item != null)
                    parameters.put("type_id", item.getId());

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                item = (Item) spinnerState.getSelectedItem();
                if (item != null)
                    parameters.put("is_new", item.getId());
        }
    }

    private void fillTemplate(TemplatesData data) {
        editTitle.setText(currentAd.getTitle());
        editPrice.setText(formattedNumber(currentAd.getPrice()));

        if (currentAd.isNegotiable())
            switchNegotiable.setChecked(true);
        else
            switchNegotiable.setChecked(false);

        if (currentAd.isFeatured())
            switchFeatured.setChecked(true);
        else
            switchFeatured.setChecked(false);

        if (currentAd.isVisiblePhone())
            checkPhone.setChecked(true);
        else
            checkPhone.setChecked(false);

        editDesc.setText(currentAd.getDescription());

        int loc = getItemIndex(new ArrayList<Item>(data.getCities()), currentAd.getCityId());
        spinnerCity.setSelection(loc);

        spinnerPeriod.setSelection(getItemIndex(data.getShowPeriods(), String.valueOf(currentAd.getShowPeriod())));


        int brand = 0;
        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:

                editSpace.setText(formattedNumber(((AdProperty) currentAd).getSpace()));
                editRooms.setText(formattedNumber(((AdProperty) currentAd).getRoomNum()));
                editFloors.setText(formattedNumber(((AdProperty) currentAd).getFloorNum()));
                editNumberFloors.setText(formattedNumber(((AdProperty) currentAd).getFloorsCount()));
                editState.setText(((AdProperty) currentAd).getState());

                if (((AdProperty) currentAd).isFurnished())
                    spinnerFurn.setSelection(1);

                break;

            case Category.JOBS:
                spinnerEdu.setSelection(getItemIndex(data.getEducations(), ((AdJob) currentAd).getEducationId()));
                spinnerCertificate.setSelection(getItemIndex(data.getCertificates(), ((AdJob) currentAd).getCertificateId()));
                spinnerSch.setSelection(getItemIndex(data.getSchedules(), ((AdJob) currentAd).getScheduleId()));
                spinnerGender.setSelection(((AdJob) currentAd).getGender());

                editEx.setText(((AdJob) currentAd).getExperience());
                editSalary.setText(formattedNumber(((AdJob) currentAd).getSalary()));

                break;

            case Category.VEHICLES:
                brand = getItemIndex(new ArrayList<Item>(categoryBrands),
                        ((AdVehicle) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);

                spinnerYear.setSelection(getItemIndex(years, ((AdVehicle) currentAd).getManufactureYear()));
                spinnerCapacity.setSelection(getItemIndex(capacities, ((AdVehicle) currentAd).getEngineCapacity()));

                editKilo.setText(formattedNumber(((AdVehicle) currentAd).getKilometer()));

                if (((AdVehicle) currentAd).isAutomatic())
                    spinnerTransmission.setSelection(1);

                if (!((AdVehicle) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions

                break;

            case Category.ELECTRONICS:
                brand = getItemIndex(new ArrayList<Item>(categoryBrands),
                        ((AdElectronic) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);
                editSize.setText(formattedNumber(((AdElectronic) currentAd).getSize()));

                if (!((AdElectronic) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions
                break;

            case Category.MOBILES:
                brand = getItemIndex(new ArrayList<Item>(categoryBrands),
                        ((AdMobile) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);

                if (!((AdMobile) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions

                break;

            case Category.FASHION:

                if (!((AdFashion) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions

                break;

            case Category.KIDS:

                if (!((AdKid) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions

                break;

            case Category.SPORTS:

                if (!((AdSport) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions

                break;

            case Category.INDUSTRIES:

                if (!((AdIndustry) currentAd).isSecondhand())
                    spinnerState.setSelection(1); // old is first item in usageOptions
        }
    }

    private void showTemplate() {
        int visibility = View.VISIBLE;

        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:

                findViewById(R.id.containerVideo).setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideSpace)))
                    containerSpace.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideRoom)))
                    containerRooms.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideFloor)))
                    containerFloors.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideNumberFloors)))
                    containerNumberFloors.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideState)))
                    containerState.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideFurn))) {
                    textFurn.setVisibility(visibility);
                    spinnerFurn.setVisibility(visibility);
                }

                break;

            case Category.JOBS:
                containerPrice.setVisibility(View.GONE);

                if (!currentCategory.shouldHideTag(getString(R.string.hideSchedule))) {
                    textSch.setVisibility(visibility);
                    spinnerSch.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideEducation))) {
                    textEdu.setVisibility(visibility);
                    spinnerEdu.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideCertificate))) {
                    textCertificate.setVisibility(visibility);
                    spinnerCertificate.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideGender))) {
                    textGender.setVisibility(visibility);
                    spinnerGender.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideEx)))
                    containerEx.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideSalary)))
                    containerSalary.setVisibility(visibility);

                break;

            case Category.VEHICLES:
                if (!currentCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    textBrand.setVisibility(visibility);
                    spinnerBrand.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideModel))) {
                    textModel.setVisibility(visibility);
                    spinnerModel.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideYear))) {
                    textDate.setVisibility(visibility);
                    spinnerYear.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideCapacity))) {
                    textCapacity.setVisibility(visibility);
                    spinnerCapacity.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideKilo)))
                    containerKilometer.setVisibility(visibility);

                if (!currentCategory.shouldHideTag(getString(R.string.hideAutomatic))) {
                    textTransmission.setVisibility(visibility);
                    spinnerTransmission.setVisibility(visibility);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    textState.setVisibility(visibility);
                    spinnerState.setVisibility(visibility);
                }

                break;

            case Category.ELECTRONICS:
                if (!currentCategory.shouldHideTag(getString(R.string.hideSize)))
                    containerSize.setVisibility(visibility);

            case Category.MOBILES:
                if (!currentCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    textBrand.setVisibility(visibility);
                    spinnerBrand.setVisibility(visibility);
                }

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                if (!currentCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    textState.setVisibility(visibility);
                    spinnerState.setVisibility(visibility);
                }
        }
    }

    class UploadImage extends AsyncTask<Image, Void, String> {

        int position;

        public UploadImage(int position) {
            this.position = position;
        }

        @Override
        protected String doInBackground(Image... images) {

            final Image image = images[0];

            //new File(image.getPath())
            AdController.getInstance(mController).uploadImage(new ImageDecoder().ConvertBitmapToFile(image.getPath()), new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    image.setServerPath(result);

                    image.setLoading(false);
                    adapter.updateViews(position);
                }
            });
            return null;
        }
    }

    class UploadVideo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            AdController.getInstance(mController).uploadVideo(new File(strings[0]), new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    videoServerPath = result;
                    progressBarVideo.setVisibility(View.INVISIBLE);
                    imageButtonCheck.setVisibility(View.VISIBLE);
                }
            });
            return null;
        }
    }

    private List<Type> getCategoryBrands(HashMap<Integer, List<Type>> brands) {
        List<Type> templateBrands = brands.get(currentCategory.getTemplateId());
        List<Type> result = new ArrayList<>();

        result.add(Type.getNoType());
        if (templateBrands != null) { //if template has types
            for (int i = 0; i < templateBrands.size(); i++) {
                if (templateBrands.get(i).getCategoryId().equals(currentCategory.getId()))
                    result.add(templateBrands.get(i));
            }
        }

        return result;
    }
}
