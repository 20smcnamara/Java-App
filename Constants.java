package com.example.a20smcnamara.whynotwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

/**
 * Created by 20smcnamara on 3/20/18.
 */

public class Constants {
    public static int SCREEN_WIDTH = 1080;
    public static int SCREEN_HEIGHT = 1920;
    public static int SCREEN_WIDTH2;
    public static int SCREEN_HEIGHT2;
    public static boolean gameSceneIsScene = false;
    public static MultiColor multiColor = new MultiColor();
    public static double wwRatio;
    public static double hhRatio;
    public static int obsI;

    public static int color1;
    public static int color2;
    public static int color3;

    public static Context CURRENT_CONTEXT;

    public static int inc = 3;

    public static long INIT_TIME;

    public static int skinIndex = 0;

    public static boolean imUsingTiltControls;
    public static int coolBack = 0;

    public static final int BLUE = Color.rgb(100,100,255);
    public static final int GREEN = Color.rgb(100,255,100);
    public static final int RED = Color.rgb(255,100,100);

    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;

    public static void update(){
        if(coolBack == 0){
            ArrayList<int[]> x = multiColor.handler();
            color1 = Color.rgb(x.get(0)[0],x.get(0)[1],x.get(0)[2]);
            color2 = Color.rgb(x.get(1)[0],x.get(1)[1],x.get(1)[2]);
            //color3 = Color.rgb(x.get(2)[0],x.get(2)[1],x.get(2)[2]);
            color3 =color1;
        }
        if(coolBack == 1){
            color1 = BLUE;
            color2 = GREEN;
            color3 = BLUE;
        }
        if(coolBack == 2){
            color1 = RED;
            color2 = GREEN;
            color3 = RED;
        }
        if(coolBack == 3){
            color1 = GREEN;
            color2 = BLUE;
            color3 = GREEN;
        }
    }

    public static void setUp(){
        sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences("settings", 0);
        editor = sharedPref.edit();
    }

    public static void drawCenterText(Canvas canvas, Paint paint, String text, Rect rect) {
        Rect r = copyRect(rect);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    public static Rect copyRect(Rect rect){
        Rect rectangle = new Rect();
        if(!rect.isEmpty()){
            rectangle.set(rect.left,rect.top,rect.right,rect.bottom);
        }
        return rectangle;
    }

    public static Rect scaleRect(Rect rect, Bitmap frame) {
        float whRatio = (float) (frame.getWidth()) / frame.getHeight();
        if (rect.width() > rect.height())
            rect.left = rect.right - (int) (rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int) (rect.width() * (1 / whRatio));
        return rect;
    }

    public static void drawRectQuick(Canvas canvas, Rect rectangle,int color){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    public static void setRatios(){
        hhRatio = SCREEN_HEIGHT2/(double)1920;
        wwRatio = SCREEN_WIDTH2/(double)1080;
    }

    public static Rect scaleRectTwo(Rect rect) {
        int x = (int)(rect.centerX()*wwRatio);
        int y = (int)(rect.centerY()*hhRatio);
        int width = (int)(rect.width()*wwRatio/2);
        int height = (int)(rect.height()*hhRatio/2);
        rect.set((x-width),(y-height),(x+width),(y+height));
        return rect;
    }

    public static void drawBackground(Canvas canvas){
        canvas.drawColor(color1);
    }
}
