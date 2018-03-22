package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.LocationAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
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
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.Location;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;

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

    private final int REQUEST_SELECT = 7;

    private boolean enabled = false;

    private final String NULL = "-1";
    private Ad currentAd;
    private Location selectedLocation;
    private List<Type> templateBrands = new ArrayList<>();
    private List<Item> years = new ArrayList<>();
    private List<Item> showPeriods = new ArrayList<>();

    private HashMap<String, String> parameters = new HashMap<>();

    private HorizontalAdapter adapter;
    private JSONArray imagesJsonArray, deletedImgsJsonArray = new JSONArray();

    private Bitmap popupBitmap;

    //views
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    private TextView textBrand, textModel,
            textDate,
            textEdu, textSch;

    private EditText editTitle, editDesc, editCategory, editPrice,
            editKilo,
            editSize,
            editSpace, editRooms, editFloors, editState,
            editEx, editSalary;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerPeriod,
            spinnerBrand, spinnerModel, spinnerYear,
            spinnerEdu, spinnerSch;

    private SwitchCompat switchNegotiable, switchSecondhand, switchFeatured,
            switchAutomatic,
            switchFurn;

    private TextInputLayout containerPrice, containerKilometer, containerSize,
            containerSpace, containerRooms, containerFloors, containerState,
            containerEx, containerSalary;

    private View line1, line2, line3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_ad);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        int startYear = 1970;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add(Item.getNoItem());
        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));
        spinnerYear.setAdapter(new ItemAdapter(mContext, years));


        editCategory.setText(MyApplication.getCategoryById(currentAd.getCategoryId()).getFullName());


        showPeriods.add(new Item("1", getString(R.string.periodWeek)));
        showPeriods.add(new Item("2", getString(R.string.periodTenDays)));
        showPeriods.add(new Item("3", getString(R.string.periodMonth)));
        spinnerPeriod.setAdapter(new ItemAdapter(mContext, showPeriods));


        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {
                currentAd = result;

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

                        autoCompleteLocation.setAdapter(new LocationAdapter(mContext, result.getLocations()));

                        templateBrands = result.getBrands().get(currentAd.getTemplate());
                        if (templateBrands != null)
                            spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                        result.getEducations().add(Item.getNoItem());
                        spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                        result.getSchedules().add(Item.getNoItem());
                        spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

                        HideProgressDialog();

                        showTemplate();
                        fillTemplate(result);
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
    }

    @Override
    public void assignActions() {
        autoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLocation = ((LocationAdapter) autoCompleteLocation.getAdapter()).getItem(i);
                autoCompleteLocation.setText(selectedLocation.getFullName());
            }
        });

        autoCompleteLocation.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (autoCompleteLocation.getText().toString().equals(""))
                    autoCompleteLocation.showDropDown();
                return false;
            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Type selectedType = ((TypeAdapter) spinnerBrand.getAdapter()).getItem(i);

                spinnerModel.setAdapter(new ItemAdapter(mContext, selectedType.getModels()));

                if (currentAd.getTemplate() == Category.VEHICLES) //it means there're models with brands
                    // and to ensure casting to AdVehicle
                    if (((AdVehicle) currentAd).getTypeId()!= null &&
                            ((AdVehicle) currentAd).getTypeId().equals(selectedType.getId()))
                        spinnerModel.setSelection(getItemIndex(selectedType.getModels(), ((AdVehicle) currentAd).getModelId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    editCategory.setError(getString(R.string.errorCategory));
                    editCategory.requestFocus();
                }
                return false;
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

                    ImageView imageView = layout.findViewById(R.id.imageView);

                    if (!clickedImage.isPreviouslyLoaded()) {
                        popupBitmap = new ImageDecoder().decodeLargeImage(clickedImage.getPath());
                        imageView.setImageBitmap(popupBitmap);
                    } else {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                        mImageLoader.get(MyApplication.getBaseUrlForImages() + clickedImage.getServerPath(),
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT) {
            List<Image> newImages = (List<Image>) data.getSerializableExtra("images");

            int base = adapter.getCount();
            adapter.setViews(newImages);

            // uploading images
            for (int i = 0; i < newImages.size(); i++)
                new UploadImage(i + base).execute(newImages.get(i));
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
            parameters.put("location_id", selectedLocation.getId());

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
                parameters.put("deleted_imags", deletedImgsJsonArray.toString());

            return true;
        }

        return false;
    }

    private void getTemplateInput() {

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

                if (inputIsEmpty(editState))
                    parameters.put("state", NULL);
                else
                    parameters.put("state", stringInput(editState));

                if (switchFurn.isChecked())
                    parameters.put("with_furniture", "1");
                else
                    parameters.put("with_furniture", "0");

                break;

            case Category.JOBS:

                if (inputIsEmpty(editEx))
                    parameters.put("experience", NULL);
                else
                    parameters.put("experience", stringInput(editEx));

                parameters.put("price", "0");
                parameters.put("salary", String.valueOf(doubleEditText(editSalary)));

                parameters.put("education_id", ((Item) spinnerEdu.getSelectedItem()).getId());

                parameters.put("schedule_id", ((Item) spinnerSch.getSelectedItem()).getId());

                break;

            case Category.VEHICLES:
                if (inputIsEmpty(editKilo))
                    parameters.put("kilometer", NULL);
                else
                    parameters.put("kilometer", String.valueOf(doubleEditText(editKilo)));

                parameters.put("type_id", ((Item) spinnerBrand.getSelectedItem()).getId());

                parameters.put("type_model_id", ((Item) spinnerModel.getSelectedItem()).getId());

                parameters.put("manufacture_date", ((Item) spinnerYear.getSelectedItem()).getId());

                if (switchAutomatic.isChecked())
                    parameters.put("is_automatic", "1");
                else
                    parameters.put("is_automatic", "0");

                if (switchSecondhand.isChecked())
                    parameters.put("is_new", "0");
                else
                    parameters.put("is_new", "1");

                break;

            case Category.ELECTRONICS:

                if (inputIsEmpty(editSize))
                    parameters.put("size", NULL);
                else
                    parameters.put("size", String.valueOf(doubleEditText(editSize)));

            case Category.MOBILES:

                parameters.put("type_id", ((Item) spinnerBrand.getSelectedItem()).getId());

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                if (switchSecondhand.isChecked())
                    parameters.put("is_new", "0");
                else
                    parameters.put("is_new", "1");
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

        editDesc.setText(currentAd.getDescription());

        int loc = getItemIndex(new ArrayList<Item>(data.getLocations()), currentAd.getLocationId());
        selectedLocation = data.getLocations().get(loc);
        autoCompleteLocation.setText(selectedLocation.getFullName());

        spinnerPeriod.setSelection(getItemIndex(showPeriods, String.valueOf(currentAd.getShowPeriod())));


        int brand = 0;
        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:

                editSpace.setText(formattedNumber(((AdProperty) currentAd).getSpace()));
                editRooms.setText(formattedNumber(((AdProperty) currentAd).getRoomNum()));
                editFloors.setText(formattedNumber(((AdProperty) currentAd).getFloorNum()));
                editState.setText(((AdProperty) currentAd).getState());

                if (((AdProperty) currentAd).isFurnished())
                    switchFurn.setChecked(true);
                else
                    switchFurn.setChecked(false);

                break;

            case Category.JOBS:
                spinnerEdu.setSelection(getItemIndex(data.getEducations(), ((AdJob) currentAd).getEducationId()));
                spinnerSch.setSelection(getItemIndex(data.getSchedules(), ((AdJob) currentAd).getScheduleId()));

                editEx.setText(((AdJob) currentAd).getExperience());
                editSalary.setText(formattedNumber(((AdJob) currentAd).getSalary()));

                break;

            case Category.VEHICLES:
                brand = getItemIndex(new ArrayList<Item>(templateBrands),
                        ((AdVehicle) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);

                spinnerYear.setSelection(getItemIndex(years, ((AdVehicle) currentAd).getManufactureYear()));

                editKilo.setText(formattedNumber(((AdVehicle) currentAd).getKilometer()));

                if (((AdVehicle) currentAd).isAutomatic())
                    switchAutomatic.setChecked(true);
                else
                    switchAutomatic.setChecked(false);

                if (((AdVehicle) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);

                break;

            case Category.ELECTRONICS:
                brand = getItemIndex(new ArrayList<Item>(templateBrands),
                        ((AdElectronic) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);
                editSize.setText(formattedNumber(((AdElectronic) currentAd).getSize()));

                if (((AdElectronic) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);
                break;

            case Category.MOBILES:
                brand = getItemIndex(new ArrayList<Item>(templateBrands),
                        ((AdMobile) currentAd).getTypeId());
                spinnerBrand.setSelection(brand);

                if (((AdMobile) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);

                break;

            case Category.FASHION:

                if (((AdFashion) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);

                break;

            case Category.KIDS:

                if (((AdKid) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);

                break;

            case Category.SPORTS:

                if (((AdSport) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);

                break;

            case Category.INDUSTRIES:

                if (((AdIndustry) currentAd).isSecondhand())
                    switchSecondhand.setChecked(true);
                else
                    switchSecondhand.setChecked(false);
        }
    }

    private void showTemplate() {
        int visibility = View.VISIBLE;

        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:

                containerSpace.setVisibility(visibility);
                containerRooms.setVisibility(visibility);
                containerFloors.setVisibility(visibility);
                containerState.setVisibility(visibility);
                switchFurn.setVisibility(visibility);
                line3.setVisibility(visibility);

                break;

            case Category.JOBS:
                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

                containerEx.setVisibility(visibility);

                containerSalary.setVisibility(visibility);

                containerPrice.setVisibility(View.GONE);

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
}
