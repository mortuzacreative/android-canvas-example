package com.mortuza.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Mortuza on 30-Aug-16.
 */
public class BarMovement {
    DrawingCanvas drawingCanvas;
    static int barX, barY;
    MyCanvas myCanvas;
    boolean leftMoveFlag;
    boolean rightMoveFlag;


    public BarMovement(Context context){
        drawingCanvas = new DrawingCanvas(myCanvas,context);
        barX = drawingCanvas.displayX/3;
        barY = drawingCanvas.displayY - drawingCanvas.displayY/8;
        leftMoveFlag = false;
        rightMoveFlag = false;
        initializeInfo();
    }

    public BarMovement(DrawingCanvas drawingCanvas){
        super();
        this.drawingCanvas = drawingCanvas;
    }
    public void initializeInfo(){

        barX = drawingCanvas.displayX/3;
        barY = drawingCanvas.displayY - drawingCanvas.displayY/8;
    }
    public BarMovement(DrawingCanvas drawingCanvas, int cX){
        barX = cX;
    }

    public void moveToLeft(){
        barX-=10;
        initializeInfo();
    }
    public void moveToRight(){
        barY+=10;
        initializeInfo();
    }

    public void startMovingLeft(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                leftMoveFlag = true;
                while (leftMoveFlag){
                    moveToLeft();
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

    }
    public void stopMovingLeft(){
        leftMoveFlag = false;
    }

    public void startMovingRight(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                rightMoveFlag = true;
                while (rightMoveFlag){
                    moveToRight();
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
    public void stopMovingRight(){
        rightMoveFlag = false;
    }

    public boolean isLeftAlive(){
        return leftMoveFlag;
    }
    public boolean isRightAlive(){
        return rightMoveFlag;
    }





}
