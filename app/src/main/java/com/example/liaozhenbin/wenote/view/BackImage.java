package com.example.liaozhenbin.wenote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.liaozhenbin.wenote.R;

/**
 * Created by liaozhenbin on 2016/4/6.
 */
public class BackImage extends LinearLayout {

    public BackImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.back_layout,this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}