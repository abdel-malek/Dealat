package com.dealat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dealat.Model.Category;
import com.dealat.R;
import com.dealat.View.MasterActivity;

import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class CategoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Category> categories;
    private boolean viewAdsCount;

    public CategoryAdapter(Context context, List<Category> categories, boolean viewAdsCount) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.categories = categories;
        this.viewAdsCount = viewAdsCount;
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

        if (view == null)
            view = this.inflater.inflate(R.layout.row_category, null);

        Category category = getItem(i);
        String adsCount = "";

        if (viewAdsCount)
            adsCount = " (" + ((MasterActivity) context).formattedNumber(((MasterActivity) context).getFullAdsCount(category))
                    + ")";

        ((TextView) view.findViewById(R.id.textView)).setText(category.getName() + adsCount);

        if (category.hasSubCats())
            view.findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.imageView).setVisibility(View.INVISIBLE);

        return view;
    }
}
