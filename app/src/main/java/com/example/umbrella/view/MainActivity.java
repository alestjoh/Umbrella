package com.example.umbrella.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.umbrella.R;
import com.example.umbrella.viewModel.WeatherDataViewModel;

public class MainActivity extends AppCompatActivity {

    WeatherDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(WeatherDataViewModel.class);
        viewModel.getWeatherData(85282);
    }
}
