package com.mortuza.canvasexample;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    MyCanvas myCanvas;

    MySensorAnimation mySensorAnimation;
    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor accelerometerSensor;

    private static float gravityX, gravityY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        myCanvas = new MyCanvas(this);
        initializeSensors();

        setContentView(myCanvas);
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    gravityX = -sensorEvent.values[0];
                    gravityY = sensorEvent.values[1];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startSensors();
    }

    private void startSensors(){
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopSensors(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    public static float getGravityX(){
        return gravityX;
    }
    public static float getGravityY(){
        return gravityY;
    }

    @Override
    protected void onPause() {
        stopSensors();
        myCanvas.stopThreadMakeBall();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startSensors();
        myCanvas.startThreadMakeBall();
        super.onResume();
    }

    @Override
    protected void onStop() {
        stopSensors();
        myCanvas.stopThreadMakeBall();
        super.onStop();
    }
}
