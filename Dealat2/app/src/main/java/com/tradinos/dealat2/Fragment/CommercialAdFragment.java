package com.tradinos.dealat2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.CommercialAd;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 19.02.18.
 */

public class CommercialAdFragment extends Fragment {

    private CommercialAd commercialAd;

    public CommercialAdFragment(){
    }

    public static CommercialAdFragment newInstance(CommercialAd ad) {

        CommercialAdFragment fragment = new CommercialAdFragment();
        fragment.setCommercialAd(ad);

        return fragment;
    }

    public void setCommercialAd(CommercialAd commercialAd) {
        this.commercialAd = commercialAd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commercial_ad, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.ad_image_39);

        ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
        mImageLoader.get(MyApplication.getBaseUrlForImages() + this.commercialAd.getImageUrl(), ImageLoader.getImageListener(imageView,
                R.drawable.dealat_logo_red_background_lined, R.drawable.dealat_logo_red_background_lined));

        return rootView;
    }
}
