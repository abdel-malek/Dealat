package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class ItemListParser implements TradinosParser<List<Item>> {

    private String idKey;

    public ItemListParser(String idKey){
        this.idKey = idKey;
    }

    @Override
    public List<Item> Parse(String text) throws JSONException {
        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            items.add(new ItemParser(idKey).Parse(jsonObject));
        }

        return items;
    }
}
