package com.tradinos.dealat2.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tradinos.dealat2.Fragment.ImageDetailsFragment;
import com.tradinos.dealat2.Fragment.VideoFragment;

import java.util.List;

/**
 * Created by developer on 29.03.18.
 */

public class ImageDetailsAdapter extends FragmentPagerAdapter {
    private List<String> paths;
    private int templateId;
    private String videoPath;

    public ImageDetailsAdapter(FragmentManager fm, List<String> paths, String videoPath, int templateId) {
        super(fm);
        this.paths = paths;
        this.templateId = templateId;
        this.videoPath = videoPath;
    }

    @Override
    public Fragment getItem(int position) {
        if (videoPath != null && position == 0)
            return VideoFragment.newInstance(videoPath);
        return ImageDetailsFragment.newInstance(paths.get(position), templateId);
    }

    @Override
    public int getCount() {
        return this.paths.size(); // no need to check for videoPath to increase count +1 because a gap empty string
        // was added to imagesPaths
        //and in getItem(int position), when paths.get(position) position will not go out of bound
    }
}
