package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jala.university.commons.presentation.BaseController;

import java.io.IOException;

public class Homepage extends BaseController {

    @FXML
    private Button btnMain;


    /***
     * Function responsible for loading the billing address screen
     */
    @FXML
    private void initialize() {
        btnMain.setOnAction(event -> {
            try {


               FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/gui/sketch/personal/personaal.fxml"));
               // FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/sketch/financial/financial.fxml"));
                Pane mainView = loader.load();

/*
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/billing_address/Billing_address.fxml"));
                ScrollPane mainView  = loader.load();
*/

                Stage stage = (Stage) btnMain.getScene().getWindow();
                Scene scene = new Scene(mainView, 600, 400);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
