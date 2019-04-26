package com.example.umbrella.model;

import java.util.List;

public class WeatherData {
    public class CityData {
        public int id;
        public String name, country;
    }

    public class WeatherItem {
        public class TemperatureData {
            public float temp;
        }

        public class WeatherType {
            public String main;
        }

        public long dt;
        public TemperatureData main;
        public List<WeatherType> weather;
    }

    public CityData city;
    public List<WeatherItem> list;
}
