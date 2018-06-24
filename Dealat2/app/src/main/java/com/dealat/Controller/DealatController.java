package com.dealat.Controller;

import android.content.Context;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.About;
import com.dealat.Parser.Parser.AboutParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

/**
 * Created by developer on 19.04.18.
 */

public class DealatController extends ParentController {
    public DealatController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public DealatController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static DealatController getInstance(Controller controller) {
        return new DealatController(controller);
    }

    public void getAbout(SuccessCallback<About> successCallback){
        String url = new URLBuilder(APIModel.data, "get_about_info").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new AboutParser(), successCallback, getmFaildCallback());

        addToHeader(request);
        request.Call();
    }

    public void getTerms(){

    }

}
