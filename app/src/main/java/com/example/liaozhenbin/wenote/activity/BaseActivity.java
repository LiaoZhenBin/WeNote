package com.example.liaozhenbin.wenote.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.example.liaozhenbin.wenote.R;

/**
 * Created by liaozhenbin on 2016/4/5.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
    }

    /**
     * init ActionBar
     */
    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(false);

        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.hide();

        int statusBarHeight = getResources().getDimensionPixelSize(
                getResources().getIdentifier("status_bar_height", "dimen", "android"));
        findViewById(R.id.bar_status).setMinimumHeight(statusBarHeight);
    }
}