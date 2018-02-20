package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Type;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 20.02.18.
 */

public class TypeParser implements TradinosParser<Type> {
    @Override
    public Type Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Type Parse(JSONObject jsonObject) throws JSONException {
        Type type = new Type();

        type.setId(jsonObject.getString("type_id"));
        type.setName(jsonObject.getString("name"));

        type.setTemplateId(jsonObject.getInt("tamplate_id"));
       // type.setModels(); models

        return type;
    }
}
