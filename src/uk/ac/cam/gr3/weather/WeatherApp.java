package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.gui.FrameController;
import uk.ac.cam.gr3.weather.gui.WeeklyReportController;
import uk.ac.cam.gr3.weather.gui.panes.SnowPane;
import uk.ac.cam.gr3.weather.gui.util.SwipeContainer;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // ---- Load Frame ----
        FXMLLoader frameLoader = new FXMLLoader(ClassLoader.getSystemResource("GUI/frame.fxml"));

        Region frame = frameLoader.load();

        FrameController frameController = frameLoader.getController();

        Scene scene = new Scene(frame);

        // ---- Load Snow Report ----
        SnowPane snowReport = new SnowPane();

        // ---- Load Home Screen ----
        Pane homeScreen = new Pane();

        // ---- Load Weekly Report ----
        FXMLLoader weekLoader = new FXMLLoader(ClassLoader.getSystemResource("GUI/weekly.fxml"));

        Region weeklyReport = weekLoader.load();

        WeeklyReportController weeklyReportController = weekLoader.getController();

        int width = (int) frame.getPrefWidth();
        int height = (int) frame.getPrefHeight();

        snowReport.setStyle("-fx-background-color: #3F3F3F");
        snowReport.setPrefWidth(width);
        snowReport.setPrefHeight(height);
        homeScreen.setStyle("-fx-background-color: #6F6F6F");
        homeScreen.setPrefWidth(width);
        homeScreen.setPrefHeight(height);
        weeklyReport.setStyle("-fx-background-color: #9F9F9F");
        weeklyReport.setPrefWidth(width);
        weeklyReport.setPrefHeight(height);

        SwipeContainer swipeContainer = new SwipeContainer(snowReport, homeScreen, weeklyReport, width);

        // ---- Initialise controllers ----
        frameController.setSwipeContainer(swipeContainer);
        frameController.init();

        weeklyReportController.init();

        // ---- Setup Stage ----
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }
}
