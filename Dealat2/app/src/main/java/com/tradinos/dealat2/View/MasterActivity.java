package com.tradinos.dealat2.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Item;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.SplashActivity;
import com.tradinos.dealat2.Utils.CustomProgressDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

        Tracker tracker = ((MyApplication)getApplication()).getDefaultTracker();
        tracker.setScreenName(this.getClass().getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        defineController();
        assignUIReferences();
        getData();

        assignActions();
        showData();
    }

    @Override
    public void setContentView(int viewRes) {
        mContext = this;

        Configuration conf = getResources().getConfiguration();

        if (MyApplication.getLocale() == null) { // if there's a language assigned before take it// or take device language
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                MyApplication.setLocale(conf.getLocales().get(0));
            else
                MyApplication.setLocale(conf.locale);
        }

        conf.setLocale(MyApplication.getLocale());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Locale.setDefault(conf.getLocales().get(0));
        else
            Locale.setDefault(conf.locale);

        mContext.getResources().updateConfiguration(conf,
                getResources().getDisplayMetrics());

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
                if (findViewById(R.id.refreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.refreshLayout)).setRefreshing(false);

                HideProgressDialog();

                // if user was logged and then chose to register from ONE other device
                // we need to log them out
                if (errorCode == Code.AuthenticationError) {
                    showMessageInToast(getString(R.string.toastRegister));

                    MyApplication.saveUserState(User.NOT_REGISTERED);
                    new CurrentAndroidUser(mContext).clearUser();

                    Intent intent = new Intent(mContext, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError)
                    showSnackBar(Message);
                else {
                    if (findViewById(R.id.parentPanel) != null)
                        Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void showSnackBar(String message) {
        showMessageInToast(message);
    }

    public Controller getController() {
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
        if (progressDialog != null) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {

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

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            return true;

        showSnackBar(getString(R.string.connection_problem));
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public int getTemplateDefaultImage(int template) {

        switch (template) {
            case Category.VEHICLES:
                return R.drawable.car_copy;

            case Category.PROPERTIES:
                return R.drawable.home;

            case Category.MOBILES:
                return R.drawable.smartphone_call;

            case Category.ELECTRONICS:
                return R.drawable.camera;

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

    public String formattedNumber(int number) {
        return NumberFormat.getInstance(Locale.ENGLISH).format(number);
    }

    public String formattedNumber(double number) {
        return NumberFormat.getInstance(Locale.ENGLISH).format(number);
    }

    protected boolean inputIsEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString()))
            return true;
        return false;
    }

    protected String stringInput(EditText editText) {
        return String.valueOf(editText.getText());
    }

    protected double doubleEditText(EditText editText) {
        // need to assign Local
        // so in US, comma is treated as grouping (thousand) separator
        // and dot is treated as decimal separator

        try {
            return NumberFormat.getInstance(Locale.US).parse(editText.getText().toString()).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected boolean registered() {
        switch (MyApplication.getUserState()) {
            case User.REGISTERED:
                return true;
            case User.PENDING:
                showMessageInToast(getString(R.string.toastVerify));
                break;
            default:
                showMessageInToast(getString(R.string.toastRegister));
        }
        return false;
    }

    protected int getItemIndex(List<Item> items, String id) {
        if (items == null)
            return 0;

        for (int i = 0; i < items.size(); i++)
            if (items.get(i).getId().equals(id))
                return i;
        return 0;
    }

    public String formattedDate(String stringDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dateWithYearFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        DateFormat timeInstance = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH); //time without seconds


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();


        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date yesterday = calendar.getTime();

        try {
            calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(stringDate));
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date currentDate = calendar.getTime();

            if (currentDate.equals(today))
                return getString(R.string.today) + " " + timeInstance.format(dateFormat.parse(stringDate));
            else if (currentDate.equals(yesterday))
                return getString(R.string.yesterday) + " " + timeInstance.format(dateFormat.parse(stringDate));
            else
                return dateWithYearFormat.format(currentDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getExpiryTime(String stringDate, int days) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dateWithYearFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(stringDate));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date expiryDate = calendar.getTime();

            return dateWithYearFormat.format(expiryDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
