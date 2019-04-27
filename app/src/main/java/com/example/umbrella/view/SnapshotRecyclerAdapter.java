package com.example.umbrella.view;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.umbrella.R;
import com.example.umbrella.model.WeatherData;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SnapshotRecyclerAdapter extends
        RecyclerView.Adapter<SnapshotRecyclerAdapter.SnapshotViewHolder> {

    List<WeatherData.WeatherItem> snapshots;
    String timeFormat, iconUrl;
    private int highIndex = -1, lowIndex = -1;
    private SharedPreferences preferences;
    private int warmColor, coolColor;

    public SnapshotRecyclerAdapter(List<WeatherData.WeatherItem> snapshots,
                                   String timeFormat, String iconUrl,
                                   SharedPreferences preferences,
                                   int warmColor, int coolColor) {
        this.snapshots = snapshots;
        this.timeFormat = timeFormat;
        this.iconUrl = iconUrl;
        this.preferences = preferences;
        this.warmColor = warmColor;
        this.coolColor = coolColor;

        findHighAndLowIndexes();
    }

    private void findHighAndLowIndexes() {
        highIndex = 0;
        lowIndex = 0;
        for (int i = 1; i < snapshots.size(); i++) {
            WeatherData.WeatherItem item = snapshots.get(i);
            if (getTemp(item) > getTemp(snapshots.get(highIndex))) {
                highIndex = i;
            }
            if (getTemp(item) < getTemp(snapshots.get(lowIndex))) {
                lowIndex = i;
            }
        }

        if (highIndex == lowIndex) {
            highIndex = lowIndex = -1;
        }
    }

    private int getTemp(WeatherData.WeatherItem item) {
        // If degree type is F...
        if (preferences.getBoolean("degree_type", false)) {
            return item.main.getTempF();
        } else {
            return item.main.getTempC();
        }
    }

    @NonNull
    @Override
    public SnapshotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.snapshot_layout, viewGroup, false);

        return new SnapshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnapshotViewHolder snapshotViewHolder, int i) {
        WeatherData.WeatherItem weatherItem = snapshots.get(i);

        SimpleDateFormat timeFormat = new SimpleDateFormat(this.timeFormat, Locale.US);
        snapshotViewHolder.time.setText(timeFormat.format(new Date(weatherItem.dt * 1000)));
        snapshotViewHolder.temp.setText(weatherItem.main.getTempF() + "°");

        String iconUrl = String.format(this.iconUrl, weatherItem.weather.get(0).icon);
        Picasso.get().load(iconUrl).into(snapshotViewHolder.icon);

        if (i == highIndex) {
            snapshotViewHolder.time.setTextColor(warmColor);
            snapshotViewHolder.temp.setTextColor(warmColor);
        } else if (i == lowIndex) {
            snapshotViewHolder.time.setTextColor(coolColor);
            snapshotViewHolder.temp.setTextColor(coolColor);
        }
    }

    @Override
    public int getItemCount() {
        return snapshots.size();
    }

    public class SnapshotViewHolder extends RecyclerView.ViewHolder {

        TextView time, temp;
        ImageView icon;

        public SnapshotViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.tv_time_snapshot);
            temp = itemView.findViewById(R.id.tv_temperature_snapshot);
            icon = itemView.findViewById(R.id.iv_icon_snapshot);
        }
    }
}
