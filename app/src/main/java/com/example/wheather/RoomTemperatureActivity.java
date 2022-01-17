package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RoomTemperatureActivity extends AppCompatActivity implements SensorEventListener {

    private ConstraintLayout constraintLayout;
    private EditText temperatureField;
    private SensorManager sensorManager;
    private Sensor ambientTemperature;
    private final String NOT_SUPPORTED_MESSAGE = "Sensor not available on this device.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_temperature);
        temperatureField = (EditText) findViewById(R.id.rt_temp);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        ambientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        //Check if sensor didn't return a value
        if (ambientTemperature == null) {
            temperatureField.setText(NOT_SUPPORTED_MESSAGE);
        }
        //Listener for swipe gestures
        constraintLayout = (ConstraintLayout) findViewById(R.id.rt_constraint);
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeLeft() {
                switchActivity();
            }
        });
    }

    //Method to start a new Activity
    public void switchActivity() {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float ambientTemperatureValue = sensorEvent.values[0];
        temperatureField.setText(String.valueOf(ambientTemperatureValue) + "°C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        //Register listener for sensor
        super.onResume();
        sensorManager.registerListener(this, ambientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Unregister the listener on the sensor when on pause
        sensorManager.unregisterListener(this);
    }
}