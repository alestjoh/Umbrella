package com.example.umbrella.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.umbrella.R;
import com.example.umbrella.model.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class WeatherRecyclerAdapter extends
        RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

    private static final int NUM_SNAPSHOTS = 40,
            ITEMS_PER_DAY = 8;
    private static final String TAG = WeatherRecyclerAdapter.class.getSimpleName();
    private String[] dayNames;
    private String timeFormat, iconUrl;
    private WeatherData data;

    public WeatherRecyclerAdapter(Context context, WeatherData data) {
        timeFormat = context.getString(R.string.time_format);
        iconUrl = context.getString(R.string.weather_icon_url);
        dayNames = context.getResources().getStringArray(R.array.day_names);

        this.data = data;
        if (data.list.size() < 40) {
            Log.e(TAG, "The list does not have enough items in it: " + data.list.size());
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.weather_day_layout, viewGroup, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        weatherViewHolder.dayText.setText(dayNames[i]);

        weatherViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(
                weatherViewHolder.recyclerView.getContext(), 4));
        weatherViewHolder.recyclerView.setAdapter(new SnapshotRecyclerAdapter(
                getItemsForDay(i), timeFormat, iconUrl));
    }

    private List<WeatherData.WeatherItem> getItemsForDay(int i) {
        List<WeatherData.WeatherItem> list = new ArrayList<>();

        int startIndex = ITEMS_PER_DAY * i;
        for (int index = 0; index < startIndex + ITEMS_PER_DAY; index++) {
            list.add(data.list.get(index));
        }

        return list;
    }

    @Override
    public int getItemCount() {
        return 5;   // Should be consistent, otherwise we logged an error message earlier
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;
        RecyclerView recyclerView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.tv_day_day);
            recyclerView = itemView.findViewById(R.id.recycler_view_day);
        }
    }
}
