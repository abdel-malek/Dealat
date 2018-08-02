package com.dealat.Controller;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.GroupedResponse;
import com.dealat.Parser.CategoriesResponseParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

/**
 * Created by developer on 19.02.18.
 */

public class CategoryController extends ParentController {

    public CategoryController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static CategoryController getInstance(Controller controller){
        return new CategoryController(controller);
    }

    public void getAllCategories(SuccessCallback<GroupedResponse> successCallback){
        String url = new URLBuilder(APIModel.categories, "get_all").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new CategoriesResponseParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        request.Call();
    }
}
