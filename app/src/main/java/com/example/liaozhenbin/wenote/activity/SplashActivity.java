package com.example.liaozhenbin.wenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.liaozhenbin.wenote.R;

public class SplashActivity extends BaseActivity {
    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_main);

        initActionBar();


        splash = (ImageView) findViewById(R.id.splash_image);

        //透明动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(2500);

//        //旋转动画
//        RotateAnimation rotate = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5F,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5F);
//        rotate.setDuration(1000);
//
//        //缩放动画
//        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5F,
//                ScaleAnimation.RELATIVE_TO_SELF, 0.5F);
//        scale.setDuration(1000);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(2500);
        animationSet.addAnimation(alpha);
//        animationSet.addAnimation(rotate);
//        animationSet.addAnimation(scale);

        final Long start = System.currentTimeMillis();
        splash.startAnimation(animationSet);
        final Long end = System.currentTimeMillis();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if ((end - start) < 3000) {
                    try {
                        Thread.sleep(3000 - (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        }).start();
    }
}