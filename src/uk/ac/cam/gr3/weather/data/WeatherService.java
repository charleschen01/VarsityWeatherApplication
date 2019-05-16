package uk.ac.cam.gr3.weather.data;

import uk.ac.cam.gr3.weather.data.util.HomeData;
import uk.ac.cam.gr3.weather.data.util.SnowData;
import uk.ac.cam.gr3.weather.data.util.WeeklyData;

public interface WeatherService {

    void refresh();

    HomeData getPeakData();

    HomeData getBaseData();

    SnowData getSnowData();

    WeeklyData getWeeklyData();
}
