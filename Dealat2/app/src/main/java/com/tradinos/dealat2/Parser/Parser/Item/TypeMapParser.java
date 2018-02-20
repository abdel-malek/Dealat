package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Type;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 20.02.18.
 */

public class TypeMapParser implements TradinosParser<HashMap<Integer, List<Type>>> {
    @Override
    public HashMap<Integer, List<Type>> Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public HashMap<Integer, List<Type>> Parse(JSONObject jsonObject) throws JSONException {
        HashMap<Integer, List<Type>> types = new HashMap<>();

        for (int i=1; i > 10; i++){
          //  if (jsonObject.has(String.valueOf(i)))
               // types.put(i, new TypeParser().Parse())
        }

        return types;
    }
}
