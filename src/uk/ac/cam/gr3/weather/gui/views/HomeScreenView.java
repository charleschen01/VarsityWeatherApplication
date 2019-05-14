package uk.ac.cam.gr3.weather.gui.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class HomeScreenView extends AnchorPane {

    public HomeScreenView() {

        //add top/bottom button
        HBox hb = new HBox();
        Button buttonTop = new Button("Top");
        Button buttonBottom = new Button("Bottom");
        hb.getChildren().addAll(buttonTop,buttonBottom);

        hb.setPrefHeight(40);
        hb.setAlignment(Pos.CENTER);

        getChildren().addAll(hb);
        setLeftAnchor(hb,0.0);
        setRightAnchor(hb,0.0);
        setTopAnchor(hb,0.0);

        //add weather mood image
        ImageView mood = new ImageView("/images/snowflake-2.png");
        mood.setFitHeight(200);
        mood.setFitWidth(200);
        mood.setX(80);
        mood.setY(80);
        getChildren().add(mood);

        //add celsius label
        Label celsius = new Label("℃");
        celsius.setLayoutX(96.0);
        celsius.setLayoutY(279.0);
        celsius.setFont(Font.font(40));
        getChildren().add(celsius);

        //add temperature label
        Label temp = new Label("8");
        temp.setLayoutX(47.0);
        temp.setLayoutY(280.0);
        temp.setFont(Font.font(80));
        getChildren().add(temp);






    }
}
