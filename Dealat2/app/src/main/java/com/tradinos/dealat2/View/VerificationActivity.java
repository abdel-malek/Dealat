package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tradinos.dealat2.R;

/**
 * Created by developer on 12.03.18.
 */

public class VerificationActivity extends MasterActivity {

    private EditText editTextCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
    }

    @Override
    public void getData() {

    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        editTextCode = (EditText) findViewById(R.id.edit_query);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonFalse){

        }
        else if (view.getId() == R.id.buttonTrue){

        }
    }
}
