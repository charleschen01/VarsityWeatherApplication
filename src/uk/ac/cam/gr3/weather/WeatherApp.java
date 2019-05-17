package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.data.util.DataImplementation;
import uk.ac.cam.gr3.weather.gui.panes.SnowPane;
import uk.ac.cam.gr3.weather.gui.util.FXMLController;
import uk.ac.cam.gr3.weather.gui.controllers.FrameController;
import uk.ac.cam.gr3.weather.gui.util.SwipeContainer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class WeatherApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // ------------------------ Load Weather Service ------------------------
        WeatherService service = new DataImplementation();

        // ------------------------ Load Frame ------------------------
        FXMLLoader frameLoader = createLoader("GUI/frame.fxml", service);

        Region frame = frameLoader.load();

        FrameController frameController = frameLoader.getController();

        // ------------------------ Load Snow Report ------------------------
        SnowPane snowReport = new SnowPane(service);


        // ------------------------ Load Home Screen ------------------------
        FXMLLoader homeScreenLoader = createLoader("GUI/HomeScreen.fxml", service);

        Region homeScreen = homeScreenLoader.load();

        // ------------------------ Load Weekly Report ------------------------
        FXMLLoader weekLoader = createLoader("GUI/weekly.fxml", service);

        Region weeklyReport = weekLoader.load();

        // ------------------------ Setup Swipe Container ------------------------
        int width = (int) frame.getPrefWidth();

        snowReport.setPrefWidth(width);

        SwipeContainer swipeContainer = new SwipeContainer(snowReport, homeScreen, weeklyReport, width);

        // ------------------------ Initialise controllers ------------------------
        frameController.setSwipeContainer(swipeContainer);

        // ------------------------ Setup Stage ------------------------
        Scene scene = new Scene(frame);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }

    private static FXMLLoader createLoader(String location, WeatherService service) {

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(location));
        loader.setControllerFactory(param -> {
            if (FXMLController.class.isAssignableFrom(param)) {

                try {
                    Constructor<?> constructor = param.getDeclaredConstructor(WeatherService.class);

                    return constructor.newInstance(service);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    throw new AssertionError("The controller does not implement the expected constructor", e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Exception in controller constructor", e);
                }
            } else throw new IllegalArgumentException("The requested controller '" + param + "' is not an instance of FXMLController.class");
        });

        return loader;
    }
}
