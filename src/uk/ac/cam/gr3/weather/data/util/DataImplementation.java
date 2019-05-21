package uk.ac.cam.gr3.weather.data.util;
import org.json.JSONObject;
import uk.ac.cam.gr3.weather.data.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataImplementation implements WeatherService {

    private final List<Runnable> listeners = new ArrayList<>();

    private HomeData peakData;
    private HomeData baseData;
    private SnowData snowData;
    private WeeklyData weeklyData;

    public DataImplementation() throws IOException {

        fetchData();
    }

    private void notifyListeners() {
        for (Runnable listener : listeners)
            listener.run();
    }

    @Override
    public void addRefreshListener(Runnable listener) {
        listeners.add(listener);
    }

    @Override
    public void removeRefreshListener(Runnable listener) {
        if (!listeners.remove(listener))
            throw new IllegalArgumentException("Removing an unregistered listener");
    }

    @Override
    public void refresh() throws IOException {

        fetchData();

        notifyListeners();
    }

    private void fetchData() throws IOException {

        JSONObject data = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/resortforecast/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");
        JSONObject generalData = URLConnectionReader.getJSON("http://api.weatherunlocked.com/api/forecast/fr.73440?app_id=34ef604f&app_key=9e15b25a985bb34ddc8db6f973324353");
        JSONObject snowReport = URLConnectionReader.getJSON("https://api.weatherunlocked.com/api/snowreport/333020?app_id=5d6c6b76&app_key=5a54d2f573d149bb65b2a2b7fd05cfc3");

        peakData = new HomeData("upper", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast"));
        baseData = new HomeData("base", generalData.getJSONArray("Days").getJSONObject(0), data.getJSONArray("forecast"));
        weeklyData = new WeeklyData(generalData.getJSONArray("Days"));
        snowData = new SnowData(snowReport);
    }

    @Override
    public HomeData getPeakData() {
        return peakData;
    }

    @Override
    public HomeData getBaseData() {
        return baseData;
    }

    @Override
    public SnowData getSnowData() {
        return snowData;
    }

    @Override
    public WeeklyData getWeeklyData() {
        return weeklyData;
    }
}
