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

            public int getTempF() {
                return (int)(1.8 * getTempC()) + 32;
            }

            public int getTempC() {
                return (int)(temp - 273.15);
            }
        }

        public class WeatherType {
            public String main, icon;
        }

        public long dt;
        public TemperatureData main;
        public List<WeatherType> weather;
    }

    public CityData city;
    public List<WeatherItem> list;
}
