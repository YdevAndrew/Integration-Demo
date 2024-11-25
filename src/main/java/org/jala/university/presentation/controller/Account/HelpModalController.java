package org.jala.university.presentation.controller.Account;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class HelpModalController {
    @FXML
    private void handleBackClick(MouseEvent event) {
        // Obtém o Stage atual e o fecha
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

