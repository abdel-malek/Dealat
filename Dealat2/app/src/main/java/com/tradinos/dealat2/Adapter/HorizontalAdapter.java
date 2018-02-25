package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class HorizontalAdapter {

    private LayoutInflater inflater;
    private List<Image> images;
    private LinearLayout linearLayout;
    private List<View> views;


    public HorizontalAdapter(Context context, LinearLayout linearLayout) {
        this.inflater = LayoutInflater.from(context);
        this.linearLayout = linearLayout;
    }

    public Image getItem(int i) {
        return this.images.get(i);
    }

    public int getCount() {
        return this.images.size();
    }

    public void setViews(final List<Image> images) {
        this.images = images;

        this.views = new ArrayList<>();

        View view;
        ImageView imageView;

        for (int i = 0; i < images.size(); i++) {

            view = this.inflater.inflate(R.layout.row_image_horizontal, null);
            // view.setLayoutParams(new FrameLayout.LayoutParams(260, 260));
            view.setPadding(4, 4, 4, 4);
            view.setTag(i);

            imageView = view.findViewById(R.id.imageView);

            imageView.setImageBitmap(new ImageDecoder().decodeSampledBitmapFromUri(images.get(i).getPath()));

            this.linearLayout.addView(view);
            this.views.add(view);
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
            textView.setText(String.valueOf(position+1));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void deleteImage(int position) {
        View removedView = views.remove(position);
        images.remove(position);

        linearLayout.removeView(removedView);

        enumerate();
    }

    public void replaceMain(int position) {
        View mainView = views.remove(position);
        Image mainImage = images.remove(position);
        linearLayout.removeView(mainView);

        images.get(0).unMarkAsMain();
        mainImage.markAsMain();

        images.add(0, mainImage);
        views.add(0, mainView);
        linearLayout.addView(mainView, 0);

        enumerate();
    }

    private void enumerate() {
        for (int i = 0; i < images.size(); i++) {
            views.get(i).setTag(i);
            ((TextView) views.get(i).findViewById(R.id.textView)).setText(String.valueOf(i + 1));
        }
    }
}
