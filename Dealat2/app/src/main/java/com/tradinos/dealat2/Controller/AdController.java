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
        String url = new URLBuilder(APIModel.ads, "ad_images_upload").getURL(getmContext());
        PhotoMultipartRequest request = new PhotoMultipartRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addFileUpload(image);

        request.Call();
    }

    public void deleteImage(JSONArray jsonArray, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "delete_images").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("images", jsonArray.toString());

        request.Call();
    }

    public void submitAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "post_new_ad").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

       // authenticationRequired(request);
        request.Call();
    }

    public void getTemplatesData(SuccessCallback<TemplatesData> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_data_lists").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new TemplatesDataParser(), successCallback,getmFaildCallback());

        request.Call();
    }

    public void getCategoryAds(String categoryId, SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_ads_by_main_category").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        request.addParameter("category_id", categoryId);

        request.Call();
    }
}
