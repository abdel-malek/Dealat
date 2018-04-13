package com.tradinos.dealat2.Utils;

import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

/**
 * Created by developer on 11.04.18.
 */

public class VideoListener implements View.OnTouchListener, MediaPlayer.OnCompletionListener {
    int position;
    VideoView videoView;
    ImageButton playImage;

    public VideoListener(VideoView videoView, ImageButton imageButton) {
        this.videoView = videoView;
        this.playImage = imageButton;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (videoView.isPlaying()) {
                videoView.pause();
                position = videoView.getCurrentPosition();
                playImage.setVisibility(View.VISIBLE);
            } else {
                playImage.setVisibility(View.INVISIBLE);
                videoView.seekTo(position);
                videoView.start();
            }
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        position = 1;
        playImage.setVisibility(View.VISIBLE);
    }
}
