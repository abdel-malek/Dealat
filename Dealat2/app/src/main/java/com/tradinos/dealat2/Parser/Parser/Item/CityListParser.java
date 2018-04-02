package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class CityListParser implements TradinosParser<List<City>> {
    @Override
    public List<City> Parse(String text) throws JSONException {

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        List<City> cities = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            cities.add(new CityParser().Parse(jsonObject));
        }

        return cities;
    }
}
