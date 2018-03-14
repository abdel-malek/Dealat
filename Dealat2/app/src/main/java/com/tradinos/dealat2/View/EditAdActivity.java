package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.view.View;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.HorizontalAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.TemplatesData;
import com.tradinos.dealat2.Model.Type;
import com.tradinos.dealat2.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class EditAdActivity extends MasterActivity {

    private Ad currentAd;
    private HashMap<Integer, List<Type>> brands = new HashMap<>();

    private HashMap<String, String> parameters = new HashMap<>();

    private HorizontalAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_ad);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {


                AdController.getInstance(mController).getTemplatesData(new SuccessCallback<TemplatesData>() {
                    @Override
                    public void OnSuccess(TemplatesData result) {
                        HideProgressDialog();
                    }
                });
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {

    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {

    }
}
