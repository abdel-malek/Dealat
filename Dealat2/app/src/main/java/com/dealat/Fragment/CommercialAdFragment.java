package com.dealat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Controller.CommercialAdsController;
import com.dealat.Model.CommercialAd;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;

/**
 * Created by developer on 19.02.18.
 */

public class CommercialAdFragment extends Fragment {

    private CommercialAd commercialAd;

    public CommercialAdFragment() {
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

        if (commercialAd != null) {
            if (commercialAd.getImageUrl() != null) {
                ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
                mImageLoader.get(MyApplication.getBaseUrl() + this.commercialAd.getImageUrl(), ImageLoader.getImageListener(imageView,
                        R.drawable.dealat_logo_red_background_lined, R.drawable.dealat_logo_red_background_lined));
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        return rootView;
    }

    public void onClicked(){
        MasterActivity activity = (MasterActivity) getActivity();

        CommercialAdsController.getInstance(activity.getController()).registerClick(commercialAd.getId(), new SuccessCallback<String>() {
            @Override
            public void OnSuccess(String result) {
            }
        });
        if (commercialAd.getAdUrl() != null) {
            Uri webpage = Uri.parse(commercialAd.getAdUrl());

            if (!commercialAd.getAdUrl().startsWith("http://") && !commercialAd.getAdUrl().startsWith("https://")) {
                webpage = Uri.parse("http://" + commercialAd.getAdUrl());
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            if (intent.resolveActivity(getContext().getPackageManager()) != null)
                getContext().startActivity(intent);
        }
        else{
            Toast.makeText(getActivity(), R.string.commercial_has_no_link_message, Toast.LENGTH_SHORT).show();
        }
    }
}
