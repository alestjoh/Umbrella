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
import com.example.umbrella.model.WeatherData.WeatherItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherRecyclerAdapter extends
        RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

    private static final int DAYS = 5,
            ITEMS_PER_ROW = 4;
    private static final String TAG = WeatherRecyclerAdapter.class.getSimpleName();
    private List<WeatherItem>[] dayLists;
    private String[] dayNames;
    private String timeFormat, iconUrl;
    private WeatherData data;

    public WeatherRecyclerAdapter(Context context, WeatherData data) {
        timeFormat = context.getString(R.string.time_format);
        iconUrl = context.getString(R.string.weather_icon_url);
        dayNames = context.getResources().getStringArray(R.array.day_names);

        this.data = data;

        initDayLists();
    }

    private void initDayLists() {
        dayLists = new ArrayList[DAYS];
        for (int i = 0; i < DAYS; i++) {
            dayLists[i] = new ArrayList<>();
        }

        int index = 0;
        int curDay = getDay(new Date(data.list.get(0).dt * 1000));
        for (WeatherItem item : data.list) {
            int day = getDay(new Date(item.dt * 1000));
            if (day != curDay) {
                curDay = day;
                index ++;
                if (index >= DAYS) {
                    break;
                }
            }
            dayLists[index].add(item);
        }
    }

    private static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
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
                weatherViewHolder.recyclerView.getContext(), ITEMS_PER_ROW));
        weatherViewHolder.recyclerView.setAdapter(new SnapshotRecyclerAdapter(
                dayLists[i], timeFormat, iconUrl));
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
