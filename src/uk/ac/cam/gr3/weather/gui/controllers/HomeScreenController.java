package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import uk.ac.cam.gr3.weather.Util;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.data.util.HomeData;
import uk.ac.cam.gr3.weather.data.util.Hour;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;
import uk.ac.cam.gr3.weather.gui.util.HSwipePane;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class HomeScreenController extends FXMLController {

    private HomeData baseData;
    private HomeData peakData;

    private Region hourlyBreakDown;

    public HomeScreenController(WeatherService service) {
        super(service);
    }

    @FXML
    private ToggleGroup altitudeSelect;

    @FXML
    private ToggleButton peakButton;

    @FXML
    private ToggleButton baseButton;

    @FXML
    private ImageView weatherMood;

    @FXML
    private Label currentTemperature;

    @FXML
    private Label windSpeed;

    @FXML
    private Label visibility;

    @FXML
    private Label sunSetTime;

    @FXML
    private Label cloudCoverage;

    @FXML
    private AnchorPane graphSwipeAnchor;

    @Override
    protected void initialize() {

        peakData = service.getPeakData();
        baseData = service.getBaseData();

        showBase();

        altitudeSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
            else {
                //if press a different button base or peak: display peak or base data accordingly
                if (newValue == baseButton) showBase();
                else if (newValue == peakButton) showPeak();
            }
        });

        Platform.runLater(() -> {
            ObjectBinding<Rectangle> clipBinding = Bindings.createObjectBinding(() -> new Rectangle(0, 0, graphSwipeAnchor.getWidth(), graphSwipeAnchor.getHeight()), graphSwipeAnchor.layoutBoundsProperty());
            graphSwipeAnchor.clipProperty().bind(clipBinding);
        });
    }

    public void setHSwipePane(HSwipePane pane) {

        graphSwipeAnchor.getChildren().setAll(pane);

        Util.fitToAnchorPane(pane);
    }

    public void setWeatherMood(String url) {
        weatherMood.setImage(new Image("/WeatherIcons/" + url));
        weatherMood.setPreserveRatio(true);
        weatherMood.setFitWidth(300);
    }

    public void setHourlyBreakDown(ArrayList<Hour> timeline) { //create a pane to pass into HSwipePane

        int i = 0;

        //create table
        GridPane table = new GridPane();

        for (Hour hour:timeline) {

            i++;

            //add column constraints
            table.getColumnConstraints().add(new ColumnConstraints(USE_PREF_SIZE, 100, USE_PREF_SIZE, Priority.SOMETIMES, HPos.CENTER, true));

            //create weather mood
            ImageView mood = new ImageView("/WeatherIcons/" + hour.getWeatherIcon());
            mood.setFitWidth(100);
            mood.setPreserveRatio(true);

            //create temperature label
            Label temp = new Label(Integer.toString(hour.getTemperature()) + "℃");
            temp.setFont(Font.font(18));
            temp.setPrefHeight(30);

            //create time label
            Label time = new Label(hour.getHour());
            time.setFont(Font.font(18));
            time.setPrefHeight(30);

            //put content into the table
            table.add(mood, i-1, 0);
            table.add(temp, i-1, 1);
            table.add(time, i-1, 2);
        }

        hourlyBreakDown = table;

    }

    //show weather data at base
    public void showBase() {
        show(baseData);
    }

    //show weather data at peak
    public void showPeak() {
        show(peakData);
    }


    //show a particular type of data
    public void show(HomeData homeData) {

        //set weatherMood
        setWeatherMood(homeData.getWeatherIcon());

        //set temperature
        currentTemperature.setText(Integer.toString(homeData.getCurrentTemperature()));

        //set hourly BreakDown, create HSwipePane
        setHourlyBreakDown(homeData.getTimeline());
        HSwipePane HBD = new HSwipePane(hourlyBreakDown);
        setHSwipePane(HBD);

        //set wind
        windSpeed.setText(Integer.toString(homeData.getWindSpeed()));

        //set visibility
        visibility.setText(Integer.toString(homeData.getVisibility()));

        //set sunset time
        sunSetTime.setText(homeData.getSunset());

        //set cloud
        cloudCoverage.setText(Integer.toString(homeData.getCloudCoverage()));

    }
}
