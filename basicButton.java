package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/3/18.
 */

public class basicButton implements Button {
    private Rect rectangle;
    private String str;
    int returnValue;

    public basicButton(Rect rect, String str, int val){
        this.rectangle = rect;
        rectangle = Constants.scaleRectTwo(this.rectangle);
        this.str = str;
        returnValue = val;
    }

    @Override
    public void draw(Canvas canvas, int color,int color2){
        Constants.drawRectQuick(canvas, rectangle, color);
        Paint paint = new Paint();
        paint.setColor(color2);
        int size = (rectangle.width()-25)/str.length()*2;
        if(size > 100){
            size = 100;

        }
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(str, rectangle.left+rectangle.width()/2, rectangle.top+rectangle.height()/2+size/3, paint);
    }

    @Override
    public int recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect temp = new Rect();
                temp.set((int) event.getX(), (int) event.getY(), (int) event.getX() + 1, (int) event.getY() + 1);
                if (Rect.intersects(rectangle, temp)) {
                    return returnValue;
                }
        }
        return -123456789;
    }

    public void scale(){
        Constants.scaleRectTwo(rectangle);
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void update(int num) {}
}
