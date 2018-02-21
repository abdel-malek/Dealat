package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class TypeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Type> items;

    public TypeAdapter(Context context, List<Type> items){
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Type getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = this.inflater.inflate(R.layout.row_item, null);
        }

        ((TextView) view.findViewById(R.id.textView)).setText(getItem(i).toString());

        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        if (view == null){
            view = this.inflater.inflate(R.layout.row_item_dropdown, null);
        }

        ((TextView) view.findViewById(R.id.textView)).setText(getItem(position).toString());

        return view;
    }
}
