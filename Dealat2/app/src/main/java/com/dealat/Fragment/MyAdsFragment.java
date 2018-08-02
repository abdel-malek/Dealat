package com.dealat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.dealat.Adapter.MyAdPagingAdapter;
import com.dealat.Adapter.StatusAdapter;
import com.dealat.Controller.UserController;
import com.dealat.Model.Ad;
import com.dealat.R;
import com.dealat.Utils.PaginationScrollListener;
import com.dealat.View.MasterActivity;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class MyAdsFragment extends Fragment {

    public final int PAGE_SIZE = 20;

    private int initialized = 0;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1; // first is called in MyProfileActivity

    private List<Ad> ads = new ArrayList<>();
    private MyAdPagingAdapter pagingAdapter;

    private SwipeRefreshLayout refreshLayout;
    private TextView layoutEmpty;
    private RecyclerView recyclerView;
    private AppCompatSpinner spinnerStatus;


    private TradinosRequest request;

    public static MyAdsFragment newInstance(List<Ad> ads) {
        MyAdsFragment fragment = new MyAdsFragment();
        fragment.setAds(ads);

        return fragment;
    }

    public void setAds(List<Ad> ads) {
        this.ads.addAll(ads);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_ads, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        spinnerStatus = rootView.findViewById(R.id.spinner);
        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        pagingAdapter = new MyAdPagingAdapter(getContext());
        pagingAdapter.addAll(ads);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pagingAdapter);

        if (pagingAdapter.getItemCount() == 0)
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            layoutEmpty.setVisibility(View.GONE);

        if (ads.size() < PAGE_SIZE) // first page is the last page too
            isLastPage = true;
        else
            pagingAdapter.addLoadingFooter();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                ads.clear();
                pagingAdapter.clear();

                currentPage = 1;
                isLastPage = false;

                spinnerStatus.setSelection(0);

                getAds(0);
            }
        });

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one

                getAds(spinnerStatus.getSelectedItemPosition());
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

        spinnerStatus.setAdapter(new StatusAdapter(getContext()));

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (++initialized > 1) { // to avoid calling onItemSelected when first creating the fragment and initializing adapter
                    currentPage = 1;
                    isLastPage = false;

                    pagingAdapter.clear();
                    ads.clear();

                    refreshLayout.setRefreshing(true); // instead of a progress bar
                    getAds(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    private void getAds(int status) {
        MasterActivity activity = (MasterActivity) getActivity();

        request = UserController.getInstance(activity.getController()).getMyAds(status, currentPage, PAGE_SIZE, new SuccessCallback<List<Ad>>() {
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

    @Override
    public void onDestroyView() {

        if (request != null && !request.isCanceled())
            request.cancel();

        super.onDestroyView();
    }
}
