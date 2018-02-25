package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.CommercialAd;
import com.tradinos.dealat2.Parser.Parser.CommercialAd.CommercialAdListParser;

import java.util.List;

/**
 * Created by developer on 25.02.18.
 */

public class CommercialAdsController extends ParentController {
    public CommercialAdsController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public CommercialAdsController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static CommercialAdsController getInstance(Controller controller){
        return new CommercialAdsController(controller);
    }

    public void getCommercialAds(String categoryId, SuccessCallback<List<CommercialAd>> successCallback){
        String url = new URLBuilder(APIModel.commercialAds, "get_commercial_ads").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new CommercialAdListParser(), successCallback,getmFaildCallback());

        request.addParameter("category_id", categoryId);

        request.Call();
    }
}
