package com.mortuza.canvasexample;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by Mortuza on 28-Aug-16.
 */
public class Ball {

    float centerX;
    float centerY;
    int height,width;
    float velocityX,velocityY;
    Bitmap ballBitmap;

    public Ball(Bitmap bitmap){
        ballBitmap = bitmap;
        centerX=centerY=0;
        height = ballBitmap.getHeight();
        width = ballBitmap.getWidth();
        velocityX = velocityY = 0;
    }

    public Ball(Bitmap bitmap, int cX, int cY){
        this(bitmap);
        centerX = cX;
        centerY = cY;
    }
    public Ball(Bitmap bitmap, Point center){
        this(bitmap, center.x, center.y);
    }
}
