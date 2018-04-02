package com.tradinos.dealat2.Parser.Parser.Ad;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Category;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 26.02.18.
 */

public class AdParser implements TradinosParser<Ad> {
    @Override
    public Ad Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Ad Parse(JSONObject jsonObject) throws JSONException {
        Ad ad = new Ad();

        ad.setId(jsonObject.getString("ad_id"));
        ad.setCategoryId(jsonObject.getString("category_id"));
        ad.setCityId(jsonObject.getString("city_id"));
        ad.setCityName(jsonObject.getString("city_name"));

        if (validData(jsonObject.getString("location_id"))) {
            ad.setLocationId(jsonObject.getString("location_id"));
            ad.setLocationName(jsonObject.getString("location_name"));
        }

        ad.setPublishDate(jsonObject.getString("publish_date"));
        ad.setTitle(jsonObject.getString("title"));
        ad.setShowPeriod(jsonObject.getInt("show_period"));

        if (validData(jsonObject.getString("reject_note")))
            ad.setRejectNote(jsonObject.getString("reject_note"));

        if (validData(jsonObject.getString("description")))
            ad.setDescription(jsonObject.getString("description"));


        if (validData(jsonObject.getString("main_image")))
            ad.setMainImageUrl(jsonObject.getString("main_image"));

        ad.setTemplate(jsonObject.getInt("tamplate_id"));
        ad.setStatus(jsonObject.getInt("status"));

        if (ad.getTemplate() != Category.JOBS)
            ad.setPrice(jsonObject.getDouble("price"));

        if (jsonObject.getInt("is_negotiable") == 0)
            ad.setNegotiable(false);
        else
            ad.setNegotiable(true);

        if (jsonObject.getInt("is_featured") == 0)
            ad.setFeatured(false);
        else
            ad.setFeatured(true);

        if (jsonObject.has("is_favorite")) {
            if (jsonObject.getInt("is_favorite") == 0)
                ad.setFavorite(false);
            else
                ad.setFavorite(true);
        }

        if (jsonObject.has("expired_after"))
            ad.setExpiresAfter(jsonObject.getInt("expired_after"));

        return ad;
    }

    private boolean validData(String data) {
        return !data.equals("null");
    }
}
