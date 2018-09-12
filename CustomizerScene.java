package com.example.a20smcnamara.whynotwork;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.*;

/**
 * Created by 20smcnamara on 4/4/18.
 */

public class CustomizerScene extends AdvancedScene {
    private int index = 0;
    private Button[] buttons = new Button[4];
    private Bitmap[] skins = new Bitmap[3];
    private final int SKIN_PRICE = 100;
    private int cantBuy = 0;
    private ArrayList<Integer> owned = new ArrayList<>();
    private int coins;

    public CustomizerScene(){

        BitmapFactory bf = new BitmapFactory();
        Bitmap skin1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p1_front);
        Bitmap skin2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p2_front);
        Bitmap skin3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p3_front);
        skins[0] = skin1;
        skins[1] = skin2;
        skins[2] = skin3;

        Rect temp = new Rect();
        int h = Constants.SCREEN_HEIGHT-400;
        temp.set(Constants.SCREEN_WIDTH/2+25,h-100,Constants.SCREEN_WIDTH-50,h+50);
        basicButton forward = new basicButton(temp,"Next",0);
        buttons[0] = forward;
        Rect temp2 = new Rect();
        temp2.set(50,h-100,Constants.SCREEN_WIDTH/2-25,h+50);
        basicButton backward = new basicButton(temp2,"Prev",1);
        buttons[1] = backward;
        Rect temp3 = new Rect();
        temp3.set(Constants.SCREEN_WIDTH/2+25,h+100,Constants.SCREEN_WIDTH-50,h+250);
        ArrayList<String> strs= new ArrayList<>();
        strs.add("Buy");
        strs.add("Equip");
        strs.add("Equipped");
        ArrayList<Integer> ints= new ArrayList<>();
        ints.add(2);
        ints.add(3);
        ints.add(-123456789);
        SuperButton buy = new SuperButton(strs,ints,temp3);
        buttons[2] = buy;
        Rect temp4 = new Rect();
        temp4.set(50,h+100, Constants.SCREEN_WIDTH/2-25, h+250);
        basicButton back = new basicButton(temp4,"Home",4);
        buttons[3] = back;
    }

    public void draw(Canvas canvas) {
        Constants.drawBackground(canvas);
        int color2 = Constants.color2;
        int color3 = Constants.color3;
        Rect rect = new Rect();
        rect.set(250,250,Constants.SCREEN_WIDTH-250,Constants.SCREEN_HEIGHT-600);
        Bitmap skin = skins[index];
        Constants.scaleRectTwo(rect);
        Constants.scaleRect(rect,skin);
        Paint p = new Paint();
        canvas.drawBitmap(skin, null, rect, p);
        for (int i = 0; i < buttons.length; i++){
            buttons[i].draw(canvas,color2,color3);
        }
        Paint paint = new Paint();
        paint.setTextSize(85f*(float)((Constants.wwRatio+Constants.hhRatio)/2));
        paint.setColor(color2);
        if(cantBuy > 0){
            canvas.drawText("You can't afford that!", Constants.SCREEN_WIDTH2/2-(float)(10*42*(Constants.wwRatio+Constants.hhRatio)/2), 350*(float)Constants.hhRatio, paint);
        }
        canvas.drawText("Coins: "+coins, 50, 150*(float)Constants.hhRatio, paint);
        for(int x = 0; x < owned.size(); x++){
            if(owned.get(x) == index){
                return;
            }
        }
        canvas.drawText("Cost: "+SKIN_PRICE, Constants.SCREEN_WIDTH2-(float)(10*42*(Constants.wwRatio+Constants.hhRatio)/2), 150*(float)Constants.hhRatio, paint);
    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void update(){
        Constants.gameSceneIsScene = false;
        if(cantBuy > 0){
            cantBuy--;
        }
    }

    @Override
    public int recieveTouch(MotionEvent event, int coins,ArrayList<Integer> owned) {
        this.owned = owned;
        this.coins = coins;

        for (Button button:buttons){
            int num = button.recieveTouch(event);
            if(num == 0){
                index++;
                if (index > skins.length-1){
                    index = 0;
                }
            }
            if(num == 1){
                index--;
                if (index < 0){
                    index = skins.length-1;
                }
            }
            boolean b = false;
            for(int x: owned){
                if(index == x){
                    buttons[2].update(1);
                    b = true;
                    break;
                }
            }
            if(!b){
                buttons[2].update(0);
            }
            if(index== Constants.skinIndex){
                buttons[2].update(2);
            }
            if (num == 2 && coins >= SKIN_PRICE){
                return index;
            }
            else if (num == 2 && coins < SKIN_PRICE){
                cantBuy = 30;
            }
            if (num == 3){
                return index;
            }
            if (num == 4){
                terminate();
            }
        }
        return -123456789;
    }
}
