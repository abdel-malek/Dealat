package com.dealat.Controller;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.CommercialAd;
import com.dealat.Parser.Parser.CommercialAd.CommercialAdListParser;
import com.dealat.Parser.Parser.StringParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.util.List;

/**
 * Created by developer on 25.02.18.
 */

public class CommercialAdsController extends ParentController {

    public CommercialAdsController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static CommercialAdsController getInstance(Controller controller){
        return new CommercialAdsController(controller);
    }

    public void getCommercialAds(String categoryId, SuccessCallback<List<CommercialAd>> successCallback){
        String url = new URLBuilder(APIModel.commercialAds, "get_commercial_items").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new CommercialAdListParser(), successCallback,getmFaildCallback());

        request.addParameter("category_id", categoryId);

        addToHeader(request);
        request.Call();
    }

    public void registerClick(String commercialId, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.commercialAds, "increment_clicks").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("commercial_ad_id", commercialId);

        addToHeader(request);
        request.Call();
    }
}
