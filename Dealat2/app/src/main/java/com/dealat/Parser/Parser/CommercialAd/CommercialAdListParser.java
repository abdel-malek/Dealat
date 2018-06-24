package com.dealat.Parser.Parser.CommercialAd;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.CommercialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 25.02.18.
 */

public class CommercialAdListParser implements TradinosParser<List<CommercialAd>> {
    @Override
    public List<CommercialAd> Parse(String text) throws JSONException {

        List<CommercialAd> commercialAds = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            commercialAds.add(new CommercialAdParser().Parse(jsonObject));
        }

        return commercialAds;
    }
}
