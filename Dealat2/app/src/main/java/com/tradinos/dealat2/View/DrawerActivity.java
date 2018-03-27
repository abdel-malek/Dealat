package com.tradinos.dealat2.View;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.CommercialAdapter;
import com.tradinos.dealat2.Controller.CommercialAdsController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Model.CommercialAd;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public abstract class DrawerActivity extends MasterActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button buttonRegister = navigationView.getHeaderView(0).findViewById(R.id.buttonRegister);
        TextView textViewName = navigationView.getHeaderView(0).findViewById(R.id.textName);
        TextView textViewCity = navigationView.getHeaderView(0).findViewById(R.id.textCity);
        Menu menu = navigationView.getMenu();

        switch (MyApplication.getUserState()) {
            case User.PENDING:
                buttonRegister.setText(getString(R.string.buttonVerify));

                buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, VerificationActivity.class);
                        startActivity(intent);
                    }
                });
                break;

            case User.REGISTERED:
                menu.findItem(R.id.nav_MyAds).setVisible(true);
                menu.findItem(R.id.nav_MyProfile).setVisible(true);
                menu.findItem(R.id.nav_savedSearches).setVisible(true);
                buttonRegister.setVisibility(View.GONE);

                User user = new CurrentAndroidUser(this).Get();
                if (user != null) {
                    navigationView.getHeaderView(0).findViewById(R.id.containerUser).setVisibility(View.VISIBLE);
                    textViewName.setText(user.getName());
                    //   textViewCity.setText(user.getCityName());
                }

                break;

            default:
                buttonRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        startActivity(intent);
                    }
                });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                if (!(mContext instanceof HomeActivity)) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                break;

            case R.id.nav_MyProfile:
            case R.id.nav_MyAds:
                Intent intent = new Intent(mContext, MyProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_categories:
                break;

            case R.id.nav_savedSearches:
                Intent intent1 = new Intent(mContext, BookmarksActivity.class);
                startActivity(intent1);
                break;

            case R.id.navAr:
                setLocale("ar");
                break;

            case R.id.navEn:
                setLocale("en");
                break;

            case R.id.navSettings:
                break;

            case R.id.navHelp:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void getCommercialAds(String categoryId) {
        final ViewPager commercialPager = (ViewPager) findViewById(R.id.viewpager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        CommercialAdsController.getInstance(mController).getCommercialAds(categoryId, new SuccessCallback<List<CommercialAd>>() {
            @Override
            public void OnSuccess(final List<CommercialAd> result) {
                HideProgressDialog();
                if (findViewById(R.id.refreshLayout) != null)
                    ((SwipeRefreshLayout) findViewById(R.id.refreshLayout)).setRefreshing(false);

                CommercialAdapter commercialAdapter = new CommercialAdapter(getSupportFragmentManager(), result);
                commercialPager.setAdapter(commercialAdapter);

                tabLayout.setupWithViewPager(commercialPager);

                final Handler handler = new Handler();
                final Runnable update = new Runnable() {
                    public void run() {
                        if (currentPage == result.size()) {
                            currentPage = 0;
                        }
                        commercialPager.setCurrentItem(currentPage++, true);
                    }
                };

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, 100, 5000);
            }
        });
    }

    private void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Configuration conf = getResources().getConfiguration();
        conf.setLocale(myLocale);
        MyApplication.setLocale(myLocale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Locale.setDefault(conf.getLocales().get(0));
        else
            Locale.setDefault(conf.locale);

        mContext.getResources().updateConfiguration(conf,
                getResources().getDisplayMetrics());

        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();
    }
}
