package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.DealatController;
import com.tradinos.dealat2.Model.About;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 24.04.18.
 */

public class TermsActivity extends MasterActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_terms);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        ShowProgressDialog();
        DealatController.getInstance(mController).getAbout(new SuccessCallback<About>() {
            @Override
            public void OnSuccess(About result) {
                HideProgressDialog();
                ((TextView)findViewById(R.id.textView)).setText(result.getTerms());
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
