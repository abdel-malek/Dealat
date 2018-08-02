package com.dealat.Parser;

import com.dealat.Model.GroupedResponse;
import com.dealat.Parser.Parser.Ad.AdListParser;
import com.dealat.Parser.Parser.CommercialAd.CommercialAdListParser;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;
import org.json.JSONObject;

public class AdsResponseParser implements TradinosParser<GroupedResponse> {
    @Override
    public GroupedResponse Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public GroupedResponse Parse(JSONObject jsonObject) throws JSONException {
        GroupedResponse response = new GroupedResponse();

        response.setAds(new AdListParser().Parse(jsonObject.getString("ads")));

        if (jsonObject.has("commercials")) // commercials are sent with first page only
            response.setCommercialAds(new CommercialAdListParser().Parse(jsonObject.getString("commercials")));

        return response;
    }
}
