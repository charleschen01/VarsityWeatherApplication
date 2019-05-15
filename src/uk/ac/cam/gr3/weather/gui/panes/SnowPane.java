package uk.ac.cam.gr3.weather.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import uk.ac.cam.gr3.weather.data.util.SnowData;
import uk.ac.cam.gr3.weather.gui.util.AlignedTextBox;

public class SnowPane extends GridPane {
    private AlignedTextBox snowConditions;
    private AlignedTextBox lastSnowed;
    private AlignedTextBox runPercentage;
    private AlignedTextBox topSnow;
    private AlignedTextBox bottomSnow;

    private SnowData snowData;


    public SnowPane() {
        setHgap(10);
        setVgap(10);

        setBackground(new Background(new BackgroundImage(new Image("file:resources/background.jpg"), null, null, null, null)));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(40);
        this.getColumnConstraints().addAll(column1);

        snowConditions = new AlignedTextBox("--", 40, Pos.CENTER);
        lastSnowed = new AlignedTextBox("--", 30);
        runPercentage = new AlignedTextBox("--", 50);
        topSnow = new AlignedTextBox("--", 30);
        bottomSnow = new AlignedTextBox("--", 30);

        // Add weather symbol to display
        // TODO: get this to dynamically update
        ImageView weatherSymbol = new ImageView(new Image("file:resources/WeatherIcons/HeavySnow.gif", 300, 0, true, true));
        HBox symbolBox = new HBox();
        symbolBox.setAlignment(Pos.CENTER);
        symbolBox.getChildren().addAll(weatherSymbol);
        add(symbolBox, 0, 0, 2, 1);

        // Add text to display
        add(new AlignedTextBox("Snow Conditions:", 20), 0 , 1, 2, 1);
        add(snowConditions,0 ,2, 2, 1);
        add(new AlignedTextBox("Last Snowed:", 20), 0, 3, 2, 1);
        add(lastSnowed, 1 ,  4);

        HBox runsOpenBox = new HBox();
        runsOpenBox.getChildren().addAll(runPercentage, new AlignedTextBox("% of the\nruns open", 20, Pos.CENTER));
        add(runsOpenBox, 0 ,5, 2, 1);

        HBox topBox = new HBox();
        topBox.getChildren().addAll(topSnow, new AlignedTextBox("cm of snow", 15, Pos.BOTTOM_CENTER));
        add(topBox, 0 ,6, 2, 1);

        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(bottomSnow, new AlignedTextBox("cm of snow", 15, Pos.BOTTOM_CENTER));
        add(bottomBox, 0 ,7, 2, 1);

        snowData = new SnowData();

        // Initial Settings
        refreshData();
    }

    private void refreshData() {
        // TODO: needs to update picture as well
        snowConditions.setText(snowData.getSnowConditions());
        lastSnowed.setText(snowData.getLastSnowed());
        runPercentage.setText(Integer.toString(snowData.getPercentageOpenRuns()));
        topSnow.setText(String.format("Top: %.1f", snowData.getSnowFallTop()));
        bottomSnow.setText(String.format("Bottom: %.1f", snowData.getSnowFallBottom()));
    }

}
