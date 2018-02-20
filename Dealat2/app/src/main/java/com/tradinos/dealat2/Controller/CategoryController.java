package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Parser.Parser.Category.CategoryListParser;

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

        request.Call();
    }
}
