package com.tradinos.dealat2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.Utils.VideoListener;

/**
 * Created by developer on 12.04.18.
 */

public class VideoFragment extends Fragment {
    private String videoPath;

    public static VideoFragment newInstance(String videoPath) {
        VideoFragment fragment = new VideoFragment();
        fragment.setVideoPath(videoPath);

        return fragment;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        VideoView videoView = rootView.findViewById(R.id.videoView);
        ImageButton imageButtonPlay = rootView.findViewById(R.id.buttonVideo);

        videoView.setVideoPath(MyApplication.getBaseUrl() + this.videoPath);

        VideoListener videoListener = new VideoListener(videoView, imageButtonPlay);
        videoView.setOnCompletionListener(videoListener);
        videoView.setOnTouchListener(videoListener);

        return rootView;
    }
}
