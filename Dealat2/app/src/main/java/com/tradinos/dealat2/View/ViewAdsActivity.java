package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AdAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class ViewAdsActivity extends DrawerActivity {

    public static final int ACTION_SEARCH = 1, ACTION_VIEW = 2;

    private int currentView, action;

    private Category selectedCategory;
    private int currentTemplate;
    private List<Ad> ads = new ArrayList<>();
    private HashMap<String, String> searchParameters = new HashMap<>();

    // views
    private GridView gridView;
    private ImageButton buttonViews;
    private ImageView imageViewCategory;
    private TextView textViewCategory;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_ads);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        // if preference isn't exist, the default view is 1
        currentView = ((MyApplication) getApplication()).getCurrentView();

        selectedCategory = (Category) getIntent().getSerializableExtra("category");
        currentTemplate = selectedCategory.getTemplateId();

        action = getIntent().getIntExtra("action", 0);

        if (action == ACTION_SEARCH)
            searchParameters = (HashMap<String, String>) getIntent().getSerializableExtra("parameters");

        getAds();
    }

    private void getAds() {
        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        if (action == ACTION_SEARCH) {
            AdController.getInstance(mController).search(searchParameters, new SuccessCallback<List<Ad>>() {
                @Override
                public void OnSuccess(List<Ad> result) {

                    if (result.isEmpty())
                        findViewById(R.id.layoutEmpty).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.layoutEmpty).setVisibility(View.GONE);

                    ads = result;
                    gridView.setAdapter(new AdAdapter(mContext, ads, getGridCellResource()));

                    getCommercialAds(selectedCategory.getId());
                }
            });

        } else if (action == ACTION_VIEW) {

            AdController.getInstance(mController).getCategoryAds(selectedCategory.getId(), new SuccessCallback<List<Ad>>() {
                @Override
                public void OnSuccess(List<Ad> result) {

                    if (result.isEmpty())
                        findViewById(R.id.layoutEmpty).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.layoutEmpty).setVisibility(View.GONE);

                    ads = result;
                    gridView.setAdapter(new AdAdapter(mContext, ads, getGridCellResource()));

                    getCommercialAds(selectedCategory.getId());
                }
            });
        }
    }

    @Override
    public void showData() {

        imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                getTemplateDefaultImage(selectedCategory.getTemplateId())));

        textViewCategory.setText(selectedCategory.getFullName());

        buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, getButtonViewsResource()));
    }

    @Override
    public void assignUIReferences() {
        gridView = (GridView) findViewById(R.id.gridView);
        buttonViews = (ImageButton) findViewById(R.id.buttonViews);
        imageViewCategory = (ImageView) findViewById(R.id.imageView);
        textViewCategory = (TextView) findViewById(R.id.textView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        searchView = (SearchView) findViewById(R.id.searchView);
    }

    @Override
    public void assignActions() {
        buttonViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentView++;
                if (currentView > 3)
                    currentView = 1;

                ((MyApplication) getApplication()).setCurrentView(currentView);

                buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, getButtonViewsResource()));
                gridView.setAdapter(new AdAdapter(mContext, ads, getGridCellResource()));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchParameters.clear();
                searchParameters.put("query", query);

                if (!selectedCategory.isMain())
                    searchParameters.put("category_id", selectedCategory.getId());

                action = ACTION_SEARCH;

                getAds();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                    searchParameters.remove("query");

                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getAds();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) { //Filter
            Intent intent = new Intent(mContext, FilterActivity.class);
            intent.putExtra("category", selectedCategory);

            startActivityForResult(intent, ACTION_SEARCH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == ACTION_SEARCH) {
                selectedCategory = (Category) data.getSerializableExtra("category");
                searchParameters = (HashMap<String, String>) data.getSerializableExtra("parameters");

                if (currentTemplate != selectedCategory.getTemplateId()) {
                    imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                            getTemplateDefaultImage(selectedCategory.getTemplateId())));

                    textViewCategory.setText(selectedCategory.getFullName());
                }

                action = ACTION_SEARCH;
                getAds();
            }
    }

    private int getButtonViewsResource() {

        switch (currentView) {
            case 1:
                return R.drawable.views;

            case 2:
                return R.drawable.views_2;

            case 3:
                return R.drawable.views_3;

            default:
                return R.drawable.views;
        }
    }

    private int getGridCellResource() {
        switch (currentView) {
            case 1:
                gridView.setNumColumns(1);
                return R.layout.row_view1;

            case 2:
                gridView.setNumColumns(1);
                return R.layout.row_view2;

            case 3:
                gridView.setNumColumns(2);
                return R.layout.row_view3;

            default:
                gridView.setNumColumns(1);
                return R.layout.row_view1;
        }
    }
}
