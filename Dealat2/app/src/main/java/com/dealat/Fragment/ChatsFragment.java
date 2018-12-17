package com.dealat.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dealat.Adapter.ChatPagingAdapter;
import com.dealat.Controller.ChatController;
import com.dealat.Model.Chat;
import com.dealat.R;
import com.dealat.Utils.PaginationScrollListener;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.SuccessCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class ChatsFragment extends Fragment {

    public static final int VIEW_CHAT = 222;
    public final int PAGE_SIZE = 20;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1; // first is called in MyProfileActivity

    private List<Chat> chats = new ArrayList<>();
    private ChatPagingAdapter pagingAdapter;

    private SwipeRefreshLayout refreshLayout;
    private TextView layoutEmpty;
    private RecyclerView recyclerView;

    public static ChatsFragment newInstance(List<Chat> chats) {
        ChatsFragment fragment = new ChatsFragment();
        fragment.setChats(chats);

        return fragment;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        pagingAdapter = new ChatPagingAdapter(getContext(), this);
        pagingAdapter.addAll(chats);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pagingAdapter);

        if (pagingAdapter.getItemCount() == 0)
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            layoutEmpty.setVisibility(View.GONE);


        if (chats.size() < PAGE_SIZE) // first page is the last page too
            isLastPage = true;
        else
            pagingAdapter.addLoadingFooter();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                pagingAdapter.clear();
                chats.clear();
                currentPage = 1;
                isLastPage = false;

                getData();
            }
        });


        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one

                getData();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        return rootView;
    }

    private void getData() {
        MasterActivity activity = (MasterActivity) getActivity();

        ChatController.getInstance(activity.getController()).getChats(currentPage, PAGE_SIZE, new SuccessCallback<List<Chat>>() {
            @Override
            public void OnSuccess(List<Chat> result) {
                refreshLayout.setRefreshing(false);

                pagingAdapter.removeLoadingFooter();
                isLoading = false;

                pagingAdapter.addAll(result);

                if (pagingAdapter.getItemCount() == 0)
                    layoutEmpty.setVisibility(View.VISIBLE);
                else
                    layoutEmpty.setVisibility(View.GONE);


                if (result.size() < PAGE_SIZE)
                    isLastPage = true;
                else
                    pagingAdapter.addLoadingFooter();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == VIEW_CHAT) {
            pagingAdapter.removeChat((Chat) data.getSerializableExtra("chat"));
            if (pagingAdapter.getItemCount() == 0)
                layoutEmpty.setVisibility(View.VISIBLE);
            else
                layoutEmpty.setVisibility(View.GONE);

        }
//            Toast.makeText(getContext(), "finished", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
