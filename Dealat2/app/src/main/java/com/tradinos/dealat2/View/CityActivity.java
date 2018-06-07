package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.RadioAdapter;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 12.03.18.
 */

public class CityActivity extends MasterActivity {

    private RadioAdapter adapter;

    // views
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_city);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        if (isNetworkAvailable()){
            if (!swipeRefreshLayout.isRefreshing())
                ShowProgressDialog();

            UserController.getInstance(mController).getCities(new SuccessCallback<List<Item>>() {
                @Override
                public void OnSuccess(List<Item> result) {
                    HideProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);

                    adapter = new RadioAdapter(mContext, result);
                    listView.setAdapter(adapter);

                    findViewById(R.id.buttonTrue).setEnabled(true);
                }
            });
        }
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        listView = findViewById(R.id.listView);
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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue){
            if (adapter.getSelected() == null)
                showMessageInToast(R.string.labelPleaseSelectCity);
            else {
                Intent mainIntent = new Intent(mContext, RegisterActivity.class);

                MyApplication.saveUserState(User.LOCATED);
                MyApplication.saveCity(adapter.getSelected().getId());

                startActivity(mainIntent);
                finish();
            }
        }
    }


    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.parentPanel), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });

        snackbar.show();
    }
}
