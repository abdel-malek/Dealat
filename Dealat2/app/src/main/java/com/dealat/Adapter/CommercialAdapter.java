package com.dealat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.dealat.Fragment.CommercialAdFragment;
import com.dealat.Model.CommercialAd;

import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class CommercialAdapter extends FragmentPagerAdapter {

    private List<CommercialAd> commercialAds;
    private CommercialAdFragment mCurrentFragment;



    public CommercialAdFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((CommercialAdFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public CommercialAdapter(FragmentManager fm, List<CommercialAd> commercialAds) {
        super(fm);
        this.commercialAds = commercialAds;
    }

    @Override
    public Fragment getItem(int position) {
        return CommercialAdFragment.newInstance(commercialAds.get(position));
    }

    @Override
    public int getCount() {
        return commercialAds.size();
    }
}
