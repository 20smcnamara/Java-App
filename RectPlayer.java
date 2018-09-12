package com.example.a20smcnamara.whynotwork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
/**
 * Created by 20smcnamara on 3/20/18.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;

    private int x;
    private int y;

    private Bitmap skin;

    public Rect getRectangle() {
        return rectangle;
    }

    public RectPlayer() {
        this.rectangle = new Rect();
    }

    @Override
    public void draw(Canvas canvas) {
        rectangle.set(x-(int)(45*Constants.wwRatio),y-(int)(65*Constants.hhRatio),x+(int)(45*Constants.wwRatio),y+(int)(65*Constants.hhRatio));
        //Constants.scaleRectTwo(this.rectangle);
        canvas.drawBitmap(skin, null, rectangle, new Paint());
    }

    public void update(){
        System.out.println("Why tho?");
    }

    public void update(Bitmap bitmap) {
        skin = bitmap;
    }

    public void update(Point point,Bitmap bitmap) {
        skin = bitmap;
        x = point.x;
        y = point.y;
    }
}
