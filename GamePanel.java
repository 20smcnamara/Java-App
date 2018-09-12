package com.example.a20smcnamara.whynotwork;

import android.content.Context;
import android.graphics.Canvas;
//import android.support.annotation.MainThread;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 20smcnamara on 3/19/18.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private SceneManager manager;

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        Constants.setUp();
        thread = new MainThread(getHolder(),this);
        manager = new SceneManager();
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int hieght){
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event){
        manager.recieveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void update(){
        manager.update();
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        manager.draw(canvas);
    }

    public void setScene(int scene){
        manager.setScene(scene);
    }
}
