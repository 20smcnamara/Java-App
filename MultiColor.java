package com.example.a20smcnamara.whynotwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 20smcnamara on 4/11/18.
 */

public class MultiColor{

    private ColorChanger colorChanger;
    ArrayList<ColorChanger> colors = new ArrayList<>();
    ArrayList<int[]> arrayList = new ArrayList<>();

    public MultiColor(){
        colorChanger = new ColorChanger(3,true);
        arrayList.add(new int[]{0,0,0});
        arrayList.add(new int[]{0,0,0});
        arrayList.add(new int[]{0,0,0});
    }

    public void reset(){
        for(ColorChanger c: colors){
            c.reset();
        }
    }

    public ArrayList<int[]> handler(){
        colorChanger.handler();
        findInts();
        return arrayList;
    }

    public void findInts(){
        arrayList.set(0,colorChanger.handler());
        arrayList.set(1,new int[]{arrayList.get(0)[1],arrayList.get(0)[2],arrayList.get(0)[0]});
        arrayList.set(2,new int[]{arrayList.get(0)[2],arrayList.get(0)[0],arrayList.get(0)[1]});
    }
}
