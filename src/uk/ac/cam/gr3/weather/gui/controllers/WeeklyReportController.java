package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uk.ac.cam.gr3.weather.data.util.Day;
import uk.ac.cam.gr3.weather.data.util.WeatherService;

public class WeeklyReportController {

    @FXML
    private VBox WeekVBox;

    //add to the page a band describing a single day
    private HBox dayBand(String dayName, String icon, String tempHigh, String tempLow) {
        HBox wednesday = new HBox();
        //<HBox alignment="CENTER_LEFT" prefHeight="50.0" prefWidth="360.0">
        wednesday.setAlignment(Pos.CENTER_LEFT);
        wednesday.setPrefHeight(50.0);
        wednesday.setPrefWidth(360.0);

        Image moodImage = new Image(icon);
        ImageView mood = new ImageView(moodImage);
        //<ImageView fitHeight="50.0" fitWidth="80.0" pickOnBounds="true" preserveRatio="true" />
        mood.setFitHeight(50.0);
        mood.setFitWidth(80.0);
        mood.setPickOnBounds(true);
        mood.setPreserveRatio(true);
        Label day = new Label(dayName);
        //<Label prefHeight="18.0" prefWidth="149.0" text="Tuesday" />
        day.setPrefHeight(18.0);
        day.setPrefWidth(149.0);
        Label tempMax = new Label(tempHigh);
        //<Label prefHeight="18.0" prefWidth="43.0" text="5 °C">
        tempMax.setPrefHeight(18.0);
        tempMax.setPrefWidth(43.0);
        tempMax.setTextFill(Color.web("#ff0000", 1.0));
        Label tempMin = new Label(tempLow);
        //<Label prefHeight="18.0" prefWidth="43.0" text="5 °C">
        tempMin.setPrefHeight(18.0);
        tempMin.setPrefWidth(43.0);
        tempMin.setTextFill(Color.web("#0000ff", 1.0));
        wednesday.getChildren().add(mood);
        wednesday.getChildren().add(day);
        wednesday.getChildren().add(tempMax);
        wednesday.getChildren().add(tempMin);

        return wednesday;
    }

    //Initiate (and fill) the weekly report page. takes as argument the Weather Service
    public void init(WeatherService ws) {
        for(Day d: ws.getWeeklyData().getWeek()) {
            WeekVBox.getChildren().add(dayBand(d.getNameOfDay(),d.getWeatherIcon(),d.getHighestTempersture()+" °C",d.getLowestTempersture()+" °C"));
        }
    }
}
