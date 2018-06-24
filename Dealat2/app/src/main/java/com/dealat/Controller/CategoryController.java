package com.dealat.Controller;

import android.content.Context;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.Category;
import com.dealat.Parser.Parser.Category.CategoryListParser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class CategoryController extends ParentController {
    public CategoryController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public CategoryController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static CategoryController getInstance(Controller controller){
        return new CategoryController(controller);
    }

    public void getAllCategories(SuccessCallback<List<Category>> successCallback){
        String url = new URLBuilder(APIModel.categories, "get_all").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new CategoryListParser(), successCallback,getmFaildCallback());

        addToHeader(request);
        request.Call();
    }
}
