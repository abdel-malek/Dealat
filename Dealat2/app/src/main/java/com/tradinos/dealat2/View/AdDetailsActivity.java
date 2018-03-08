package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AdImagesAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsActivity extends MasterActivity {

    private Ad currentAd;

    // views
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.stub);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {

        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        getAd();
    }

    private void getAd() {
        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {
                HideProgressDialog();

                viewPager.setAdapter(new AdImagesAdapter(getSupportFragmentManager(), result.getImagesPaths(), currentAd.getTemplate()));

                tabLayout.setupWithViewPager(viewPager);

                LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

                for (int i = 0; i < result.getImagesPaths().size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.row_tab_image, null);

                    ImageView imageView = view.findViewById(R.id.imageView);

                    int defaultDrawable = getTemplateDefaultImage(currentAd.getTemplate());

                    if (result.getImagePath(i) != null) {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                        mImageLoader.get(MyApplication.getBaseUrlForImages() + result.getImagePath(i), ImageLoader.getImageListener(imageView,
                                defaultDrawable, defaultDrawable));
                    }
                    tabLayout.getTabAt(i).setCustomView(view);

                    View tab = tabStrip.getChildAt(i);
                    tab.setPadding(0, 0, 0, 0);
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                    p.setMargins(4, 4, 4, 4);
                    tab.requestLayout();
                }
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }
}
