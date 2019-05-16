package uk.ac.cam.gr3.weather.data.util;

//Creates a hour for the temperature timeline on the home page.
public class Hour {
    //If you want to access these use the getter methods below.
    private String hour; //24 hour clock in the format HH:MM
    private int temperature;
    private String weatherIcon;

    //constructor
    Hour(String hour, int temperature, String weatherIcon){
        this.hour = hour;
        this.temperature = temperature;
        this.weatherIcon = weatherIcon;
    }

    //getters
    public String getHour() {
        return hour;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    //setters
    void setHour(String hour) {
        this.hour = hour;
    }

    void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
