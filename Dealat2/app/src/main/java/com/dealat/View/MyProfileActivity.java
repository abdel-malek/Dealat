package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.dealat.Controller.CurrentAndroidUser;
import com.dealat.Fragment.FavoritesFragment;
import com.dealat.Fragment.MyAdsFragment;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.dealat.Controller.ChatController;
import com.dealat.Controller.UserController;
import com.dealat.Fragment.ChatsFragment;
import com.dealat.Model.Ad;
import com.dealat.Model.Chat;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;

import java.util.List;

public class MyProfileActivity extends MasterActivity {

    private final int REQUEST_EDIT_PROFILE = 1;

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
                                mViewPager = findViewById(R.id.viewpager);
                                mViewPager.setAdapter(mSectionsPagerAdapter);

                                TabLayout tabLayout = findViewById(R.id.tab_layout);
                                tabLayout.setupWithViewPager(mViewPager);

                                mViewPager.setCurrentItem(page);


                                User user = new CurrentAndroidUser(mContext).Get();
                                if (user != null && !TextUtils.isEmpty(user.getImageUrl())) {
                                    ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                                    mImageLoader.get(MyApplication.getBaseUrl() + user.getImageUrl(),
                                            ImageLoader.getImageListener(((ImageView) findViewById(R.id.imageView)),
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
        if (view.getId() == R.id.buttonTrue) {
            Intent intent = new Intent(mContext, EditProfileActivity.class);
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT_PROFILE) {
            User user = new CurrentAndroidUser(mContext).Get();
            if (user != null && !TextUtils.isEmpty(user.getImageUrl())) {
                ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                mImageLoader.get(MyApplication.getBaseUrl() + user.getImageUrl(),
                        ImageLoader.getImageListener(((ImageView) findViewById(R.id.imageView)),
                                R.drawable.ic_person_48dp, R.drawable.ic_person_48dp));
            }
        }
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.main_content), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });

        snackbar.show();
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
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
