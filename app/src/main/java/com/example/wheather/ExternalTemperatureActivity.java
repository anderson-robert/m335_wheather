package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExternalTemperatureActivity extends AppCompatActivity {

    private static final String OpenWeatherAPIURL = "api.openweathermap.org/data/2.5/weather?q=Zurich&appid=337567a0234864c15055bd00c1fd627e";
    private static final String OpenWeatherAPITempTerm = "temp";
    private TextView temperatureField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_temperature);
        temperatureField = (TextView) findViewById(R.id.et_temp);
        getTemperatureFromAPI();
    }

    private void getTemperatureFromAPI() {
        String returnedTemp;
        try {
            URL url = new URL(OpenWeatherAPIURL);
            HttpURLConnection connection= (HttpURLConnection)url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            JsonReader reader =new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            reader.beginObject();
            reader.nextName();
            reader.beginArray();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(OpenWeatherAPITempTerm)) {
                    returnedTemp = String.valueOf(reader.nextDouble());
                    temperatureField.setText(returnedTemp);
                } else {
                    reader.skipValue();
                }
            }
            reader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}