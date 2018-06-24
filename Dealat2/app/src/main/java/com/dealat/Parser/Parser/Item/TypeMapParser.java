package com.dealat.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class TypeMapParser implements TradinosParser<HashMap<Integer, List<Type>>> {
    @Override
    public HashMap<Integer, List<Type>> Parse(String text) throws JSONException {
        if (text.equals("[]"))
            return new HashMap<>();
        return Parse(new JSONObject(text));
    }

    public HashMap<Integer, List<Type>> Parse(JSONObject jsonObject) throws JSONException {
        HashMap<Integer, List<Type>> types = new HashMap<>();

        List<Type> typeList;

        JSONArray jsonArray;
        JSONObject jsonType;

        for (int i = 1; i < 10; i++) { // as we have 10 templates
            if (jsonObject.has(String.valueOf(i))) { // if template has types

                typeList = new ArrayList<>();
                jsonArray = new JSONArray(jsonObject.getString(String.valueOf(i)));

                typeList.add(Type.getNoType()); //for filter

                for (int j = 0; j < jsonArray.length(); j++) {
                    jsonType = jsonArray.getJSONObject(j);
                    typeList.add(new TypeParser().Parse(jsonType));
                }

                types.put(i, typeList);
            }
        }

        return types;
    }
}
