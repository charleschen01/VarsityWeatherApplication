package uk.ac.cam.gr3.weather.data;

import uk.ac.cam.gr3.weather.data.util.HomeData;
import uk.ac.cam.gr3.weather.data.util.SnowData;
import uk.ac.cam.gr3.weather.data.util.WeeklyData;

import java.io.IOException;

public interface WeatherService {

    void refresh() throws IOException;

    void addRefreshListener(Runnable listener);

    void removeRefreshListener(Runnable listener);

    HomeData getPeakData();

    HomeData getBaseData();

    SnowData getSnowData();

    WeeklyData getWeeklyData();
}
