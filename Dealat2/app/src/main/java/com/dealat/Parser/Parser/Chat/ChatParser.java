package com.dealat.Parser.Parser.Chat;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.Chat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 22.03.18.
 */

public class ChatParser implements TradinosParser<Chat> {
    @Override
    public Chat Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Chat Parse(JSONObject jsonObject) throws JSONException {
        Chat chat = new Chat();

        chat.setAdId(jsonObject.getString("ad_id"));
        chat.setTemplateId(jsonObject.getString("template_id"));

        chat.setUserId(jsonObject.getString("user_id"));
        chat.setSellerId(jsonObject.getString("seller_id"));
        chat.setChatId(jsonObject.getString("chat_session_id"));

        chat.setUserName(jsonObject.getString("user_name"));
        chat.setSellerName(jsonObject.getString("seller_name"));

        if (validData(jsonObject.getString("user_pic")))
            chat.setUserPic(jsonObject.getString("user_pic"));

        if (validData(jsonObject.getString("seller_pic")))
            chat.setSellerPic(jsonObject.getString("seller_pic"));

        if (jsonObject.has("ad_title"))
            chat.setAdTitle(jsonObject.getString("ad_title"));


        if (jsonObject.getInt("user_seen") == 1)
            chat.setUserSeen(true);

        if (jsonObject.getInt("seller_seen") == 1)
            chat.setSellerSeen(true);

        return chat;
    }

    private boolean validData(String data) {
        return !data.equals("null");
    }
}
