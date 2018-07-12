package com.dealat.Controller;

import android.net.Uri;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.Ad;
import com.dealat.Model.Item;
import com.dealat.Model.TemplatesData;
import com.dealat.Parser.Parser.Ad.AdDetailsParser;
import com.dealat.Parser.Parser.Ad.AdListParser;
import com.dealat.Parser.Parser.Item.ItemListParser;
import com.dealat.Parser.Parser.StringParser;
import com.dealat.Parser.Parser.TemplatesDataParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.PhotoMultipartRequest;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import org.json.JSONArray;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 19.02.18.
 */

public class AdController extends ParentController {

    private AdController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static AdController getInstance(Controller controller) {
        return new AdController(controller);
    }

    public void uploadImage(File image, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "item_images_upload").getURL(getmContext());
        PhotoMultipartRequest request = new PhotoMultipartRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addFileUpload("image", image);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void deleteImage(JSONArray jsonArray, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "delete_images").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("images", jsonArray.toString());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void uploadVideo(File video, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "item_video_upload").getURL(getmContext());
        PhotoMultipartRequest request = new PhotoMultipartRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addFileUpload("video", video);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void submitAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "post_new_item").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void editAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "edit").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void changeStatus(String adId, int status, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "change_status").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("ad_id", adId);
        request.addParameter("status", String.valueOf(status));

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getTemplatesData(SuccessCallback<TemplatesData> successCallback) {
        String url = new URLBuilder(APIModel.ads, "get_data_lists").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new TemplatesDataParser(), successCallback, getmFaildCallback());

        addToHeader(request);
        request.Call();
    }

    public void getCategoryAds(String categoryId, SuccessCallback<List<Ad>> successCallback) {
        String url = new URLBuilder(APIModel.ads, "get_items_by_main_category").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdListParser(), successCallback, getmFaildCallback());

        request.addParameter("category_id", categoryId);

        // authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getAdDetails(String adId, int templateId, SuccessCallback<Ad> successCallback) {
        String url = new URLBuilder(APIModel.ads, "get_item_details").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdDetailsParser(), successCallback, getmFaildCallback());

        request.addParameter("ad_id", adId);
        request.addParameter("template_id", String.valueOf(templateId));

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void search(HashMap<String, String> parameters, SuccessCallback<List<Ad>> successCallback) {
        String url = new URLBuilder(APIModel.ads, "search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdListParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            request.addParameter(entry.getKey(), Uri.encode(entry.getValue(), "UTF-8"));
        }

        addToHeader(request);
        request.Call();
    }

    public void setAsFavorite(String adId, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "set_as_favorite").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("ad_id", adId);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }


    public void removeFromFavorite(String adId, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "remove_from_favorite").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("ad_id", adId);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getReportList(SuccessCallback<List<Item>> successCallback) {
        String url = new URLBuilder(APIModel.ads, "get_report_messages").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get,
                new ItemListParser("report_message_id", "msg"), successCallback, getmFaildCallback());

        addToHeader(request);
        request.Call();
    }

    public void reportAd(String adId, String reportId, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.ads, "report_item").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post,
                new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("ad_id", adId);
        request.addParameter("report_message_id", reportId);

        addToHeader(request);
        request.Call();
    }
}
