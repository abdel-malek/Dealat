package com.tradinos.dealat2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MyAdAdapter;
import com.tradinos.dealat2.Adapter.StatusAdapter;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.AdDetailsActivity;
import com.tradinos.dealat2.View.MasterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class MyAdsFragment extends Fragment {

    private List<Ad> ads = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    TextView layoutEmpty;
    ListView listView;


    public static MyAdsFragment newInstance(List<Ad> ads) {
        MyAdsFragment fragment = new MyAdsFragment();

        fragment.setAds(ads);

        return fragment;
    }

    public void setAds(List<Ad> ads) {
        this.ads.clear();
        this.ads.addAll(ads);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_ads, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        listView = rootView.findViewById(R.id.listView);
        final AppCompatSpinner spinnerStatus = rootView.findViewById(R.id.spinner);
        spinnerStatus.setAdapter(new StatusAdapter(getContext()));

        if (ads.isEmpty())
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            listView.setAdapter(new MyAdAdapter(getContext(), ads));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                Ad ad = (Ad) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), AdDetailsActivity.class);
                intent.putExtra("ad", ad);
                getContext().startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                MasterActivity activity = (MasterActivity) getActivity();

                UserController.getInstance(activity.getController()).getMyAds(new SuccessCallback<List<Ad>>() {
                    @Override
                    public void OnSuccess(List<Ad> result) {
                        refreshLayout.setRefreshing(false);

                        if (result.isEmpty())
                            layoutEmpty.setVisibility(View.VISIBLE);
                        else
                            layoutEmpty.setVisibility(View.GONE);

                        setAds(result);
                        listView.setAdapter(new MyAdAdapter(getContext(), ads));
                        spinnerStatus.setSelection(0);
                    }
                });
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<Ad> result = filter(i);

                if (result.isEmpty())
                    layoutEmpty.setVisibility(View.VISIBLE);
                else
                    layoutEmpty.setVisibility(View.GONE);

                listView.setAdapter(new MyAdAdapter(getContext(), result));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    private List<Ad> filter(int status) {

        if (status == 0) // All
            return ads;
        else {
            List<Ad> result = new ArrayList<>();

            for (int i = 0; i < ads.size(); i++) {
                if (ads.get(i).getStatus() == status)
                    result.add(ads.get(i));
            }
            return result;
        }
    }
}
