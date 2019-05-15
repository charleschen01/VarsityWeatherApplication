package uk.ac.cam.gr3.weather.data.util;

//Creates a day for the weekly report.
public class Day {
    //If you want to access these use the getter methods below.
    private String nameOfDay;
    private int lowestTempersture;
    private int highestTempersture;
    private String weatherIcon;

    //constructor
    public Day(String date, int lowestTempersture, int highestTempersture, String weatherIcon) {
        refresh(date, lowestTempersture, highestTempersture, weatherIcon);
    }

    //Refreshes the data corresponding to the day.
    public void refresh(String date, int lowestTempersture, int highestTempersture, String weatherIcon) {
        this.nameOfDay = date;
        this.lowestTempersture = lowestTempersture;
        this.highestTempersture = highestTempersture;
        this.weatherIcon = weatherIcon;
    }

    //getter methods
    public String getNameOfDay() {
        return nameOfDay;
    }

    public int getLowestTempersture() {
        return lowestTempersture;
    }

    public int getHighestTempersture() {
        return highestTempersture;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
