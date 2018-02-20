package com.tradinos.dealat2.Parser.Parser.Category;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Category;

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

        category.setImageUrl(jsonObject.getString("mobile_image"));
        //description

        return category;
    }
}
