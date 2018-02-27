package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AdAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class ViewAdsActivity extends DrawerActivity {

    private int currentPage = 0, currentView = 1;

    private Category selectedCategory;
    private List<Ad> ads;

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

        ShowProgressDialog();
        AdController.getInstance(mController).getCategoryAds(selectedCategory.getId(), new SuccessCallback<List<Ad>>() {
            @Override
            public void OnSuccess(List<Ad> result) {
                HideProgressDialog();
                ads = result;

                adapter = new AdAdapter(mContext, ads, R.layout.row_view1);
                gridView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showData() {
        imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                getTemplateDefaultImage(selectedCategory.getTemplateId())));
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
                int rowResource = R.layout.row_view1;
                int imageResource = R.drawable.views;

                currentView++;
                if (currentView >3)
                    currentView = 1;

                switch (currentView){
                    case 1:
                        rowResource = R.layout.row_view1;
                        imageResource = R.drawable.views;
                        gridView.setNumColumns(1);
                        break;

                    case 2:
                        rowResource = R.layout.row_view2;
                        imageResource = R.drawable.views_2;
                        gridView.setNumColumns(1);
                        break;
                    case 3:
                        rowResource = R.layout.row_view3;
                        imageResource = R.drawable.views_3;
                        gridView.setNumColumns(2);
                        break;
                }

                buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, imageResource));
                adapter = new AdAdapter(mContext, ads, rowResource);
                gridView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
