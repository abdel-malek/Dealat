package com.dealat.Controller;

import android.content.Context;

import com.dealat.API.APIModel;
import com.dealat.API.URLBuilder;
import com.dealat.Model.Chat;
import com.dealat.Model.Message;
import com.dealat.Parser.Parser.Chat.ChatListParser;
import com.dealat.Parser.Parser.Chat.MessageListParser;
import com.dealat.Parser.Parser.Chat.MessagePaser;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void getChats(int pageNum, int pageSize, SuccessCallback<List<Chat>> successCallback){
        String url = new URLBuilder(APIModel.users, "get_my_chat_sessions").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get, new ChatListParser(), successCallback,getmFaildCallback());

        request.addParameter("page_num", String.valueOf(pageNum));
        request.addParameter("page_size", String.valueOf(pageSize));

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

    public void sendMessage(HashMap<String, String> parameters, SuccessCallback<Message> successCallback){
        String url = new URLBuilder(APIModel.users, "send_msg").getURL(getmContext());
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post, new MessagePaser(), successCallback,getmFaildCallback());

        for (Map.Entry<String, String> entry : parameters.entrySet())
            request.addParameter(entry.getKey(), entry.getValue());

        authenticationRequired(request);
        addToHeader(request);
        request.Call();
    }
}
