package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dealat.Adapter.MainCatAdapter;
import com.dealat.Controller.CategoryController;
import com.dealat.Model.Category;
import com.dealat.Model.GroupedResponse;
import com.dealat.MyApplication;
import com.dealat.R;
import com.tradinos.core.network.SuccessCallback;

import java.util.HashMap;

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

        if (!isNetworkAvailable()) {
            refreshLayout.setRefreshing(false);
            return;
        }

        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        CategoryController.getInstance(mController).getAllCategories(new SuccessCallback<GroupedResponse>() {
            @Override
            public void OnSuccess(GroupedResponse result) {

                HideProgressDialog();
                if (findViewById(R.id.refreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.refreshLayout)).setRefreshing(false);

                result.getCategories().add(mainCategory);
                ((MyApplication) getApplication()).setAllCategories(result.getCategories());

                mainCategory.setSubCategories(((MyApplication) getApplication()).getSubCatsById("0"));

                listView.setAdapter(new MainCatAdapter(mContext, mainCategory.getSubCategories()));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                        Category category = ((MainCatAdapter)listView.getAdapter()).getItem(i);
                        intent.putExtra("category", category);
                      //  intent.putExtra("category", mainCategory.getSubCategories().get(i));
                        intent.putExtra("action", SubCategoriesActivity.ACTION_VIEW);

                        startActivity(intent);
                    }
                });

                getCommercialAds(result.getCommercialAds());
            }
        });
    }

    @Override
    public void showData() {
    }

    @Override
    public void assignUIReferences() {
        listView = findViewById(R.id.listView);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.searchView);
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

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HashMap<String, String> parameters = new HashMap<>();
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

            if (registered()) {
                Intent intent = new Intent(mContext, SubmitAdActivity.class);
                intent.putExtra("category", mainCategory);

                startActivity(intent);
            }
        } else if (view.getId() == R.id.buttonFilter) {
            Intent intent = new Intent(mContext, FilterActivity.class);
            intent.putExtra("category", mainCategory);
            intent.putExtra("action", 1);
            startActivity(intent);
        }
    }
}
