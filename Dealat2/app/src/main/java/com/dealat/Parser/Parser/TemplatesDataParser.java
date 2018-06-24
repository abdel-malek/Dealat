package com.dealat.Parser.Parser;

import com.dealat.Parser.Parser.Item.CityListParser;
import com.dealat.Parser.Parser.Item.ItemListParser;
import com.dealat.Parser.Parser.Item.TypeMapParser;
import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.TemplatesData;

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

        data.setCities(new CityListParser().Parse(jsonObject.getString("nested_locations")));

        data.setBrands(new TypeMapParser().Parse(jsonObject.getString("types")));

        data.setEducations(new ItemListParser("education_id").Parse(jsonObject.getString("educations")));

        data.setSchedules(new ItemListParser("schedule_id").Parse(jsonObject.getString("schedules")));

        data.setShowPeriods(new ItemListParser("show_period_id").Parse(jsonObject.getString("show_periods")));

        data.setCertificates(new ItemListParser("certificate_id").Parse(jsonObject.getString("certificates")));

        data.setPropertyStates(new ItemListParser("property_state_id").Parse(jsonObject.getString("states")));

        return data;
    }
}
