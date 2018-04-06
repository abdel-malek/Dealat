package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 04.04.18.
 */

public class RadioAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Item> items;
    private int selectedPosition = -1;

    public RadioAdapter(Context context, List<Item> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Item getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public Item getSelected() {
        if (selectedPosition > -1 && selectedPosition < getCount())
            return this.items.get(selectedPosition);

        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.inflater.inflate(R.layout.row_radio_item, null);
        }

        view.setTag(i);
        ((TextView) view.findViewById(R.id.textView)).setText(getItem(i).getName());

        if (i == selectedPosition)
            view.findViewById(R.id.buttonTrue).setVisibility(View.VISIBLE);
        else
            view.findViewById(R.id.buttonTrue).setVisibility(View.INVISIBLE);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = (int) view.getTag();
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
