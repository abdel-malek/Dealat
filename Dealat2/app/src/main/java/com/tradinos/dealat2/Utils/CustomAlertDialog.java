package com.tradinos.dealat2.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tradinos.dealat2.R;

public class CustomAlertDialog extends Dialog {

    String alertText;
    Button buttonTrue;

    public CustomAlertDialog(@NonNull Context context, String alertText) {
        super(context);
        this.alertText = alertText;
    }

    public Button getButtonTrue() {
        return buttonTrue;
    }

    public void setExtraText(String extraText) { // call it after show
        TextView textView = findViewById(R.id.text);
        textView.setText(extraText);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_custom_alert);

        //setCancelable(false);
        TextView textView = findViewById(R.id.textView);
        Button buttonFalse = findViewById(R.id.buttonFalse);
        buttonTrue = findViewById(R.id.buttonTrue);

        textView.setText(alertText);

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }
}
