package com.tradinos.dealat2.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MessageAdapter;
import com.tradinos.dealat2.Controller.ChatController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Model.Message;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 13.03.18.
 */

public class ChatActivity extends MasterActivity {

    // to show or hide buttonSend
    private final int SHOW = 1, HIDE = 2;

    private User user;
    private Chat currentChat;

    private HashMap<String, String> parameters = new HashMap<>();

    private MessageAdapter adapter;

    // views
    private RecyclerView recyclerView;
    private EditText editTextMsg;
    private ImageButton buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.dealat.MSG");

        ChatReceiver receiver = new ChatReceiver();
        registerReceiver(receiver, filter);
        super.onResume();
    }

    @Override
    public void getData() {
        user = new CurrentAndroidUser(mContext).Get();
        currentChat = (Chat) getIntent().getSerializableExtra("chat");

        String key, value;

        if (amISeller()) { // coming from MyProfile Activity
            key = "chat_session_id";
            value = currentChat.getChatId();
        } else { // coming from MyProfile Activity or AdDetails Activity
            key = "ad_id";
            value = currentChat.getAdId();
        }

        ShowProgressDialog();
        ChatController.getInstance(mController).getChatMessages(key, value, new SuccessCallback<List<Message>>() {
            @Override
            public void OnSuccess(List<Message> result) {
                HideProgressDialog();
                adapter = new MessageAdapter(mContext, result, amISeller());

                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void showData() {
        ((TextView) findViewById(R.id.title)).setText(currentChat.getAdTitle());
    }

    @Override
    public void assignUIReferences() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextMsg = (EditText) findViewById(R.id.edit_query);
        buttonSend = (ImageButton) findViewById(R.id.buttonTrue);
    }

    @Override
    public void assignActions() {
        editTextMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0)
                    showButton(HIDE);
                else
                    showButton(SHOW);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextMsg.getText().toString().isEmpty()) {

                    parameters.put("ad_id", currentChat.getAdId());
                    parameters.put("msg", stringInput(editTextMsg));

                    if (amISeller())
                        parameters.put("chat_session_id", currentChat.getChatId());

                    ChatController.getInstance(mController).sendMessage(parameters, new SuccessCallback<Message>() {
                        @Override
                        public void OnSuccess(Message result) {
                            editTextMsg.setText("");
                            showButton(HIDE);

                            if (adapter == null)
                                adapter = new MessageAdapter(mContext, new ArrayList<Message>(), amISeller());

                            adapter.addMessage(result);
                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    public class ChatReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Message();
            message.setText(intent.getStringExtra("msg"));

            if (amISeller())
                message.setToSeller(true);

            if (adapter == null)
                adapter = new MessageAdapter(mContext, new ArrayList<Message>(), amISeller());

            adapter.addMessage(message);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    private boolean amISeller() {
        if (user != null) {
            if (user.getId().equals(currentChat.getSellerId()))
                return true;
        }
        return false;
    }

    private void showButton(int visibility) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.container3);

            Slide slide;
            if (visibility == SHOW) {
                slide = new Slide(Gravity.LEFT);
                TransitionManager.beginDelayedTransition(viewGroup, slide);
                buttonSend.setVisibility(View.VISIBLE);
            } else { // Hide
                slide = new Slide(Gravity.LEFT);
                TransitionManager.beginDelayedTransition(viewGroup, slide);
                buttonSend.setVisibility(View.GONE);
            }
        } else {
            if (visibility == SHOW)
                buttonSend.setVisibility(View.VISIBLE);
            else
                buttonSend.setVisibility(View.GONE);
        }
    }
}
