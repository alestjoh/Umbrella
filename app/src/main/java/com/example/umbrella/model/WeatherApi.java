package com.example.umbrella.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast")
    Call<WeatherData> getData(@Query("zip") int zip, @Query("appid") String key);
}
