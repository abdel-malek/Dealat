package com.tradinos.dealat2.Parser.Parser.Ad;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.AdProperty;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsParser implements TradinosParser<Ad> {
    @Override
    public Ad Parse(String text) throws JSONException {
        return Parse(new JSONObject(text));
    }

    public Ad Parse(JSONObject jsonObject) throws JSONException {
        Ad ad;

        switch (jsonObject.getInt("tamplate_id")) {
            case Category.VEHICLES:
                ad = new AdVehicle();


                break;

            case Category.PROPERTIES:
                ad = new AdProperty();

                if (validData(jsonObject.getString("state")))
                    ((AdProperty) ad).setState(jsonObject.getString("state"));

                if (validData(jsonObject.getString("floor")))
                    ((AdProperty) ad).setFloorNum(jsonObject.getInt("floor"));

                if (validData(jsonObject.getString("rooms_num")))
                    ((AdProperty) ad).setRoomNum(jsonObject.getInt("rooms_num"));

                if (validData(jsonObject.getString("space")))
                    ((AdProperty) ad).setSpace(jsonObject.getDouble("space"));

                if (jsonObject.getInt("with_furniture") == 0)
                    ((AdProperty) ad).setFurnished(false);
                else
                    ((AdProperty) ad).setFurnished(true);

                break;

         /*   case Category.MOBILES:

                break;

            case Category.ELECTRONICS:

                break;

            case Category.FASHION:

                break;

            case Category.KIDS:

                break;

            case Category.SPORTS:

                break;

            case Category.JOBS:

                break;

            case Category.INDUSTRIES:

                break;

            case Category.SERVICES:

                break;*/

            default:
                ad = new Ad();
        }

        ad.setId(jsonObject.getString("ad_id"));
        ad.setCategoryId(jsonObject.getString("category_id"));
        ad.setTemplate(jsonObject.getInt("tamplate_id"));

        ad.setTitle(jsonObject.getString("title"));
        ad.setDescription(jsonObject.getString("description"));
        ad.setPrice(jsonObject.getDouble("price"));
        ad.setStatus(jsonObject.getInt("status"));

        ad.setPublishDate(jsonObject.getString("publish_date"));

        if (jsonObject.getInt("is_negotiable") == 0)
            ad.setNegotiable(false);
        else
            ad.setNegotiable(true);

        if (jsonObject.getInt("is_featured") == 0)
            ad.setFeatured(false);
        else
            ad.setFeatured(true);


        String s = jsonObject.getString("main_image");
        if (!s.equals("null")) {
            ad.setMainImageUrl(s);
            ad.addImagePath(s);
        }

        JSONArray jsonArray = new JSONArray(jsonObject.getString("images"));
        JSONObject imageObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            imageObject = jsonArray.getJSONObject(i);
            ad.addImagePath(imageObject.getString("image"));
        }


        return ad;
    }

    private boolean validData(String data) {
        return !data.equals("null");
    }
}
