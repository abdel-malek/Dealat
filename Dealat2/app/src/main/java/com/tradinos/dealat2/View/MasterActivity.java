package com.tradinos.dealat2.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.Category;
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
                if(findViewById(R.id.refreshLayout) != null)
                    ((SwipeRefreshLayout)findViewById(R.id.refreshLayout)).setRefreshing(false);


                // if user was logged and then chose to register from ONE other device
                // we need to log them out
                if (Message.equals("Not authorized")){
                    MyApplication.saveUserState(User.NOT_REGISTERED);
                    new CurrentAndroidUser(mContext).clearUser();

                    Intent intent = new Intent(mContext, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Message = getString(R.string.toastRegister);
                }
                
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

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            return true;

        showMessageInSnackbar(R.string.connection_problem);
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


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

    public String formattedNumber(int number){
        return NumberFormat.getInstance(Locale.ENGLISH).format(number);
    }

    public String formattedNumber(double number){
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

    protected double doubleEditText(EditText editText) throws ParseException {
        // need to assign Local
        // so in US, comma is treated as grouping (thousand) separator
        // and dot is treated as decimal separator

        return NumberFormat.getInstance(Locale.US).parse(editText.getText().toString()).doubleValue();
    }

    protected boolean registered(){
        switch (MyApplication.getUserState()){
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

    public String formattedDate(String stringDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dateWithoutYearFormat = new SimpleDateFormat("dd-MM");
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
                return getString(R.string.today)+" "+timeInstance.format(dateFormat.parse(stringDate));
            else if (currentDate.equals(yesterday))
                return getString(R.string.yesterday)+" "+timeInstance.format(dateFormat.parse(stringDate));
            else
                return dateWithoutYearFormat.format(currentDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
