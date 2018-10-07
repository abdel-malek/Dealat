package com.dealat.View;

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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealat.Adapter.AutoCompleteAdapter;
import com.dealat.Adapter.CityAdapter;
import com.dealat.Adapter.HorizontalAdapter;
import com.dealat.Adapter.ItemAdapter;
import com.dealat.Adapter.TypeAdapter;
import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Utils.ImageDecoder;
import com.dealat.Utils.NumberTextWatcher;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.dealat.Controller.AdController;
import com.dealat.Controller.ParentController;
import com.dealat.Model.AdVehicle;
import com.dealat.Model.Category;
import com.dealat.Model.City;
import com.dealat.Model.Image;
import com.dealat.Model.Item;
import com.dealat.Model.TemplatesData;
import com.dealat.Model.Type;
import com.dealat.Model.User;
import com.dealat.R;
import com.dealat.Utils.ConfirmDialog;
import com.dealat.Utils.CustomAlertDialog;
import com.dealat.Utils.ScalableImageView;
import com.vdurmont.emoji.EmojiParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by developer on 20.02.18.
 */

public class SubmitAdActivity extends MasterActivity {

    private boolean enabled = false;

    private final int REQUEST_SELECT_VIDEO = 8, REQUEST_SELECT_CAT = 9, REQUEST_SELECT_IMG = 7;

    private Category selectedCategory;
    private Item selectedLocation;
    private int currentTemplate;
    private String videoServerPath;

    private HashMap<Integer, List<Type>> brands = new HashMap<>();

    private HashMap<String, String> parameters = new HashMap<>();

    private JSONArray deletedImgsJsonArray = new JSONArray(), deletedVideosJsonArray = new JSONArray();

    private HorizontalAdapter adapter;

    private Bitmap popupBitmap;

    //views
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    private TextView textBrand, textModel, textTransmission, textCapacity,
            textDate,
            textEdu, textSch, textCertificate, textGender,
            textState,
            textFurn, textPropertyState;

    private EditText editTitle, editDesc, editCategory, editPrice, editTextError,
            editKilo,
            editSize,
            editSpace, editRooms, editFloors, editNumberFloors,
            editEx, editSalary;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerPeriod, spinnerCity,
            spinnerBrand, spinnerModel, spinnerYear, spinnerTransmission, spinnerCapacity,
            spinnerEdu, spinnerCertificate, spinnerSch, spinnerGender,
            spinnerState,
            spinnerFurn, spinnerPropertyState;

    private SwitchCompat switchNegotiable, switchFeatured;

    private Button buttonTerms;
    private CheckBox checkboxTerms, checkPhone;
    private TextInputLayout containerPrice, containerKilometer, containerSize,
            containerSpace, containerRooms, containerFloors, containerNumberFloors,
            containerEx, containerSalary;


