package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class ItemInfoActivity extends MasterActivity {

    private final int REQUEST_SELECT_CAT = 1;


    private Category selectedCategory;
    private List<Image> images;
    private int currentTemplate;

    private HashMap<String, String> parameters = new HashMap<>();

    private HorizontalAdapter adapter;

    //views
    private LinearLayout linearLayout;

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

    private CheckBox checkboxNegotiable, checkboxSecondhand,
            checkboxAutomatic,
            checkboxFurn;


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

                autoCompleteLocation.setAdapter(new LocationAdapter(mContext, result.getLocations()));

                List<Type> brands = result.getBrands().get(currentTemplate);
                if (brands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, brands));

                spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));

            }
        });
    }

    @Override
    public void showData() {
        setTemplateVisibility(View.VISIBLE);

        editCategory.setText(selectedCategory.getFullName());

        List<Item> showPeriods = new ArrayList<>();
        showPeriods.add(new Item("1", getString(R.string.periodWeek)));
        showPeriods.add(new Item("2", getString(R.string.periodTenDays)));
        showPeriods.add(new Item("3", getString(R.string.periodMonth)));

        spinnerPeriod.setAdapter(new ItemAdapter(mContext, showPeriods));


        int startYear = 1990;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(Item.getNoItem());

        for (int i=startYear; i<= currentYear; i++)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));

        spinnerYear.setAdapter(new ItemAdapter(mContext, years));

        adapter = new HorizontalAdapter(mContext, linearLayout);
        adapter.setViews(images);
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

        checkboxNegotiable = (CheckBox) findViewById(R.id.checkboxNegotiable);
        checkboxSecondhand = (CheckBox) findViewById(R.id.checkboxSecondhand);
        checkboxAutomatic = (CheckBox) findViewById(R.id.checkboxAutomatic);
        checkboxFurn = (CheckBox) findViewById(R.id.checkboxFurn);
    }

    @Override
    public void assignActions() {

        autoCompleteLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Item selectedItem = ((ItemAdapter) autoCompleteLocation.getAdapter()).getItem(i);
                parameters.put("location_id", selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                intent.putExtra("category", selectedCategory);
             //   startActivityForResult(intent, );
            }
        });

        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Item selectedItem = ((ItemAdapter) spinnerPeriod.getAdapter()).getItem(i);

                parameters.put("show_period", selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Item selectedItem = ((ItemAdapter) spinnerBrand.getAdapter()).getItem(i);

                if (selectedItem.getId().equals("-1")) // --
                    parameters.remove("type_id");
                else
                    parameters.put("type_id", selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Item selectedItem = ((ItemAdapter) spinnerYear.getAdapter()).getItem(i);

                if (selectedItem.getId().equals("-1")) // --
                    parameters.remove("manufacture_date");
                else
                    parameters.put("manufacture_date", selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { //Submit

            if (checkGeneralInput()) {

                getTemplateInput();

             /*   ShowProgressDialog();
                AdController.getInstance(mController).submitAd(parameters, new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        HideProgressDialog();
                        showMessageInToast(R.string.buttonSubmit);

                        setResult(RESULT_OK);
                        finish();
                    }
                });*/
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_CAT) {
                selectedCategory = (Category) data.getSerializableExtra("category");
                editCategory.setText(selectedCategory.getFullName());

                if (selectedCategory.getTemplateId() != currentTemplate)
                    replaceTemplate();
            }
        }
    }

    private void replaceTemplate() {
        setTemplateVisibility(View.GONE);
        currentTemplate = selectedCategory.getTemplateId();
        setTemplateVisibility(View.VISIBLE);
    }

    private void setTemplateVisibility(int visibility) {
        if (visibility != View.VISIBLE || visibility != View.GONE)
            return;

        switch (currentTemplate) {
            case Category.PROPERTIES:
                editSpace.setVisibility(visibility);
                editRooms.setVisibility(visibility);
                editFloors.setVisibility(visibility);
                editState.setVisibility(visibility);
                checkboxFurn.setVisibility(visibility);

                break;

            case Category.JOBS:
                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

                editEx.setVisibility(visibility);

                editSalary.setVisibility(visibility);

                if (visibility == View.VISIBLE)
                    editPrice.setVisibility(View.GONE);
                else
                    editPrice.setVisibility(View.VISIBLE);

                break;

            case Category.VEHICLES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

                textModel.setVisibility(visibility);
                spinnerModel.setVisibility(visibility);

                textDate.setVisibility(visibility);
                spinnerYear.setVisibility(visibility);

                editKilo.setVisibility(visibility);
                checkboxAutomatic.setVisibility(visibility);

                checkboxSecondhand.setVisibility(visibility);

                break;

            case Category.ELECTRONICS:
                editSize.setVisibility(visibility);

            case Category.MOBILES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                checkboxSecondhand.setVisibility(visibility);
        }
    }

    private boolean checkGeneralInput() {

        if (editTitle.getText().toString().equals(""))
            editTitle.setError(getString(R.string.errorRequired));
        else if (currentTemplate != Category.JOBS && editPrice.getText().toString().equals(""))
            editPrice.setError(getString(R.string.errorRequired));
        else {
            if (!editDesc.getText().toString().equals(""))
                parameters.put("description", String.valueOf(editDesc.getText()));

            if (checkboxNegotiable.isChecked())
                parameters.put("is_negotiable", "1");

            parameters.put("title", String.valueOf(editTitle.getText()));
            parameters.put("category_id", selectedCategory.getId());
            parameters.put("show_period", ((Item) spinnerPeriod.getSelectedItem()).getId());

            return true;
        }

        return false;
    }

    private void getTemplateInput() {
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

                if (checkboxFurn.isChecked())
                    parameters.put("with_furniture", "1");

                break;

            case Category.JOBS:

                if (!inputIsEmpty(editEx))
                    parameters.put("experience", stringInput(editEx));

                if (!inputIsEmpty(editSalary)) {
                    parameters.put("price", "0");
                    parameters.put("salary", stringInput(editSalary));
                }

                // spinnerSch.setVisibility();

                //spinnerEdu.setVisibility();

                break;

            case Category.VEHICLES:

                if (!inputIsEmpty(editKilo))
                    parameters.put("kilometer", stringInput(editKilo));


                if (checkboxAutomatic.isChecked())
                    parameters.put("is_automatic", "1");

                if (!checkboxSecondhand.isChecked())
                    parameters.put("is_new", "1");

                //    spinnerModel.setVisibility();

                //  spinnerYear.setVisibility();

                break;

            case Category.ELECTRONICS:

                if (!inputIsEmpty(editSize))
                    parameters.put("size", stringInput(editSize));

            case Category.MOBILES:

                // spinnerBrand.setVisibility(visibility);

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

                if (!checkboxSecondhand.isChecked())
                    parameters.put("is_new", "1");
        }
    }

    private boolean inputIsEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString()))
            return true;
        return false;
    }

    private String stringInput(EditText editText) {
        return String.valueOf(editText.getText());
    }
}
