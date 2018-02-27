package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.tradinos.dealat2.Adapter.CategoryAdapter;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 19.02.18.
 */

public class SubCategoriesActivity extends MasterActivity {

    public static final int ACTION_VIEW = 1, ACTION_SELL = 2, ACTION_SELECT_CAT = 3;

    public final int REQUEST_SELECT_IMG = 1;

    private MyApplication application;

    private int action;
    private Category category;

    private CategoryAdapter adapter;

    //views
    private ListView listView;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sub_categories);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        application = (MyApplication) getApplication();

        action = getIntent().getIntExtra("action", 0);
        category = (Category) getIntent().getSerializableExtra("category");
    }

    @Override
    public void showData() {
        if (action == ACTION_VIEW) {
            Category all = new Category();
            all.setId(category.getId());
            all.setParentId(category.getParentId());
            all.setName(getString(R.string.all));

            category.setSubCategories(application.getSubCatsById(category.getId()));
            category.addSubCat(all);
        } else if (action == ACTION_SELECT_CAT) {
            if (!category.getId().equals("0")) //this check is really not necessary
                buttonBack.setVisibility(View.VISIBLE);

            category = MyApplication.getCategoryById(category.getParentId());
            category.setSubCategories(application.getSubCatsById(category.getId()));
        }

        adapter = new CategoryAdapter(mContext, category.getSubCategories());
        listView.setAdapter(adapter);
    }

    @Override
    public void assignUIReferences() {
        buttonBack = (ImageButton) findViewById(R.id.buttonTrue);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapter.getItem(i);

                if (category.hasSubCats()) {

                    category.setSubCategories(application.getSubCatsById(category.getId()));

                    if (action == ACTION_VIEW) {
                        Category all = new Category();
                        all.setId(category.getId());
                        all.setParentId(category.getParentId());
                        all.setName(getString(R.string.all));
                        category.addSubCat(all);
                    }

                    adapter = new CategoryAdapter(mContext, category.getSubCategories());
                    listView.setAdapter(adapter);

                    buttonBack.setVisibility(View.VISIBLE);
                } else {
                    Intent intent;
                    if (action == ACTION_SELL) {
                        intent = new Intent(mContext, SelectImagesActivity.class);
                        intent.putExtra("category", category);
                        startActivityForResult(intent, REQUEST_SELECT_IMG);

                    } else if (action == ACTION_VIEW) {
                        intent = new Intent(mContext, ViewAdsActivity.class);
                        intent.putExtra("category", category);
                        startActivity(intent);

                    } else if (action == ACTION_SELECT_CAT) {
                        intent = new Intent();
                        intent.putExtra("category", category);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_IMG)
            if (resultCode == RESULT_OK)
                finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { // back
            category = MyApplication.getCategoryById(category.getParentId());
            category.setSubCategories(application.getSubCatsById(category.getId()));

            if (action == ACTION_VIEW && !category.getId().equals("0")) {
                Category all = new Category();
                all.setId(category.getId());
                all.setParentId(category.getParentId());
                all.setName(getString(R.string.all));
                category.addSubCat(all);
            }

            adapter = new CategoryAdapter(mContext, category.getSubCategories());
            listView.setAdapter(adapter);

            if (category.getId().equals("0"))
                buttonBack.setVisibility(View.INVISIBLE);
        }
    }
}
