package com.mortuza.canvasexample;

/**
 * Created by Mortuza on 29-Aug-16.
 */
public class MySensorAnimation extends Thread{

    private boolean flagAnimation = false;
    DrawingCanvas drawingCanvas;
    float  gY;
    float timeConstant = 0.1f;


    public MySensorAnimation(DrawingCanvas drawingCanvas){
        super();
        this.drawingCanvas = drawingCanvas;
    }

    @Override
    public void run() {

        flagAnimation = true;
        while (flagAnimation){
            updateAllPosition();
            try {
                sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAllPosition() {
        gY = 9.8f;
        for (int i = 0; i < drawingCanvas.allBalls.size(); i++) {
            updateBallsPosition(drawingCanvas.allBalls.get(i));
        }
    }

    private void updateBallsPosition(Ball ball) {
        ball.centerY += (ball.velocityY*timeConstant)+0.5*gY*timeConstant*timeConstant;
        ball.velocityY += gY*timeConstant;
    }

    public void stopThread() {
        flagAnimation = false;
    }


}
