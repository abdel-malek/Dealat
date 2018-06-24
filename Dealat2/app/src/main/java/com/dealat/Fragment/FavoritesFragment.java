package com.dealat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.dealat.Adapter.AdAdapter;
import com.dealat.Controller.UserController;
import com.dealat.Model.Ad;
import com.dealat.R;
import com.dealat.View.MasterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class FavoritesFragment extends Fragment {

    private List<Ad> favs = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    TextView layoutEmpty;
    ListView listView;

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
        listView = rootView.findViewById(R.id.listView);

        if (favs.isEmpty())
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            listView.setAdapter(new AdAdapter(getContext(), favs, R.layout.row_view1));
        // startActivityForResult(); to refresh if ad was unfavorited

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                MasterActivity activity = (MasterActivity) getActivity();

                UserController.getInstance(activity.getController()).getMyFavorites(new SuccessCallback<List<Ad>>() {
                    @Override
                    public void OnSuccess(List<Ad> result) {
                        refreshLayout.setRefreshing(false);

                        if (result.isEmpty())
                            layoutEmpty.setVisibility(View.VISIBLE);
                        else
                            layoutEmpty.setVisibility(View.GONE);

                        setFavs(result);
                        listView.setAdapter(new AdAdapter(getContext(), result, R.layout.row_view1));
                    }
                });
            }
        });

        return rootView;
    }
}
