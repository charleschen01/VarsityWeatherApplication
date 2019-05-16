package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.data.util.DataImplementation;
import uk.ac.cam.gr3.weather.gui.controllers.FrameController;
import uk.ac.cam.gr3.weather.gui.controllers.HomeScreenController;
import uk.ac.cam.gr3.weather.gui.controllers.WeeklyReportController;
import uk.ac.cam.gr3.weather.gui.panes.SnowPane;
import uk.ac.cam.gr3.weather.gui.util.HSwipePane;
import uk.ac.cam.gr3.weather.gui.util.SwipeContainer;

import java.io.IOException;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        DataImplementation service = new DataImplementation();

        // ---- Load Frame ----
        FXMLLoader frameLoader = new FXMLLoader(ClassLoader.getSystemResource("GUI/frame.fxml"));

        Region frame = frameLoader.load();

        FrameController frameController = frameLoader.getController();

        Scene scene = new Scene(frame);

        // ---- Load Snow Report ----
        SnowPane snowReport = new SnowPane(service);

        // ---- Load Home Screen ----
        FXMLLoader homeScreenLoader = new FXMLLoader(ClassLoader.getSystemResource("GUI/HomeScreen.fxml"));

        Region homeScreen = homeScreenLoader.load();

        HomeScreenController homeScreenController = homeScreenLoader.getController();

        // ---- Load Weekly Report ----
        FXMLLoader weekLoader = new FXMLLoader(ClassLoader.getSystemResource("GUI/weekly.fxml"));

        Region weeklyReport = weekLoader.load();

        WeeklyReportController weeklyReportController = weekLoader.getController();

        int width = (int) frame.getPrefWidth();
        int height = (int) frame.getPrefHeight();

        snowReport.setPrefWidth(width);
//        snowReport.setPrefHeight(height);
//        homeScreen.setPrefWidth(width);
//        homeScreen.setPrefHeight(height);
//        weeklyReport.setPrefWidth(width);
//        weeklyReport.setPrefHeight(height);

        SwipeContainer swipeContainer = new SwipeContainer(snowReport, homeScreen, weeklyReport, width);

        Pane t = new Pane();
        t.setPrefHeight(200);
        t.setPrefWidth(600);

        t.setStyle("-fx-background-color: linear-gradient(to right, red, blue)");

        HSwipePane pane = new HSwipePane(t);

        // ---- Initialise controllers ----
        frameController.setSwipeContainer(swipeContainer);
        frameController.init();

        //homeScreenController.setHSwipePane(pane);
        homeScreenController.init();

        weeklyReportController.init(service);

        // ---- Setup Stage ----
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }
}
