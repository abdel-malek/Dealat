package com.tradinos.dealat2.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

/**
 * Created by developer on 12.03.18.
 */

public class CityAdapter extends BaseAdapter {
    Integer selected_position = -1;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        // Your Code
/*
        chkbox.setChecked(position == selected_position);

        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected_position = position;
                } else {
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;


    }*/

        return null;
    }
}
