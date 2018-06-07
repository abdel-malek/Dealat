package com.tradinos.dealat2.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tradinos.dealat2.Fragment.AdImageFragment;

import java.util.List;

/**
 * Created by developer on 07.03.18.
 */

public class AdImagesAdapter extends FragmentPagerAdapter {

    private List<String> paths;
    private int templateId;

    public AdImagesAdapter(FragmentManager fm, List<String> paths, int templateId) {
        super(fm);
        this.paths = paths;
        this.templateId = templateId;
    }

    @Override
    public Fragment getItem(int position) {
        return AdImageFragment.newInstance(paths.get(position), templateId);
    }

    @Override
    public int getCount() {
        return this.paths.size(); // no need to check for videoPath to increase count +1 because a gap empty string
        // was added to imagesPaths
    }
}
