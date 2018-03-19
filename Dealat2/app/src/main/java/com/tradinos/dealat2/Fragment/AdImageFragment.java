package com.tradinos.dealat2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.MasterActivity;

/**
 * Created by developer on 07.03.18.
 */

public class AdImageFragment extends Fragment {

    private String path;
    private int templateId;

    public static AdImageFragment newInstance(String path, int templateId){
        AdImageFragment fragment = new AdImageFragment();
        fragment.setPath(path);
        fragment.setTemplateId(templateId);

        return fragment;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ad_image, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);

        int defaultDrawable = ((MasterActivity)getContext()).getTemplateDefaultImage(templateId);

        if (path != null){
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + this.path, ImageLoader.getImageListener(imageView,
                    defaultDrawable, defaultDrawable));
        }
        else
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), defaultDrawable));

        return rootView;
    }
}
