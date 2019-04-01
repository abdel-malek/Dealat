package com.dealat.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Adapter.CommercialAdapter;
import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Controller.UserController;
import com.dealat.Fragment.CommercialAdFragment;
import com.dealat.Model.CommercialAd;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.SplashActivity;
import com.dealat.Utils.CustomAlertDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class DrawerActivity extends MasterActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int currentPage = 0;
    protected Handler handler = new Handler();
    protected Runnable update;


    CommercialAdapter commercialAdapter;
    //views
    ViewPagerPauseAble commercialPager;
    Menu menu;
    public static final int START_SLIDER_DELAY = 100;
    public static final int NEXT_ITEM_DELAY = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView imageViewUser = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Button buttonRegister = navigationView.getHeaderView(0).findViewById(R.id.buttonRegister);
        TextView textViewName = navigationView.getHeaderView(0).findViewById(R.id.textName);
        //TextView textViewCity = navigationView.getHeaderView(0).findViewById(R.id.textCity);
        menu = navigationView.getMenu();

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
                menu.findItem(R.id.nav_Fav).setVisible(true);
                menu.findItem(R.id.nav_Chats).setVisible(true);
                menu.findItem(R.id.nav_savedSearches).setVisible(true);
                menu.findItem(R.id.navLogout).setVisible(true);

                // if device has camera
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
                    menu.findItem(R.id.nav_qrCode).setVisible(true);

                buttonRegister.setVisibility(View.GONE);
                imageViewUser.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_person_48dp));


                User user = new CurrentAndroidUser(this).Get();
                if (user != null) {
                    navigationView.getHeaderView(0).findViewById(R.id.containerUser).setVisibility(View.VISIBLE);
                    textViewName.setText(user.getName());
                    //   textViewCity.setText(user.getCityName());

                    if (!TextUtils.isEmpty(user.getImageUrl())) {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();

                        // you may comment FacebookSdk.sdkInitialize(this); in onCreate
                        //   mImageLoader.get(user.getImageUrl(), ImageLoader.getImageListener(imageViewUser,
                        //                   R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));

                        mImageLoader.get(MyApplication.getBaseUrl() + user.getImageUrl(), ImageLoader.getImageListener(imageViewUser,
                                R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
                    }

                    imageViewUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, MyProfileActivity.class);
                            intent.putExtra("page", 0);
                            startActivity(intent);
                        }
                    });
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

        commercialPager = findViewById(R.id.viewpager);
        update = new Runnable() {
            public void run() {
                if (DrawerActivity.this.commercialPager.getCurrentItem() == commercialAdapter.getCount() - 1) {
                    currentPage = 0;
                    commercialPager.setCurrentItem(0,true);
                }
                else{
                    commercialPager.setCurrentItem(DrawerActivity.this.commercialPager.getCurrentItem() + 1, true);
                }
                currentPage++;
                handler.postDelayed(this, NEXT_ITEM_DELAY);
            }
        };

        int height = getResources().getDisplayMetrics().widthPixels / 3;
        commercialPager.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MyApplication.getUserState() == User.REGISTERED) {
            User user = new CurrentAndroidUser(this).Get();
            if (user != null) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                TextView textViewName = navigationView.getHeaderView(0).findViewById(R.id.textName);
                ImageView imageViewUser = navigationView.getHeaderView(0).findViewById(R.id.imageView);

                textViewName.setText(user.getName());

                if (!TextUtils.isEmpty(user.getImageUrl())) {
                    ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();

                    mImageLoader.get(MyApplication.getBaseUrl() + user.getImageUrl(), ImageLoader.getImageListener(imageViewUser,
                            R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        Intent intent;
        switch (id) {
            case R.id.nav_home:
                if (!(mContext instanceof HomeActivity)) {
                    intent = new Intent(mContext, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.share_app:
                Uri appURL = Uri.parse("http://www.deal-at.com");
                intent = new Intent(Intent.ACTION_VIEW, appURL);
                startActivity(intent);
                break;

            case R.id.nav_MyAds:
                intent = new Intent(mContext, MyProfileActivity.class);
                intent.putExtra("page", 0);
                startActivity(intent);
                break;

            case R.id.nav_Fav:
                intent = new Intent(mContext, MyProfileActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                break;

            case R.id.nav_Chats:
                intent = new Intent(mContext, MyProfileActivity.class);
                intent.putExtra("page", 2);
                startActivity(intent);
                break;

            case R.id.nav_savedSearches:
                intent = new Intent(mContext, BookmarksActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_qrCode:
                intent = new Intent(mContext, QRCodeActivity.class);
                startActivity(intent);
                break;

            case R.id.navAr:
                setLocale("ar");
                break;

            case R.id.navEn:
                setLocale("en");
                break;

            case R.id.navLogout:
                logout();

              /*  MyApplication.saveUserState(User.NOT_REGISTERED);
                new CurrentAndroidUser(mContext).clearUser();
                LoginManager.getInstance().logOut();*/

                break;


            case R.id.navSettings:
                break;

            case R.id.navAbout:
                intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.navHelp:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void getCommercialAds(List<CommercialAd> commercialAds) {
        handler.removeCallbacks(update);

        commercialAdapter = new CommercialAdapter(getSupportFragmentManager(), commercialAds);
        commercialPager.setAdapter(commercialAdapter);
        commercialPager.setViewPagerInteractions(new ViewPagerPauseAble.ViewPagerInteractions() {
            @Override
            public void onItemClicked() {
                commercialAdapter.getCurrentFragment().onClicked();
            }

            @Override
            public void onActionDown() {
                handler.removeCallbacks(update);
            }

            @Override
            public void onActionUp(int delay ) {
                handler.postDelayed(update, delay);
            }
        });
        handler.postDelayed(update,START_SLIDER_DELAY);
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

        if (MyApplication.getUserState() == User.REGISTERED) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            UserController.getInstance(mController).updateLanguage(refreshedToken, new SuccessCallback<String>() {
                @Override
                public void OnSuccess(String result) {
                    Intent refresh = new Intent(mContext, HomeActivity.class);
                    startActivity(refresh);
                    finish();
                }
            });
        } else {
            Intent refresh = new Intent(mContext, HomeActivity.class);
            startActivity(refresh);
            finish();
        }
    }

    protected void logout() {
        CustomAlertDialog dialog = new CustomAlertDialog(mContext, getString(R.string.logout_messg));
        dialog.show();

        dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                UserController.getInstance(mController).logOut(refreshedToken, new SuccessCallback<String>() {
                    @Override
                    public void OnSuccess(String result) {
                        MyApplication.saveUserState(User.NOT_REGISTERED);
                        new CurrentAndroidUser(mContext).clearUser();

                        Intent intent = new Intent(mContext, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
