package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class MainCatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categories;


    public MainCatAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }


    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Category getItem(int i) {
        return this.categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = this.inflater.inflate(R.layout.row_main_category, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        textView.setText(getItem(i).getName());
        if (i % 2 == 0)
            textView.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
        else
            textView.setGravity(View.TEXT_ALIGNMENT_VIEW_START);


        int defaultDrawable = 0;

        switch (getItem(i).getTemplateId()) {
            case Category.VEHICLES:
                defaultDrawable = R.drawable.car_copy;
                break;

            case Category.PROPERTIES:
                defaultDrawable = R.drawable.home;
                break;

            case Category.MOBILES:
                defaultDrawable = R.drawable.smartphone_call;
                break;

            case Category.ELECTRONICS:
                defaultDrawable = R.drawable.photo_camera;
                break;

            case Category.FASHION:
                defaultDrawable = R.drawable.female_black_dress;
                break;

            case Category.KIDS:
                defaultDrawable = R.drawable.teddy_bear;
                break;

            case Category.SPORTS:
                defaultDrawable = R.drawable.dumbbell;
                break;

            case Category.JOBS:
                defaultDrawable = R.drawable.old_fashion_briefcase;
                break;

            case Category.INDUSTRIES:
                defaultDrawable = R.drawable.industries;
                break;

            case Category.SERVICES:
                defaultDrawable = R.drawable.services;
                break;

            default:
                defaultDrawable = R.drawable.others;
        }

        final String BASE = "http://dealat.tradinos.com/";
        // final String BASE = "http://192.168.9.53/Dealat/";
        ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
        mImageLoader.get(BASE + getItem(i).getImageUrl(), ImageLoader.getImageListener(imageView,
                defaultDrawable, defaultDrawable));

        return view;
    }
}
