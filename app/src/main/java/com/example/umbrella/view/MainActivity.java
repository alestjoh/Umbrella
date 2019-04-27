package com.example.umbrella.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.umbrella.R;
import com.example.umbrella.model.WeatherData;
import com.example.umbrella.viewModel.WeatherDataViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    WeatherDataViewModel viewModel;

    @BindView(R.id.tv_temp_currentWeather)
    TextView temp;
    @BindView(R.id.tv_cityName_currentWeather)
    TextView cityName;
    @BindView(R.id.tv_description_currentWeather)
    TextView description;
    @BindView(R.id.constraintLayout_currentWeather)
    ConstraintLayout currentWeatherLayout;
    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;
    @BindView(R.id.btn_settings_currentWeather)
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(WeatherDataViewModel.class);
        viewModel.getWeatherData().observe(this, weatherData -> {
            Log.d(TAG, "City: " + weatherData.city.name);
            Log.d(TAG, "Temp: " + weatherData.list.get(0).main.getTempF());
            Log.d(TAG, "Number of items: " + weatherData.list.size());

            temp.setText(getTemperatureText(weatherData));
            cityName.setText(weatherData.city.name);
            description.setText(weatherData.list.get(0).weather.get(0).main);

            if (weatherData.list.get(0).main.getTempF() < 60) {
                currentWeatherLayout.setBackgroundColor(
                        getResources().getColor(R.color.colorCool));
            } else {
                currentWeatherLayout.setBackgroundColor(
                        getResources().getColor(R.color.colorWarm));
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new WeatherRecyclerAdapter(this, weatherData));
        });
        viewModel.getWeatherData(85282, getString(R.string.weather_api_key));
    }

    private String getTemperatureText(WeatherData weatherData) {

        return weatherData.list.get(0).main.getTempF() + getString(R.string.degree_sign);
    }

    @OnClick(R.id.btn_settings_currentWeather)
    public void openSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder_main, new WeatherPreferencesFragment())
                .addToBackStack(null)
                .commit();
    }
}
