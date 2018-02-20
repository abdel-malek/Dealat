package com.tradinos.dealat2.Parser.Parser;

import com.tradinos.core.network.TradinosParser;

import org.json.JSONException;

/**
 * Created by developer on 20.02.18.
 */

public class StringParser implements TradinosParser<String> {
    @Override
    public String Parse(String text) throws JSONException {
        return text;
    }
}
