package com.tradinos.dealat2.Utils;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.tradinos.dealat2.Model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 26.09.17.
 */

public class ItemFilter extends Filter {
    ArrayAdapter customAdapter;
    List<Item> data = new ArrayList<>();

    public ItemFilter(ArrayAdapter adapter, List<Item> data) {
        this.customAdapter = adapter;
        this.data = data;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults result = new FilterResults();
        // if no constraint is given, return the whole list
        if (constraint == null) {
            result.values = data;
            result.count = data.size();
        } else {
            String substr = constraint.toString().toLowerCase();

            final ArrayList<Item> retList = new ArrayList<>();
            for (Item o : data) {
                if (o.toString().toLowerCase().contains(substr)) {
                    retList.add(o);
                }
            }
            result.values = retList;
            result.count = retList.size();
        }
        return result;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        // we clear the adapter and then pupulate it with the new results
        customAdapter.clear();
        if (filterResults.count > 0) {
            for (Item o : (ArrayList<Item>) filterResults.values) {
                customAdapter.add(o);
            }
        }
    }
}
