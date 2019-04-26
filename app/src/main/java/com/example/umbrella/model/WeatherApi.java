package com.example.umbrella.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
//zip units appid
public interface WeatherApi {
    @GET("forecast")
    Call<WeatherData> getData(@Query("zip") int zip, @Query("appid") String key);
}
