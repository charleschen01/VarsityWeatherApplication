package uk.ac.cam.gr3.weather.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class WeeklyReportController {

    @FXML
    private VBox WeekVBox;

    private HBox dayBand(String dayName, String icon, String tempHigh, String tempLow) {
        HBox wednesday = new HBox();
        //<HBox alignment="CENTER_LEFT" prefHeight="50.0" prefWidth="360.0">
        wednesday.setAlignment(Pos.CENTER_LEFT);
        wednesday.setPrefHeight(50.0);
        wednesday.setPrefWidth(360.0);

        javafx.scene.image.Image moodImage = new javafx.scene.image.Image(icon);
        javafx.scene.image.ImageView mood = new javafx.scene.image.ImageView(moodImage);
        //<ImageView fitHeight="50.0" fitWidth="80.0" pickOnBounds="true" preserveRatio="true" />
        mood.setFitHeight(50.0);
        mood.setFitWidth(80.0);
        mood.setPickOnBounds(true);
        mood.setPreserveRatio(true);
        javafx.scene.control.Label day = new javafx.scene.control.Label(dayName);
        //<Label prefHeight="18.0" prefWidth="149.0" text="Tuesday" />
        day.setPrefHeight(18.0);
        day.setPrefWidth(149.0);
        javafx.scene.control.Label tempMax = new javafx.scene.control.Label(tempHigh);
        //<Label prefHeight="18.0" prefWidth="43.0" text="5 °C">
        tempMax.setPrefHeight(18.0);
        tempMax.setPrefWidth(43.0);
        tempMax.setTextFill(javafx.scene.paint.Color.web("#ff0000", 1.0));
        javafx.scene.control.Label tempMin = new javafx.scene.control.Label(tempLow);
        //<Label prefHeight="18.0" prefWidth="43.0" text="5 °C">
        tempMin.setPrefHeight(18.0);
        tempMin.setPrefWidth(43.0);
        tempMin.setTextFill(javafx.scene.paint.Color.web("#0000ff", 1.0));
        wednesday.getChildren().add(mood);
        wednesday.getChildren().add(day);
        wednesday.getChildren().add(tempMax);
        wednesday.getChildren().add(tempMin);

        return wednesday;
    }

    public void init() {
        WeekVBox.getChildren().add(dayBand("Wednesday","WeatherIcons/PartCloudSleetSnowThunderNight.gif","5 °C","2 °C"));
        WeekVBox.getChildren().add(dayBand("Thursday","WeatherIcons/PartlyCloudyDay.gif","50 °C","-20 °C"));
    }
}
