package com.example.a20smcnamara.whynotwork;

/**
 * Created by 20smcnamara on 3/26/18.
 */

public class ColorChanger {
    public int[] color = {0,0,0};
    public int index = 5;
    public int inc;
    private int startInc;
    private boolean basic;

    public ColorChanger(int inc,boolean b){
        this.inc = inc;
        this.startInc = inc;
        this.basic = b;
    }

    private void adjustIndex(){
        if(index == 1 && color[1] > 255-inc-1){
            index++;
            adjustIndex();
        }
        if(index == 2 && color[0] < inc){
            index++;
            adjustIndex();
        }
        if(index == 3 && color[2] > 255-inc-1){
            index++;
            adjustIndex();
        }
        if(index == 4 && color[1] < inc){
            index++;
            adjustIndex();
        }
        if(index == 5 && color[0] > 255-inc-1){
            index++;
            adjustIndex();
        }
        if(index == 6 && color[2] < inc){
            index=1;
            if(!basic) {
                Constants.inc += 2;
                if (Constants.inc > 75) {
                    Constants.inc = 75;
                }
            }
            adjustIndex();
        }
    }

    private void adjustColor(){
        if(index == 1){
            color[1]+=inc;
        }
        if(index == 2){
            color[0]-=inc;
        }
        if(index == 3){
            color[2]+=inc;
        }
        if(index == 4){
            color[1]-=inc;
        }
        if(index == 5){
            color[0]+=inc;
        }
        if(index == 6){
            color[2]-=inc;
        }
    }

    public int[] getColor(){
        return color;
    }

    public int getIndex(){
        return index;
    }

    public void reset(){ inc = startInc; }

    public int[] handler(){
        if(!basic) {
            inc = Constants.inc;
        }
        adjustIndex();
        adjustColor();
        return color;
    }

}
