package com.dealat.View;

import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dealat.Adapter.MainCatAdapter;
import com.dealat.Controller.CategoryController;
import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Controller.ParentController;
import com.dealat.Model.Category;
import com.dealat.Model.GroupedResponse;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.SplashActivity;
import com.dealat.Utils.CustomAlertDialog;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;

import java.util.HashMap;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

/**
 * Created by developer on 19.02.18.
 */

public class HomeActivity extends DrawerActivity {

    private Category mainCategory;
    public static final String FIRST_LOGIN  = "first_login";

    //views
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);


        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setRemindInterval(4) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(HomeActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    @Override
    public void getData() {

        mainCategory = Category.getMain(getString(R.string.allCategories));

       /* if (!isNetworkAvailable()) { //if you un-comment this ... please check messages (we have a connection error alert here in home activity)
            refreshLayout.setRefreshing(false);
            return;
        }*/

        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        CategoryController.getInstance(new ParentController(getmContext(), new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message, String data) {
                refreshLayout.setRefreshing(false);

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
                } else if (errorCode == Code.NetworkError || errorCode == Code.TimeOutError || errorCode==Code.ParsingError)
                    showConnectionErrorDialog(getString(R.string.connection_error));
                else {
                    //network or server error ... inforce retry again dialog to ensure that we have categories in all sections in the app
                    showConnectionErrorDialog(Message);
                }
            }
        })).getAllCategories(new SuccessCallback<GroupedResponse>() {
            @Override
            public void OnSuccess(GroupedResponse result) {

                HideProgressDialog();
                if (findViewById(R.id.refreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.refreshLayout)).setRefreshing(false);

                result.getCategories().add(mainCategory);
                ((MyApplication) getApplication()).setAllCategories(result.getCategories());

                mainCategory.setSubCategories(((MyApplication) getApplication()).getSubCatsById("0"));


                listView.setAdapter(new MainCatAdapter(mContext, mainCategory.getSubCategories()));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext, SubCategoriesActivity.class);

                        Category category = ((MainCatAdapter) listView.getAdapter()).getItem(i);
                        intent.putExtra("category", category);
                        //  intent.putExtra("category", mainCategory.getSubCategories().get(i));
                        intent.putExtra("action", SubCategoriesActivity.ACTION_VIEW);
                        startActivity(intent);
                    }
                });

                getCommercialAds(result.getCommercialAds());

                if(MyApplication.isFirstLogin()){
                    final CustomAlertDialog dialog = new CustomAlertDialog(mContext, MyApplication.getWelcomeMessage());
                    dialog.setOneButtonDialog(true);
                    dialog.setOneButtonButtonText(getString(R.string.dismiss));
                    dialog.show();

                    MyApplication.setFirstLogin(false);

                    dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void showConnectionErrorDialog(String message) {
        CustomAlertDialog dialog = new CustomAlertDialog(mContext, message);
        dialog.setOneButtonDialog(true);

        try {
            dialog.show();
        }catch (WindowManager.BadTokenException e){}

        dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }

    @Override
    public void showData() {
    }

    @Override
    public void assignUIReferences() {
        listView = findViewById(R.id.listView);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.searchView);
    }

    @Override
    public void assignActions() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("query", query);

                Intent intent = new Intent(mContext, ViewAdsActivity.class);

                intent.putExtra("action", ViewAdsActivity.ACTION_SEARCH);
                intent.putExtra("category", mainCategory);
                intent.putExtra("parameters", parameters);

                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue) {

            if (registered()) {
                Intent intent = new Intent(mContext, SubmitAdActivity.class);
                intent.putExtra("category", mainCategory);

                startActivity(intent);
            }
        } else if (view.getId() == R.id.buttonFilter) {
            Intent intent = new Intent(mContext, FilterActivity.class);
            intent.putExtra("category", mainCategory);
            intent.putExtra("action", 1);
            startActivity(intent);
        }
    }
}
