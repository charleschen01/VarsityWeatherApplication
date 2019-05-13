package uk.ac.cam.gr3.weather.gui.panes;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

public class SnowPane extends GridPane {
    private Text snowConditions;
    private Text lastSnowed;
    private Text runPercentage;
    private Text topSnow;
    private Text bottomSnow;


    public SnowPane() {
        setHgap(10);
        setVgap(10);

        snowConditions = getTextObject("--", 40);
        lastSnowed = getTextObject("--", 30);
        runPercentage = getTextObject("--", 40);
        topSnow = getTextObject("--", 30);
        bottomSnow = getTextObject("--", 30);

        Text test1 = new Text("Image");
        test1.setFont(Font.font(30));
        add(test1, 0, 0);

        // Picture needs to go here
        add(getTextObject("Snow Conditions:", 20), 0 , 1);
        add(snowConditions,0 ,2);
        add(getTextObject("Last Snowed:", 20), 0, 3);
        add(lastSnowed, 0 ,  4);

        HBox runsOpenBox = new HBox();
        runsOpenBox.getChildren().addAll(runPercentage, getTextObject("% of the runs open", 20));
        add(runsOpenBox, 0 ,5);

        HBox topBox = new HBox();
        topBox.getChildren().addAll(getTextObject("Top:", 30), topSnow);
        add(topBox, 0 ,6);

        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(getTextObject("Bottom:", 30), bottomSnow);
        add(bottomBox, 0 ,7);
    }

    private Text getTextObject(String text, int fontSize) {
        Text result = new Text(text);
        result.setFont(Font.font(fontSize));
        return result;
    }

    public void setSnowConditions(String conditions) {
        // Could be using an enum to set possible valid conditions
        // Probably needs to update the picture as well
        snowConditions.setText(conditions);
    }
    public void setDateLastSnowed(String snowed) {
        lastSnowed.setText(snowed);
    }

    public void setOpenRunPercentage(int percentage) {
        runPercentage.setText(Integer.toString(percentage));
    }

    public void setTopSnowDepth(int depth) {
        topSnow.setText(depth + "cm of snow");
    }

    public void setBottomSnow(int depth) {
        bottomSnow.setText(depth + "cm of snow");
    }


}
