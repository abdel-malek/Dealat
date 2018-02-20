package com.tradinos.dealat2.Parser.Parser;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Parser.Parser.Item.ItemListParser;
import com.tradinos.dealat2.Parser.Parser.Item.TypeMapParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 20.02.18.
 */

public class TemplatesDataParser implements TradinosParser<TemplatesData> {
    @Override
    public TemplatesData Parse(String text) throws JSONException {
        return null;
    }

    public TemplatesData Parse(JSONObject jsonObject) throws JSONException {
        TemplatesData data = new TemplatesData();

        data.setEducations(new ItemListParser("education_id").Parse(jsonObject.getString("educations")));
        data.setSchedules(new ItemListParser("schedule_id").Parse(jsonObject.getString("schedules")));
        data.setTypes(new TypeMapParser().Parse(jsonObject.getString("types")));

        return data;
    }
}
