package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.MultiPartStringRequest;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.Model.User;


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
}
