package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Parser.Parser.StringParser;

import java.util.HashMap;
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

    public void submitAd(HashMap<String, String> parameters, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.ads, "post_new_ad").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

       // authenticationRequired(request);
        request.Call();
    }


}
