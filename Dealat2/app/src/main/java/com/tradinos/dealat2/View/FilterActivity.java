package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.CheckableAdapter;
import com.tradinos.dealat2.Adapter.CityAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Adapter.AutoCompleteAdapter;
import com.tradinos.dealat2.Adapter.TypeAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.City;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 01.03.18.
 */

public class FilterActivity extends MasterActivity {

    private final int REQUEST_FILTER_CAT = 8;

    private int action = 0;
    private int currentTemplate;
    private Category selectedCategory;
    private Item selectedLocation;

    private HashMap<String, String> parameters = new HashMap<>();

    private HashMap<Integer, List<Type>> brands = new HashMap<>();

    //Views
    private TextView textPrice,
            textBrand, textModel, textTransmission, textKilo,
            textDate,
            textEdu, textSch, textSalary,
            textFurn, textSpace, textRooms, textFloor,
            textSize,
            textState;

    private EditText editQuery, editCategory, editPriceMin, editPriceMax,
            editKilometerMin, editKilometerMax,
            editSizeMin, editSizeMax,
            editSpaceMin, editSpaceMax, editRoomsMin, editRoomsMax, editFloorsMin, editFloorsMax,
            editSalaryMin, editSalaryMax;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerBrand, spinnerModel, spinnerYear, spinnerTransmission,
            spinnerEdu, spinnerSch,
            spinnerFurn,
            spinnerState,
            spinnerCity;

    private LinearLayout containerKilometer, containerSize,
            containerSalary, containerSpace, containerRooms, containerFloors, containerPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_filter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        selectedCategory = (Category) getIntent().getSerializableExtra("category");
        currentTemplate = selectedCategory.getTemplateId();

        action = getIntent().getIntExtra("action", 0);

