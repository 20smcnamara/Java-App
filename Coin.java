package com.example.a20smcnamara.whynotwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by 20smcnamara on 4/11/18.
 */

public class Coin implements GameObject{
    private Rect rect;
    private Bitmap img;

    public Coin(int x,int y){
        rect = new Rect(x-(int)(Constants.wwRatio*100),y-(int)(Constants.hhRatio*100),x+(int)(Constants.wwRatio*100),y+(int)(Constants.hhRatio*100));
        BitmapFactory bf = new BitmapFactory();
        img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.coin);
    }

    public Rect getRect(){
        return rect;
    }

    @Override
    public void draw(Canvas canvas){
        rect = Constants.scaleRect(rect,img);
        canvas.drawBitmap(img,null,rect,new Paint());
    }

    public void update(float x){
        rect.top+=x;
        rect.bottom+=x;
    }

    public boolean playerCollide(RectPlayer player){
        if(player.getRectangle().intersect(rect)){
            return true;
        }
        return false;
    }
}
