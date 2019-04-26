package com.example.umbrella.viewModel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.umbrella.model.WeatherApi;
import com.example.umbrella.model.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataViewModel extends ViewModel {

    public final static String TAG = WeatherDataViewModel.class.getSimpleName();

    private WeatherApi api = null;

    private void initRetrofit() {
        Log.d(TAG, "Initializing Retrofit...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherApi.class);
    }

    public void getWeatherData(int zip) {
        if (api == null) {
            initRetrofit();
        }

        Log.d(TAG, "Calling api method...");
        api.getData(zip).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.d(TAG, "Got a valid response");
                if (response.body() == null) {
                    Log.e(TAG, "Response body was null");
                    Log.e(TAG, response.code() + " " + response.message());
                } else {

                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e(TAG, "Call failed: " + t.getMessage());
            }
        });
    }
}
