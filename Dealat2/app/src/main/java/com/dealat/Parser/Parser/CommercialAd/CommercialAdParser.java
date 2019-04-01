package com.dealat.Parser.Parser.CommercialAd;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.CommercialAd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 25.02.18.
 */

public class CommercialAdParser implements TradinosParser<CommercialAd> {
    @Override
    public CommercialAd Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public CommercialAd Parse(JSONObject jsonObject) throws JSONException {
        CommercialAd ad = new CommercialAd();

        ad.setId(jsonObject.getString("commercial_ad_id"));
        ad.setCategoryId(jsonObject.getString("category_id"));
        ad.setTitle(jsonObject.getString("title"));

        if (jsonObject.has("external")) {
            ad.setExternal(jsonObject.getInt("external"));
        }

        if (validData(jsonObject.getString("description")))
            ad.setDescription(jsonObject.getString("description"));

        if (validData(jsonObject.getString("image")))
            ad.setImageUrl(jsonObject.getString("image"));

        if (validData(jsonObject.getString("ad_url")))
            ad.setAdUrl(jsonObject.getString("ad_url"));

        return ad;
    }

    private boolean validData(String data) {
        return !data.equals("null");
    }
}
