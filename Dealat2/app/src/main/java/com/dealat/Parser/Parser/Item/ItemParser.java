package com.dealat.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 20.02.18.
 */

public class ItemParser implements TradinosParser<Item> {
    private String idKey, nameKey;

    public ItemParser(String idKey, String nameKey){
        this.idKey = idKey;
        this.nameKey = nameKey;
    }

    @Override
    public Item Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Item Parse(JSONObject jsonObject) throws JSONException {
        return new Item(jsonObject.getString(this.idKey), jsonObject.getString(this.nameKey));
    }
}
