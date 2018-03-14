package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.MultiPartStringRequest;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;


/**
 * Created by malek on 5/10/16.
 */
public class ParentController extends Controller {


    public ParentController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public void authenticationRequired (TradinosRequest request) {
        if(CurrentAndroidUser.getInstance(getmContext()).IsLogged()) {
            User user = CurrentAndroidUser.getInstance(getmContext()).Get();
            request.turnOnAuthentication(user.getPhone(), user.getServerKey());
        }
    }
    public void authenticationRequired (MultiPartStringRequest request) {
        if(CurrentAndroidUser.getInstance(getmContext()).IsLogged()) {
            User user = CurrentAndroidUser.getInstance(getmContext()).Get();
            request.turnOnAuthentication(user.getPhone(), user.getServerKey());
        }
    }

    protected void addToHeader(TradinosRequest request){
        try {
            request.getHeaders().put("city_id", MyApplication.getCity());
            request.getHeaders().put("lang", MyApplication.getLocale().toString());
            request.getHeaders().put("Api-call", "1");

        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
