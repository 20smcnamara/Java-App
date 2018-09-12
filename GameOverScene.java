package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by 20smcnamara on 4/10/18.
 */

public class GameOverScene extends AdvancedScene{
    private Button[] buttons = new Button[2];

    public GameOverScene(){
        Rect temp = new Rect(Constants.SCREEN_WIDTH/2-300,Constants.SCREEN_HEIGHT/4+200,Constants.SCREEN_WIDTH/2+300,Constants.SCREEN_HEIGHT/4+400);
        Rect temp2 = new Rect(Constants.SCREEN_WIDTH/2-300,Constants.SCREEN_HEIGHT/4+500,Constants.SCREEN_WIDTH/2+300,Constants.SCREEN_HEIGHT/4+700);
        basicButton quit = new basicButton(temp2,"Quit",0);
        basicButton repeat = new basicButton(temp,"Play Again",1);
        buttons[0] = quit;
        buttons[1] = repeat;
    }

    public void draw(Canvas canvas){
        int color1 = Constants.color3;
        int color2 = Constants.color2;
        canvas.drawColor(color1);
        Paint p = new Paint();
        p.setTextSize(100);
        p.setColor(color2);
        p.setTextAlign(Paint.Align.CENTER);
        //Constants.drawCenterText(canvas, p,"Game Over",textRect);
        canvas.drawText("Game Over",Constants.SCREEN_WIDTH2/2,Constants.SCREEN_HEIGHT2/4,p);
        for (Button button: buttons) {
            button.draw(canvas,color2,color1);
        }
    }

    public int recieveTouch(MotionEvent event){
        for (Button button: buttons) {
            int num = button.recieveTouch(event);
            if(num == 0){
                return 0;
            }
            if(num == 1){
                return 1;
            }
        }
        return -123456789;
    }
}
