package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tradinos.dealat2.Model.Image;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.ImageDecoder;

import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class HorizontalAdapter {

    private LayoutInflater inflater;
    private LinearLayout linearLayout;
    private List<Image> images;

    public HorizontalAdapter(Context context, LinearLayout linearLayout){
        this.inflater = LayoutInflater.from(context);
        this.linearLayout = linearLayout;
    }

    public void setViews(List<Image> images){
        this.images = images;

        View view;
        ImageView imageView;
        for (int i=0; i< images.size(); i++){
            view = this.inflater.inflate(R.layout.row_image, null);
            view.setLayoutParams(new FrameLayout.LayoutParams(260, 260));
            view.setPadding(4, 4, 4, 4);

            imageView = view.findViewById(R.id.imageView);

            imageView.setImageBitmap(new ImageDecoder().decodeSampledBitmapFromUri(images.get(i).getPath()));

            this.linearLayout.addView(view);
        }
    }
}
