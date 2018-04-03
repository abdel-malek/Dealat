package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MainCatAdapter;
import com.tradinos.dealat2.Controller.CategoryController;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class HomeActivity extends DrawerActivity {

    private Category mainCategory;

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

        mainCategory = Category.getMain(getString(R.string.allCategories));

        if (!isNetworkAvailable())
            return;

        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        CategoryController.getInstance(mController).getAllCategories(new SuccessCallback<List<Category>>() {
            @Override
            public void OnSuccess(List<Category> result) {

                result.add(mainCategory);
                ((MyApplication) getApplication()).setAllCategories(result);

                mainCategory.setSubCategories(((MyApplication) getApplication()).getSubCatsById("0"));

                listView.setAdapter(new MainCatAdapter(mContext, mainCategory.getSubCategories()));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                        intent.putExtra("category", mainCategory.getSubCategories().get(i));
                        intent.putExtra("action", SubCategoriesActivity.ACTION_VIEW);

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
                Intent intent = new Intent(mContext, ItemInfoActivity.class);
                intent.putExtra("category", mainCategory);

                startActivity(intent);
            }
        }
    }
}
