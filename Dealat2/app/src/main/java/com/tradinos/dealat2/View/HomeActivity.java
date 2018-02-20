package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.CommercialAdapter;
import com.tradinos.dealat2.Adapter.MainCatAdapter;
import com.tradinos.dealat2.Controller.CategoryController;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.CommercialAd;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by developer on 19.02.18.
 */

public class HomeActivity extends DrawerActivity {


    private int currentPage =0;
    private List<Category> mainCategories;

    //views
    private ViewPager commercialPager;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(commercialPager);
    }

    @Override
    public void getData() {


        Category category = new Category();
        category.setId("1");
        category.setParentId("0");
        category.setName("Kids");

        Category sub = new Category();
        sub.setId("2");
        sub.setParentId(category.getId());
        sub.setName("toys");
        sub.setTemplateId(6);

        category.addSubCat(sub);


        List<Category> categories = new ArrayList<>();
        categories.add(Category.getMain());
        categories.add(category);
        categories.add(sub);


        ((MyApplication)getApplication()).setAllCategories(categories);

        mainCategories = ((MyApplication)getApplication()).getSubCatsById("0");
        listView.setAdapter(new MainCatAdapter(mContext, mainCategories));

     /*   ShowProgressDialog();
        CategoryController.getInstance(mController).getAllCategories(new SuccessCallback<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> result) {
                HideProgressDialog();

                result.add(Category.getMain());
                ((MyApplication)getApplication()).setAllCategories(result);

                mainCategories = ((MyApplication)getApplication()).getSubCatsById("0");

                listView.setAdapter(new MainCatAdapter(mContext, mainCategories));
            }
        });*/
    }

    @Override
    public void showData() {
        final List<CommercialAd> commercialAds = new ArrayList<>();
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());


        CommercialAdapter commercialAdapter = new CommercialAdapter(getSupportFragmentManager(),commercialAds );
        commercialPager.setAdapter(commercialAdapter);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == commercialAds.size()) {
                    currentPage = 0;
                }
                commercialPager.setCurrentItem(currentPage++, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);
    }

    @Override
    public void assignUIReferences() {
        commercialPager = (ViewPager) findViewById(R.id.viewpager);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                intent.putExtra("category", mainCategories.get(i));
                intent.putExtra("action", SubCategoriesActivity.ACTION_VIEW);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue){
            Intent intent = new Intent(mContext, SubCategoriesActivity.class);

            Category category = Category.getMain();
            category.setSubCategories(mainCategories);
            intent.putExtra("category", category);

            intent.putExtra("action", SubCategoriesActivity.ACTION_SELL);
            startActivity(intent);
        }
    }
}
