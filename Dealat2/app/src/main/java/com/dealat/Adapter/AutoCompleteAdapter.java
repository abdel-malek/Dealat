package com.dealat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dealat.Model.Item;
import com.dealat.R;
import com.dealat.Utils.ItemFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class AutoCompleteAdapter extends ArrayAdapter<Item> {

    private ItemFilter customFilter;
    private LayoutInflater inflater;
    private List<Item> items;

    public AutoCompleteAdapter(Context context, List<Item> items){
        super(context, R.layout.row_item_dropdown, new ArrayList<>(items));
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = this.inflater.inflate(R.layout.row_item_dropdown, viewGroup, false);
        }

        Item item = getItem(i);
        view.setTag(item);

        ((TextView)view.findViewById(R.id.textView)).setText(item.getName());

        return view;
    }

    @Override
    public ItemFilter getFilter() {
        if(customFilter == null){
            customFilter = new ItemFilter(this, items);
        }
        return customFilter;
    }
}
