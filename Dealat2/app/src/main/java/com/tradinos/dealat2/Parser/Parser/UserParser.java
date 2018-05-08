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

    public User Parse(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.setId(jsonObject.getString("user_id"));
        user.setName(jsonObject.getString("name"));
        user.setPhone(jsonObject.getString("phone"));
        user.setCityId(jsonObject.getString("city_id"));

        if (validData(jsonObject.getString("birthday")))
            user.setBirthday(jsonObject.getString("birthday"));

        if (validData(jsonObject.getString("gender")))
            user.setGender(jsonObject.getInt("gender"));

        if (validData(jsonObject.getString("whatsup_number")))
            user.setWhatsAppNumber(jsonObject.getString("whatsup_number"));

        if (validData(jsonObject.getString("email")))
            user.setEmail(jsonObject.getString("email"));

        if (validData(jsonObject.getString("server_key")))
            user.setServerKey(jsonObject.getString("server_key"));

        if (validData(jsonObject.getString("personal_image")))
            user.setImageUrl(jsonObject.getString("personal_image"));

        return user;
    }

    private boolean validData(String data) {
        return !data.equals("null") && !data.equals("");
    }
}