        ShowProgressDialog();
        AdController.getInstance(mController).getTemplatesData(new SuccessCallback<TemplatesData>() {
            @Override
            public void OnSuccess(TemplatesData result) {
                HideProgressDialog();

                findViewById(R.id.buttonTrue).setEnabled(true);

                result.getCities().add(0, City.getNoCity());
                spinnerCity.setAdapter(new CityAdapter(mContext, result.getCities()));

                brands = result.getBrands();
                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands));

                result.getEducations().add(0, new Item("-1", getString(R.string.all)));
                spinnerEdu.setAdapter(new CheckableAdapter(mContext, result.getEducations()));

                result.getSchedules().add(0, new Item("-1", getString(R.string.all)));
                spinnerSch.setAdapter(new CheckableAdapter(mContext, result.getSchedules()));

                setTemplateVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showData() {

        editCategory.setText(selectedCategory.getFullName());

        int startYear = AdVehicle.START_YEAR;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Item> years = new ArrayList<>();
        years.add(new Item("-1", getString(R.string.all)));

        for (int i = currentYear; i >= startYear; i--)
            years.add(new Item(String.valueOf(i), String.valueOf(i)));

        spinnerYear.setAdapter(new CheckableAdapter(mContext, years));


        List<Item> furnOptions = new ArrayList<>();
        furnOptions.add(new Item("-1", getString(R.string.all)));
        furnOptions.add(new Item("1", getString(R.string.yes)));
        furnOptions.add(new Item("0", getString(R.string.no)));

        spinnerFurn.setAdapter(new ItemAdapter(mContext, furnOptions));

        List<Item> usageOptions = new ArrayList<>();
        usageOptions.add(new Item("-1", getString(R.string.all)));
        usageOptions.add(new Item("1", getString(R.string.newU)));
        usageOptions.add(new Item("0", getString(R.string.old)));

        spinnerState.setAdapter(new ItemAdapter(mContext, usageOptions));

        List<Item> transmissionOptions = new ArrayList<>();
        transmissionOptions.add(new Item("-1", getString(R.string.all)));
        transmissionOptions.add(new Item("1", getString(R.string.labelAutomatic)));
        transmissionOptions.add(new Item("0", getString(R.string.manual)));

        spinnerTransmission.setAdapter(new ItemAdapter(mContext, transmissionOptions));
    }

    @Override
    public void assignUIReferences() {
        textPrice = (TextView) findViewById(R.id.textPrice);
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

        textState = (TextView) findViewById(R.id.textState);

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

        spinnerState = (AppCompatSpinner) findViewById(R.id.spinnerState);

        spinnerCity = (AppCompatSpinner) findViewById(R.id.spinner);

        containerKilometer = (LinearLayout) findViewById(R.id.containerKilometer);
        containerSize = (LinearLayout) findViewById(R.id.containerSize);
        containerSalary = (LinearLayout) findViewById(R.id.containerSalary);
        containerSpace = (LinearLayout) findViewById(R.id.containerSpace);
        containerRooms = (LinearLayout) findViewById(R.id.containerRooms);
        containerFloors = (LinearLayout) findViewById(R.id.containerFloors);
        containerPrice = (LinearLayout) findViewById(R.id.containerPrice);
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

                if (selectedLocation.isNothing())
                    selectedLocation = null;
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

        editCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                    intent.putExtra("action", SubCategoriesActivity.ACTION_FILTER_CAT);
                    intent.putExtra("category", selectedCategory);
                    startActivityForResult(intent, REQUEST_FILTER_CAT);
                }
                return false;
            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Type selectedType = ((TypeAdapter) spinnerBrand.getAdapter()).getItem(i);

                spinnerModel.setAdapter(new CheckableAdapter(mContext, selectedType.getModels()));
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

            if (action == 0) {

                Intent intent = new Intent();
                intent.putExtra("category", selectedCategory);
                intent.putExtra("parameters", parameters);
                setResult(RESULT_OK, intent);

            } else if (action == 1) {
                Intent intent = new Intent(mContext, ViewAdsActivity.class);
                intent.putExtra("category", selectedCategory);
                intent.putExtra("parameters", parameters);
                intent.putExtra("action", ViewAdsActivity.ACTION_SEARCH);

                startActivity(intent);
            }

            finish();
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

        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_FILTER_CAT) {
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

    private void getGeneralInput() {
        if (!inputIsEmpty(editQuery))
            parameters.put(getString(R.string.query), stringInput(editQuery));

        if (!selectedCategory.isMain()) {
            parameters.put("category_id", selectedCategory.getId());
            parameters.put(getString(R.string.categoryName), selectedCategory.getFullName());
        }

        City city = (City) spinnerCity.getSelectedItem();
        if (!city.isNothing()) {
            parameters.put("city_id", city.getId());
            parameters.put(getString(R.string.cityName), city.getName());
        }

        if (selectedLocation != null) {
            parameters.put("location_id", selectedLocation.getId());
            parameters.put(getString(R.string.locationName), selectedLocation.getName());
        }

        if (!inputIsEmpty(editPriceMax))
            parameters.put(getString(R.string.priceMax), stringInput(editPriceMax));

        if (!inputIsEmpty(editPriceMin))
            parameters.put(getString(R.string.priceMin), stringInput(editPriceMin));
    }

    private void getTemplateInput() {
        Item item;
        JSONArray jsonArray;

        switch (currentTemplate) {

            case Category.PROPERTIES:

                if (!inputIsEmpty(editSpaceMax))
                    parameters.put(getString(R.string.spaceMax), stringInput(editSpaceMax));

                if (!inputIsEmpty(editSpaceMin))
                    parameters.put(getString(R.string.spaceMin), stringInput(editSpaceMin));

                if (!inputIsEmpty(editRoomsMax))
                    parameters.put(getString(R.string.roomsNumMax), stringInput(editRoomsMax));

                if (!inputIsEmpty(editRoomsMin))
                    parameters.put(getString(R.string.roomsNumMin), stringInput(editRoomsMin));

                if (!inputIsEmpty(editFloorsMax))
                    parameters.put(getString(R.string.floorMax), stringInput(editFloorsMax));

                if (!inputIsEmpty(editFloorsMin))
                    parameters.put(getString(R.string.floorMin), stringInput(editFloorsMin));

                item = ((Item) spinnerFurn.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("with_furniture", item.getId());
                    parameters.put(getString(R.string.furnitureName), item.getName());
                }

                break;

            case Category.JOBS:

                if (!inputIsEmpty(editSalaryMax))
                    parameters.put(getString(R.string.salaryMax), stringInput(editSalaryMax));

                if (!inputIsEmpty(editSalaryMin))
                    parameters.put(getString(R.string.salaryMin), stringInput(editSalaryMin));

                jsonArray = ((CheckableAdapter) spinnerEdu.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("education_id", jsonArray.toString());
                    parameters.put(getString(R.string.educationName), ((CheckableAdapter) spinnerEdu.getAdapter()).getSelectedNames());
                }

                jsonArray = ((CheckableAdapter) spinnerSch.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("schedule_id", jsonArray.toString());
                    parameters.put(getString(R.string.scheduleName), ((CheckableAdapter) spinnerSch.getAdapter()).getSelectedNames());
                }

                break;

            case Category.VEHICLES:

                if (!inputIsEmpty(editKilometerMax))
                    parameters.put(getString(R.string.kilometerMax), stringInput(editKilometerMax));

                if (!inputIsEmpty(editKilometerMin))
                    parameters.put(getString(R.string.kilometerMin), stringInput(editKilometerMin));

                item = ((Item) spinnerTransmission.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("is_automatic", item.getId());
                    parameters.put(getString(R.string.automaticName), item.getName());
                }

                item = ((Item) spinnerBrand.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("type_id", item.getId());
                    parameters.put(getString(R.string.typeName), item.getName());
                }

                jsonArray = ((CheckableAdapter) spinnerModel.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("type_model_id", jsonArray.toString());
                    parameters.put(getString(R.string.modelName), ((CheckableAdapter) spinnerModel.getAdapter()).getSelectedNames());
                }

                jsonArray = ((CheckableAdapter) spinnerYear.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("manufacture_date", jsonArray.toString());
                    parameters.put(getString(R.string.yearsName), ((CheckableAdapter) spinnerYear.getAdapter()).getSelectedNames());
                }

                item = ((Item) spinnerState.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("is_new", item.getId());
                    parameters.put(getString(R.string.stateName), item.getName());
                }

                break;

            case Category.ELECTRONICS:

                if (!inputIsEmpty(editSizeMax))
                    parameters.put(getString(R.string.sizeMax), stringInput(editSizeMax));

                if (!inputIsEmpty(editSizeMin))
                    parameters.put(getString(R.string.sizeMin), stringInput(editSizeMin));

            case Category.MOBILES:
                item = ((Item) spinnerBrand.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("type_id", item.getId());
                    parameters.put(getString(R.string.typeName), item.getName());
                }

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

                item = ((Item) spinnerState.getSelectedItem());
                if (!item.isNothing()){
                    parameters.put("is_new", item.getId());
                    parameters.put(getString(R.string.stateName), item.getName());
                }
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
                if (!selectedCategory.shouldHideTag(getString(R.string.hideSpace))) {
                    textSpace.setVisibility(visibility);
                    containerSpace.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideRoom))) {
                    textRooms.setVisibility(visibility);
                    containerRooms.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFloor))) {
                    textFloor.setVisibility(visibility);
                    containerFloors.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideFurn))) {
                    textFurn.setVisibility(visibility);
                    spinnerFurn.setVisibility(visibility);
                }

                break;

            case Category.JOBS:
                if (!selectedCategory.shouldHideTag(getString(R.string.hideSchedule))) {
                    textSch.setVisibility(visibility);
                    spinnerSch.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideEducation))) {
                    textEdu.setVisibility(visibility);
                    spinnerEdu.setVisibility(visibility);
                }

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSalary))) {
                    textSalary.setVisibility(visibility);
                    containerSalary.setVisibility(visibility);
                }

                if (visibility == View.GONE) {
                    textPrice.setVisibility(View.VISIBLE);
                    containerPrice.setVisibility(View.VISIBLE);
                } else {
                    textPrice.setVisibility(View.GONE);
                    containerPrice.setVisibility(View.GONE);
                }

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

                if (!selectedCategory.shouldHideTag(getString(R.string.hideKilo))) {
                    textKilo.setVisibility(visibility);
                    containerKilometer.setVisibility(visibility);
                }

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
                if (!selectedCategory.shouldHideTag(getString(R.string.hideSize))) {
                    textSize.setVisibility(visibility);
                    containerSize.setVisibility(visibility);
                }

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
}
