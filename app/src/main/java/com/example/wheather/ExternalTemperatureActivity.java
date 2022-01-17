package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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