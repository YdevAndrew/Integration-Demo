package org.jala.university.presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.ViewSwitcher;
public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new ScrollPane());
        ViewSwitcher.setup(primaryStage, scene);
        ViewSwitcher.switchTo(CreditCardView.HOMEPAGE.getView());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Credit Card Module Application");
        primaryStage.show();
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMaximized(true);
    }



}
