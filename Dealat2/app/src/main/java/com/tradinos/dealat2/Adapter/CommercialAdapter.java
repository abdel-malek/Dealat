package com.tradinos.dealat2.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tradinos.dealat2.Fragment.CommercialAdFragment;
import com.tradinos.dealat2.Model.CommercialAd;

import java.util.List;

/**
 * Created by developer on 19.02.18.
 */

public class CommercialAdapter extends FragmentPagerAdapter {

    private List<CommercialAd> commercialAds;

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
