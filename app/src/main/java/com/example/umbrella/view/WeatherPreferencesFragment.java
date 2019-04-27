package com.example.umbrella.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.umbrella.R;

public class WeatherPreferencesFragment extends PreferenceFragmentCompat {

    private static final String DEFAULT_ZIP = "85282";
    public MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.weather_preference_layout);

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);
    }

    private void setDefaultZip() {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                .putString("zip", DEFAULT_ZIP).apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.dark));
        view.setAlpha(.9f);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mainActivity != null) {
            mainActivity.requestWeatherData();
        }
    }

    private void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ("zip".equals(key)) {
            String ans = sharedPreferences.getString("zip", "").trim();
            try {
                Integer.parseInt(ans);
                if (ans.length() != 5) {
                    throw new Exception("ZIP was not five characters");
                }
            } catch (Exception e) {
                Toast.makeText(WeatherPreferencesFragment.this.getContext(),
                        "Sorry, " + ans + " is not a valid zip.",
                        Toast.LENGTH_SHORT).show();
                setDefaultZip();
            }
        }
    }
}
