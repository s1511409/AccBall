package com.example.masa.accball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity
                            implements SensorEventListener, SurfaceHolder.Callback{
    SensorManager mSensorManager;
    Sensor mAccSensor;
    SurfaceHolder mHolder;
    int mSurfaceWidth;
    int mSurfaceHeight;

    static final float RADIUS = 50.0f;
    static final float COEF = 1000.0f;

    float mBallX;
    float mBallY;
    float mVX;
    float mVY;

    long mForm;
    long mTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d("MainActivity",
                    "x=" + String.valueOf(event.values[0]) +
                    "y=" + String.valueOf(event.values[1]) +
                    "z=" + String.valueOf(event.values[2]));
        }
        float x = -event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        mTo = System.currentTimeMillis();
        float t = (float)(mTo - mForm);
        t = t / 1000.0f;

        float dx = mVX * t + x * t * t / 2.0f;
        float dy = mVY * t + y * t * t / 2.0f;
        mBallX = mBallX + x * t;
        mBallY = mBallY + y * t;
        mVX = mVX + x * t;
        mVY = mVY + y * t;



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceWidth = width;
        mSurfaceHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSensorManager.unregisterListener(this);
    }
}
