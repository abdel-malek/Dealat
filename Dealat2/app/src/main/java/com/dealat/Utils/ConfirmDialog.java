package com.dealat.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;

import com.dealat.R;

/**
 * Created by developer on 09.05.18.
 */

public class ConfirmDialog extends Dialog {

    Button buttonOk;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    public Button getButtonOk() {
        return buttonOk;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_confirm);

        setCancelable(false);

        buttonOk = findViewById(R.id.buttonTrue);
    }
}
