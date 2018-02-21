package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class LocationListParser implements TradinosParser<List<Location>> {
    @Override
    public List<Location> Parse(String text) throws JSONException {

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;

        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            locations.add(new LocationParser().Parse(jsonObject));
        }

        return locations;
    }
}
