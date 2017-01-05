package com.mortuza.canvasexample;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.Random;

/**
 * Created by Mortuza on 18-Aug-16.
 */
public class MyCanvas extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    Context context;
    SurfaceHolder surfaceHolder;
    DrawingCanvas drawingCanvas;
    boolean flagDrawBall = false;
    BarMovement barMovement;
    //BarMovementThread barMovementThread;
    float addValue = 0;
    Thread thread = null;
    boolean touchActionFlag;

    public MyCanvas(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //barMovementThread = new BarMovementThread();
        drawingCanvas = new DrawingCanvas(this,context);
    }


    @Override
    public void run() {
         barMovement = new BarMovement(context);
         Random random = new Random();
        while (flagDrawBall){
            int centerX = random.nextInt(drawingCanvas.displayX - drawingCanvas.displayX / 6);
            if (centerX < drawingCanvas.displayX / 8)
                centerX += drawingCanvas.displayX / 8;
            int centerY = 0;

            drawingCanvas.allBalls.add(new Ball(drawingCanvas.allPossibleBalls.get(random.nextInt(6)), centerX, centerY));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Be sure to call the superclass implementation

        float x = event.getX();
        float y = event.getY();
        Point point = new Point((int)x,(int)y);


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                            touchActionFlag = true;
                            if(thread == null) {
                                flagDrawBall = true;
                                thread = new Thread(MyCanvas.this);
                                thread.start();
                            }
                                           //barMovementThread.start();
                                if (x < drawingCanvas.displayX / 2 && barMovement.barX >= 0) {
                                            //barMovement.startMovingLeft();
                                            barMovement.barX-=50;
                                } else if (x > drawingCanvas.displayX / 2 && barMovement.barX < drawingCanvas.displayX - drawingCanvas.displayX / 3) {
                                            //barMovement.startMovingRight();
                                            barMovement.barX+=50;
                                }


                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(barMovement.isLeftAlive()) {
                    barMovement.stopMovingLeft();
                }
                if(barMovement.isRightAlive()) {
                    barMovement.stopMovingRight();
                }
                break;
        }
        return super.onTouchEvent(event);
    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            drawingCanvas.start();
        } catch (Exception e) {
            e.printStackTrace();
            restartThread();
        }
    }



    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        drawingCanvas.stopThread();
    }

    private void restartThread() {
        drawingCanvas.stopThread();
        drawingCanvas = null;
        drawingCanvas = new DrawingCanvas(this,context);
        drawingCanvas.start();
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void stopThreadMakeBall(){
        flagDrawBall = false;
    }
    public void startThreadMakeBall(){
        flagDrawBall = true;
    }
    public boolean getTouchActionFlag(){
        return touchActionFlag;
    }

}
