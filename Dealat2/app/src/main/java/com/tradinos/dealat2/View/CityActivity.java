package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.ItemAdapter;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 12.03.18.
 */

public class CityActivity extends MasterActivity {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_city);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        if (!swipeRefreshLayout.isRefreshing())
            ShowProgressDialog();

        UserController.getInstance(mController).getCities(new SuccessCallback<List<Item>>() {
            @Override
            public void OnSuccess(List<Item> result) {
                HideProgressDialog();
                swipeRefreshLayout.setRefreshing(false);

                listView.setAdapter(new ItemAdapter(mContext, result));
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mainIntent = new Intent(mContext, LoginActivity.class);

                startActivity(mainIntent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
