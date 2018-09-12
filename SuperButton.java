package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by 20smcnamara on 4/5/18.
 */

public class SuperButton implements Button {

    private ArrayList<basicButton> buttons = new ArrayList<>();
    private int buttonIndex;

    public SuperButton(ArrayList<String> strs, ArrayList<Integer> ints,Rect rect){
        Constants.scaleRectTwo(rect);
        Rect temp = new Rect();
        temp.set(0,0,10,10);
        for(int i = 0; i < strs.size() && i < ints.size(); i++){
            buttons.add(new basicButton(temp,strs.get(i),ints.get(i)));
            buttons.get(i).setRectangle(rect);
        }
        buttonIndex = 0;
    }

    @Override
    public void draw(Canvas canvas, int color, int color2){
        buttons.get(buttonIndex).draw(canvas,color,color2);
    }

    public void update(int index){
        buttonIndex = index;
    }

    @Override
    public int recieveTouch(MotionEvent event) {
        return buttons.get(buttonIndex).recieveTouch(event);
    }
}
