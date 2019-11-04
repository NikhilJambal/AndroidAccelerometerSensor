package com.example.accerlotersenosrdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    SensorManager sensorManager;
    Sensor acce;
    long lastTime;
    float x;
    float y;
    float z;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastTime = System.currentTimeMillis();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        acce = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] value = event.values;
        float x = value[0];
        float y = value[1];
        float z = value[2];

        float acceleationSqureRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = System.currentTimeMillis();

        if (acceleationSqureRoot >= 2) {
            if (actualTime - lastTime < 200) {
                return;
            }


            lastTime = actualTime;

            //Perform ypor Action here...

            Intent i = new Intent(MainActivity.this, Second.class);
            startActivity(i);

            Toast.makeText(this, "your next activity sucessfully called ", Toast.LENGTH_SHORT).show();

            finish();
        }



    }

    @Override
    protected void onResume () {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor
                (Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {

        super.onPause();

        sensorManager.unregisterListener(this);
    }
}