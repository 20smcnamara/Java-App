package com.example.a20smcnamara.whynotwork;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 20smcnamara on 3/26/18.
 */

public class SceneManager {
    private ArrayList<AdvancedScene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;
    private int index = 0;
    private Bitmap[] skins;
    private ArrayList<Integer> owned;
    private int coins;
    private Coin c = new Coin(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2);
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Set set = new HashSet<String>();

    public SceneManager() {
        ACTIVE_SCENE = 1;
        owned = new ArrayList<>();
        owned.add(0);
        BitmapFactory bf = new BitmapFactory();
        Bitmap p1Img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_front);
        Bitmap p2Img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p2_front);
        Bitmap p3Img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p3_front);
        skins = new Bitmap[]{p1Img,p2Img,p3Img};

        scenes.add(new GameplayScene(skins[index]));
        scenes.add(new StartScreen());
        scenes.add(new CustomizerScene());
        scenes.add(new SettingsScene());
        sharedPref = Constants.sharedPref;
        editor = Constants.editor;
        if(sharedPref.getStringSet("skins",null) == null) {
            editor.putStringSet("skins", set);
            editor.apply();
        }
        if(sharedPref.getInt("score",-1) == -1) {
            editor.putInt("coins", 0);
            editor.apply();
        }
        coins = sharedPref.getInt("coins",0);
        set = sharedPref.getStringSet("skins",set);
        for(int i = 0; i < set.size(); i++){
            if(set.contains(Integer.toString(i))){
                owned.add(i);
            }
        }
    }

    public void update(){
        Constants.update();
        if(ACTIVE_SCENE == 0){
            if(scenes.get(0).update(skins[index]) == 1){
                coins++;
                editor.putInt("coins", coins);
                editor.apply();

            }
        }
        else {
            scenes.get(ACTIVE_SCENE).update();
        }
    }

    public void recieveTouch(MotionEvent event){
        int x;
        if (ACTIVE_SCENE == 2){
            x = scenes.get(ACTIVE_SCENE).recieveTouch(event,coins,owned);
        }
        else {
            x = scenes.get(ACTIVE_SCENE).recieveTouch(event);
        }
        if(x != -123456789) {
            for (int i : owned) {
                if (i == x) {
                    index = x;
                    Constants.skinIndex = x;
                    editor.putInt("coins", coins);
                    editor.putStringSet("skins", set);
                    editor.apply();
                    return;
                }
            }
            owned.add(x);
            coins-=100;
            editor.putInt("coins", coins);
            editor.putStringSet("skins", set);
            editor.apply();
        }
        return;
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
    
    public void setScene(int scene){
        ACTIVE_SCENE = scene;
    }
}



































/*BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_front);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk01);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk02);
        Bitmap walk3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk03);
        Bitmap walk4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk04);
        Bitmap walk5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk05);
        Bitmap walk6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk06);
        Bitmap walk7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk07);
        Bitmap walk8 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk08);
        Bitmap walk9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk08);
        Bitmap walk10 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk09);
        Bitmap walk11 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk10);

        c2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk09);
        c3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_walk10);

        idle = new Animation(new Bitmap[]{idleImg}, 2f);
        walkRight = new Animation(new Bitmap[]{walk1, walk2,walk3,walk4,walk5,walk6,walk7,walk8,walk9,walk10,walk11}, 5.0f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);
        walk3 = Bitmap.createBitmap(walk3, 0, 0, walk3.getWidth(), walk3.getHeight(), m, false);
        walk4 = Bitmap.createBitmap(walk4, 0, 0, walk4.getWidth(), walk4.getHeight(), m, false);
        walk5 = Bitmap.createBitmap(walk5, 0, 0, walk5.getWidth(), walk5.getHeight(), m, false);
        walk6 = Bitmap.createBitmap(walk6, 0, 0, walk6.getWidth(), walk6.getHeight(), m, false);
        walk7 = Bitmap.createBitmap(walk7, 0, 0, walk7.getWidth(), walk7.getHeight(), m, false);
        walk8 = Bitmap.createBitmap(walk8, 0, 0, walk8.getWidth(), walk8.getHeight(), m, false);
        walk9 = Bitmap.createBitmap(walk9, 0, 0, walk9.getWidth(), walk9.getHeight(), m, false);
        walk10 = Bitmap.createBitmap(walk10, 0, 0,walk10.getWidth(),walk10.getHeight(), m, false);
        walk11 = Bitmap.createBitmap(walk11, 0, 0,walk11.getWidth(),walk11.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2,walk3,walk4,walk5,walk6,walk7,walk8,walk9,walk10,walk11}, 5.0f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
        */