package com.tradinos.dealat2.Parser.Parser.CommercialAd;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.CommercialAd;

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
        ad.setDescription(jsonObject.getString("description"));
        ad.setImageUrl(jsonObject.getString("image"));

        if (!jsonObject.getString("ad_url").equals("null"))
            ad.setAdUrl(jsonObject.getString("ad_url"));

        return ad;
    }
}
