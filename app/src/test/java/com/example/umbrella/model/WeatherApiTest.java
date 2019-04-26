package com.example.umbrella.model;

import android.util.Log;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class WeatherApiTest implements Callback<WeatherData> {

    WeatherData weatherData = null;

    @Test
    public void testWeatherApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);

        api.getData(85282).enqueue(this);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull(weatherData);
        assertNotNull(weatherData.city);
        assertNotNull(weatherData.list);
        assert(weatherData.list.size() > 0);
        WeatherData.WeatherItem item = weatherData.list.get(0);
        assertNotNull(item);
        assert(Math.abs(item.dt - System.currentTimeMillis()) < 100000);
    }

    @Override
    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
        weatherData = response.body();
    }

    @Override
    public void onFailure(Call<WeatherData> call, Throwable t) {
        weatherData = null;
    }
}