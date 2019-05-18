package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import uk.ac.cam.gr3.weather.Util;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.data.util.DangerousWeather;
import uk.ac.cam.gr3.weather.data.util.HomeData;
import uk.ac.cam.gr3.weather.data.util.Hour;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;
import uk.ac.cam.gr3.weather.gui.util.HSwipePane;

import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class HomeScreenController extends FXMLController {

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
    private AnchorPane hourlyBreakdownAnchor;

    @Override
    protected void initialize() {

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

        InvalidationListener changeClip = it -> {

            double width = hourlyBreakdownAnchor.getWidth();
            double height = hourlyBreakdownAnchor.getHeight();

            // The width over which fading will be applied.
            int fadeWidth = 40;

            // This is separating the area into three parts; left and right will be faded while centre will not.
            Rectangle leftFade = new Rectangle(0, 0, fadeWidth, height);
            Rectangle centre = new Rectangle(fadeWidth, 0,width - 2 * fadeWidth, height);
            Rectangle rightFade = new Rectangle(width - fadeWidth, 0, fadeWidth, height);

            // Lets JavaFX know it shouldn't try to do layout with those.
            leftFade.setManaged(false);
            centre.setManaged(false);
            rightFade.setManaged(false);

            // The fill paint is used to determine transparency of the components.
            // Centre is just plain, flat colour.
            centre.setFill(Color.BLACK);

            // Left and right have a linear gradient that goes from black to transparent.
            leftFade.setFill(new LinearGradient(0, 0, fadeWidth, 0, false, CycleMethod.NO_CYCLE, new Stop(0, Color.TRANSPARENT), new Stop(1, Color.BLACK)));
            rightFade.setFill(new LinearGradient(width - fadeWidth, 0, width, 0, false, CycleMethod.NO_CYCLE, new Stop(0, Color.BLACK), new Stop(1, Color.TRANSPARENT)));

            Group group = new Group(leftFade, centre, rightFade);

            // This applies the rectangles and fading to the component.
            hourlyBreakdownAnchor.setClip(group);
        };

        // Update the clip when the width or height change.
        // This also captures when they are first set, which is what we're interested in.
        hourlyBreakdownAnchor.widthProperty().addListener(changeClip);
        hourlyBreakdownAnchor.heightProperty().addListener(changeClip);
    }

    private void setHSwipePane(HSwipePane pane) {

        hourlyBreakdownAnchor.getChildren().setAll(pane);

        Util.fitToAnchorPane(pane);
    }

    private void setWeatherMood(String url) {
        weatherMood.setImage(new Image("/WeatherIcons/" + url));
        weatherMood.setPreserveRatio(true);
        weatherMood.setFitWidth(300);
    }

    private void setHourlyBreakDown(ArrayList<Hour> timeline) { //create a pane to pass into HSwipePane

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
            Label temp = new Label(hour.getTemperature() + "℃");
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
    @FXML
    private void showBase() {
        show(service.getBaseData());
    }

    //show weather data at peak
    @FXML
    private void showPeak() {
        show(service.getPeakData());
    }


    //show a particular type of data
    private void show(HomeData homeData) {

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

        //give an alert if the weather is dangerous
        if(DangerousWeather.isDangerous(homeData.getWeatherCondition())) {
            Platform.runLater(() -> {
                String warningText = String.format("The weather may be dangerous. Current conditions are %s.", homeData.getWeatherCondition());
                Alert weatherAlert = new Alert(Alert.AlertType.WARNING, warningText, ButtonType.OK);
                weatherAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                weatherAlert.showAndWait();
            });
        }

    }
}
