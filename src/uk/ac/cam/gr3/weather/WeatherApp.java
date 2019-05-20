package uk.ac.cam.gr3.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import uk.ac.cam.gr3.weather.data.WeatherService;
import uk.ac.cam.gr3.weather.data.util.DataImplementation;
import uk.ac.cam.gr3.weather.gui.controllers.HomeScreenController;
import uk.ac.cam.gr3.weather.gui.controllers.SnowReportController;
import uk.ac.cam.gr3.weather.gui.controllers.WeeklyReportController;
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
        FXMLLoader snowReportLoader = createLoader("GUI/snowReport.fxml", service);

        Region snowReport = snowReportLoader.load();

        SnowReportController snowReportController = snowReportLoader.getController();

        // ------------------------ Load Home Screen ------------------------
        FXMLLoader homeScreenLoader = createLoader("GUI/homeScreen.fxml", service);

        Region homeScreen = homeScreenLoader.load();

        HomeScreenController homeScreenController = homeScreenLoader.getController();

        // ------------------------ Load Weekly Report ------------------------
        FXMLLoader weekLoader = createLoader("GUI/weekly.fxml", service);

        Region weeklyReport = weekLoader.load();

        WeeklyReportController weeklyReportController = weekLoader.getController();

        // ------------------------ Setup Swipe Container ------------------------
        int width = (int) frame.getPrefWidth();

        SwipeContainer swipeContainer = new SwipeContainer(snowReport, snowReportController, homeScreen, homeScreenController, weeklyReport, weeklyReportController, width);

        // ------------------------ Initialise controllers ------------------------
        frameController.setSwipeContainer(swipeContainer);

        // ------------------------ Setup Stage ------------------------
        Scene scene = new Scene(frame);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }

    /**
     * <p>Attempts to create an FXMLLoader for the fxml file in system resources with path 'location'.
     * This assumes that the associated controller class is a subclass of FXMLController.
     *
     * <p>It also assumes the controller class has a public constructor that takes a WeatherService, and uses that to
     * inject the WeatherService into the controller.
     */
    private static FXMLLoader createLoader(String location, WeatherService service) {

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(location));
        loader.setControllerFactory(param -> {
            if (FXMLController.class.isAssignableFrom(param)) {
                // If the requested controller class is a subclass of FXMLController

                try {
                    // Get the public constructor taking a WeatherService as input.
                    Constructor<?> constructor = param.getDeclaredConstructor(WeatherService.class);

                    // Create an instance.
                    return constructor.newInstance(service);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    // Either the desired constructor doesn't exist, the controller class is abstract
                    // or the constructor isn't public.
                    throw new AssertionError("The controller does not implement the expected constructor", e);
                } catch (InvocationTargetException e) {
                    // The constructor threw an exception.
                    throw new RuntimeException("Exception in controller constructor", e);
                }
            } else throw new IllegalArgumentException("The requested controller '" + param + "' is not an instance of FXMLController.class");
        });

        return loader;
    }
}
