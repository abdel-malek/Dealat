package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.LocationAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.Location;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 01.03.18.
 */

public class FilterActivity extends MasterActivity {

    private final int REQUEST_SELECT_CAT = 1;

    private int currentTemplate;
    private Category selectedCategory;
    private Location selectedLocation;

    private HashMap<String, String> parameters = new HashMap<>();

    private HashMap<Integer, List<Type>> brands = new HashMap<>();

    //Views
    private TextView textBrand, textModel, textTransmission, textKilo,
            textDate,
            textEdu, textSch, textSalary,
            textFurn, textSpace, textRooms, textFloor,
            textSize;

    private EditText editQuery, editCategory, editPriceMin, editPriceMax,
            editKilometerMin, editKilometerMax,
            editSizeMin, editSizeMax,
            editSpaceMin, editSpaceMax, editRoomsMin, editRoomsMax, editFloorsMin, editFloorsMax,
            editSalaryMin, editSalaryMax;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerBrand, spinnerModel, spinnerYear, spinnerTransmission,
            spinnerEdu, spinnerSch,
            spinnerFurn;

    private LinearLayout containerKilometer, containerSize,
            containerSalary, containerSpace, containerRooms, containerFloors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_filter);
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

                autoCompleteLocation.setAdapter(new LocationAdapter(mContext, result.getLocations()));

                brands = result.getBrands();
                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                spinnerEdu.setAdapter(new ItemAdapter(mContext, result.getEducations()));

                spinnerSch.setAdapter(new ItemAdapter(mContext, result.getSchedules()));
            }
        });
    }

    @Override
    public void showData() {

        if (!selectedCategory.isMain()){
            editCategory.setText(selectedCategory.getFullName());
            setTemplateVisibility(View.VISIBLE);
        }

        int startYear = 1980;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(Item.getNoItem());

        for (int i = startYear; i <= currentYear; i++)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));

        spinnerYear.setAdapter(new ItemAdapter(mContext, years));
    }

    @Override
    public void assignUIReferences() {
        textBrand = (TextView) findViewById(R.id.textBrand);
        textModel = (TextView) findViewById(R.id.textModel);
        textDate = (TextView) findViewById(R.id.textDate);
        textTransmission = (TextView) findViewById(R.id.textTransmission);
        textKilo = (TextView) findViewById(R.id.textKilo);

        textEdu = (TextView) findViewById(R.id.textEdu);
        textSch = (TextView) findViewById(R.id.textSch);
        textSalary = (TextView) findViewById(R.id.textSalary);


        textRooms = (TextView) findViewById(R.id.textRooms);
        textFloor = (TextView) findViewById(R.id.textFloor);
        textFurn = (TextView) findViewById(R.id.textFurn);
        textSpace = (TextView) findViewById(R.id.textSpace);

        textSize = (TextView) findViewById(R.id.textSize);

        editQuery = (EditText) findViewById(R.id.editQuery);
        editCategory = (EditText) findViewById(R.id.editCategory);
        editPriceMax = (EditText) findViewById(R.id.editPriceMax);
        editPriceMin = (EditText) findViewById(R.id.editPriceMin);

        editKilometerMax = (EditText) findViewById(R.id.editKilometerMax);
        editKilometerMin = (EditText) findViewById(R.id.editKilometerMin);

        editSizeMax = (EditText) findViewById(R.id.editSizeMax);
        editSizeMin = (EditText) findViewById(R.id.editSizeMin);

        editSalaryMax = (EditText) findViewById(R.id.editSalaryMax);
        editSalaryMin = (EditText) findViewById(R.id.editSalaryMin);


        editSpaceMax = (EditText) findViewById(R.id.editSpaceMax);
        editSpaceMin = (EditText) findViewById(R.id.editSpaceMin);

        editRoomsMax = (EditText) findViewById(R.id.editRoomsMax);
        editRoomsMin = (EditText) findViewById(R.id.editRoomsMin);

        editFloorsMax = (EditText) findViewById(R.id.editFloorsMax);
        editFloorsMin = (EditText) findViewById(R.id.editFloorsMin);

        autoCompleteLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocation);

        spinnerBrand = (AppCompatSpinner) findViewById(R.id.spinnerBrand);
        spinnerModel = (AppCompatSpinner) findViewById(R.id.spinnerModel);
        spinnerYear = (AppCompatSpinner) findViewById(R.id.spinnerYear);
        spinnerTransmission = (AppCompatSpinner) findViewById(R.id.spinnerTransmission);

        spinnerEdu = (AppCompatSpinner) findViewById(R.id.spinnerEdu);
        spinnerSch = (AppCompatSpinner) findViewById(R.id.spinnerSch);

        spinnerFurn = (AppCompatSpinner) findViewById(R.id.spinnerFurn);

        containerKilometer = (LinearLayout) findViewById(R.id.containerKilometer);
        containerSize = (LinearLayout) findViewById(R.id.containerSize);
        containerSalary = (LinearLayout) findViewById(R.id.containerSalary);
        containerSpace = (LinearLayout) findViewById(R.id.containerSpace);
        containerRooms = (LinearLayout) findViewById(R.id.containerRooms);
        containerFloors = (LinearLayout) findViewById(R.id.containerFloors);
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

                if (selectedType.getId().equals("-1")) // --
                    parameters.remove("type_id");
                else
                    parameters.put("type_id", selectedType.getId());

                spinnerModel.setAdapter(new ItemAdapter(mContext, selectedType.getModels()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { //Show

            getGeneralInput();
            getTemplateInput();

            Intent intent = new Intent();
            intent.putExtra("category", selectedCategory);
            intent.putExtra("parameters", parameters);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_CAT) {
                selectedCategory = (Category) data.getSerializableExtra("category");
                editCategory.setText(selectedCategory.getFullName());

                if (!selectedCategory.isMain())
                    parameters.put("category_id", selectedCategory.getId());

                if (selectedCategory.getTemplateId() != currentTemplate) {
                    replaceTemplate();

                    List<Type> templateBrands = brands.get(currentTemplate);
                    if (templateBrands != null)
                        spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                    //  parameters.clear();
                }
            }
        }
    }

    private void getGeneralInput(){
        if (!inputIsEmpty(editQuery))
            parameters.put("query", stringInput(editQuery));

        if (selectedLocation != null)
            parameters.put("location_id", selectedLocation.getId());

        if (!inputIsEmpty(editPriceMax))
            parameters.put("price_max", stringInput(editPriceMax));

        if (!inputIsEmpty(editPriceMin))
            parameters.put("price_min", stringInput(editPriceMin));
    }

    private void getTemplateInput(){
        switch (currentTemplate) {

            case Category.PROPERTIES:

                if (!inputIsEmpty(editSpaceMax))
                    parameters.put("space_max", stringInput(editSpaceMax));

                if (!inputIsEmpty(editSpaceMin))
                    parameters.put("space_min", stringInput(editSpaceMin));

                if (!inputIsEmpty(editRoomsMax))
                    parameters.put("rooms_num_max", stringInput(editRoomsMax));

                if (!inputIsEmpty(editRoomsMin))
                    parameters.put("rooms_num_min", stringInput(editRoomsMin));

                if (!inputIsEmpty(editFloorsMax))
                    parameters.put("floor_max", stringInput(editFloorsMax));

                if (!inputIsEmpty(editFloorsMin))
                    parameters.put("floor_min", stringInput(editFloorsMin));

               // if (checkboxFurn.isChecked())
               //     parameters.put("with_furniture", "1");

                break;

            case Category.JOBS:

                if (!inputIsEmpty(editSalaryMax))
                    parameters.put("salary_max", stringInput(editSalaryMax));

                break;

            case Category.VEHICLES:

                if (!inputIsEmpty(editKilometerMax))
                    parameters.put("kilometer_max", stringInput(editKilometerMax));


              //  if (checkboxAutomatic.isChecked())
                 //   parameters.put("is_automatic", "1");

              //  if (!checkboxSecondhand.isChecked())
                //    parameters.put("is_new", "1");

                break;

            case Category.ELECTRONICS:

                if (!inputIsEmpty(editSizeMax))
                    parameters.put("size_max", stringInput(editSizeMax));

            case Category.MOBILES:

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

              //  if (!checkboxSecondhand.isChecked())
                //    parameters.put("is_new", "1");
        }
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
                textSpace.setVisibility(visibility);
                containerSpace.setVisibility(visibility);

                textRooms.setVisibility(visibility);
                containerRooms.setVisibility(visibility);

                textFloor.setVisibility(visibility);
                containerFloors.setVisibility(visibility);

                textFurn.setVisibility(visibility);
                spinnerFurn.setVisibility(visibility);

                break;

            case Category.JOBS:
                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

                textSalary.setVisibility(visibility);
                containerSalary.setVisibility(visibility);

                break;

            case Category.VEHICLES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

                textModel.setVisibility(visibility);
                spinnerModel.setVisibility(visibility);

                textDate.setVisibility(visibility);
                spinnerYear.setVisibility(visibility);

                textKilo.setVisibility(visibility);
                containerKilometer.setVisibility(visibility);

                textTransmission.setVisibility(visibility);
                spinnerTransmission.setVisibility(visibility);

                //    checkboxSecondhand.setVisibility(visibility);
                break;

            case Category.ELECTRONICS:
                textSize.setVisibility(visibility);
                containerSize.setVisibility(visibility);


            case Category.MOBILES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                //    checkboxSecondhand.setVisibility(visibility);
        }
    }
}
