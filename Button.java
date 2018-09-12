package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/6/18.
 */

public interface Button {

    void draw(Canvas canvas,int color,int color2);
    void update(int num);
    int recieveTouch(MotionEvent event);
}
