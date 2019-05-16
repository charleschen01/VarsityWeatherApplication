package uk.ac.cam.gr3.weather.data.util;

public interface WeatherService {

    void refresh();

    HomeData getPeakData();

    HomeData getBaseData();

    SnowData getSnowData();

    WeeklyData getWeeklyData();
}
