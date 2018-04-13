package com.tradinos.dealat2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ScalableImageView;
import com.tradinos.dealat2.View.MasterActivity;

/**
 * Created by developer on 29.03.18.
 */

public class ImageDetailsFragment extends Fragment {
    private String path;
    private int templateId;
    private MasterActivity activity;

    public static ImageDetailsFragment newInstance(String path, int templateId){
        ImageDetailsFragment fragment = new ImageDetailsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_image_details, container, false);

        activity = (MasterActivity) getActivity();
        int defaultDrawable = activity.getTemplateDefaultImage(templateId);

        if (path != null){
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrl() + this.path, ImageLoader.getImageListener((ScalableImageView)rootView.findViewById(R.id.imageView),
                    defaultDrawable, defaultDrawable));
        }

        return rootView;
    }
}
