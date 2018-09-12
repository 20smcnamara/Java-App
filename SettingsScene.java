package com.example.a20smcnamara.whynotwork;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by 20smcnamara on 4/10/18.
 */

public class SettingsScene extends AdvancedScene {
    private Button[] buttons = new Button[3];
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public SettingsScene(){

        Rect temp = new Rect();
        int h = Constants.SCREEN_HEIGHT/2;
        temp.set(100,h-400,Constants.SCREEN_WIDTH-100,h-200);
        ChangingButton controls = new ChangingButton(temp,"Touch Controls","Tilt Controls",0,1);
        buttons[0] = controls;
        Rect temp2 = new Rect();
        temp2.set(100,h-100,Constants.SCREEN_WIDTH-100,h+100);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Rainbow");
        strings.add("Blue");
        strings.add("Red");
        strings.add("Green");
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        SuperButton color = new SuperButton(strings,integers,temp2);
        buttons[1] = color;
        Rect temp3 = new Rect();
        temp3.set(100,h+200,Constants.SCREEN_WIDTH-100,h+400);
        basicButton back = new basicButton(temp3,"Back",2);
        buttons[2] = back;
        sharedPref = Constants.sharedPref;
        editor = Constants.editor;
        if(sharedPref.getBoolean("tilt",false)){
            editor.putBoolean("tilt", false);
        }
        if(sharedPref.getInt("coolback",-1) ==1){
            editor.putInt("coolback", 1);
        }
        Constants.imUsingTiltControls = sharedPref.getBoolean("tilt",false);
        Constants.coolBack = sharedPref.getInt("coolback",1);
    }

    @Override
    public void draw(Canvas canvas) {
        Constants.drawBackground(canvas);
        int color2 = Constants.color2;
        int color3 = Constants.color3;
        Paint paint = new Paint();
        paint.setColor(color2);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Current Settings",Constants.SCREEN_WIDTH2/2,350,paint);
        for (Button button:buttons){
            button.draw(canvas,color2,color3);
        }

    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void update(){
        Constants.gameSceneIsScene = false;
        buttons[1].update(Constants.coolBack);
        if(Constants.imUsingTiltControls) {
            buttons[0].update(1);
        }
        else {
            buttons[0].update(0);
        }
    }

    @Override
    public int recieveTouch(MotionEvent event) {
        for (Button button:buttons){
            int num = button.recieveTouch(event);
            if(num == 0){
                Constants.imUsingTiltControls = true;
                editor.putBoolean("tilt", true);
                buttons[0].update(1);
            }
            if(num == 1){
                Constants.imUsingTiltControls = false;
                editor.putBoolean("tilt", false);
                buttons[0].update(0);
            }
            if (num >= 3 && num <= 5){
                Constants.coolBack = num-2;
                buttons[1].update(num-2);
                editor.putInt("coolback", num-2);
            }
            if(num == 6){
                Constants.coolBack = 0;
                buttons[1].update(0);
                editor.putInt("coolback", 0);
            }
            if (num == 2){
                terminate();
            }
        }
        editor.putBoolean("tilt",Constants.imUsingTiltControls);
        editor.apply();
        return -123456789;
    }
}
