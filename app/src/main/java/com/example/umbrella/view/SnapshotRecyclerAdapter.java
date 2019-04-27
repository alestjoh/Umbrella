package com.example.umbrella.view;

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

    public SnapshotRecyclerAdapter(List<WeatherData.WeatherItem> snapshots,
                                   String timeFormat, String iconUrl) {
        this.snapshots = snapshots;
        this.timeFormat = timeFormat;
        this.iconUrl = iconUrl;
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
        snapshotViewHolder.time.setText(timeFormat.format(new Date(weatherItem.dt)));

        snapshotViewHolder.temp.setText(weatherItem.main.getTempF() + "°");

        String iconUrl = String.format(this.iconUrl, weatherItem.weather.get(0).icon);
        Picasso.get().load(iconUrl).into(snapshotViewHolder.icon);
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
