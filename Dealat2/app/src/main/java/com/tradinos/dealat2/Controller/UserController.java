package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Bookmark;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.Parser.Parser.Ad.AdListParser;
import com.tradinos.dealat2.Parser.Parser.Bookmark.BookmarkListParser;
import com.tradinos.dealat2.Parser.Parser.Item.ItemListParser;
import com.tradinos.dealat2.Parser.Parser.StringParser;
import com.tradinos.dealat2.Parser.Parser.UserParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by developer on 12.03.18.
 */

public class UserController extends ParentController {
    public UserController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public UserController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static UserController getInstance(Controller controller){
        return new UserController(controller);
    }

    public void getCities(SuccessCallback<List<Item>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_countries").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new ItemListParser("city_id"), successCallback,getmFaildCallback());

        //addToHeader(request);
        request.Call();
    }

    public void registerUser(HashMap<String, String> parameters, SuccessCallback<User> successCallback){
        String url = new URLBuilder(APIModel.users, "register").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new UserParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        request.Call();
    }

    public void verifyUser(HashMap<String, String> parameters, SuccessCallback<User> successCallback){
        String url = new URLBuilder(APIModel.users, "verify").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new UserParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        request.Call();
    }

    public void saveUserToken(String token, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "save_user_token").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("token", token);
        request.addParameter("os", "1");
        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getUserInfo(SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_info").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new StringParser(), successCallback,getmFaildCallback());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void editUserInfo(HashMap<String, String> parameters, SuccessCallback<User> successCallback){
        String url = new URLBuilder(APIModel.users, "edit_user_info").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new UserParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getMyAds(SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_items").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void getMyFavorites(SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_favorites").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void getBookmarks(SuccessCallback<List<Bookmark>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_bookmarks").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new BookmarkListParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void bookmarkSearch(HashMap<String, String> parameters, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "mark_search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void deleteBookmark(String id, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "delete_bookmark").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("user_bookmark_id", id);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void getBookmarkAds(String id, SuccessCallback<List<Ad>> successCallback){
        String url = new URLBuilder(APIModel.ads, "get_bookmark_search").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new AdListParser(), successCallback,getmFaildCallback());

        request.addParameter("user_bookmark_id", id);

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }

    public void rateSeller(String sellerId, double rate, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "rate_seller").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("seller_id", sellerId);
        request.addParameter("rate", String.valueOf(rate));

        addToHeader(request);
        authenticationRequired(request);
        request.Call();
    }
}
