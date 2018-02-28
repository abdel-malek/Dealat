package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
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
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class ViewAdsActivity extends DrawerActivity {

    private int currentPage = 0, currentView;

    private Category selectedCategory;
    private List<Ad> ads = new ArrayList<>();

    private AdAdapter adapter;

    // views
    private ViewPager commercialPager;
    private GridView gridView;
    private ImageButton buttonViews;
    ImageView imageViewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_ads);
        super.onCreate(savedInstanceState);

        //   TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabLayout.setupWithViewPager(commercialPager);
    }

    @Override
    public void getData() {
        selectedCategory = (Category) getIntent().getSerializableExtra("category");

        // if preference isn't exist, the default view is 1
        currentView = ((MyApplication) getApplication()).getCurrentView();

        ShowProgressDialog();
        AdController.getInstance(mController).getCategoryAds(selectedCategory.getId(), new SuccessCallback<List<Ad>>() {
            @Override
            public void OnSuccess(List<Ad> result) {
                HideProgressDialog();
                ads = result;

                gridView.setAdapter(new AdAdapter(mContext, ads, getGridCellResource()));
            }
        });
    }

    @Override
    public void showData() {
        imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                getTemplateDefaultImage(selectedCategory.getTemplateId())));

        ((TextView)findViewById(R.id.textView)).setText(selectedCategory.getFullName());

        buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, getButtonViewsResource()));
    }

    @Override
    public void assignUIReferences() {
        commercialPager = (ViewPager) findViewById(R.id.viewpager);
        gridView = (GridView) findViewById(R.id.gridView);
        buttonViews = (ImageButton) findViewById(R.id.buttonViews);
        imageViewCategory = (ImageView) findViewById(R.id.imageView);
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
    }

    @Override
    public void onClick(View view) {

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
