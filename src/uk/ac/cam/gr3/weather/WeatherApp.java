package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.gui.FrameController;
import uk.ac.cam.gr3.weather.gui.panes.SnowPane;
import uk.ac.cam.gr3.weather.gui.util.HSwipeContainer;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("GUI/frame.fxml"));

        Region frame = loader.load();

        FrameController controller = loader.getController();

        Scene scene = new Scene(frame);

        SnowPane left = new SnowPane();
        Pane centre = new Pane(), right = new Pane();

        int width = (int) frame.getPrefWidth();
        int height = (int) frame.getPrefHeight();

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

        controller.setSwipeContainer(container);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }
}
