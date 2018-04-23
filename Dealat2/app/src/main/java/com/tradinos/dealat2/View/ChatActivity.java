package com.tradinos.dealat2.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by developer on 13.03.18.
 */

public class ChatActivity extends MasterActivity {

    // to show or hide buttonSend
    private final int SHOW = 1, HIDE = 2;

    private ChatReceiver receiver;
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
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.dealat.MSG");

        receiver = new ChatReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
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

        if (amISeller())
            ((TextView) findViewById(R.id.textName)).setText(currentChat.getUserName());
        else
            ((TextView) findViewById(R.id.textName)).setText(currentChat.getSellerName());
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

        // this handy, when editTextMsg is focused and keypad is opened, the very last messages will be pushed up
        // and not covered with editTextMsg (more precisely container2
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (adapter != null)
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

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
                if (!inputIsEmpty(editTextMsg)) {

                    Message message = new Message();
                    message.setText(stringInput(editTextMsg));

                    // HH gives 24hours but hh give 12hours // so it's formatted back to 12hours in MessageAdapter
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    message.setCreatedAt(dateFormat.format(new Date()));

                    if (amISeller())
                        message.setToSeller(false);
                    else
                        message.setToSeller(true);

                    if (adapter == null)
                        adapter = new MessageAdapter(mContext, new ArrayList<Message>(), amISeller());

                    adapter.addMessage(message);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                    HashMap<String, String> parameters = new HashMap<>();

                    parameters.put("ad_id", currentChat.getAdId());
                    parameters.put("msg", message.getText());
                    if (amISeller())
                        parameters.put("chat_session_id", currentChat.getChatId());


                    editTextMsg.setText("");
                    showButton(HIDE);

                    new SendMessage(parameters, adapter.getItemCount() - 1).execute(message.getText());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    public class ChatReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Message();
            message.setText(intent.getStringExtra("msg"));

            // HH gives 24hours but hh give 12hours // so it's formatted back to 12hours in MessageAdapter
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            message.setCreatedAt(dateFormat.format(new Date()));

            if (amISeller())
                message.setToSeller(true);

            if (adapter == null)
                adapter = new MessageAdapter(mContext, new ArrayList<Message>(), amISeller());

            adapter.addMessage(message);
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    class SendMessage extends AsyncTask<String, Void, Message> {

        int position;
        HashMap<String, String> parameters;

        SendMessage(HashMap<String, String> parameters, int position) {
            this.position = position;
            this.parameters = parameters;
        }

        @Override
        protected Message doInBackground(String... strings) {

            ChatController.getInstance(mController).sendMessage(parameters, new SuccessCallback<Message>() {
                @Override
                public void OnSuccess(Message result) {
                    adapter.getItem(position).setSent(true);
                    adapter.notifyDataSetChanged();
                }
            });

            return null;
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
                TranslateAnimation animation = new TranslateAnimation(0, buttonSend.getWidth(), 0, 0);
                animation.setDuration(200);
                buttonSend.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        buttonSend.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        } else {
            if (visibility == SHOW)
                buttonSend.setVisibility(View.VISIBLE);
            else
                buttonSend.setVisibility(View.GONE);
        }
    }
}
