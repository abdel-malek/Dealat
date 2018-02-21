package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 21.02.18.
 */

public class LocationParser implements TradinosParser<Location> {
    @Override
    public Location Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Location Parse(JSONObject jsonObject) throws JSONException {
        Location location = new Location();

        location.setId(jsonObject.getString("location_id"));
        location.setName(jsonObject.getString("location_name"));
        location.setCityName(jsonObject.getString("city_name"));
        location.setCityId(jsonObject.getString("city_id"));

        return location;
    }
}
