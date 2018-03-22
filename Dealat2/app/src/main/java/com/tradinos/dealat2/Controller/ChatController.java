package com.tradinos.dealat2.Controller;

import android.content.Context;

import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;
import com.tradinos.dealat2.API.APIModel;
import com.tradinos.dealat2.API.URLBuilder;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Model.Message;
import com.tradinos.dealat2.Parser.Parser.Chat.ChatListParser;
import com.tradinos.dealat2.Parser.Parser.Chat.MessageListParser;
import com.tradinos.dealat2.Parser.Parser.StringParser;

import java.util.List;

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

    public void getChats(SuccessCallback<List<Chat>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_chat_sessions").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new ChatListParser(), successCallback,getmFaildCallback());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void getChatMessages(String key, String id, SuccessCallback<List<Message>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_chat_messages").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new MessageListParser(), successCallback,getmFaildCallback());

        request.addParameter(key, id);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }

    public void sendMessage(String adId, String message, SuccessCallback<String> successCallback){
        String url = new URLBuilder(APIModel.users, "send_msg").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new StringParser(), successCallback,getmFaildCallback());

        request.addParameter("ad_id", adId);
        request.addParameter("msg", message);

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }
}
