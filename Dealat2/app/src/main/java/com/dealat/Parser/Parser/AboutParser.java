package com.dealat.Parser.Parser;

import com.tradinos.core.network.TradinosParser;
import com.dealat.Model.About;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 19.04.18.
 */

public class AboutParser implements TradinosParser<About> {
    @Override
    public About Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public About Parse(JSONObject jsonObject) throws JSONException {
        About about = new About();

        if (validData(jsonObject.getString("about_us")))
            about.setContent(jsonObject.getString("about_us"));

        if (validData(jsonObject.getString("terms")))
            about.setTerms(jsonObject.getString("terms"));

        if (validData(jsonObject.getString("facebook_link")))
            about.setFacebookLink(jsonObject.getString("facebook_link"));

        if (validData(jsonObject.getString("twiter_link")))
            about.setTwitterLink(jsonObject.getString("twiter_link"));

        if (validData(jsonObject.getString("youtube_link")))
            about.setYoutubeLink(jsonObject.getString("youtube_link"));

        if (validData(jsonObject.getString("linkedin_link")))
            about.setLinkedInLink(jsonObject.getString("linkedin_link"));

        if (validData(jsonObject.getString("instagram_link")))
            about.setInstagramLink(jsonObject.getString("instagram_link"));

        if (validData(jsonObject.getString("phone")))
            about.setPhone(jsonObject.getString("phone"));

        if (validData(jsonObject.getString("email")))
            about.setEmail(jsonObject.getString("email"));

        return about;
    }

    private boolean validData(String data) {
        return !data.equals("null") && !data.equals("");
    }
}
