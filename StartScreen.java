package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/3/18.
 */

public class StartScreen extends AdvancedScene {
    private basicButton[] buttons = new basicButton[3];

    public StartScreen(){
        Rect temp = new Rect();
        int h =Constants.SCREEN_HEIGHT/2;
        temp.set(100,h-350,Constants.SCREEN_WIDTH-100,h-150);
        basicButton startButton = new basicButton(temp,"Start",0);
        buttons[0] = startButton;
        Rect temp2 = new Rect();
        temp2.set(100,h-100,Constants.SCREEN_WIDTH-100,h+100);
        basicButton payToWinButton = new basicButton(temp2,"Customise Player",2);
        buttons[1] = payToWinButton;
        Rect temp3 = new Rect();
        temp3.set(100,h+150,Constants.SCREEN_WIDTH-100,h+350);
        basicButton settingsButton = new basicButton(temp3,"Settings",3);
        buttons[2] = settingsButton;
    }

    public void draw(Canvas canvas) {
        Constants.drawBackground(canvas);
        int color2 = Constants.color2;
        int color3 = Constants.color3;
        for (int i = 0; i < buttons.length; i++){
            buttons[i].draw(canvas,color2,color3);
        }
    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    public void terminate(int x) {
        if (x == 0){
            Constants.gameSceneIsScene = true;
        }
        SceneManager.ACTIVE_SCENE = x;
    }

    @Override
    public void update(){
        Constants.gameSceneIsScene = false;
    }

    public int recieveTouch(MotionEvent event) {
        for (basicButton button:buttons){
            int num = button.recieveTouch(event);
            if(num != -123456789 && num != -1){
                terminate(num);
            }
        }
        return -123456789;
    }
}
