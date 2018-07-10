package com.dealat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.dealat.Model.Category;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.View.MasterActivity;

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
        TextView textView = view.findViewById(R.id.textViewCat);
        TextView textViewCounts = view.findViewById(R.id.textView);

        Category category = getItem(i);

        textView.setText(category.getName());
        textViewCounts.setText(((MasterActivity) context).formattedNumber(((MasterActivity) context).getFullAdsCount(category)));

        if (i % 2 == 0) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            textViewCounts.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        } else {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            textViewCounts.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }

        int defaultDrawable = ((MasterActivity) context).getTemplateDefaultImage(category.getTemplateId());

        ImageLoader mImageLoader = InternetManager.getInstance(context).getImageLoader();
        mImageLoader.get(MyApplication.getBaseUrl() + category.getImageUrl(), ImageLoader.getImageListener(imageView,
                defaultDrawable, defaultDrawable));

        return view;
    }
}
