package com.dealat.Fragment;

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

import com.dealat.Adapter.AdPagingAdapter;
import com.dealat.Controller.UserController;
import com.dealat.Model.Ad;
import com.dealat.R;
import com.dealat.Utils.PaginationScrollListener;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.SuccessCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class FavoritesFragment extends Fragment {

    public final int PAGE_SIZE = 20;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1; // first is called in MyProfileActivity

    private List<Ad> favs = new ArrayList<>();
    private AdPagingAdapter pagingAdapter;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView layoutEmpty;


    public static FavoritesFragment newInstance(List<Ad> favs) {
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setFavs(favs);

        return fragment;
    }

    public void setFavs(List<Ad> favs) {
        this.favs = favs;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        pagingAdapter = new AdPagingAdapter(getContext(), R.layout.row_view1);
        pagingAdapter.addAll(favs);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pagingAdapter);


        if (pagingAdapter.getItemCount() == 0)
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            layoutEmpty.setVisibility(View.GONE);

        if (favs.size() < PAGE_SIZE) // first page is the last page too
            isLastPage = true;
        else
            pagingAdapter.addLoadingFooter();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                pagingAdapter.clear();
                favs.clear();

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

        UserController.getInstance(activity.getController()).getMyFavorites(currentPage, PAGE_SIZE, new SuccessCallback<List<Ad>>() {
            @Override
            public void OnSuccess(List<Ad> result) {
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
}
