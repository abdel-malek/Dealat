package com.tradinos.dealat2.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tradinos.dealat2.Fragment.ImageDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 29.03.18.
 */

public class ImageDetailsAdapter extends FragmentPagerAdapter {
    private List<String> paths = new ArrayList<>();
    private int templateId;

    public ImageDetailsAdapter(FragmentManager fm, List<String> paths, int templateId) {
        super(fm);
        this.paths = paths;
        this.templateId = templateId;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageDetailsFragment.newInstance(paths.get(position), templateId);
    }

    @Override
    public int getCount() {
        return this.paths.size();
    }
}
