package com.tradinos.dealat2.View;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MainCatAdapter;
import com.tradinos.dealat2.Controller.CategoryController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class HomeActivity extends DrawerActivity {

    private Category mainCategory;
    private List<Category> mainCategories = new ArrayList<>();

    //views
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {

        if (!isNetworkAvailable())
            return;

        mainCategory = Category.getMain(getString(R.string.allCategories));

        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        CategoryController.getInstance(mController).getAllCategories(new SuccessCallback<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> result) {

                result.add(mainCategory);
                ((MyApplication) getApplication()).setAllCategories(result);

                mainCategories = ((MyApplication) getApplication()).getSubCatsById("0");
                mainCategory.setSubCategories(mainCategories);

                listView.setAdapter(new MainCatAdapter(mContext, mainCategories));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                        intent.putExtra("category", mainCategories.get(i));
                        intent.putExtra("action", SubCategoriesActivity.ACTION_VIEW);

                       // Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle();
                      //  startActivity(intent, bundle);
                        startActivity(intent);
                    }
                });

                getCommercialAds("0");
            }
        });
    }

    @Override
    public void showData() {
    }

    @Override
    public void assignUIReferences() {
        listView = (ListView) findViewById(R.id.listView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        searchView = (SearchView) findViewById(R.id.searchView);
    }

    @Override
    public void assignActions() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("query", query);

                Intent intent = new Intent(mContext, ViewAdsActivity.class);

                intent.putExtra("action", ViewAdsActivity.ACTION_SEARCH);
                intent.putExtra("category", mainCategory);
                intent.putExtra("parameters", parameters);

                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) {

            if (registered()){
                Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                intent.putExtra("category", mainCategory);
                intent.putExtra("action", SubCategoriesActivity.ACTION_SELL);

                // Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this).toBundle();
                // startActivity(intent, bundle);
                startActivity(intent);
            }
        }
    }
}
