package com.mortuza.canvasexample;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Mortuza on 30-Aug-16.
 */
public class BarMovementThread extends Thread {
    MyCanvas myCanvas;
    private float value;
    Context context;
    boolean flagBarThread = false;

    public BarMovementThread (){
        value = 0;
    }

    @Override
    public void run() {
        flagBarThread = true;
        while(flagBarThread){
            if(myCanvas.touchActionFlag) {
                value = 10;
            }
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public float getValue (){
        return value;
    }


    public void stopThread(){
        flagBarThread = false;
    }
}
