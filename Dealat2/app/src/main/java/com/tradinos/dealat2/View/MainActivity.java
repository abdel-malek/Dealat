package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tradinos.dealat2.Adapter.CommercialAdapter;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.CommercialAd;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MasterActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int currentPage =0;
    private List<Category> mainCategories;

    //views
    private ViewPager commercialPager;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public void getData() {
        final List<CommercialAd> commercialAds = new ArrayList<>();
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());
        commercialAds.add(new CommercialAd());


        CommercialAdapter commercialAdapter = new CommercialAdapter(getSupportFragmentManager(),commercialAds );
        commercialPager.setAdapter(commercialAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(commercialPager);
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        commercialPager = (ViewPager) findViewById(R.id.viewpager);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {

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

        switch (id){
            case R.id.nav_home:
                break;

            case R.id.nav_MyProfile:
                break;

            case R.id.nav_MyAds:
                break;

            case R.id.nav_categories:
                break;

            case R.id.nav_savedSearches:
                break;

            case R.id.navLang:
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

    @Override
    public void onClick(View view) {

    }
}
