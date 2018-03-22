package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MessageAdapter;
import com.tradinos.dealat2.Controller.ChatController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Model.Message;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 13.03.18.
 */

public class ChatActivity extends MasterActivity {

    private User user;
    private Chat currentChat;

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
    public void getData() {
        user = new CurrentAndroidUser(mContext).Get();
        currentChat = (Chat) getIntent().getSerializableExtra("chat");

        String key, value;

        if (currentChat.getChatId() == null) {
            key = "ad_id";
            value = currentChat.getAdId();
        } else {
            key = "chat_session_id";
            value = currentChat.getChatId();
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
                    buttonSend.setVisibility(View.GONE);
                else
                    buttonSend.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextMsg.getText().toString().isEmpty()) {
                    final Message message = new Message();
                    message.setText(stringInput(editTextMsg));

                    ChatController.getInstance(mController).sendMessage(currentChat.getAdId(), stringInput(editTextMsg), new SuccessCallback<String>() {
                        @Override
                        public void OnSuccess(String result) {
                            showMessageInToast("sent");
                            editTextMsg.setText("");
                            buttonSend.setVisibility(View.GONE);

                            adapter.addMessage(message);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    private boolean amISeller() {
        if (user != null){
            if (user.getId().equals(currentChat.getSellerId()))
                return true;
        }
        return false;
    }
}
