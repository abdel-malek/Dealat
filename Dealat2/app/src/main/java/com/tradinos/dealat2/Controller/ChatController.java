package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;

/**
 * Created by developer on 12.03.18.
 */

public class ChatController extends ParentController{
    public ChatController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public ChatController(Controller controller){
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public static ChatController getInstance(Controller controller){
        return new ChatController(controller);
    }
}
