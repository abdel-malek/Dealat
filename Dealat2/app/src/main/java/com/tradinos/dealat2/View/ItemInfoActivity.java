package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
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

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { //Submit

            if (checkGeneralInput()) {

                getTemplateInput();

                //ShowProgressDialog();


                showMessageInToast(R.string.buttonSubmit);
                finish();
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

            parameters.put("title", String.valueOf(editTitle.getText()));
            parameters.put("category_id", selectedCategory.getId());
            parameters.put("show_period", ((Item)spinnerPeriod.getSelectedItem()).getId());

            return true;
        }

        return false;
    }

    private void getTemplateInput(){

    }
}
