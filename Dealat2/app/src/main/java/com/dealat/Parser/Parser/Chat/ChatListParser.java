package com.dealat.Parser.Parser.Chat;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 22.03.18.
 */

public class ChatListParser implements TradinosParser<List<Chat>> {
    @Override
    public List<Chat> Parse(String text) throws JSONException {

        JSONArray jsonArray = new JSONArray(text);
        List<Chat> chats = new ArrayList<>();

        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            chats.add(new ChatParser().Parse(jsonObject));
        }

        return chats;
    }
}
