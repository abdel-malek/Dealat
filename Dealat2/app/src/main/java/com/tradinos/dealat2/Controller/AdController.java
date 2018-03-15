package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.PhotoMultipartRequest;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Parser.Parser.Ad.AdDetailsParser;
import com.tradinos.dealat2.Parser.Parser.Ad.AdListParser;
import com.tradinos.dealat2.Parser.Parser.StringParser;
import com.tradinos.dealat2.Parser.Parser.TemplatesDataParser;

import org.json.JSONArray;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 19.02.18.
 */

public class AdController extends ParentController {
    public AdController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public AdController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static AdController getInstance(Controller controller){
        return new AdController(controller);
    }

    public void uploadImage(File image, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "item_images_upload").getURL(getmContext());
        PhotoMultipartRequest request = new PhotoMultipartRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addFileUpload(image);

        addToHeader(request);
        request.Call();
    }

    public void deleteImage(JSONArray jsonArray, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "delete_images").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("images", jsonArray.toString());

        addToHeader(request);
        request.Call();
    }

    public void submitAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "post_new_item").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void editAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "edit").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getTemplatesData(SuccessCallback<TemplatesData> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_data_lists").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new TemplatesDataParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        request.Call();
    }

    public void getCategoryAds(String categoryId, SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_items_by_main_category").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        request.addParameter("category_id", categoryId);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getAdDetails(String adId, int templateId, SuccessCallback<Ad> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_item_details").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdDetailsParser(), successCallback,getmFaildCallback());

        request.addParameter("ad_id", adId);
        request.addParameter("template_id", String.valueOf(templateId));

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void search(HashMap<String, String> parameters, SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.ads, "search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        request.Call();
    }

    public void setAsFavorite(String adId, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "set_as_favorite").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("ad_id", adId);
        authenticationRequired(request);

        addToHeader(request);
        request.Call();
    }


    public void removeFromFavorite(String adId, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "remove_from_favorite").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("ad_id", adId);
        authenticationRequired(request);

        addToHeader(request);
        request.Call();
    }
}
