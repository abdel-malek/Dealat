package com.tradinos.dealat2.Utils;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.tradinos.dealat2.Model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 26.09.17.
 */

public class LocationFilter extends Filter {
    ArrayAdapter customAdapter;
    List<Location> data = new ArrayList<>();

    public LocationFilter(ArrayAdapter adapter, List<Location> data) {
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

            final ArrayList<Location> retList = new ArrayList<>();
            for (Location o : data) {
                if (o.toString().toLowerCase().contains(substr) || o.getCityName().toLowerCase().contains(substr)) {
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
            for (Location o : (ArrayList<Location>) filterResults.values) {
                customAdapter.add(o);
            }
        }
    }
}
