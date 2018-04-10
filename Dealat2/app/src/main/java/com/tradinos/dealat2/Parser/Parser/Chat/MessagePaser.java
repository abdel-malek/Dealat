package com.tradinos.dealat2.Parser.Parser.Chat;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 22.03.18.
 */

public class MessagePaser implements TradinosParser<Message> {
    @Override
    public Message Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Message Parse(JSONObject jsonObject) throws JSONException {
        Message message = new Message();

        message.setId(jsonObject.getString("message_id"));
        message.setText(jsonObject.getString("text"));
        message.setCreatedAt(jsonObject.getString("created_at"));
        message.setSent(true);

        if (jsonObject.getInt("to_seller") == 1)
            message.setToSeller(true);
        else
            message.setToSeller(false);

        return message;
    }
}
