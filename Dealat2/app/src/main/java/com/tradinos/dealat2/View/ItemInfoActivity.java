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

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.LocationAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.Location;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;

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

public class ItemInfoActivity extends MasterActivity {

    private boolean enabled = false;

    private final int REQUEST_SELECT_CAT = 9;

    private Category selectedCategory;
    private Location selectedLocation;
    private List<Image> images;
    private int currentTemplate;

    private HashMap<Integer, List<Type>> brands = new HashMap<>();

    private HashMap<String, String> parameters = new HashMap<>();

    private JSONArray imagesJsonArray, deletedImgsJsonArray = new JSONArray();

    private HorizontalAdapter adapter;

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

    private SwitchCompat switchNegotiable, switchSecondhand,
            switchAutomatic,
            switchFurn;

    private TextInputLayout containerPrice, containerKilometer, containerSize,
            containerSpace, containerRooms, containerFloors, containerState,
            containerEx, containerSalary;

    private View line1, line2, line3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_item_info);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        selectedCategory = (Category) getIntent().getSerializableExtra("category");
        currentTemplate = selectedCategory.getTemplateId();

        images = (List<Image>) getIntent().getSerializableExtra("images");

        ShowProgressDialog();
        AdController.getInstance(mController).getTemplatesData(new SuccessCallback<TemplatesData>() {
            @Override
            public void OnSuccess(TemplatesData result) {
                HideProgressDialog();

                enabled = true;

                autoCompleteLocation.setAdapter(new LocationAdapter(mContext, result.getLocations()));

                brands = result.getBrands();
                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                result.getEducations().add(Item.getNoItem());
                spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                result.getSchedules().add(Item.getNoItem());
                spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

                setTemplateVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showData() {

        editCategory.setText(selectedCategory.getFullName());

        List<Item> showPeriods = new ArrayList<>();
        showPeriods.add(new Item("1", getString(R.string.periodWeek)));
        showPeriods.add(new Item("2", getString(R.string.periodTenDays)));
        showPeriods.add(new Item("3", getString(R.string.periodMonth)));

        spinnerPeriod.setAdapter(new ItemAdapter(mContext, showPeriods));


        int startYear = 1970;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(Item.getNoItem());

        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));

        spinnerYear.setAdapter(new ItemAdapter(mContext, years));

        if (images.isEmpty()) // HorizontalScrollView
            findViewById(R.id.container).setVisibility(View.GONE);

        adapter = new HorizontalAdapter(mContext, linearLayout);
        adapter.setViews(images);

        // uploading images
        for (int i = 0; i < images.size(); i++)
            new UploadImage(i).execute(images.get(i));
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                    intent.putExtra("action", SubCategoriesActivity.ACTION_SELECT_CAT);
                    intent.putExtra("category", selectedCategory);
                    startActivityForResult(intent, REQUEST_SELECT_CAT);
                }
                return false;
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
        } else if (view.getId() == R.id.layoutHorizontal) {
            final int position = Integer.parseInt(view.getTag().toString());
            final Image clickedImage = adapter.getItem(position);

            if (!clickedImage.isLoading()) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.popup_layout,
                        (ViewGroup) findViewById(R.id.popupLayout));

                ImageView imageView = layout.findViewById(R.id.imageView);
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

                if (selectedCategory.getTemplateId() != currentTemplate) {
                    replaceTemplate();

                    List<Type> templateBrands = brands.get(currentTemplate);
                    if (templateBrands != null)
                        spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));
                }
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
        setTemplateVisibility(View.GONE);
        currentTemplate = selectedCategory.getTemplateId();
        setTemplateVisibility(View.VISIBLE);
    }

    private void setTemplateVisibility(int visibility) {
        if (visibility != View.VISIBLE && visibility != View.GONE)
            return;

        switch (currentTemplate) {
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

                if (visibility == View.VISIBLE)
                    containerPrice.setVisibility(View.GONE);
                else
                    containerPrice.setVisibility(View.VISIBLE);

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

        if (inputIsEmpty(editTitle)) {
            editTitle.setError(getString(R.string.errorRequired));
            editTitle.requestFocus();

        } else if (currentTemplate != Category.JOBS && inputIsEmpty(editPrice)) {
            editPrice.setError(getString(R.string.errorRequired));
            editPrice.requestFocus();

        } else if (selectedLocation == null) {
            autoCompleteLocation.setError(getString(R.string.errorRequired));
            autoCompleteLocation.requestFocus();

        } else if (adapter.isLoading())
            showMessageInToast(getString(R.string.toastWaitTillUploading));
        else {
            if (!inputIsEmpty(editDesc))
                parameters.put("description", stringInput(editDesc));

            if (currentTemplate != Category.JOBS)
                parameters.put("price", stringInput(editPrice));

            if (switchNegotiable.isChecked())
                parameters.put("is_negotiable", "1");

            parameters.put("title", stringInput(editTitle));
            parameters.put("category_id", selectedCategory.getId());
            parameters.put("show_period", ((Item) spinnerPeriod.getSelectedItem()).getId());
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
                parameters.put("deleted_imags", deletedImgsJsonArray.toString());

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

                if (!inputIsEmpty(editSalary)) {
                    parameters.put("price", "0");
                    parameters.put("salary", stringInput(editSalary));
                }

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
}
