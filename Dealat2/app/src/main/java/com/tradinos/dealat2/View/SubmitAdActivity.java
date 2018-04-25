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
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AutoCompleteAdapter;
import com.tradinos.dealat2.Adapter.CityAdapter;
import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.City;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;
import com.tradinos.dealat2.Utils.ScalableImageView;

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

    private JSONArray imagesJsonArray, deletedImgsJsonArray = new JSONArray(), deletedVideosJsonArray = new JSONArray();

    private HorizontalAdapter adapter;

    private Bitmap popupBitmap;

    //views
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    private TextView textBrand, textModel,
            textDate,
            textEdu, textSch;

    private EditText editTitle, editDesc, editCategory, editPrice, editTextError,
            editKilo,
            editSize,
            editSpace, editRooms, editFloors, editState,
            editEx, editSalary;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerPeriod, spinnerCity,
            spinnerBrand, spinnerModel, spinnerYear,
            spinnerEdu, spinnerSch;

    private SwitchCompat switchNegotiable, switchSecondhand, switchFeatured,
            switchAutomatic,
            switchFurn;

    private Button buttonTerms;
    private CheckBox checkboxTerms;
    private TextInputLayout containerPrice, containerKilometer, containerSize,
            containerSpace, containerRooms, containerFloors, containerState,
            containerEx, containerSalary;

    private View line1, line2, line3;
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
                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                result.getEducations().add(Item.getNoItem());
                spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                result.getSchedules().add(Item.getNoItem());
                spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

                showTemplate();
            }
        });
    }

    @Override
    public void showData() {

        ((TextView) findViewById(R.id.textView)).setText(getString(R.string.selectImages) + " " + String.valueOf(Image.MAX_IMAGES) +
                " " + getString(R.string.images) + "\n" + getString(R.string.alsoSelectVideo));

        int startYear = AdVehicle.START_YEAR;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(Item.getNoItem());

        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));

        spinnerYear.setAdapter(new ItemAdapter(mContext, years));

        adapter = new HorizontalAdapter(mContext, linearLayout);


        SpannableString content = new SpannableString(getString(R.string.labelTerms));
        content.setSpan(new UnderlineSpan(), 0, getString(R.string.labelTerms).length(), 0);
        buttonTerms.setText(content);
    }

    @Override
    public void assignUIReferences() {
        linearLayout = (LinearLayout) findViewById(R.id.layout);

        textBrand = (TextView) findViewById(R.id.textBrand);
        textModel = (TextView) findViewById(R.id.textModel);
        textDate = (TextView) findViewById(R.id.textDate);
        textEdu = (TextView) findViewById(R.id.textEdu);
        textSch = (TextView) findViewById(R.id.textSch);

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
        editState = (EditText) findViewById(R.id.editState);

        autoCompleteLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocation);

        spinnerCity = (AppCompatSpinner) findViewById(R.id.spinner);
        spinnerPeriod = (AppCompatSpinner) findViewById(R.id.spinnerPeriod);

        spinnerBrand = (AppCompatSpinner) findViewById(R.id.spinnerBrand);
        spinnerModel = (AppCompatSpinner) findViewById(R.id.spinnerModel);
        spinnerYear = (AppCompatSpinner) findViewById(R.id.spinnerYear);

        spinnerEdu = (AppCompatSpinner) findViewById(R.id.spinnerEdu);
        spinnerSch = (AppCompatSpinner) findViewById(R.id.spinnerSch);

        switchNegotiable = (SwitchCompat) findViewById(R.id.switchNegotiable);
        switchFeatured = (SwitchCompat) findViewById(R.id.switchFeatured);
        switchSecondhand = (SwitchCompat) findViewById(R.id.switchSecondhand);
        switchAutomatic = (SwitchCompat) findViewById(R.id.switchAutomatic);
        switchFurn = (SwitchCompat) findViewById(R.id.switchFurn);

        containerPrice = (TextInputLayout) findViewById(R.id.containerPrice);

        containerKilometer = (TextInputLayout) findViewById(R.id.containerKilometer);

        containerSize = (TextInputLayout) findViewById(R.id.containerSize);

        containerSpace = (TextInputLayout) findViewById(R.id.containerSpace);
        containerRooms = (TextInputLayout) findViewById(R.id.containerRooms);
        containerFloors = (TextInputLayout) findViewById(R.id.containerFloors);
        containerState = (TextInputLayout) findViewById(R.id.containerState);

        containerEx = (TextInputLayout) findViewById(R.id.containerEx);
        containerSalary = (TextInputLayout) findViewById(R.id.containerSalary);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);

        progressBarVideo = (ProgressBar) findViewById(R.id.progressBar);
        imageButtonCheck = (ImageButton) findViewById(R.id.imageCheck);
        imageButtonVideo = (ImageButton) findViewById(R.id.buttonVideo);

        checkboxTerms = (CheckBox) findViewById(R.id.checkboxTerms);
        buttonTerms = (Button) findViewById(R.id.buttonTerms);

        editTextError = (EditText) findViewById(R.id.editTextError);
    }

    @Override
    public void assignActions() {

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
        if (view.getId() == R.id.buttonTrue) { //Submit

            parameters.clear();

            if (checkGeneralInput()) {

                getTemplateInput();

                ShowProgressDialog();
                AdController.getInstance(mController).submitAd(parameters, new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        HideProgressDialog();
                        showMessageInToast(R.string.toastAdSubmit);

                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        } else if (view.getId() == R.id.buttonEdit) {
            if (adapter.getCount() >= Image.MAX_IMAGES)
                showMessageInToast(R.string.toastMaxImages);
            else {
                Intent intent = new Intent(mContext, SelectImagesActivity.class);
                intent.putExtra("counter", adapter.getCount());
                startActivityForResult(intent, REQUEST_SELECT_IMG);
            }

        } else if (view.getId() == R.id.layoutHorizontal) {
            final int position = Integer.parseInt(view.getTag().toString());
            final Image clickedImage = adapter.getItem(position);

            if (!clickedImage.isLoading()) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_CAT) {
                selectedCategory = (Category) data.getSerializableExtra("category");
                editCategory.setText(selectedCategory.getFullName());
                //if error was set, then category was selected
                //previous error didn't disappear automatically
                editCategory.setError(null);

                replaceTemplate();

                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

            } else if (requestCode == REQUEST_SELECT_IMG) {
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

                if (!selectedCategory.shouldHideTag(getString(R.string.hideState)))
                    containerState.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFurn))) {
                    switchFurn.setVisibility(visibility);
                    line3.setVisibility(visibility);
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

                if (!selectedCategory.shouldHideTag(getString(R.string.hideKilo)))
                    containerKilometer.setVisibility(visibility);

                if (!selectedCategory.shouldHideTag(getString(R.string.hideAutomatic))) {
                    switchAutomatic.setVisibility(visibility);
                    line1.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    switchSecondhand.setVisibility(visibility);
                    line2.setVisibility(visibility);
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
                    line2.setVisibility(visibility);
                    switchSecondhand.setVisibility(visibility);
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
                containerState.setVisibility(visibility);

                switchFurn.setVisibility(visibility);
                line3.setVisibility(visibility);

                break;

            case Category.JOBS:
                containerPrice.setVisibility(View.VISIBLE);

                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

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

                containerKilometer.setVisibility(visibility);

                switchAutomatic.setVisibility(visibility);
                line1.setVisibility(visibility);

                switchSecondhand.setVisibility(visibility);
                line2.setVisibility(visibility);

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
                line2.setVisibility(visibility);
                switchSecondhand.setVisibility(visibility);
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
        } else if (currentTemplate == Category.PROPERTIES && selectedLocation == null) {
            autoCompleteLocation.setError(getString(R.string.errorRequired));
            autoCompleteLocation.requestFocus();

        } else if (currentTemplate != Category.JOBS && inputIsEmpty(editPrice)) {
            editPrice.setError(getString(R.string.errorRequired));
            editPrice.requestFocus();

        } else if (adapter.isLoading())
            showMessageInToast(getString(R.string.toastWaitTillUploading));
        else if (progressBarVideo.getVisibility() == View.VISIBLE)
            showMessageInToast(R.string.toastWaitTillVideoUploading);
        else {
            if (!inputIsEmpty(editDesc))
                parameters.put("description", stringInput(editDesc));

            if (currentTemplate != Category.JOBS)
                parameters.put("price", stringInput(editPrice));

            if (switchNegotiable.isChecked())
                parameters.put("is_negotiable", "1");

            if (switchFeatured.isChecked())
                parameters.put("is_featured", "1");

            parameters.put("title", stringInput(editTitle));
            parameters.put("category_id", selectedCategory.getId());
            parameters.put("show_period", ((Item) spinnerPeriod.getSelectedItem()).getId());
            parameters.put("city_id", ((City) spinnerCity.getSelectedItem()).getId());

            if (selectedLocation != null)
                parameters.put("location_id", selectedLocation.getId());

            imagesJsonArray = new JSONArray();
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

            return true;
        }

        return false;
    }

    private void getTemplateInput() {
        Item item;
        switch (currentTemplate) {

            case Category.PROPERTIES:

                if (!inputIsEmpty(editSpace))
                    parameters.put("space", stringInput(editSpace));

                if (!inputIsEmpty(editRooms))
                    parameters.put("rooms_num", stringInput(editRooms));

                if (!inputIsEmpty(editFloors))
                    parameters.put("floor", stringInput(editFloors));

                if (!inputIsEmpty(editState))
                    parameters.put("state", stringInput(editState));

                if (switchFurn.isChecked())
                    parameters.put("with_furniture", "1");

                break;

            case Category.JOBS:

                if (!inputIsEmpty(editEx))
                    parameters.put("experience", stringInput(editEx));

                // send it any way, even if salary is empty because price is always required
                parameters.put("price", "0");

                if (!inputIsEmpty(editSalary))
                    parameters.put("salary", stringInput(editSalary));

                item = ((Item) spinnerEdu.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("education_id", item.getId());

                item = ((Item) spinnerSch.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("schedule_id", item.getId());

                break;

            case Category.VEHICLES:

                if (!inputIsEmpty(editKilo))
                    parameters.put("kilometer", stringInput(editKilo));

                item = ((Item) spinnerBrand.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("type_id", item.getId());

                item = ((Item) spinnerModel.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("type_model_id", item.getId());

                item = ((Item) spinnerYear.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("manufacture_date", item.getId());

                if (switchAutomatic.isChecked())
                    parameters.put("is_automatic", "1");

                if (!switchSecondhand.isChecked())
                    parameters.put("is_new", "1");

                break;

            case Category.ELECTRONICS:

                if (!inputIsEmpty(editSize))
                    parameters.put("size", stringInput(editSize));

            case Category.MOBILES:

                item = ((Item) spinnerBrand.getSelectedItem());
                if (!item.isNothing())
                    parameters.put("type_id", item.getId());

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

                if (!switchSecondhand.isChecked())
                    parameters.put("is_new", "1");
        }
    }

    @Override
    public void onBackPressed() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupBitmap.recycle();
            popupWindow.dismiss();
            popupWindow = null;
            popupBitmap = null;
        } else {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

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

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            builder.setMessage(R.string.areYouSureDiscard).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                    .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
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
            AdController.getInstance(mController).uploadImage(new File(image.getPath()), new SuccessCallback<String>() {
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
}
