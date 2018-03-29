package com.tradinos.dealat2.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageSaver;
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
            Picasso.with(getContext()).load(MyApplication.getBaseUrlForImages() + this.path).placeholder(defaultDrawable)
                    .error(defaultDrawable).into(getTarget(rootView));
        }

        return rootView;
    }

    //target to save
    private Target getTarget(final View rootView){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Button buttonSave = rootView.findViewById(R.id.buttonTrue);
                buttonSave.setVisibility(View.VISIBLE);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ImageSaver.insertImage(activity.getContentResolver(), bitmap) != null)
                            activity.showMessageInToast(R.string.toastImageSave);
                        }
                });

                ((ScalableImageView)rootView.findViewById(R.id.imageView)).setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}
