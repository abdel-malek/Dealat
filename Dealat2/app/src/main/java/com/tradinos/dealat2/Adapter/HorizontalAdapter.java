package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class HorizontalAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Image> images;
    private LinearLayout linearLayout;
    private List<View> views;


    public HorizontalAdapter(Context context, LinearLayout linearLayout) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.linearLayout = linearLayout;
        this.views = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public Image getItem(int i) {
        return this.images.get(i);
    }

    public int getCount() {
        return this.images.size();
    }

    public void setViews(List<Image> images) {
        int base = this.getCount();

        this.images.addAll(images);

        View view;
        ImageView imageView;

        for (int i = 0; i < images.size(); i++) {

            // first image is Main by default
            if (i == 0)
                this.images.get(0).markAsMain();

            view = this.inflater.inflate(R.layout.row_image_horizontal, null);
            // view.setLayoutParams(new FrameLayout.LayoutParams(260, 260));
            view.setPadding(4, 4, 4, 4);
            view.setTag(i + base);

            imageView = view.findViewById(R.id.imageView);

            imageView.setImageBitmap(new ImageDecoder().decodeSmallImage(images.get(i).getPath()));

            this.linearLayout.addView(view);
            this.views.add(view);
        }
    }

    public void loadViews(List<Image> images) {
        this.images.addAll(images);

        View view;
        ImageView imageView;
        for (int i = 0; i < images.size(); i++) {
            view = this.inflater.inflate(R.layout.row_image_horizontal, null);
            // view.setLayoutParams(new FrameLayout.LayoutParams(260, 260));
            view.setPadding(4, 4, 4, 4);
            view.setTag(i);

            imageView = view.findViewById(R.id.imageView);

            ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
            mImageLoader.get(MyApplication.getBaseUrlForImages() + images.get(i).getServerPath(),
                    ImageLoader.getImageListener(imageView,
                            R.drawable.others, R.drawable.others));

            this.linearLayout.addView(view);
            this.views.add(view);
            updateViews(i);
        }
    }

    public boolean isLoading() {
        for (int i = 0; i < images.size(); i++)
            if (images.get(i).isLoading())
                return true;

        return false;
    }

    public void updateViews(int position) {

        View view = views.get(position);
        Image image = images.get(position);

        TextView textView = view.findViewById(R.id.textView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        if (!image.isLoading()) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(position + 1));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteImage(int position) {
        View removedView = views.remove(position);
        images.remove(position);

        linearLayout.removeView(removedView);

        if (position == 0) { // if first and main image is deleted
            if (images.size() > 0) // mark the current first image
                images.get(0).markAsMain();
        }

        enumerate();
    }

    public void replaceMain(int position) {
        images.get(0).unMarkAsMain();

        View mainView = views.remove(position);
        Image mainImage = images.remove(position);
        linearLayout.removeView(mainView);

        mainImage.markAsMain();

        images.add(0, mainImage);
        views.add(0, mainView);
        linearLayout.addView(mainView, 0); //should add button and make it 1 !!

        enumerate();
    }

    private void enumerate() {
        for (int i = 0; i < images.size(); i++) {
            views.get(i).setTag(i);
            ((TextView) views.get(i).findViewById(R.id.textView)).setText(String.valueOf(i + 1));
        }
    }
}
