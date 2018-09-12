package com.example.a20smcnamara.whynotwork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.content.SharedPreferences;

/**
 * Created by 20smcnamara on 3/26/18.
 */

public class GameplayScene extends AdvancedScene {

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager = null;
    private boolean movingPLayer = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private static long HIGH_SCORE = 0;
    private OrientationData orientationData;
    private long frameTime;
    private Bitmap skin;
    private Rect textRect = new Rect();
    private boolean shouldReset = false;
    private GameOverScene gameOverScene = new GameOverScene();
    private CoolColorChanger colorChanger;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    public GameplayScene(Bitmap skin){
        textRect.set(Constants.SCREEN_WIDTH/2-5,Constants.SCREEN_HEIGHT/2-1,Constants.SCREEN_WIDTH/2+5,Constants.SCREEN_HEIGHT/2+1);
        textRect = Constants.scaleRectTwo(textRect);
        //Rect temp = new Rect(Constants.SCREEN_WIDTH2/2-(int)(Constants.wwRatio*50),75,Constants.SCREEN_WIDTH2/2+(int)(Constants.wwRatio*50),150);
        player = new RectPlayer();
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT*3/4);
        player.update(skin);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
        this.skin = skin;
        colorChanger = new CoolColorChanger(25,true);
        sharedPref= Constants.sharedPref;
        editor = Constants.editor;
        if(sharedPref.getInt("score",-1) == -1) {
            editor.putInt("score", 0);
            editor.apply();
        }
    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        int color2;
        int[] x = colorChanger.handler();
        int color = Color.rgb(x[0],x[1],x[2]);
        if(Constants.coolBack == 0) {
            canvas.drawColor(color);
            color2 = Color.rgb(x[1],x[2],x[0]);
        }
        else {
            color = Constants.color1;
            color2 = Constants.color2;
            canvas.drawColor(color);
        }
        if(!gameOver){
            Paint pa = new Paint();
            pa.setTextSize(100);
            pa.setColor(color2);
            player.draw(canvas);
            if (obstacleManager == null){
                reset(skin);
            }
            obstacleManager.draw(canvas,pa,color,color2);
            canvas.drawText("High Score: "+HIGH_SCORE, 50,50+pa.descent()-pa.ascent(),pa);
        }
        else{
            gameOverScene.draw(canvas);
        }

    }

    @Override
    public int update(Bitmap skin){
        this.skin = skin;
        if(obstacleManager == null){
            reset(skin);
        }
        if(!gameOver){
            if(frameTime < Constants.INIT_TIME){
                frameTime = Constants.INIT_TIME;
            }
            if(Constants.imUsingTiltControls) {
                int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
                frameTime = System.currentTimeMillis();
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH2 / 500f;
                    float ySpeed = pitch * Constants.SCREEN_HEIGHT2 / 1000f;

                    playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                    playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
                }
            }
            if(playerPoint.x<75/2) {
                playerPoint.x = 75/2;
            }
            if(playerPoint.x >Constants.SCREEN_WIDTH2-75/2){
                playerPoint.x = Constants.SCREEN_WIDTH2 - 75/2;
            }
            if(playerPoint.y<75/2) {
                playerPoint.y = 75/2;
            }
            if(playerPoint.y >Constants.SCREEN_HEIGHT2-75/2){
                playerPoint.y = Constants.SCREEN_HEIGHT2-75/2;
            }

            player.update(playerPoint,skin);
            obstacleManager.update();
        }
        int num = obstacleManager.playerCollide(player);
        if(num == 1 && !gameOver){
            gameOver = true;
            Constants.multiColor.reset();
            gameOverTime = System.currentTimeMillis();
        }
        if (num == 2){
            return 1;
        }
        return -123456789;
    }

    public void reset(Bitmap skin) {
        if (obstacleManager == null) {
            obstacleManager = new ObstacleManager(300, 400, 75, Color.BLACK);
        }
        if (obstacleManager.getScore() > HIGH_SCORE) {
            editor.putInt("score", (int) obstacleManager.getScore());
            editor.apply();
        }
        gameOver = false;
        playerPoint = new Point(Constants.SCREEN_WIDTH2/2,Constants.SCREEN_HEIGHT2*3/4);
        player.update(skin);
        Constants.inc = 5;
        obstacleManager = new ObstacleManager(300,400,75,Color.BLACK);
        movingPLayer = false;
        HIGH_SCORE = findHighScore();

    }

    @Override
    public int recieveTouch(MotionEvent event) {
        if(!gameOver && !Constants.imUsingTiltControls) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                        movingPLayer = true;
                    }
                    if (shouldReset) {
                        gameOver = false;
                        reset(skin);
                        orientationData.newGame();
                        shouldReset = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!gameOver) {
                        movingPLayer = false;
                    }

                case MotionEvent.ACTION_MOVE:
                    if (!gameOver && movingPLayer) {
                        if (!Constants.imUsingTiltControls) {
                            playerPoint.set((int) event.getX(), (int) event.getY());
                        }
                    }
            }
        }
        else if (gameOver){
            colorChanger.reset();
            int num = gameOverScene.recieveTouch(event);
            if(num == 1){
                gameOver = false;
                reset(skin);
                orientationData.newGame();
                shouldReset = false;
            }
            if(num == 0){
                reset(skin);
                orientationData.newGame();
                shouldReset = false;
                gameOver = true;
                SceneManager.ACTIVE_SCENE = 1;
            }
        }
        if(!gameOver){
            if(obstacleManager == null){
                reset(skin);
            }
            obstacleManager.recieveTouch(event);
        }
        return -123456789;
    }

    public int findHighScore(){
        return sharedPref.getInt("score",0);
    }
}
