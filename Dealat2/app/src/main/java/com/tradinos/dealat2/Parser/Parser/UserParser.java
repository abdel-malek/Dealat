package com.tradinos.dealat2.Parser.Parser;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 12.03.18.
 */

public class UserParser implements TradinosParser<User> {
    @Override
    public User Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public User Parse(JSONObject jsonObject) throws JSONException{
        User user = new User();

        user.setId(jsonObject.getString("user_id"));
        user.setName(jsonObject.getString("name"));
        user.setPhone(jsonObject.getString("phone"));

        if (!jsonObject.getString("server_key").equals("null"))
            user.setServerKey(jsonObject.getString("server_key"));

        user.setCityId(jsonObject.getString("city_id"));

        return user;
    }
}
