package com.tradinos.dealat2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 05.03.18.
 */

public class CheckableAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Item> items, selectedItems = new ArrayList<>();

    public CheckableAdapter(Context context, List<Item> items) {
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

    public JSONArray getSelectedItems() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < selectedItems.size(); i++)
            jsonArray.put(selectedItems.get(i).getId());

        return jsonArray;
    }

    //beside showing selecte items in spinner// this method is also to send names of selected items,
    // and they we'll be stored when a Search is saved(we may call it bookmark)
    // these labels will reduce join on database to get names of selected items by their Ids

    public String getSelectedNames() {
        String names = "";

        if (selectedItems.isEmpty())
            if (getItem(0) != null && getItem(0).isNothing())
                names = getItem(0).getName();

        for (int i = 0; i < selectedItems.size(); i++) {
            if (i == 0)
                names += selectedItems.get(i).getName();
            else
                names += ", " + selectedItems.get(i).getName();
        }

        return names;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = this.inflater.inflate(R.layout.row_check_result, null);

        ((TextView) view.findViewById(R.id.textView)).setText(getSelectedNames());

        return view;
    }

    @Override
    public View getDropDownView(final int i, View view, ViewGroup parent) {

        view = this.inflater.inflate(R.layout.row_checkable, null);

        view.setTag(i);

        final CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setText(getItem(i).getName());

        if (getItem(i).isChecked())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (getItem(i).isNothing()) { // All

                        getItem(i).check();
                        // unCheck all items
                        for (int i = 0; i < selectedItems.size(); i++)
                            selectedItems.get(i).unCheck();

                        selectedItems.clear();
                    } else {
                        if (getItem(0) != null && getItem(0).isNothing())
                            getItem(0).unCheck(); // unCheck All

                        getItem(i).check();
                        selectedItems.add(getItem(i));
                    }
                } else {
                    getItem(i).unCheck();
                    selectedItems.remove(getItem(i));
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
