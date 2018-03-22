package com.tradinos.dealat2.Parser.Parser.Chat;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 22.03.18.
 */

public class MessageListParser implements TradinosParser<List<Message>> {
    @Override
    public List<Message> Parse(String text) throws JSONException {
        List<Message> messages = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(text);
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            messages.add(new MessagePaser().Parse(jsonObject));
        }

        return messages;
    }
}