    // views for upload video
    private ProgressBar progressBarVideo;
    private ImageButton imageButtonCheck, imageButtonVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_submit_ad);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        selectedCategory = (Category) getIntent().getSerializableExtra("category");
        currentTemplate = selectedCategory.getTemplateId();

        ShowProgressDialog();
        AdController.getInstance(mController).getTemplatesData(new SuccessCallback<TemplatesData>() {
            @Override
            public void OnSuccess(TemplatesData result) {
                HideProgressDialog();

                enabled = true;
                findViewById(R.id.buttonTrue).setEnabled(true);

                spinnerPeriod.setAdapter(new ItemAdapter(mContext, result.getShowPeriods()));

                spinnerCity.setAdapter(new CityAdapter(mContext, result.getCities()));

                brands = result.getBrands();

                spinnerBrand.setAdapter(new TypeAdapter(mContext, getCategoryBrands()));

                result.getEducations().add(0, Item.getNoItem());
                spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                result.getSchedules().add(0, Item.getNoItem());
                spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

                result.getCertificates().add(0, Item.getNoItem());
                spinnerCertificate.setAdapter(new ItemAdapter(mContext, result.getCertificates()));

                result.getPropertyStates().add(0, Item.getNoItem());
                spinnerPropertyState.setAdapter(new ItemAdapter(mContext, result.getPropertyStates()));

                showTemplate();
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

        int startYear = AdVehicle.START_YEAR;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(Item.getNoItem());
        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));
        spinnerYear.setAdapter(new ItemAdapter(mContext, years));


        List<Item> capacities = new ArrayList<>();
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


        adapter = new HorizontalAdapter(mContext, linearLayout);

        buttonTerms.setText(underlineString(getString(R.string.labelTerms)));
    }

    @Override
    public void assignUIReferences() {
        linearLayout = findViewById(R.id.layout);

        textBrand = findViewById(R.id.textBrand);
        textModel = findViewById(R.id.textModel);
        textDate = findViewById(R.id.textDate);
        textTransmission = findViewById(R.id.textTransmission);
        textCapacity = findViewById(R.id.textCapacity);
        textEdu = findViewById(R.id.textEdu);
        textCertificate = findViewById(R.id.textCertificate);
        textSch = findViewById(R.id.textSch);
        textGender = findViewById(R.id.textGender);
        textState = findViewById(R.id.textState);
        textFurn = findViewById(R.id.textFurn);
        textPropertyState = findViewById(R.id.textPropertyState);

        editTitle = findViewById(R.id.editTitle);
        editCategory = findViewById(R.id.editCategory);
        editDesc = findViewById(R.id.editDesc);
        editPrice = findViewById(R.id.editPrice);

        editKilo = findViewById(R.id.editKilo);

        editSize = findViewById(R.id.editSize);

        editSalary = findViewById(R.id.editSalary);
        editEx = findViewById(R.id.editEx);

        editSpace = findViewById(R.id.editSpace);
        editRooms = findViewById(R.id.editRooms);
        editFloors = findViewById(R.id.editFloors);
        editNumberFloors = findViewById(R.id.editNumberFloors);

        autoCompleteLocation = findViewById(R.id.autoCompleteLocation);

        spinnerCity = findViewById(R.id.spinner);
        spinnerPeriod = findViewById(R.id.spinnerPeriod);

        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerModel = findViewById(R.id.spinnerModel);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerTransmission = findViewById(R.id.spinnerTransmission);
        spinnerCapacity = findViewById(R.id.spinnerCapacity);

        spinnerEdu = findViewById(R.id.spinnerEdu);
        spinnerCertificate = findViewById(R.id.spinnerCertificate);
        spinnerSch = findViewById(R.id.spinnerSch);
        spinnerGender = findViewById(R.id.spinnerGender);

        spinnerState = findViewById(R.id.spinnerState);

        spinnerFurn = findViewById(R.id.spinnerFurn);
        spinnerPropertyState = findViewById(R.id.spinnerPropertyState);

        switchNegotiable = findViewById(R.id.switchNegotiable);
        switchFeatured = findViewById(R.id.switchFeatured);

        containerPrice = findViewById(R.id.containerPrice);

        containerKilometer = findViewById(R.id.containerKilometer);

        containerSize = findViewById(R.id.containerSize);

        containerSpace = findViewById(R.id.containerSpace);
        containerRooms = findViewById(R.id.containerRooms);
        containerFloors = findViewById(R.id.containerFloors);
        containerNumberFloors = findViewById(R.id.containerNumberFloors);

        containerEx = findViewById(R.id.containerEx);
        containerSalary = findViewById(R.id.containerSalary);

        progressBarVideo = findViewById(R.id.progressBar);
        imageButtonCheck = findViewById(R.id.imageCheck);
        imageButtonVideo = findViewById(R.id.buttonVideo);

        checkboxTerms = findViewById(R.id.checkboxTerms);
        checkPhone = findViewById(R.id.checkPhone);
        buttonTerms = findViewById(R.id.buttonTerms);

        editTextError = findViewById(R.id.editTextError);
    }

    @Override
    public void assignActions() {

        editPrice.addTextChangedListener(new NumberTextWatcher(editPrice));
        editSalary.addTextChangedListener(new NumberTextWatcher(editSalary));
        editKilo.addTextChangedListener(new NumberTextWatcher(editKilo));
        editSize.addTextChangedListener(new NumberTextWatcher(editSize));
        editSpace.addTextChangedListener(new NumberTextWatcher(editSpace));

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                City city = ((CityAdapter) spinnerCity.getAdapter()).getItem(i);
                autoCompleteLocation.setAdapter(new AutoCompleteAdapter(mContext, city.getLocations()));

                autoCompleteLocation.setText("");
                selectedLocation = null;
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

                if (selectedLocation.isNothing()) //this unnecessary because there is no NoItem in locations
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
                Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                intent.putExtra("action", SubCategoriesActivity.ACTION_SELECT_CAT);
                intent.putExtra("category", selectedCategory);
                startActivityForResult(intent, REQUEST_SELECT_CAT);
            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Type selectedType = ((TypeAdapter) spinnerBrand.getAdapter()).getItem(i);

                spinnerModel.setAdapter(new ItemAdapter(mContext, selectedType.getModels()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "Select a Video"), REQUEST_SELECT_VIDEO);
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

        buttonTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TermsActivity.class);
                startActivity(intent);
            }
        });

        checkboxTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    editTextError.setError(null);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTrue:  //Submit

                parameters.clear();

                if (checkGeneralInput())
                    if (checkTemplateInput()) {

                        ShowProgressDialog();
                        AdController.getInstance(mController).submitAd(parameters, new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();

                                ConfirmDialog dialog = new ConfirmDialog(mContext);
                                try {
                                    dialog.show();
                                } catch (WindowManager.BadTokenException e) {
                                }
                                dialog.getButtonOk().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });

                            }
                        });
                    }
                break;

            case R.id.buttonEdit:
                if (adapter.getCount() >= Image.MAX_IMAGES)
                    showMessageInToast(R.string.toastMaxImages);
                else {
                    Intent intent = new Intent(mContext, SelectImagesActivity.class);
                    intent.putExtra("counter", adapter.getCount());
                    startActivityForResult(intent, REQUEST_SELECT_IMG);
                }

                break;

            case R.id.layoutHorizontal:
                final int position = Integer.parseInt(view.getTag().toString());
                final Image clickedImage = adapter.getItem(position);

                if (!clickedImage.isLoading()) {

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    //Inflate the view from a predefined XML layout
                    View layout = inflater.inflate(R.layout.popup_layout,
                            (ViewGroup) findViewById(R.id.popupLayout));

                    ScalableImageView imageView = layout.findViewById(R.id.imageView);
                    popupBitmap = new ImageDecoder().decodeLargeImage(clickedImage.getPath());
                    imageView.setImageBitmap(popupBitmap);

                    popupWindow = new PopupWindow(layout, MATCH_PARENT, MATCH_PARENT);

                    layout.findViewById(R.id.buttonTrue).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adapter.replaceMain(position);

                            if (popupBitmap != null) // because of a crash
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

                            if (popupBitmap != null) // because of a crash
                                popupBitmap.recycle();
                            popupWindow.dismiss();
                            popupWindow = null;
                            popupBitmap = null;
                        }
                    });

                    popupWindow.showAtLocation(findViewById(R.id.container2), Gravity.CENTER, 0, 0);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_CAT:
                    selectedCategory = (Category) data.getSerializableExtra("category");
                    editCategory.setText(selectedCategory.getFullName());
                    //if error was set, then category was selected
                    //previous error didn't disappear automatically
                    editCategory.setError(null);

                    replaceTemplate();

                    spinnerBrand.setAdapter(new TypeAdapter(mContext, getCategoryBrands()));

                    break;

                case REQUEST_SELECT_IMG:
                    List<Image> newImages = (List<Image>) data.getSerializableExtra("images");

                    int base = adapter.getCount();
                    adapter.setViews(newImages);

                    // uploading images
                    for (int i = 0; i < newImages.size(); i++)
                        new UploadImage(i + base).execute(newImages.get(i));

                    break;

                case REQUEST_SELECT_VIDEO:
                    String path = new ImageDecoder().getVideoPath(data.getData(), getContentResolver());
                    Bitmap bm = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                    imageButtonVideo.setImageBitmap(bm);
                    progressBarVideo.setVisibility(View.VISIBLE);
                    new UploadVideo().execute(path);
                    break;
            }
        }
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.container2), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getString(R.string.refresh), new View.OnClickListener() {
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

    private void replaceTemplate() {
        hideTemplate();
        currentTemplate = selectedCategory.getTemplateId();
        showTemplate();
    }

    private void showTemplate() {
        int visibility = View.VISIBLE;

        switch (currentTemplate) {
            case Category.PROPERTIES:

                findViewById(R.id.containerVideo).setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSpace)))
                    containerSpace.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideRoom)))
                    containerRooms.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFloor)))
                    containerFloors.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideNumberFloors)))
                    containerNumberFloors.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hidePropertyState))) {
                    textPropertyState.setVisibility(visibility);
                    spinnerPropertyState.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFurn))) {
                    textFurn.setVisibility(visibility);
                    spinnerFurn.setVisibility(visibility);
                }

                break;

            case Category.JOBS:
                containerPrice.setVisibility(View.GONE);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSchedule))) {
                    textSch.setVisibility(visibility);
                    spinnerSch.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideEducation))) {
                    textEdu.setVisibility(visibility);
                    spinnerEdu.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideCertificate))) {
                    textCertificate.setVisibility(visibility);
                    spinnerCertificate.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideGender))) {
                    textGender.setVisibility(visibility);
                    spinnerGender.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideEx)))
                    containerEx.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSalary)))
                    containerSalary.setVisibility(visibility);

                break;

            case Category.VEHICLES:
                if (!selectedCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    textBrand.setVisibility(visibility);
                    spinnerBrand.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideModel))) {
                    textModel.setVisibility(visibility);
                    spinnerModel.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideYear))) {
                    textDate.setVisibility(visibility);
                    spinnerYear.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideCapacity))) {
                    textCapacity.setVisibility(visibility);
                    spinnerCapacity.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideKilo)))
                    containerKilometer.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideAutomatic))) {
                    textTransmission.setVisibility(visibility);
                    spinnerTransmission.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    textState.setVisibility(visibility);
                    spinnerState.setVisibility(visibility);
                }

                break;

            case Category.ELECTRONICS:
                if (!selectedCategory.shouldHideTag(getString(R.string.hideSize)))
                    containerSize.setVisibility(visibility);

            case Category.MOBILES:
                if (!selectedCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    textBrand.setVisibility(visibility);
                    spinnerBrand.setVisibility(visibility);
                }

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                if (!selectedCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    textState.setVisibility(visibility);
                    spinnerState.setVisibility(visibility);
                }
        }
    }

    private void hideTemplate() {
        int visibility = View.GONE;

        switch (currentTemplate) {
            case Category.PROPERTIES:

                // video is only available with property
                if (videoServerPath != null) {
                    deletedVideosJsonArray.put(videoServerPath);
                    videoServerPath = null;
                    imageButtonVideo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_video_call_white_36dp));
                    imageButtonCheck.setVisibility(View.INVISIBLE);
                }
                findViewById(R.id.containerVideo).setVisibility(visibility);

                containerSpace.setVisibility(visibility);
                containerRooms.setVisibility(visibility);
                containerFloors.setVisibility(visibility);
                containerNumberFloors.setVisibility(visibility);

                textPropertyState.setVisibility(visibility);
                spinnerPropertyState.setVisibility(visibility);

                textFurn.setVisibility(visibility);
                spinnerFurn.setVisibility(visibility);

                break;

            case Category.JOBS:
                containerPrice.setVisibility(View.VISIBLE);

                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

                textCertificate.setVisibility(visibility);
                spinnerCertificate.setVisibility(visibility);

                textGender.setVisibility(visibility);
                spinnerGender.setVisibility(visibility);

                containerEx.setVisibility(visibility);
                containerSalary.setVisibility(visibility);

                break;

            case Category.VEHICLES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

                textModel.setVisibility(visibility);
                spinnerModel.setVisibility(visibility);

                textDate.setVisibility(visibility);
                spinnerYear.setVisibility(visibility);

                textCapacity.setVisibility(visibility);
                spinnerCapacity.setVisibility(visibility);

                containerKilometer.setVisibility(visibility);

                textTransmission.setVisibility(visibility);
                spinnerTransmission.setVisibility(visibility);

                textState.setVisibility(visibility);
                spinnerState.setVisibility(visibility);

                break;

            case Category.ELECTRONICS:
                containerSize.setVisibility(visibility);

            case Category.MOBILES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                textState.setVisibility(visibility);
                spinnerState.setVisibility(visibility);
        }
    }

    private boolean checkGeneralInput() {

        if (!checkboxTerms.isChecked()) {
            editTextError.setError(getString(R.string.errorAgreeToTerms));
            editTextError.requestFocus();

        } else if (inputIsEmpty(editTitle)) {
            editTitle.setError(getString(R.string.errorRequired));
            editTitle.requestFocus();

        } else if (selectedCategory.isMain()) {
            editCategory.setError(getString(R.string.errorRequired));
            editCategory.requestFocus();

        } /*else if (currentTemplate == Category.PROPERTIES && selectedLocation == null) {
            autoCompleteLocation.setError(getString(R.string.errorRequired));
            autoCompleteLocation.requestFocus();

        }*/ else if (currentTemplate != Category.JOBS && inputIsEmpty(editPrice)) {
            editPrice.setError(getString(R.string.errorRequired));
            editPrice.requestFocus();

        } else if (inputIsEmpty(editDesc)) {
            editDesc.setError(getString(R.string.errorRequired));
            editDesc.requestFocus();

        } else if (currentTemplate == Category.VEHICLES && adapter.getCount() == 0)
            showMessageInToast(getString(R.string.toastSelectImage));

        else if (adapter.isLoading())
            showMessageInToast(getString(R.string.toastWaitTillUploading));
        else if (progressBarVideo.getVisibility() == View.VISIBLE)
            showMessageInToast(R.string.toastWaitTillVideoUploading);
        else {

            if (currentTemplate != Category.JOBS)
                parameters.put("price", String.valueOf(doubleEditText(editPrice)));

            if (switchNegotiable.isChecked())
                parameters.put("is_negotiable", "1");

            if (switchFeatured.isChecked())
                parameters.put("is_featured", "1");

            parameters.put("title", stringInput(editTitle));
            parameters.put("description", EmojiParser.parseToAliases(editDesc.getText().toString()));
            parameters.put("category_id", selectedCategory.getId());
            parameters.put("show_period", ((Item) spinnerPeriod.getSelectedItem()).getId());
            parameters.put("city_id", ((City) spinnerCity.getSelectedItem()).getId());

            if (selectedLocation != null)
                parameters.put("location_id", selectedLocation.getId());

            JSONArray imagesJsonArray = new JSONArray();
            Image image;
            for (int i = 0; i < adapter.getCount(); i++) {
                image = adapter.getItem(i);
                if (image.isMarkedAsMain())
                    parameters.put("main_image", image.getServerPath());
                else
                    imagesJsonArray.put(image.getServerPath());
            }

            if (imagesJsonArray.length() > 0)
                parameters.put("images", imagesJsonArray.toString());

            if (deletedImgsJsonArray.length() > 0)
                parameters.put("deleted_images", deletedImgsJsonArray.toString());

            if (deletedVideosJsonArray.length() > 0)
                parameters.put("deleted_videos", deletedVideosJsonArray.toString());

            if (videoServerPath != null)
                parameters.put("main_video", videoServerPath);

            if (checkPhone.isChecked())
                parameters.put("ad_visible_phone", "1");
            else
                parameters.put("ad_visible_phone", "0");

            return true;
        }

        return false;
    }

    private boolean checkTemplateInput() {
        boolean result = true; // nothing is required by default
        Item item;
        switch (currentTemplate) {
            // only check visible fields
            // if field is hidden so obviously it's not required
            case Category.PROPERTIES:

                item = (Item) spinnerPropertyState.getSelectedItem();
                if (!selectedCategory.shouldHideTag(getString(R.string.hidePropertyState))) {
                    if (item != null) {
                        if (item.isNothing()) {
                            setSpinnerError(spinnerPropertyState);
                            result = false;
                        } else
                            parameters.put("property_state_id", item.getId());
                    }
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideNumberFloors))) {
                    if (inputIsEmpty(editNumberFloors)) {
                        editNumberFloors.setError(getString(R.string.errorRequired));
                        editNumberFloors.requestFocus();
                        result = false;
                    } else
                        parameters.put("floors_number", stringInput(editNumberFloors));
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFloor))) {
                    if (inputIsEmpty(editFloors)) {
                        editFloors.setError(getString(R.string.errorRequired));
                        editFloors.requestFocus();
                        result = false;
                    } else
                        parameters.put("floor", stringInput(editFloors));
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideRoom))) {
                    if (inputIsEmpty(editRooms)) {
                        editRooms.setError(getString(R.string.errorRequired));
                        editRooms.requestFocus();
                        result = false;
                    } else
                        parameters.put("rooms_num", stringInput(editRooms));
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSpace))) {
                    if (inputIsEmpty(editSpace)) {
                        editSpace.setError(getString(R.string.errorRequired));
                        editSpace.requestFocus();
                        result = false;
                    } else
                        parameters.put("space", String.valueOf(doubleEditText(editSpace)));
                }

                item = (Item) spinnerFurn.getSelectedItem();
                if (item != null)
                    parameters.put("with_furniture", item.getId());

                break;

            case Category.JOBS:

                if (!inputIsEmpty(editEx))
                    parameters.put("experience", stringInput(editEx));

                // send it any way, even if salary is empty because price is always required
                parameters.put("price", "0");

                if (!inputIsEmpty(editSalary))
                    parameters.put("salary", String.valueOf(doubleEditText(editSalary)));

                item = (Item) spinnerCertificate.getSelectedItem();
                if (!selectedCategory.shouldHideTag(getString(R.string.hideCertificate))) {
                    if (item != null) {
                        if (item.isNothing()) {
                            setSpinnerError(spinnerCertificate);
                            result = false;
                        } else
                            parameters.put("certificate_id", item.getId());
                    }
                }

                item = ((Item) spinnerEdu.getSelectedItem());
                if (!selectedCategory.shouldHideTag(getString(R.string.hideEducation))) {
                    if (item != null) {
                        if (item.isNothing()) {
                            setSpinnerError(spinnerEdu);
                            result = false;
                        } else
                            parameters.put("education_id", item.getId());
                    }
                }

                item = ((Item) spinnerSch.getSelectedItem());
                if (item != null && !item.isNothing())
                    parameters.put("schedule_id", item.getId());


                item = (Item) spinnerGender.getSelectedItem();
                if (item != null && !item.isNothing())
                    parameters.put("gender", item.getId());

                break;

            case Category.VEHICLES:

                item = ((Item) spinnerYear.getSelectedItem());
                if (!selectedCategory.shouldHideTag(getString(R.string.hideYear))) {
                    if (item != null) {
                        if (item.isNothing()) {
                            setSpinnerError(spinnerYear);
                            result = false;
                        } else
                            parameters.put("manufacture_date", item.getId());
                    }
                }

                item = ((Item) spinnerBrand.getSelectedItem());
                if (!selectedCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    if (item != null) {
                        if (item.isNothing()) {
                            setSpinnerError(spinnerBrand);
                            result = false;
                        } else
                            parameters.put("type_id", item.getId());
                    }
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideKilo))) {
                    if (inputIsEmpty(editKilo)) {
                        editKilo.setError(getString(R.string.errorRequired));
                        editKilo.requestFocus();
                        result = false;
                    } else
                        parameters.put("kilometer", String.valueOf(doubleEditText(editKilo)));
                }

                item = ((Item) spinnerModel.getSelectedItem());
                if (item != null && !item.isNothing())
                    parameters.put("type_model_id", item.getId());


                item = (Item) spinnerCapacity.getSelectedItem();
                if (item != null && !item.isNothing())
                    parameters.put("engine_capacity", item.getId());

                item = (Item) spinnerTransmission.getSelectedItem();
                if (item != null)
                    parameters.put("is_automatic", item.getId());

                item = (Item) spinnerState.getSelectedItem();
                if (item != null)
                    parameters.put("is_new", item.getId());

                break;

            case Category.ELECTRONICS:

                if (!inputIsEmpty(editSize))
                    parameters.put("size", String.valueOf(doubleEditText(editSize)));

            case Category.MOBILES:

                item = ((Item) spinnerBrand.getSelectedItem());
                if (item != null && !item.isNothing())
                    parameters.put("type_id", item.getId());

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

                item = (Item) spinnerState.getSelectedItem();
                if (item != null)
                    parameters.put("is_new", item.getId());
        }

        return result;
    }

    @Override
    public void onBackPressed() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupBitmap.recycle();
            popupWindow.dismiss();
            popupWindow = null;
            popupBitmap = null;
        } else {

            CustomAlertDialog dialog = new CustomAlertDialog(mContext, getString(R.string.areYouSureDiscard));
            dialog.show();

            dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONArray jsonArray = new JSONArray();

                    Image image;
                    for (int i = 0; i < adapter.getCount(); i++) {
                        image = adapter.getItem(i);
                        if (!image.isLoading())
                            jsonArray.put(image.getServerPath());
                    }

                    try {
                        for (int i = 0; i < deletedImgsJsonArray.length(); i++) {
                            jsonArray.put(deletedImgsJsonArray.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (jsonArray.length() > 0) {
                        ShowProgressDialog();
                        AdController.getInstance(mController).deleteImage(jsonArray, new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    } else {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        }
    }

    class UploadImage extends AsyncTask<Image, Void, String> {

        int position;

        UploadImage(int position) {
            this.position = position;
        }

        @Override
        protected String doInBackground(Image... images) {

            final Image image = images[0];

            //new File(image.getPath())
            AdController.getInstance(new ParentController(mContext, new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message, String data) {
                    showMessageInToast(getString(R.string.toastUploadError));
                    //  image.setLoading(false);
                    // adapter.errorView(position);
                }
            })).uploadImage(new ImageDecoder().ConvertBitmapToFile(image.getPath()), new SuccessCallback<String>() {
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

            AdController.getInstance(new ParentController(mContext, new FaildCallback() {
                @Override
                public void OnFaild(Code errorCode, String Message, String data) {
                    imageButtonVideo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_video_call_white_36dp));
                    progressBarVideo.setVisibility(View.INVISIBLE);
                    showMessageInToast(getString(R.string.toastUploadVideoError));
                }
            })).uploadVideo(new File(strings[0]), new SuccessCallback<String>() {
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

    private List<Type> getCategoryBrands() {
        List<Type> templateBrands = brands.get(currentTemplate);
        List<Type> result = new ArrayList<>();

        result.add(Type.getNoType());
        if (templateBrands != null) { //if template has types
            for (int i = 0; i < templateBrands.size(); i++) {
                if (templateBrands.get(i).getCategoryId().equals(selectedCategory.getId()))
                    result.add(templateBrands.get(i));
            }
        }

        return result;
    }
}
