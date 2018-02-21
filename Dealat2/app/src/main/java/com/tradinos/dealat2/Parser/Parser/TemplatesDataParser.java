package com.tradinos.dealat2.Parser.Parser;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Parser.Parser.Item.ItemListParser;
import com.tradinos.dealat2.Parser.Parser.Item.LocationListParser;
import com.tradinos.dealat2.Parser.Parser.Item.TypeMapParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 20.02.18.
 */

public class TemplatesDataParser implements TradinosParser<TemplatesData> {
    @Override
    public TemplatesData Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public TemplatesData Parse(JSONObject jsonObject) throws JSONException {
        TemplatesData data = new TemplatesData();

        data.setLocations(new LocationListParser().Parse(jsonObject.getString("location")));

        data.setBrands(new TypeMapParser().Parse(jsonObject.getString("types")));

        data.setEducations(new ItemListParser("education_id").Parse(jsonObject.getString("educations")));
        data.getEducations().add(0, Item.getNoItem());

        data.setSchedules(new ItemListParser("schedule_id").Parse(jsonObject.getString("schedules")));
        data.getSchedules().add(0, Item.getNoItem());

        return data;
    }
}
