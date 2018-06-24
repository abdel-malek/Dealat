package com.dealat.Parser.Parser.Ad;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Ad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 26.02.18.
 */

public class AdListParser implements TradinosParser<List<Ad>> {
    @Override
    public List<Ad> Parse(String text) throws JSONException {

        List<Ad> ads = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            ads.add(new AdParser().Parse(jsonObject));
        }

        return ads;
    }
}
