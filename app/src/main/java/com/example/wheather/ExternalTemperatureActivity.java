package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.IOException;

import java.util.Map;


public class ExternalTemperatureActivity extends AppCompatActivity {

    private static final String OpenWeatherAPIURL = "http://api.openweathermap.org/data/2.5/weather?q=Zurich&units=metric&appid=337567a0234864c15055bd00c1fd627e";
    private static final String OpenWeatherAPITempTerm = "temp";
    private TextView temperatureField;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_temperature);
        temperatureField = (TextView) findViewById(R.id.et_temp);
        try {
            getTemperatureFromAPI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        constraintLayout = (ConstraintLayout) findViewById(R.id.et_constraint);
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeRight() {
                switchActivity();
            }
        });
    }

    //Method to start a new Activity
    public void switchActivity() {
        Intent intent = new Intent(this, RoomTemperatureActivity.class);
        startActivity(intent);
    }

    private void getTemperatureFromAPI() throws IOException {


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, OpenWeatherAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        float returnedTemp;
                        OpenWeatherJson opj = new Gson().fromJson(response, OpenWeatherJson.class);
                        returnedTemp = opj.main.temp;
                        temperatureField.setText(String.valueOf(returnedTemp) + "°C");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);




    }

    private class OpenWeatherJson {
        Map<String, Float> coord;
        public OpenWeatherJson_Temp main;
        Map<String, String> wind;
        String base;
        String visibility;
        Map<String, Float> clouds;
        Integer dt;
        Map<String, String> sys;
        String timezone;
        Integer id;
        String name;
        Integer cod;
    }

    private class OpenWeatherJson_Temp {
        public Float temp;
    }

}