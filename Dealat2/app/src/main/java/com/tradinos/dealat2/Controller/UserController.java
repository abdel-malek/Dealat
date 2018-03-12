package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Parser.Parser.Item.ItemListParser;
import com.tradinos.dealat2.Parser.Parser.StringParser;

import java.util.List;


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

        request.Call();
    }

    public void registerUser(SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "register").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());



        request.Call();
    }

    public void verifyUser(SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "verify").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.Call();
    }

    public void saveUserToken(SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "save_user_token").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());



        request.Call();
    }

    public void getUserInfo(SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_info").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new StringParser(), successCallback,getmFaildCallback());



        request.Call();
    }

    public void getMyAds(){

    }

    public void getMyFavorites(){

    }

}
