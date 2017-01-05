package com.mortuza.canvasexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by Mortuza on 27-Aug-16.
 */
public class DrawingCanvas extends Thread {

    public Canvas canvas;
    private  MyCanvas myCanvas;
    private Context context;
    boolean canDraw;
    Bitmap backgroundBitmap;
    Bitmap barBitmap;
    int displayX;
    int displayY;
    Paint paint = new Paint();
    BarMovement barMovement;

    int scoreCount = 0;
    int missedBall = 0;

    ArrayList<Ball> allBalls;
    ArrayList<Bitmap> allPossibleBalls;

    public DrawingCanvas(MyCanvas myCanvas, Context context){
        super();
        this.myCanvas = myCanvas;
        this.context = context;
        canDraw = false;

        initializeAll();
    }

    private void initializeAll() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();

        Point displayDimension = new Point();
        defaultDisplay.getSize(displayDimension);

        displayX = displayDimension.x;
        displayY = displayDimension.y;

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.wall);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap,displayX,displayY,true);
        barBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.bar);
        barBitmap = Bitmap.createScaledBitmap(barBitmap,displayX/3,displayX/8,true);
        initializeAllBalls();
    }

    private void initializeAllBalls() {
        allPossibleBalls = new ArrayList<Bitmap>();
        allBalls = new ArrayList<Ball>();

        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_one));
        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_two));
        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_three));
        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_four));
        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_five));
        allPossibleBalls.add(getScaleBitmap(R.drawable.ball_six));

    }
    private Bitmap getScaleBitmap(int resourceID){

        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(),resourceID);
        tempBitmap = Bitmap.createScaledBitmap(tempBitmap,displayX/8,displayX/8,true);

        return tempBitmap;
    }

    @Override
    public void run() {
        canDraw = true;
        MySensorAnimation mySensorAnimation = new MySensorAnimation(this);
        mySensorAnimation.start();
        barMovement = new BarMovement(this);
        while (canDraw){

            canvas = myCanvas.surfaceHolder.lockCanvas();
            try {
                synchronized (myCanvas.surfaceHolder){
                    updateDisplay();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!=null){
                    myCanvas.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }


        }
        mySensorAnimation.stopThread();
    }

    private void updateDisplay() {
        canvas.drawBitmap(backgroundBitmap,0,0,paint);
        if(allBalls.isEmpty()) {
            drawWelcomeText();
        } else {
            drawBarBitmap();
        }

        for (int i = 0; i < allBalls.size(); i++){
            Ball tempBall = allBalls.get(i);

            canvas.drawBitmap(tempBall.ballBitmap,tempBall.centerX,tempBall.centerY,null);
            if(BarMovement.barX <= (int)tempBall.centerX && BarMovement.barX+displayX/3 >= (int)tempBall.centerX && (int)tempBall.centerY == (displayY - displayY/8)){
                scoreCount+=1;
                allBalls.remove(i);

            }else if(tempBall.centerY > displayY+10) {
                missedBall+=1;
                allBalls.remove(i);
            }
            drawScore();
            drawMissed();
            if(missedBall == 3){
                drawGameOver();
                canDraw = false;
            }

            //startAnimation(5, tempBall);
        }
        //drawSensorData();
    }

    private void drawGameOver() {
        paint.setTextSize(55);
        paint.setColor(Color.RED);
        canvas.drawText("Game Over!",displayX/4, displayY/2,paint);
    }

    private void drawScore() {
        paint.setTextSize(30);
        paint.setColor(Color.GREEN);
        canvas.drawText("Score: "+scoreCount,displayX - displayX/4, (displayY+displayY/12)-displayY,paint);
    }
    private void drawMissed() {
        paint.setTextSize(30);
        paint.setColor(Color.RED);
        canvas.drawText("Missed: "+missedBall,displayX+5 - displayX, (displayY+displayY/12)-displayY,paint);
    }

    private void drawBarBitmap() {

        canvas.drawBitmap(barBitmap,barMovement.barX,barMovement.barY,paint);
    }

    private void startAnimation(float speed, Ball tempBall) {
        if(displayY > tempBall.centerY){
            tempBall.centerY += speed;
        }
    }

    public void stopThread(){
        canDraw = false;
    }

    private void drawSensorData(){
        Paint paint = new Paint();
        paint.setTextSize(displayY/15);
        paint.setColor(Color.RED);
        canvas.drawText("X :"+MainActivity.getGravityX(),0,displayY/6,paint);
        canvas.drawText("Y :"+MainActivity.getGravityY(),0,displayY/4,paint);
    }
    public  void drawWelcomeText(){
        Paint paint = new Paint();
        paint.setTextSize(displayY/20);
        paint.setColor(Color.BLACK);
        canvas.drawText("Touch To Start",displayX/4,displayY/2,paint);
    }
}
