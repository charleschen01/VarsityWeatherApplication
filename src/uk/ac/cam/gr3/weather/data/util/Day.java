package uk.ac.cam.gr3.weather.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        //Converts the date into the day of the week
        Date now = null;
        try {
            now = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");

        this.nameOfDay = simpleDateformat.format(now);
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
