package uk.ac.cam.gr3.weather.gui.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import uk.ac.cam.gr3.weather.Util;
import uk.ac.cam.gr3.weather.gui.util.HSwipePane;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class HomeScreenController {

    @FXML
    private ToggleGroup altitudeSelect;

    @FXML
    private ImageView weatherMood;

    @FXML
    private Label currentTemperature;

    @FXML
    private AnchorPane graphSwipeAnchor;

    private Region hourlyBreakDown;

    private void setHSwipePane(HSwipePane pane) {

        graphSwipeAnchor.getChildren().setAll(pane);

        Util.fitToAnchorPane(pane);
    }

    private void setHourlyBreakDown() { //create a pane to pass into HSwipePane

        //create table
        GridPane table = new GridPane();

        //create data arrays
        String[] moods = new String[8];
        for (int i = 0; i < moods.length; i++) {
            moods[i] = "/WeatherIcons/HeavySnow.gif";
        }
        String[] temps = new String[8];
        for (int i = 0; i < temps.length; i++) {
            temps[i] = "2℃";
        }
        String[] times = new String[8];
        for (int i = 0; i < times.length; i++) {
            times[i] = "9AM";
        }

        for (int i = 0; i<8; i++) {

            //add column constraints
            table.getColumnConstraints().add(new ColumnConstraints(USE_PREF_SIZE, 100, USE_PREF_SIZE, Priority.SOMETIMES, HPos.CENTER, true));

            //create weather mood
            ImageView mood = new ImageView(moods[i]);
            mood.setFitWidth(100);
            mood.setPreserveRatio(true);

            //create temperature label
            Label temp = new Label(temps[i]);
            temp.setFont(Font.font(18));
            temp.setPrefHeight(30);

            //create time label
            Label time = new Label(times[i]);
            time.setFont(Font.font(18));
            time.setPrefHeight(30);

            //put content into the table
            table.add(mood, i, 0);
            table.add(temp, i, 1);
            table.add(time, i, 2);
        }

        hourlyBreakDown = table;

    }

    public void init() {

        //create HSwipePane
        setHourlyBreakDown();
        HSwipePane HBD = new HSwipePane(hourlyBreakDown);
        setHSwipePane(HBD);


        altitudeSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                oldValue.setSelected(true);
        });

        Platform.runLater(() -> {
            ObjectBinding<Rectangle> clipBinding = Bindings.createObjectBinding(() -> new Rectangle(0, 0, graphSwipeAnchor.getWidth(), graphSwipeAnchor.getHeight()), graphSwipeAnchor.layoutBoundsProperty());
            graphSwipeAnchor.clipProperty().bind(clipBinding);
        });
    }
}
