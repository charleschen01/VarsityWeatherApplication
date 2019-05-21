package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.application.Platform;
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

import java.util.List;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class HomeScreenController extends FXMLController {

    public HomeScreenController(WeatherService service) {
        super(service);
    }

    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private HBox altitudeBox;

    @FXML
    private ToggleGroup altitudeSelect;

    @FXML
    private ToggleButton peakButton, baseButton;

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
    private Label sunRiseTime;

    @FXML
    private Label cloudCoverage;

    @FXML
    private AnchorPane hourlyBreakdownAnchor;

    @Override
    protected void initialize() {  //set the homescreen upon first opening it

        showBase();

        // Negate the effect of scrolling on the altitude selection buttons.
        altitudeBox.translateYProperty().bind(rootAnchor.translateYProperty().negate());

        altitudeSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true); // Prevents the user from not having a selected toggle.
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

    @Override
    protected void update() {
        if (altitudeSelect.getSelectedToggle() == baseButton)
            showBase();
        else if (altitudeSelect.getSelectedToggle() == peakButton)
            showPeak();
    }

    private void setHSwipePane(HSwipePane pane) {

        hourlyBreakdownAnchor.getChildren().setAll(pane);

        Util.fitToAnchorPane(pane);
    }

    //set the main weathermood image on the homescreen
    private void setWeatherMood(String url) {
        weatherMood.setImage(new Image("/WeatherIcons/" + url));
        weatherMood.setPreserveRatio(true);
        weatherMood.setFitWidth(300);
    }

    private GridPane createHourlyBreakdown(List<Hour> timeline) { //create a pane that contains hourly breakdown to pass into HSwipePane

        int i = 0;

        GridPane table = new GridPane();

        for (Hour hour:timeline) {

            i++;

            //add column constraints
            table.getColumnConstraints().add(new ColumnConstraints(USE_PREF_SIZE, 100, USE_PREF_SIZE, Priority.SOMETIMES, HPos.CENTER, true));

            //create weather mood image
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

        return table;
    }

    //show weather data at base
    private void showBase() {
        show(service.getBaseData());
    }

    //show weather data at peak
    private void showPeak() {
        show(service.getPeakData());
    }


    //show a particular type of data
    private void show(HomeData homeData) {

        //set weatherMood
        String weatherIcon = homeData.getWeatherIcon();
        setWeatherMood(weatherIcon);

        //set temperature
        currentTemperature.setText(Integer.toString(homeData.getCurrentTemperature()));

        //create hourly breakdown, create HSwipePane
        Region hourlyBreakdown = createHourlyBreakdown(homeData.getTimeline());
        HSwipePane hourlyBreakdownSwipe = new HSwipePane(hourlyBreakdown);
        setHSwipePane(hourlyBreakdownSwipe);

        //set wind
        windSpeed.setText(Integer.toString(homeData.getWindSpeed()));

        //set visibility
        visibility.setText(Integer.toString(homeData.getVisibility()));

        //set sunset time
        sunSetTime.setText(homeData.getSunset());

        //set sunrise time
        sunRiseTime.setText(homeData.getSunrise());

        //set cloud
        cloudCoverage.setText(Integer.toString(homeData.getCloudCoverage()));

        // Remove extension from icon name
        String weatherConditionsName = weatherIcon.substring(0, weatherIcon.lastIndexOf('.'));
        //give an alert if the weather is dangerous
        if(DangerousWeather.isDangerous(weatherConditionsName)) {
            Platform.runLater(() -> {
                String warningText = String.format("The weather may be dangerous. Current conditions are %s.", homeData.getWeatherCondition());
                Alert weatherAlert = new Alert(Alert.AlertType.WARNING, warningText, ButtonType.OK);
                weatherAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                weatherAlert.showAndWait();
            });
        }

    }
}
