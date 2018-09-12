package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/10/18.
 */

public class AdvancedButton implements Button{
    private int buttonIndex;

    public void update(int index){
        System.out.println("UP");
    }

    public void draw(Canvas canvas, int color, int color2){
        System.out.println("Not implemented DR");
    }

    public int recieveTouch(MotionEvent event) {
        System.out.println("Not implemented RE");
        return -123456789;
    }
}
