package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Location;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.LocationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class LocationAdapter extends ArrayAdapter<Location> {

    private LocationFilter customFilter;
    private LayoutInflater inflater;
    private List<Location> locations;

    public LocationAdapter(Context context, List<Location> locations){
        super(context, R.layout.row_location, new ArrayList<Location>(locations));
        this.inflater = LayoutInflater.from(context);
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return this.locations.size();
    }

    @Override
    public Location getItem(int i) {
        return this.locations.get(i);
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

        Location location = getItem(i);
        //view.setTag(location);
        ((TextView)view.findViewById(R.id.textView)).setText(location.getFullName());

        return view;
    }

    @Override
    public View getDropDownView(int i, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null){
            view = this.inflater.inflate(R.layout.row_location, parent, false);
        }

        Location location = getItem(i);

        ((TextView)view.findViewById(R.id.textView)).setText(location.getName());
        ((TextView)view.findViewById(R.id.text2)).setText(location.getCityName());

        return view;
    }

    @Override
    public LocationFilter getFilter() {
        if(customFilter == null){
            customFilter = new LocationFilter(this, locations);
        }
        return customFilter;
    }
}
