package com.dealat.Parser.Parser.Category;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class CategoryListParser implements TradinosParser<List<Category>> {
    @Override
    public List<Category> Parse(String text) throws JSONException {

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            categories.add(new CategoryParser().Parse(jsonObject));
        }

        return categories;
    }
}
