package com.tradinos.dealat2.Parser.Parser.Ad;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Ad;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsParser implements TradinosParser<Ad> {
    @Override
    public Ad Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Ad Parse(JSONObject jsonObject){
        Ad ad = new Ad();



        return ad;
    }
}
