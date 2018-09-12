package com.example.a20smcnamara.whynotwork;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 20smcnamara on 3/20/18.
 */

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long initTime;
    private long score = 0;
    private long startTime;
    private int x = 1;
    private Button b;
    private ArrayList<Coin> coins = new ArrayList<>();
    private int win = 1;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = (int)(playerGap*Constants.wwRatio);//Gap between two sides of a obstacle object
        this.obstacleGap = (int)(obstacleGap*Constants.hhRatio);//Gap between two obstacle objects
        this.obstacleHeight = (int)(obstacleHeight*Constants.hhRatio);//How tall a obstacle object is
        this.color = color;


        startTime = System.currentTimeMillis();//Used to check if still in game
        initTime = startTime;//^

        obstacles = new ArrayList<>();

        populateObstacles();//Add obstacles to obstacles
        Rect r = new Rect();//creates and set up Rect r for win button
        r.set(Constants.SCREEN_WIDTH2/2-100,Constants.SCREEN_HEIGHT2-150,Constants.SCREEN_WIDTH2/2+100, Constants.SCREEN_HEIGHT2-10);
        Constants.scaleRectTwo(r);
        //b = new basicButton(r,"WIN",1);//creates win button
    }

    public int playerCollide(RectPlayer player){
        for(Obstacle ob: obstacles){//Loops through obstacles
            if(ob.playerCollide(player)){//Checks if that obstacle is colliding w/ player
                return 1;//Returns 1 if it is colliding
            }
        }
        for(int coin = 0; coin < coins.size(); coin++){//Loops through coins
            if(coins.get(coin).playerCollide(player)){//Checks if that coin is colliding w/ player
                coins.remove(coin);//Get rid of coin
                return 2;//Returns 2 if it is colliding
            }
        }
        return 0;//Returns 0 if not colliding w/ anything
    }

    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGHT2/4;//Calculates where to put obstacle before it comes on screen
        while(currY < 0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH2-playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));
            currY += obstacleHeight +obstacleGap;
        }
    }

    public void getObstacleHeight() {
        Constants.obsI = (int)(obstacleGap*Constants.hhRatio)+obstacleHeight;
    }

    public void update(){
        if(startTime < Constants.INIT_TIME){
            startTime = Constants.INIT_TIME;
        }
        int passedTime = (int)(System.currentTimeMillis()-startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime-initTime)/1000.0))*Constants.SCREEN_HEIGHT2/(7500.0f);
        for(Obstacle ob: obstacles){
            ob.incrementY(speed*passedTime*win);
        }
        if (obstacles.get(obstacles.size()-1).getRectangle().top>=Constants.SCREEN_HEIGHT2){
            int xStart = (int) (Math.random()*Constants.SCREEN_WIDTH2-playerGap);
            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top- obstacleHeight -obstacleGap,playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
            Random r = new Random();
            int x= r.nextInt(5);
            if(x == 0){
                coins.add(new Coin(100+r.nextInt(Constants.SCREEN_WIDTH2-200),obstacles.get(0).getRectangle().bottom+(playerGap/2)));
            }
            for(int coin = 0; coin < coins.size(); coin++){
                if(coins.get(coin).getRect().top >= Constants.SCREEN_HEIGHT2){
                    coins.remove(0);
                }
            }
        }
        for(Coin coin: coins){
            coin.update(speed*passedTime);
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        Paint pa = new Paint();
        pa.setTextSize(100);
        pa.setColor(Color.GREEN);
        canvas.drawText("Current: "+score, 50,150+pa.descent()-pa.ascent(),pa);
        for(int coin = 0; coin < coins.size(); coin++){
            coins.get(coin).draw(canvas);
        }
        b.draw(canvas,Constants.color1,Constants.color2);
    }

    public void draw(Canvas canvas, Paint pa,int color1,int color2) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        canvas.drawText("Current: "+score, 50,150+pa.descent()-pa.ascent(),pa);
        for(int coin = 0; coin < coins.size(); coin++){
            coins.get(coin).draw(canvas);
        }
        //b.draw(canvas,color1,color2);
    }

    public void recieveTouch(MotionEvent event){
        /*if(b.recieveTouch(event)==1){
            win = 1000;
        }*/
    }

    public long getScore(){
        return score;
    }
}
