package com.dealat.Controller;

import com.dealat.Model.GroupedResponse;
import com.dealat.Parser.AdsResponseParser;
import com.dealat.Parser.Parser.Bookmark.BookmarkListParser;
import com.dealat.Parser.Parser.Item.ItemListParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.PhotoMultipartRequest;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.Ad;
import com.dealat.Model.Bookmark;
import com.dealat.Model.Item;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.Parser.Parser.Ad.AdListParser;
import com.dealat.Parser.Parser.StringParser;
import com.dealat.Parser.Parser.UserParser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by developer on 12.03.18.
 */

public class UserController extends ParentController {

    private UserController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static UserController getInstance(Controller controller) {
        return new UserController(controller);
    }

    public void getCities(SuccessCallback<List<Item>> successCallback) {
        String url = new URLBuilder(APIModel.users, "get_countries").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new ItemListParser("city_id"), successCallback, getmFaildCallback());

        addToHeader(request);
        request.Call();
    }

    public void registerUser(HashMap<String, String> parameters, SuccessCallback<User> successCallback) {
        String url = new URLBuilder(APIModel.users, "register").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new UserParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        request.Call();
    }

    public void verifyUser(HashMap<String, String> parameters, SuccessCallback<User> successCallback) {
        String url = new URLBuilder(APIModel.users, "verify").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new UserParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        request.Call();
    }

    public void saveUserToken(String token, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "save_user_token").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("token", token);
        request.addParameter("os", "1");
        request.addParameter("lang", MyApplication.getLocale().toString());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void logOut(String token, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "logout").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("token", token);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getUserInfo(SuccessCallback<User> successCallback) {
        String url = new URLBuilder(APIModel.users, "get_my_info").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new UserParser(), successCallback, getmFaildCallback());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void editUserInfo(File image, HashMap<String, String> parameters, SuccessCallback<User> successCallback) {
        String url = new URLBuilder(APIModel.users, "edit_user_info").getURL(getmContext());
        PhotoMultipartRequest request = new PhotoMultipartRequest(getmContext(), url, RequestMethod.Post, new UserParser(), successCallback, getmFaildCallback());

        if (image != null)
            request.addFileUpload("personal_image", image);

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addStringUpload(entry.getKey(), entry.getValue());

        addToHeader(request);
        authenticationRequired(request);

        request.Call();
    }

    public void deactivateAccount(SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "delete_my_account").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void updateLanguage(String token, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "update_lang").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("token", token);
        request.addParameter("lang", MyApplication.getLocale().toString());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public TradinosRequest getMyAds(int status, int pageNum, int pageSize, SuccessCallback<List<Ad>> successCallback) {
        String url = new URLBuilder(APIModel.users, "get_my_items").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdListParser(), successCallback, getmFaildCallback());

        request.addParameter("status", String.valueOf(status));
        request.addParameter("page_num", String.valueOf(pageNum));
        request.addParameter("page_size", String.valueOf(pageSize));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();

        return request;
    }

    public void getMyFavorites(int pageNum, int pageSize, SuccessCallback<List<Ad>> successCallback) {
        String url = new URLBuilder(APIModel.users, "get_my_favorites").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdListParser(), successCallback, getmFaildCallback());

        request.addParameter("page_num", String.valueOf(pageNum));
        request.addParameter("page_size", String.valueOf(pageSize));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void getBookmarks(int pageNum, int pageSize, SuccessCallback<List<Bookmark>> successCallback) {
        String url = new URLBuilder(APIModel.users, "get_my_bookmarks").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new BookmarkListParser(), successCallback, getmFaildCallback());

        request.addParameter("page_num", String.valueOf(pageNum));
        request.addParameter("page_size", String.valueOf(pageSize));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void bookmarkSearch(HashMap<String, String> parameters, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "mark_search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void deleteBookmark(String id, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "delete_bookmark").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("user_bookmark_id", id);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void getBookmarkAds(int pageNum, int pageSize, String id, SuccessCallback<GroupedResponse> successCallback) {
        String url = new URLBuilder(APIModel.ads, "get_bookmark_search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AdsResponseParser(), successCallback, getmFaildCallback());

        request.addParameter("user_bookmark_id", id);
        request.addParameter("page_num", String.valueOf(pageNum));
        request.addParameter("page_size", String.valueOf(pageSize));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void sendQrCode(String generatedCode, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.qrUsers, "QR_code_scan").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("gen_code", generatedCode);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }
    public void deleteChat(String chatId, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "delete_chat").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());
        request.addParameter("chat_id",chatId);
        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void rateSeller(String sellerId, double rate, SuccessCallback<String> successCallback) {
        String url = new URLBuilder(APIModel.users, "rate_seller").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Post, new StringParser(), successCallback, getmFaildCallback());

        request.addParameter("seller_id", sellerId);
        request.addParameter("rate", String.valueOf(rate));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }
}
