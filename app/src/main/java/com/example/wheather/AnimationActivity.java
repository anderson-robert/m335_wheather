package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

public class AnimationActivity extends AppCompatActivity {

    private ImageView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        animationView = (ImageView) findViewById(R.id.animation_image);
        animationView.setImageResource(R.drawable.sprite_rain_animation);
        AnimationDrawable rainAnimation = (AnimationDrawable) animationView.getDrawable();
        rainAnimation.start();
        checkIfAnimationDone(rainAnimation);
    }

    private void checkIfAnimationDone(AnimationDrawable animationDrawable){
        final AnimationDrawable a = animationDrawable;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    checkIfAnimationDone(a);
                } else{
                    switchActivity();
                }
            }
        }, timeBetweenChecks);
    }

    //Method to start a new Activity
    public void switchActivity() {
        Intent intent = new Intent(this, ExternalTemperatureActivity.class);
        startActivity(intent);
    }
}