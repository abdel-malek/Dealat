package com.tradinos.dealat2.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Controller.ChatController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Fragment.ChatsFragment;
import com.tradinos.dealat2.Fragment.FavoritesFragment;
import com.tradinos.dealat2.Fragment.MyAdsFragment;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.Model.User;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends MasterActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private List<Ad> myAdsList, myFavsList;
    private List<Chat> chats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_profile);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        final int page = getIntent().getIntExtra("page", 0);

        ShowProgressDialog();
        UserController.getInstance(mController).getMyAds(new SuccessCallback<List<Ad>>() {
            @Override
            public void OnSuccess(List<Ad> result) {
                myAdsList = result;

                UserController.getInstance(mController).getMyFavorites(new SuccessCallback<List<Ad>>() {
                    @Override
                    public void OnSuccess(List<Ad> result) {

                        myFavsList = result;

                        ChatController.getInstance(mController).getChats(new SuccessCallback<List<Chat>>() {
                            @Override
                            public void OnSuccess(List<Chat> result) {
                                chats = result;

                                HideProgressDialog();

                                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                                // Set up the ViewPager with the sections adapter.
                                mViewPager = (ViewPager) findViewById(R.id.viewpager);
                                mViewPager.setAdapter(mSectionsPagerAdapter);

                                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                                tabLayout.setupWithViewPager(mViewPager);

                                mViewPager.setCurrentItem(page);


                                User user = new CurrentAndroidUser(mContext).Get();
                                if (user != null && !TextUtils.isEmpty(user.getImageUrl())){
                                    ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                                    mImageLoader.get(MyApplication.getBaseUrlForImages() + user.getImageUrl(),
                                            ImageLoader.getImageListener(((ImageView)findViewById(R.id.imageView)),
                                            R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {

    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue){
            Intent intent = new Intent(mContext, EditProfileActivity.class);
            startActivity(intent);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch (position){
               case 0:
                   return MyAdsFragment.newInstance(myAdsList);
               case 1:
                   return FavoritesFragment.newInstance(myFavsList);
               case 2:
                   return ChatsFragment.newInstance(chats);
           }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tabMyAds);
                case 1:
                    return getString(R.string.tabFav);
                case 2:
                    return getString(R.string.tabChats);
            }
            return null;
        }
    }
}
