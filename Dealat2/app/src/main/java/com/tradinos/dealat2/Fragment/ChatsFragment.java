package com.tradinos.dealat2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.ChatAdapter;
import com.tradinos.dealat2.Controller.ChatController;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.ChatActivity;
import com.tradinos.dealat2.View.MasterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class ChatsFragment extends Fragment {

    private List<Chat> chats = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    TextView layoutEmpty;
    ListView listView;

    public static ChatsFragment newInstance(List<Chat> chats){
        ChatsFragment fragment = new ChatsFragment();
        fragment.setChats(chats);

        return fragment;
    }

    public void setChats(List<Chat> chats) {
        this.chats.clear();
        this.chats.addAll(chats);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        listView = rootView.findViewById(R.id.listView);

        if (chats.isEmpty())
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            listView.setAdapter(new ChatAdapter(getContext(), chats));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChatActivity.class);

                intent.putExtra("chat", chats.get(i));

                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                MasterActivity activity = (MasterActivity) getActivity();

                ChatController.getInstance(activity.getController()).getChats(new SuccessCallback<List<Chat>>() {
                    @Override
                    public void OnSuccess(List<Chat> result) {
                        refreshLayout.setRefreshing(false);

                        if (result.isEmpty())
                            layoutEmpty.setVisibility(View.VISIBLE);
                        else
                            layoutEmpty.setVisibility(View.GONE);

                        setChats(result);
                        listView.setAdapter(new ChatAdapter(getContext(), chats));
                    }
                });
            }
        });


        return rootView;
    }
}
