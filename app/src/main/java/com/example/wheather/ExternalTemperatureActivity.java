package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

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

    private static final String OpenWeatherAPIURL = "api.openweathermap.org/data/2.5/weather?q=Zurich&units=metric&appid=337567a0234864c15055bd00c1fd627e";
    private static final String OpenWeatherAPITempTerm = "temp";
    private TextView temperatureField;

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
    }

    private void getTemperatureFromAPI() throws IOException {
        String returnedTemp = "";
        URL url = new URL(OpenWeatherAPIURL);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        OpenWeatherJson opj = new Gson().fromJson(reader, OpenWeatherJson.class);

        returnedTemp = opj.main.get("temp");
        returnedTemp = "7";
        temperatureField.setText("7");
    }

    private class OpenWeatherJson {
        Map<String, Float> coord;
        Map<String, String> main;
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
}