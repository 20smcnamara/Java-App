package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/5/18.
 */

public class ChangingButton implements Button {

    private basicButton[] buttons = new basicButton[2];
    private int buttonIndex;

    public ChangingButton(Rect rect, String str, String str2, int val, int val2){
        Constants.scaleRectTwo(rect);
        Rect temp = new Rect();
        temp.set(0,0,10,10);
        basicButton buttonOne = new basicButton(temp, str, val);
        basicButton buttonTwo = new basicButton(temp, str2, val2);
        buttons[0] = buttonOne;
        buttons[1] = buttonTwo;
        buttons[0].setRectangle(rect);
        buttons[1].setRectangle(rect);
        buttonIndex = 0;
    }

    @Override
    public void draw(Canvas canvas, int color, int color2){
        buttons[buttonIndex].draw(canvas,color,color2);
    }

    public void update(int index){
        buttonIndex = index;
    }

    @Override
    public int recieveTouch(MotionEvent event) {
        return buttons[buttonIndex].recieveTouch(event);
    }
}
