package com.dealat.View;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Date;

import static com.dealat.View.DrawerActivity.NEXT_ITEM_DELAY;
import static com.dealat.View.DrawerActivity.START_SLIDER_DELAY;

public class ViewPagerPauseAble extends ViewPager {
    private ViewPagerInteractions viewPagerInteractions;
    private Date touchDownSliderDate;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;


    private Handler handler = new Handler();
    private Runnable removeAutoScroll =  new Runnable() {
        public void run() {
            ViewPagerPauseAble.this.viewPagerInteractions.onActionDown();
            Log.d("SAED", "run: I did remove the automation");
        }
    };

    public ViewPagerPauseAble(Context context) {
        super(context);
    }

    public ViewPagerPauseAble(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPagerInteractions(ViewPagerInteractions viewPagerInteractions) {
        this.viewPagerInteractions = viewPagerInteractions;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if(this.viewPagerInteractions == null) {
            return super.onInterceptTouchEvent(motionEvent);
        }

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                handler.postDelayed(removeAutoScroll,200);
                this.touchDownSliderDate = new Date();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                float deltaX = x2 - x1;


                if(this.touchDownSliderDate == null || new Date().getTime() - this.touchDownSliderDate.getTime() > 200) {
                    this.viewPagerInteractions.onActionUp(START_SLIDER_DELAY);
                }
                else{
                    this.handler.removeCallbacks(removeAutoScroll);
                }


                // check if this is considered normal click, and not swipe.
                if((this.touchDownSliderDate == null || new Date().getTime() - this.touchDownSliderDate.getTime() < 200) && (Math.abs(deltaX) <= MIN_DISTANCE )) {
                    viewPagerInteractions.onItemClicked();
                }

                // if swipe call the super function
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Reset the next item runnable.
                    this.viewPagerInteractions.onActionDown();
                    this.viewPagerInteractions.onActionUp(NEXT_ITEM_DELAY);

                    // Check if swipe to left or right.
                    if(x1 > x2) {
                        if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
                            this.setCurrentItem(0,true);
                        } else {
                            this.setCurrentItem(this.getCurrentItem() + 1, true);
                        }
                    } else {
                        this.setCurrentItem(this.getCurrentItem() - 1,true);
                    }
                }

        }
        return false;
    }


    interface ViewPagerInteractions{
        void onItemClicked();
        void onActionDown();
        void onActionUp(int delay);
    }

}

