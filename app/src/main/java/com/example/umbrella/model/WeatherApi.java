package com.example.umbrella.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherApi {
    @Headers("user-key: b414d76d1f5dfdc063dd2a95ef460736")
    @GET("forecast")
    Call<WeatherData> getData(@Query("q") int zip);
}
