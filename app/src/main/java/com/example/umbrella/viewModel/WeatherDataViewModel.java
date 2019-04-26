package com.example.umbrella.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.umbrella.R;
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

    private MutableLiveData<WeatherData> weatherData = new MutableLiveData<>();

    private void initRetrofit() {
        Log.d(TAG, "Initializing Retrofit...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WeatherApi.class);
    }

    public void getWeatherData(int zip, String key) {
        if (api == null) {
            initRetrofit();
        }

        Log.d(TAG, "Calling api method...");
        api.getData(zip, key).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                Log.d(TAG, "Got a valid response");
                if (response.body() == null) {
                    Log.e(TAG, "Response body was null");
                    Log.e(TAG, response.code() + " " + response.message());
                } else {
                    weatherData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e(TAG, "Call failed: " + t.getMessage());
            }
        });
    }

    public LiveData<WeatherData> getWeatherData() {
        return weatherData;
    }
}
