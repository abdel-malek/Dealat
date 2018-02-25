package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class CategoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories){
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

        if (view == null)
            view = this.inflater.inflate(R.layout.row_category, null);

        ((TextView)view.findViewById(R.id.textView)).setText(getItem(i).getName());




        return view;
    }
}
