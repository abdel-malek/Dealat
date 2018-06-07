package com.tradinos.dealat2.Parser.Parser.Category;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 19.02.18.
 */

public class CategoryParser implements TradinosParser<Category> {
    @Override
    public Category Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Category Parse(JSONObject jsonObject) throws JSONException {
        Category category = new Category();

        category.setId(jsonObject.getString("category_id"));
        category.setParentId(jsonObject.getString("parent_id"));
        category.setName(jsonObject.getString("category_name"));
        category.setTemplateId(jsonObject.getInt("tamplate_id"));

        if (validData(jsonObject.getString("mobile_image")))
            category.setImageUrl(jsonObject.getString("mobile_image"));
        //description

        JSONArray jsonArray;
        if (validData(jsonObject.getString("hidden_fields"))) {
            jsonArray = new JSONArray(jsonObject.getString("hidden_fields"));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++)
                stringBuilder.append(jsonArray.getString(i).replaceAll("\"\"", " "));

            category.setHiddenFields(stringBuilder.toString());
        }

        return category;
    }

    private boolean validData(String data) {
        return !data.equals("null") && !data.equals("0");
    }
}
