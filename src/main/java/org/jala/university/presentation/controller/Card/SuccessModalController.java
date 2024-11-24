
package org.jala.university.presentation.controller.Card;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SuccessModalController {

    @FXML
    private Button okButton;

    @FXML
    private Label dueDataLabel;

    @FXML
    private Label labelCompletDate;

    public void setDueDate(String dueDate, String completeDate) {
        dueDataLabel.setText(dueDate);
        labelCompletDate.setText(completeDate);
    }

    @FXML
    private void closeSuccessModal(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}