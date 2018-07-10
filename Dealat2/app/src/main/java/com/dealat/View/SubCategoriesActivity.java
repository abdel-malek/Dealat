package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dealat.Adapter.CategoryAdapter;
import com.dealat.Model.Category;
import com.dealat.MyApplication;
import com.dealat.R;

/**
 * Created by developer on 19.02.18.
 */

public class SubCategoriesActivity extends MasterActivity {

    public static final int ACTION_VIEW = 1, ACTION_SELECT_CAT = 3, ACTION_FILTER_CAT = 4;

    private MyApplication application;

    private int action;
    private Category category;

    private CategoryAdapter adapter;

    //views
    private ListView listView;
    private ImageButton buttonBack;
    private TextView textViewTitle;

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

            Category all = Category.getAll(category, getString(R.string.all));
            category.setSubCategories(application.getSubCatsById(category.getId()));
            category.addSubCat(all);

        } else if (action == ACTION_SELECT_CAT) {
            if (!category.getId().equals("0")) {
                if (!category.getParentId().equals("0"))
                    buttonBack.setVisibility(View.VISIBLE);

                category = MyApplication.getCategoryById(category.getParentId());
            }

            category.setSubCategories(application.getSubCatsById(category.getId()));

        } else if (action == ACTION_FILTER_CAT) {
            if (!category.getId().equals("0")) {
                if (!category.getParentId().equals("0"))
                    buttonBack.setVisibility(View.VISIBLE);

                category = MyApplication.getCategoryById(category.getParentId());
            }

            Category all;
            if (category.isMain())
                all = Category.getAll(category, "");
            else
                all = Category.getAll(category, getString(R.string.all));

            category.setSubCategories(application.getSubCatsById(category.getId()));
            category.addSubCat(all);
        }

        textViewTitle.setText(category.getName());

        if (action == ACTION_VIEW)
            adapter = new CategoryAdapter(mContext, category.getSubCategories(), true);
        else
            adapter = new CategoryAdapter(mContext, category.getSubCategories(), false);
        listView.setAdapter(adapter);
    }

    @Override
    public void assignUIReferences() {
        buttonBack = findViewById(R.id.buttonTrue);
        listView = findViewById(R.id.listView);
        textViewTitle = findViewById(R.id.title);
    }

    @Override
    public void assignActions() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapter.getItem(i);

                if (category.hasSubCats()) {

                    category.setSubCategories(application.getSubCatsById(category.getId()));

                    if (action == ACTION_VIEW || action == ACTION_FILTER_CAT) {
                        Category all = Category.getAll(category, getString(R.string.all));
                        category.addSubCat(all);
                    }

                    textViewTitle.setText(category.getName());

                    if (action == ACTION_VIEW)
                        adapter = new CategoryAdapter(mContext, category.getSubCategories(), true);
                    else
                        adapter = new CategoryAdapter(mContext, category.getSubCategories(), false);
                    listView.setAdapter(adapter);

                    buttonBack.setVisibility(View.VISIBLE);

                    animate(-1);

                } else {
                    Intent intent;
                    if (action == ACTION_VIEW) {
                        intent = new Intent(mContext, ViewAdsActivity.class);
                        intent.putExtra("action", ViewAdsActivity.ACTION_VIEW);
                        intent.putExtra("category", category);
                        startActivity(intent);

                    } else if (action == ACTION_SELECT_CAT || action == ACTION_FILTER_CAT) {
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
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { // back
            category = MyApplication.getCategoryById(category.getParentId());
            category.setSubCategories(application.getSubCatsById(category.getId()));

            if ((action == ACTION_VIEW || action == ACTION_FILTER_CAT)) {
                Category all;
                if (category.isMain())
                    all = Category.getAll(category, "");
                else
                    all = Category.getAll(category, getString(R.string.all));

                category.addSubCat(all);
            }

            textViewTitle.setText(category.getName());

            if (action == ACTION_VIEW)
                adapter = new CategoryAdapter(mContext, category.getSubCategories(), true);
            else
                adapter = new CategoryAdapter(mContext, category.getSubCategories(), false);
            listView.setAdapter(adapter);

            animate(1);
            if (category.getId().equals("0"))
                buttonBack.setVisibility(View.INVISIBLE);

        } else if (view.getId() == R.id.container)
            finish();
    }

    private void animate(int dir) {
        final int DURATION = 250;

        TranslateAnimation textAnimation, animation;

        if (MyApplication.getLocale().toString().equals("ar")) {
            animation = new TranslateAnimation(dir * -listView.getWidth(), 0, 0, 0);
            textAnimation = new TranslateAnimation(dir * -textViewTitle.getWidth(), 0, 0, 0);
        } else { //en
            animation = new TranslateAnimation(dir * listView.getWidth(), 0, 0, 0);
            textAnimation = new TranslateAnimation(dir * textViewTitle.getWidth(), 0, 0, 0);
        }

        textAnimation.setDuration(DURATION);
        textViewTitle.setAnimation(textAnimation);
        animation.setDuration(DURATION);
        listView.startAnimation(animation);
    }
}
