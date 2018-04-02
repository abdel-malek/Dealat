package com.tradinos.dealat2.Parser.Parser.Item;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.City;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 21.02.18.
 */

public class CityParser implements TradinosParser<City> {
    @Override
    public City Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public City Parse(JSONObject jsonObject) throws JSONException {
        City city = new City();

        city.setId(jsonObject.getString("city_id"));
        city.setName(jsonObject.getString("city_name"));

        if (!jsonObject.getString("locations").equals("null")){
            city.setLocations(new ItemListParser("location_id", "location_name").Parse(jsonObject.getString("locations")));
            city.addNoLocation();
        }

        return city;
    }
}
