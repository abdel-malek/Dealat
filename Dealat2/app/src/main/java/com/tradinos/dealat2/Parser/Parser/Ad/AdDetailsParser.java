package com.tradinos.dealat2.Parser.Parser.Ad;

import com.tradinos.core.network.TradinosParser;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.AdElectronic;
import com.tradinos.dealat2.Model.AdFashion;
import com.tradinos.dealat2.Model.AdIndustry;
import com.tradinos.dealat2.Model.AdJob;
import com.tradinos.dealat2.Model.AdKid;
import com.tradinos.dealat2.Model.AdMobile;
import com.tradinos.dealat2.Model.AdProperty;
import com.tradinos.dealat2.Model.AdSport;
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

                if (validData(jsonObject.getString("type_id"))) {
                    ((AdVehicle) ad).setTypeId(jsonObject.getString("type_id"));
                    ((AdVehicle) ad).setTypeName(jsonObject.getString("type_name"));
                }

                if (validData(jsonObject.getString("type_model_id"))) {
                    ((AdVehicle) ad).setModelId(jsonObject.getString("type_model_id"));
                    ((AdVehicle) ad).setModelName(jsonObject.getString("type_model_name"));
                }

                if (validData(jsonObject.getString("manufacture_date")))
                    ((AdVehicle) ad).setManufactureYear(jsonObject.getString("manufacture_date"));

                if (validData(jsonObject.getString("kilometer")))
                    ((AdVehicle) ad).setKilometer(jsonObject.getDouble("kilometer"));

                if (jsonObject.getInt("is_automatic") == 0)
                    ((AdVehicle) ad).setAutomatic(false);
                else
                    ((AdVehicle) ad).setAutomatic(true);

                if (jsonObject.getInt("is_new") == 0)
                    ((AdVehicle) ad).setSecondhand(true);
                else
                    ((AdVehicle) ad).setSecondhand(false);

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

            case Category.JOBS:
                ad = new AdJob();

                if (validData(jsonObject.getString("education_id"))) {
                    ((AdJob) ad).setEducationId(jsonObject.getString("education_id"));
                    ((AdJob) ad).setEducationName(jsonObject.getString("education_name"));
                }

                if (validData(jsonObject.getString("schedule_id"))) {
                    ((AdJob) ad).setScheduleId(jsonObject.getString("schedule_id"));
                    ((AdJob) ad).setScheduleName(jsonObject.getString("schedule_name"));
                }

                if (validData(jsonObject.getString("experience")))
                    ((AdJob) ad).setExperience(jsonObject.getString("experience"));

                if (validData(jsonObject.getString("salary")))
                    ((AdJob) ad).setSalary(jsonObject.getDouble("salary"));

                break;

            case Category.MOBILES:
                ad = new AdMobile();

                if (validData(jsonObject.getString("type_id"))) {
                    ((AdMobile) ad).setTypeId(jsonObject.getString("type_id"));
                    ((AdMobile) ad).setTypeName(jsonObject.getString("type_name"));
                }

                if (jsonObject.getInt("is_new") == 0)
                    ((AdMobile) ad).setSecondhand(true);
                else
                    ((AdMobile) ad).setSecondhand(false);

                break;

            case Category.ELECTRONICS:
                ad = new AdElectronic();

                if (validData(jsonObject.getString("type_id"))) {
                    ((AdElectronic) ad).setTypeId(jsonObject.getString("type_id"));
                    ((AdElectronic) ad).setTypeName(jsonObject.getString("type_name"));
                }

                if (validData(jsonObject.getString("size")))
                    ((AdElectronic) ad).setSize(jsonObject.getDouble("size"));

                if (jsonObject.getInt("is_new") == 0)
                    ((AdElectronic) ad).setSecondhand(true);
                else
                    ((AdElectronic) ad).setSecondhand(false);

                break;

            case Category.FASHION:
                ad = new AdFashion();

                if (jsonObject.getInt("is_new") == 0)
                    ((AdFashion) ad).setSecondhand(true);
                else
                    ((AdFashion) ad).setSecondhand(false);

                break;

            case Category.KIDS:
                ad = new AdKid();

                if (jsonObject.getInt("is_new") == 0)
                    ((AdKid) ad).setSecondhand(true);
                else
                    ((AdKid) ad).setSecondhand(false);

                break;

            case Category.SPORTS:
                ad = new AdSport();

                if (jsonObject.getInt("is_new") == 0)
                    ((AdSport) ad).setSecondhand(true);
                else
                    ((AdSport) ad).setSecondhand(false);

                break;

            case Category.INDUSTRIES:
                ad = new AdIndustry();

                if (jsonObject.getInt("is_new") == 0)
                    ((AdIndustry) ad).setSecondhand(true);
                else
                    ((AdIndustry) ad).setSecondhand(false);

                break;

            //case Category.SERVICES:

            default:
                ad = new Ad();
        }

        ad.setId(jsonObject.getString("ad_id"));
        ad.setTitle(jsonObject.getString("title"));
        ad.setCreationDate(jsonObject.getString("created_at"));
        ad.setSellerId(jsonObject.getString("user_id"));
        ad.setSellerId(jsonObject.getString("seller_id"));
        ad.setSellerName(jsonObject.getString("seller_name"));
        ad.setSellerPhone(jsonObject.getString("seller_phone"));
        ad.setCategoryId(jsonObject.getString("category_id"));
        ad.setTemplate(jsonObject.getInt("tamplate_id"));
        ad.setCityId(jsonObject.getString("city_id"));
        ad.setCityName(jsonObject.getString("city_name"));

        if (validData(jsonObject.getString("whatsup_number")))
            ad.setWhatsAppNumber(jsonObject.getString("whatsup_number"));

        if (validData(jsonObject.getString("location_id"))) {
            ad.setLocationId(jsonObject.getString("location_id"));
            ad.setLocationName(jsonObject.getString("location_name"));
        }

        if (validData(jsonObject.getString("description")))
            ad.setDescription(jsonObject.getString("description"));

        if (jsonObject.getInt("visible_phone") == 1)
            ad.setVisiblePhone(true);

        ad.setPrice(jsonObject.getDouble("price"));
        ad.setStatus(jsonObject.getInt("status"));
        ad.setShowPeriod(jsonObject.getInt("show_period"));

        if (validData(jsonObject.getString("reject_note")))
            ad.setRejectNote(jsonObject.getString("reject_note"));

        if (validData(jsonObject.getString("publish_date")))
            ad.setPublishDate(jsonObject.getString("publish_date"));

        if (jsonObject.has("days")) // it most likely does have days
            ad.setDays(jsonObject.getInt("days"));

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

        String s = jsonObject.getString("main_image");
        if (!s.equals("null")) { // main images always first in list
            ad.setMainImageUrl(s);
            ad.addImagePath(s);
        }

        if (validData(jsonObject.getString("main_video")))
            ad.setMainVideoUrl(jsonObject.getString("main_video"));

        JSONArray jsonArray = new JSONArray(jsonObject.getString("images"));
        JSONObject imageObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            imageObject = jsonArray.getJSONObject(i);
            ad.addImagePath(imageObject.getString("image"));
        }

        return ad;
    }

    private boolean validData(String data) {
        return !data.equals("null") && !data.equals("0") && !data.equals("");
    }
}
