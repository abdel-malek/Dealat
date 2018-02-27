package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.CustomProgressDialog;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by malek on 5/15/16.
 */
public abstract class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    protected int prossessThread = 0;

    Dialog progressDialog = null;

    protected Context mContext;
    protected Controller mController;
    int contentViewRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(contentViewRes);
        mContext = this; //assigned in setContentView

        defineController();
        assignUIReferences();
        getData();

        assignActions();
        showData();
    }

    @Override
    public void setContentView(int viewRes) {
        mContext = this;
      /*  Locale myLocale = new Locale("ar");
        Configuration conf = getResources().getConfiguration();
        conf.setLocale(myLocale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Locale.setDefault(conf.getLocales().get(0));
        else
            Locale.setDefault(conf.locale);

        mContext.getResources().updateConfiguration(conf,
                getResources().getDisplayMetrics());*/
        // Configuration conf = getResources().getConfiguration();

   /*    if(MyApplication.getLocale() == null) { // if there's a language assigned before take it// or take device language
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                MyApplication.setLocale(conf.getLocales().get(0));
            else
                MyApplication.setLocale(conf.locale);
        }
        else{
            Locale.setDefault(MyApplication.getLocale());
            conf.setLocale(MyApplication.getLocale());

          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                mContext.createConfigurationContext(conf);
            else{*/
   /*    conf.locale = MyApplication.getLocale();
        mContext.getResources().updateConfiguration(conf,
                getResources().getDisplayMetrics());
        // }
    }*/

        this.contentViewRes = viewRes;
    }

    public abstract void getData();

    public abstract void showData();

    @Override
    public void setTitle(CharSequence title) {
        showBackButton();
    }


    void showBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public abstract void assignUIReferences();

    public abstract void assignActions();


    public void defineController() {
        mController = new Controller(this, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message, String data) {
               // if(findViewById(R.id.refreshLayout) != null)
                  //  ((SwipeRefreshLayout)findViewById(R.id.refreshLayout)).setRefreshing(false);

                if (findViewById(R.id.parentPanel) != null)
                    Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                HideProgressDialog();
            }
        });
    }

    public Controller getController(){
        return mController;
    }

    public Context getmContext() {
        return mContext;
    }

    public void ShowProgressDialog() {
        prossessThread++;
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this, getResources().getString(R.string.wait), true);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void HideProgressDialog() {
        if (progressDialog!=null) {
            try {
                progressDialog.cancel();
            }catch (Exception e){

            }
        }
    }

    @Override
    protected void onPause() {
        //HideProgressDialog();
        super.onPause();
    }

    protected void showMessageInToast(String message) {
        Toast.makeText(getApplicationContext(), Html.fromHtml(message), Toast.LENGTH_SHORT).show();
    }

    public void showMessageInToast(int messageRes) {
        Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(String message) {
        if (findViewById(R.id.parentPanel) != null)
            Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(message), Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageInSnackbar(int messageRes) {
        if (findViewById(R.id.parentPanel) != null)
            Snackbar.make(findViewById(R.id.parentPanel), messageRes, Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), messageRes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

/*
    protected void logout(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                        UserController.getInstance(mController).Logout(refreshedToken, new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                CurrentAndroidUser user=new CurrentAndroidUser(MasterActivity.this);
                                user.SignOut();
                                Intent intent=new Intent(MasterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setMessage(getResources().getString(R.string.logout_messg)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }*/

   /* public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
     //   if(view != null)
     //       params.height += view.getMeasuredHeight()/5;
        listView.setLayoutParams(params);
    }*/

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public int getTemplateDefaultImage(int template){

        switch (template) {
            case Category.VEHICLES:
                 return R.drawable.car_copy;

            case Category.PROPERTIES:
                return R.drawable.home;

            case Category.MOBILES:
                return R.drawable.smartphone_call;

            case Category.ELECTRONICS:
                return R.drawable.photo_camera;

            case Category.FASHION:
                return R.drawable.female_black_dress;

            case Category.KIDS:
                return R.drawable.teddy_bear;

            case Category.SPORTS:
                return R.drawable.dumbbell;

            case Category.JOBS:
                return R.drawable.old_fashion_briefcase;

            case Category.INDUSTRIES:
                return R.drawable.industries;

            case Category.SERVICES:
                return R.drawable.services;

            default:
                return R.drawable.others;
        }
    }

    protected String formattedNumber(int number){
        return NumberFormat.getInstance(Locale.ENGLISH).format(number);
    }

    protected String formattedNumber(double number){
        return NumberFormat.getInstance(Locale.ENGLISH).format(number);
    }

    protected double doubleEditText(EditText editText) throws ParseException {
        // need to assign Local
        // so in US, comma is treated as grouping (thousand) separator
        // and dot is treated as decimal separator

        return NumberFormat.getInstance(Locale.US).parse(editText.getText().toString()).doubleValue();
    }
}
