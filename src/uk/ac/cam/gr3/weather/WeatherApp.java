package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.gui.util.HSwipeContainer;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane left = new Pane(), centre = new Pane(), right = new Pane();

        int width = 480;
        int height = 720;

        left.setStyle("-fx-background-color: #3F3F3F");
        left.setPrefWidth(width);
        left.setPrefHeight(height);
        centre.setStyle("-fx-background-color: #6F6F6F");
        centre.setPrefWidth(width);
        centre.setPrefHeight(height);
        right.setStyle("-fx-background-color: #9F9F9F");
        right.setPrefWidth(width);
        right.setPrefHeight(height);

        HSwipeContainer container = new HSwipeContainer(left, centre, right, width);

        Scene scene = new Scene(container);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
