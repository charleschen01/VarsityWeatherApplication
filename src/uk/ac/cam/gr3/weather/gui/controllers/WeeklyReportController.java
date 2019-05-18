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
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;

public class WeeklyReportController extends FXMLController {

    public WeeklyReportController(WeatherService service) {
        super(service);
    }

    @FXML
    private VBox WeekVBox;

    @Override
    protected void initialize() {
        //Initiate (and fill) the weekly report page.
        for(Day d : service.getWeeklyData().getWeek()) {
            WeekVBox.getChildren().add(dayBand(d.getNameOfDay(),d.getWeatherIcon(),d.getHighestTemperature()+" °C",d.getLowestTemperature()+" °C"));
        }
    }

    //add to the page a band describing a single day
    private HBox dayBand(String dayName, String icon, String tempHigh, String tempLow) {
        HBox dayBox = new HBox();
        //<HBox alignment="CENTER_LEFT" prefHeight="50.0" prefWidth="360.0">
        dayBox.setAlignment(Pos.CENTER_LEFT);
        dayBox.setPrefHeight(50.0);
        dayBox.setPrefWidth(360.0);

        final double IMAGE_WIDTH = 80.0;

        Image moodImage = new Image("/WeatherIcons/" + icon);
        ImageView mood = new ImageView(moodImage);
        //<ImageView fitHeight="50.0" fitWidth="80.0" pickOnBounds="true" preserveRatio="true" />
        mood.setFitHeight(50.0);
        mood.setFitWidth(IMAGE_WIDTH);
        mood.setPickOnBounds(true);
        mood.setPreserveRatio(true);
        HBox moodBox = new HBox();
        moodBox.setMinWidth(IMAGE_WIDTH);
        moodBox.getChildren().addAll(mood);
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

        dayBox.getChildren().addAll(moodBox, day, tempMax, tempMin);

        return dayBox;
    }
}
