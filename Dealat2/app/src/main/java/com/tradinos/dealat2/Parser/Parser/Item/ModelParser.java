package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Item;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 20.02.18.
 */

public class ModelParser implements TradinosParser<Item> {
    @Override
    public Item Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Item Parse(JSONObject jsonObject) throws JSONException{
        return new Item(jsonObject.getString("type_model_id"), jsonObject.getString("name"));
    }
}
