package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 3/26/18.
 */

public interface Scene {

    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void update(AnimationManager animationManager);
    public int recieveTouch(MotionEvent event);
}
