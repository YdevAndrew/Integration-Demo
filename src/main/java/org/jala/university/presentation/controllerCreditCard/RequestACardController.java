package org.jala.university.presentation.controllerCreditCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RequestACardController {

    @FXML
    private Button botao;

    @FXML
    public void loadPersonalInfo() {
        try {

            if(CardsPageController.validIfVirtualExist() != null){
                botao.setDisable(true);

            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/personal_information/Form_personal_info.fxml"));
                Parent modalPersonalInfo = fxmlLoader.load();

                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setTitle("Personal Information");
                modalStage.setScene(new Scene(modalPersonalInfo));
                modalStage.showAndWait();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
