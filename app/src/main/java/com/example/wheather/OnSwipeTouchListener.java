package com.example.wheather;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int swipeThreshhold = 100;
        private static final int swipeVelocityThreshold = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            boolean result= false;
            try {
                float differenceX = event2.getX() - event1.getX();
                float differenceY = event2.getY() - event1.getY();

                if (Math.abs(differenceX) > Math.abs(differenceY)) {
                    if (Math.abs(differenceX) > swipeThreshhold && Math.abs(velocityX) > swipeVelocityThreshold) {
                        if (differenceX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                } else if (Math.abs(differenceY) > swipeThreshhold && Math.abs(velocityY) > swipeVelocityThreshold) {
                    if (differenceY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } return result;
        }
    }

    //Overwrite onSwipe methods in an Activity with a Listener
    public void onSwipeRight() {

    }

    public void onSwipeLeft() {

    }

    public void onSwipeBottom() {

    }

    public void onSwipeTop() {

    }

}
