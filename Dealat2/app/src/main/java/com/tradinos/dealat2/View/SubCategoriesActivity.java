package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.view.View;

import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 19.02.18.
 */

public class SubCategoriesActivity extends MasterActivity {

    private int action;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sub_categories);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        action = getIntent().getIntExtra("action", 0);
        category = (Category) getIntent().getSerializableExtra("category");
    }

    @Override
    public void showData() {
        if(action == HomeActivity.ACTION_VIEW){
            Category all = new Category();
            category.setId(category.getId());
            category.setParentId(category.getParentId());
            category.setName(getString(R.string.all));

            category.setSubCategories(((MyApplication)getApplication()).getSubCatsById(category.getId()));
            category.addSubCat(all);
        }
        else if(action == HomeActivity.ACTION_SELL){
            category.setSubCategories(((MyApplication)getApplication()).getSubCatsById(category.getId()));
        }
    }

    @Override
    public void assignUIReferences() {

    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }
}
