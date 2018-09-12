package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

/**
 * Created by 20smcnamara on 3/20/18.
 */

public class Obstacle implements  GameObject{

    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private int startX;
    private int playerGap;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top +=y;
        rectangle.bottom += y;
        rectangle2.bottom += y;
        rectangle2.top +=y;
    }

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap){
        if(startX < 25){
            startX = 25;
        }
        rectangle = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap,startY,Constants.SCREEN_WIDTH2,startY+rectHeight);
        this.color = color;
        this.startX = startX;
        this.playerGap = playerGap;
    }

    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2,player.getRectangle());
    }

    public int getObsHeight(){
        return rectangle.height();
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    public void update(){

    }
}
