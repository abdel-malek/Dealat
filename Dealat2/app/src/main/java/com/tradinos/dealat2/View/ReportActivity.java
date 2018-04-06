package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.RadioAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.R;

import java.util.List;

/**
 * Created by developer on 04.04.18.
 */

public class ReportActivity extends MasterActivity {

    private Ad currentAd;

    private RadioAdapter adapter;
    //views
    private ListView listView;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        ShowProgressDialog();
        AdController.getInstance(mController).getReportList(new SuccessCallback<List<Item>>() {
            @Override
            public void OnSuccess(List<Item> result) {
                HideProgressDialog();

                adapter = new RadioAdapter(mContext, result);
                listView.setAdapter(adapter);
                findViewById(R.id.buttonTrue).setEnabled(true);
            }
        });
    }

    @Override
    public void showData() {
        textViewTitle.setText(currentAd.getTitle());
    }

    @Override
    public void assignUIReferences() {
        listView = (ListView) findViewById(R.id.listView);
        textViewTitle = (TextView) findViewById(R.id.title);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonTrue){

            if (adapter.getSelected() == null){
                showMessageInToast(R.string.toastSelectReport);
            }
            else {
                ShowProgressDialog();
                AdController.getInstance(mController).reportAd(currentAd.getId(), adapter.getSelected().getId(), new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        HideProgressDialog();
                        showMessageInToast(R.string.toastAdReported);
                        finish();
                    }
                });
            }
        }
    }
}
