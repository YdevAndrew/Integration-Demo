package org.jala.university.presentation.controller.Account;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;

import java.io.IOException;

public class Homepage extends BaseController {

    @FXML
    private Button btnMain;

    /**
     * Function responsible for loading the dashboard screen.
     */
    @FXML
    private void initialize() {
        btnMain.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/board/DashboardApp.fxml"));
                AnchorPane mainView = loader.load();

                Stage stage = (Stage) btnMain.getScene().getWindow();
                Scene scene = new Scene(mainView, 600, 400);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // Or handle the exception appropriately
            }
        });
    }
}