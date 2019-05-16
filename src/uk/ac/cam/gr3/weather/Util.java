package uk.ac.cam.gr3.weather;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Util {

    public static double clamp(double value, double min, double max) {

        if (value <= min)
            return min;
        if (value >= max)
            return max;
        return value;
    }

    public static void fitToAnchorPane(Node child) {

        setAnchorPaneConstraints(child, 0, 0, 0, 0);
    }

    public static void setAnchorPaneConstraints(Node child, double top, double right, double bottom, double left) {

        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setRightAnchor(child, right);
        AnchorPane.setBottomAnchor(child, bottom);
        AnchorPane.setLeftAnchor(child, left);
    }
}
