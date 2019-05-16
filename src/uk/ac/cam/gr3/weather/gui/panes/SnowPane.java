package uk.ac.cam.gr3.weather.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import uk.ac.cam.gr3.weather.data.util.SnowData;
import uk.ac.cam.gr3.weather.data.WeatherService;

public class SnowPane extends GridPane {
    private Label snowConditions;
    private Label lastSnowed;
    private Label runPercentage;
    private Label topSnow;
    private Label bottomSnow;

    private SnowData snowData;


    public SnowPane(WeatherService service) {
        setHgap(10);
        setVgap(10);

        setBackground(new Background(new BackgroundImage(new Image("background.jpg"), null, null, null, null)));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(40);
        this.getColumnConstraints().addAll(column1);

        snowConditions = getLabel("--", 40);
        lastSnowed = getLabel("--", 30);
        runPercentage = getLabel("--", 60);
        topSnow = getLabel("--", 30);
        bottomSnow = getLabel("--", 30);

        // Add weather symbol to display
        HBox symbolBox = new HBox();
        symbolBox.setAlignment(Pos.CENTER);
        symbolBox.getChildren().addAll(new ImageView(new Image("images/snowflake-2.png", 200, 200, true, true)));
        add(symbolBox, 0, 0, 2, 1);

        // Add text to display
        add(getLabel("Snow Conditions:", 20), 0 , 1, 2, 1);

        HBox snowConditionsBox = new HBox();
        snowConditionsBox.getChildren().addAll(snowConditions);
        snowConditionsBox.setAlignment(Pos.CENTER);
        add(snowConditionsBox,0 ,2, 2, 1);

        add(getLabel("Last Snowed:", 20), 0, 3, 2, 1);
        add(lastSnowed, 1 ,  4);

        HBox runsOpenBox = new HBox();
        runPercentage.getStyleClass().add("outline");
        runsOpenBox.getChildren().addAll(runPercentage, getLabel("% of the\nruns open", 25));
        runsOpenBox.setAlignment(Pos.CENTER_LEFT);
        add(runsOpenBox, 0 ,5, 2, 1);

        HBox topBox = new HBox();
        topBox.getChildren().addAll(topSnow, getLabel("cm of snow", 15));
        topBox.setAlignment(Pos.BASELINE_LEFT);
        add(topBox, 0 ,6, 2, 1);

        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(bottomSnow, getLabel("cm of snow", 15));
        bottomBox.setAlignment(Pos.BASELINE_LEFT);
        add(bottomBox, 0 ,7, 2, 1);

        snowData = service.getSnowData();

        // Initial Settings
        refreshData();
        //demoData();
    }

    private void refreshData() {
        snowConditions.setText(snowData.getSnowConditions());
        lastSnowed.setText(snowData.getLastSnowed());
        runPercentage.setText(Integer.toString(snowData.getPercentageOpenRuns()));
        topSnow.setText(String.format("Top: %.1f", snowData.getSnowFallTop()));
        bottomSnow.setText(String.format("Bottom: %.1f", snowData.getSnowFallBottom()));
    }

    private void demoData() {
        snowConditions.setText("Icy");
        lastSnowed.setText("01/01/1970");
        runPercentage.setText(Integer.toString(0));
        topSnow.setText(String.format("Top: %.1f", 0.0));
        bottomSnow.setText(String.format("Bottom: %.1f", 0.0));
    }

    private Label getLabel(String text, int fontSize) {
        Label output = new Label(text);
        output.setFont(Font.font(fontSize));
        return output;
    }

}
