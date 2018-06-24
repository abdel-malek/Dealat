package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealat.Adapter.AutoCompleteAdapter;
import com.dealat.Adapter.CheckableAdapter;
import com.dealat.Adapter.CityAdapter;
import com.dealat.Adapter.ItemAdapter;
import com.dealat.Adapter.TypeAdapter;
import com.tradinos.core.network.SuccessCallback;
import com.dealat.Controller.AdController;
import com.dealat.Model.AdVehicle;
import com.dealat.Model.Category;
import com.dealat.Model.City;
import com.dealat.Model.Item;
import com.dealat.Model.TemplatesData;
import com.dealat.Model.Type;
import com.dealat.R;

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
            textBrand, textModel, textTransmission, textKilo, textCapacity,
            textDate,
            textEdu, textCertificate, textSch, textSalary, textGender,
            textFurn, textSpace, textRooms, textFloor, textNumberFloors, textPropertyState,
            textSize,
            textState;

    private EditText editQuery, editCategory, editPriceMin, editPriceMax,
            editKilometerMin, editKilometerMax, editCapacityMin, editCapacityMax,
            editSizeMin, editSizeMax,
            editSpaceMin, editSpaceMax, editRoomsMin, editRoomsMax, editFloorsMin, editFloorsMax, editNumberFloorsMin, editNumberFloorsMax,
            editSalaryMin, editSalaryMax;

    private AutoCompleteTextView autoCompleteLocation;

    private AppCompatSpinner spinnerBrand, spinnerModel, spinnerYear, spinnerTransmission,
            spinnerEdu, spinnerCertificate, spinnerSch, spinnerGender,
            spinnerFurn, spinnerPropertyState,
            spinnerState,
            spinnerCity;

    private LinearLayout containerKilometer, containerSize, containerCapacity,
            containerSalary, containerSpace, containerRooms, containerFloors, containerNumberFloors, containerPrice;

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
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands, true));

                result.getEducations().add(0, new Item("-1", getString(R.string.all)));
                spinnerEdu.setAdapter(new CheckableAdapter(mContext, result.getEducations()));

                result.getCertificates().add(0, new Item("-1", getString(R.string.all)));
                spinnerCertificate.setAdapter(new CheckableAdapter(mContext, result.getCertificates()));

                result.getSchedules().add(0, new Item("-1", getString(R.string.all)));
                spinnerSch.setAdapter(new CheckableAdapter(mContext, result.getSchedules()));

                result.getPropertyStates().add(0, Item.getNoItem());
                spinnerPropertyState.setAdapter(new CheckableAdapter(mContext, result.getPropertyStates()));

                showTemplate();
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


        List<Item> genders = new ArrayList<>();
        genders.add(new Item("-1", getString(R.string.all)));
        genders.add(new Item("1", getString(R.string.male)));
        genders.add(new Item("2", getString(R.string.female)));
        spinnerGender.setAdapter(new ItemAdapter(mContext, genders));


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
        textPrice = findViewById(R.id.textPrice);
        textBrand = findViewById(R.id.textBrand);
        textModel = findViewById(R.id.textModel);
        textDate = findViewById(R.id.textDate);
        textTransmission = findViewById(R.id.textTransmission);
        textCapacity = findViewById(R.id.textCapacity);
        textKilo = findViewById(R.id.textKilo);

        textEdu = findViewById(R.id.textEdu);
        textCertificate = findViewById(R.id.textCertificate);
        textSch = findViewById(R.id.textSch);
        textGender = findViewById(R.id.textGender);
        textSalary = findViewById(R.id.textSalary);


        textRooms = findViewById(R.id.textRooms);
        textFloor = findViewById(R.id.textFloor);
        textNumberFloors = findViewById(R.id.textNumberFloors);
        textFurn = findViewById(R.id.textFurn);
        textPropertyState = findViewById(R.id.textPropertyState);
        textSpace = findViewById(R.id.textSpace);


        textSize = findViewById(R.id.textSize);

        textState = findViewById(R.id.textState);

        editQuery = findViewById(R.id.editQuery);
        editCategory = findViewById(R.id.editCategory);
        editPriceMax = findViewById(R.id.editPriceMax);
        editPriceMin = findViewById(R.id.editPriceMin);

        editKilometerMax = findViewById(R.id.editKilometerMax);
        editKilometerMin = findViewById(R.id.editKilometerMin);
        editCapacityMin = findViewById(R.id.editCapacityMin);
        editCapacityMax = findViewById(R.id.editCapacityMax);

        editSizeMax = findViewById(R.id.editSizeMax);
        editSizeMin = findViewById(R.id.editSizeMin);

        editSalaryMax = findViewById(R.id.editSalaryMax);
        editSalaryMin = findViewById(R.id.editSalaryMin);


        editSpaceMax = findViewById(R.id.editSpaceMax);
        editSpaceMin = findViewById(R.id.editSpaceMin);

        editRoomsMax = findViewById(R.id.editRoomsMax);
        editRoomsMin = findViewById(R.id.editRoomsMin);

        editFloorsMax = findViewById(R.id.editFloorsMax);
        editFloorsMin = findViewById(R.id.editFloorsMin);

        editNumberFloorsMin = findViewById(R.id.editNumberFloorsMin);
        editNumberFloorsMax = findViewById(R.id.editNumberFloorsMax);

        autoCompleteLocation = findViewById(R.id.autoCompleteLocation);

        spinnerBrand = findViewById(R.id.spinnerBrand);
        spinnerModel = findViewById(R.id.spinnerModel);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerTransmission = findViewById(R.id.spinnerTransmission);

        spinnerEdu = findViewById(R.id.spinnerEdu);
        spinnerCertificate = findViewById(R.id.spinnerCertificate);
        spinnerSch = findViewById(R.id.spinnerSch);
        spinnerGender = findViewById(R.id.spinnerGender);

        spinnerFurn = findViewById(R.id.spinnerFurn);
        spinnerPropertyState = findViewById(R.id.spinnerPropertyState);

        spinnerState = findViewById(R.id.spinnerState);

        spinnerCity = findViewById(R.id.spinner);

        containerKilometer = findViewById(R.id.containerKilometer);
        containerSize = findViewById(R.id.containerSize);
        containerSalary = findViewById(R.id.containerSalary);
        containerSpace = findViewById(R.id.containerSpace);
        containerRooms = findViewById(R.id.containerRooms);
        containerFloors = findViewById(R.id.containerFloors);
        containerNumberFloors = findViewById(R.id.containerNumberFloors);
        containerPrice = findViewById(R.id.containerPrice);
        containerCapacity = findViewById(R.id.containerCapacity);
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

        autoCompleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteLocation.getText().toString().equals(""))
                    autoCompleteLocation.showDropDown();
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

        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubCategoriesActivity.class);
                intent.putExtra("action", SubCategoriesActivity.ACTION_FILTER_CAT);
                intent.putExtra("category", selectedCategory);
                startActivityForResult(intent, REQUEST_FILTER_CAT);
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

                replaceTemplate();

                List<Type> templateBrands = brands.get(currentTemplate);
                if (templateBrands != null)
                    spinnerBrand.setAdapter(new TypeAdapter(mContext, templateBrands, true));

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

                if (!inputIsEmpty(editNumberFloorsMin))
                    parameters.put(getString(R.string.numberFloorsMin), stringInput(editNumberFloorsMin));

                if (!inputIsEmpty(editNumberFloorsMax))
                    parameters.put(getString(R.string.numberFloorsMax), stringInput(editNumberFloorsMax));

                jsonArray = ((CheckableAdapter) spinnerPropertyState.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("property_state_id", jsonArray.toString());
                    parameters.put(getString(R.string.propertyStateName), ((CheckableAdapter) spinnerPropertyState.getAdapter()).getSelectedNames());
                }

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

                jsonArray = ((CheckableAdapter) spinnerCertificate.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("certificate_id", jsonArray.toString());
                    parameters.put(getString(R.string.certificateName), ((CheckableAdapter) spinnerCertificate.getAdapter()).getSelectedNames());
                }

                jsonArray = ((CheckableAdapter) spinnerSch.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("schedule_id", jsonArray.toString());
                    parameters.put(getString(R.string.scheduleName), ((CheckableAdapter) spinnerSch.getAdapter()).getSelectedNames());
                }

                item = (Item) spinnerGender.getSelectedItem();
                if (item != null && !item.isNothing()) {
                    parameters.put("gender", item.getId());
                    parameters.put(getString(R.string.genderName), item.getName());
                }

                break;

            case Category.VEHICLES:

                if (!inputIsEmpty(editKilometerMax))
                    parameters.put(getString(R.string.kilometerMax), stringInput(editKilometerMax));

                if (!inputIsEmpty(editKilometerMin))
                    parameters.put(getString(R.string.kilometerMin), stringInput(editKilometerMin));

                if (!inputIsEmpty(editCapacityMin))
                    parameters.put(getString(R.string.capacityMin), stringInput(editCapacityMin));

                if (!inputIsEmpty(editCapacityMax))
                    parameters.put(getString(R.string.capacityMax), stringInput(editCapacityMax));

                item = ((Item) spinnerTransmission.getSelectedItem());
                if (item != null && !item.isNothing()) {
                    parameters.put("is_automatic", item.getId());
                    parameters.put(getString(R.string.automaticName), item.getName());
                }

                item = ((Item) spinnerBrand.getSelectedItem());
                if (item != null && !item.isNothing()) {
                    parameters.put("type_id", item.getId());
                    parameters.put(getString(R.string.typeName), item.getName());
                }

                if (spinnerModel.getAdapter() != null) {
                    jsonArray = ((CheckableAdapter) spinnerModel.getAdapter()).getSelectedItems();
                    if (jsonArray.length() > 0) {
                        parameters.put("type_model_id", jsonArray.toString());
                        parameters.put(getString(R.string.modelName), ((CheckableAdapter) spinnerModel.getAdapter()).getSelectedNames());
                    }
                }

                jsonArray = ((CheckableAdapter) spinnerYear.getAdapter()).getSelectedItems();
                if (jsonArray.length() > 0) {
                    parameters.put("manufacture_date", jsonArray.toString());
                    parameters.put(getString(R.string.yearsName), ((CheckableAdapter) spinnerYear.getAdapter()).getSelectedNames());
                }

                item = ((Item) spinnerState.getSelectedItem());
                if (item != null && !item.isNothing()) {
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
                if (item != null && !item.isNothing()) {
                    parameters.put("type_id", item.getId());
                    parameters.put(getString(R.string.typeName), item.getName());
                }

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:

                item = ((Item) spinnerState.getSelectedItem());
                if (!item.isNothing()) {
                    parameters.put("is_new", item.getId());
                    parameters.put(getString(R.string.stateName), item.getName());
                }
        }
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

                if (!selectedCategory.shouldHideTag(getString(R.string.hideNumberFloors))) {
                    textNumberFloors.setVisibility(visibility);
                    containerNumberFloors.setVisibility(visibility);
                }

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

                if (!selectedCategory.shouldHideTag(getString(R.string.hideSalary))) {
                    textSalary.setVisibility(visibility);
                    containerSalary.setVisibility(visibility);
                }

                textPrice.setVisibility(View.GONE);
                containerPrice.setVisibility(View.GONE);

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
                    containerCapacity.setVisibility(visibility);
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

    private void hideTemplate() {
        int visibility = View.GONE;

        switch (currentTemplate) {
            case Category.PROPERTIES:
                textSpace.setVisibility(visibility);
                containerSpace.setVisibility(visibility);

                textRooms.setVisibility(visibility);
                containerRooms.setVisibility(visibility);

                textFloor.setVisibility(visibility);
                containerFloors.setVisibility(visibility);

                textNumberFloors.setVisibility(visibility);
                containerNumberFloors.setVisibility(visibility);

                textPropertyState.setVisibility(visibility);
                spinnerPropertyState.setVisibility(visibility);

                textFurn.setVisibility(visibility);
                spinnerFurn.setVisibility(visibility);

                break;

            case Category.JOBS:
                textSch.setVisibility(visibility);
                spinnerSch.setVisibility(visibility);

                textEdu.setVisibility(visibility);
                spinnerEdu.setVisibility(visibility);

                textCertificate.setVisibility(visibility);
                spinnerCertificate.setVisibility(visibility);

                textGender.setVisibility(visibility);
                spinnerGender.setVisibility(visibility);

                textSalary.setVisibility(visibility);
                containerSalary.setVisibility(visibility);

                textPrice.setVisibility(View.VISIBLE);
                containerPrice.setVisibility(View.VISIBLE);

                break;

            case Category.VEHICLES:
                textBrand.setVisibility(visibility);
                spinnerBrand.setVisibility(visibility);

                textModel.setVisibility(visibility);
                spinnerModel.setVisibility(visibility);

                textDate.setVisibility(visibility);
                spinnerYear.setVisibility(visibility);

                textCapacity.setVisibility(visibility);
                containerCapacity.setVisibility(visibility);

                textKilo.setVisibility(visibility);
                containerKilometer.setVisibility(visibility);

                textTransmission.setVisibility(visibility);
                spinnerTransmission.setVisibility(visibility);

                textState.setVisibility(visibility);
                spinnerState.setVisibility(visibility);

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
                textState.setVisibility(visibility);
                spinnerState.setVisibility(visibility);
        }
    }
}
