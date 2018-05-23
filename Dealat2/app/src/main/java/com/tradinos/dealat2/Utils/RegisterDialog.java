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

public class RegisterDialog extends Dialog {

    TextView textView;
    Button buttonOk;

    public RegisterDialog(@NonNull Context context) {
        super(context);
    }

    public Button getButtonOk() {
        return buttonOk;
    }

    public void setText(String text){
        textView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_register);

        //setCancelable(false);
        textView = findViewById(R.id.textView);
        buttonOk = findViewById(R.id.buttonTrue);

        findViewById(R.id.buttonFalse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }
}
