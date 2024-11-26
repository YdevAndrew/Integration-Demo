package org.jala.university.presentation.controller.External;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.jala.university.commons.presentation.BaseController;
import org.springframework.stereotype.Controller;
import javafx.scene.control.Button;


import java.awt.*;
import java.io.IOException;

@Controller
public class ButtonServiceController extends BaseController {

    private String serviceName;

    @FXML
    private Button serviceNameButton;

    @FXML
    private AnchorPane mainContent; // Altere para AnchorPane para corresponder ao tipo em ButtonService.fxml

    // Método para definir os dados do pagamento com validações
    public void initialize(String serviceName) {

        this.serviceName = serviceName;

        serviceNameButton.setText("serviceName");
    }

    @FXML
    public void handleServiceClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/External/ScheduleServices/ServiceDetail.fxml"));
        Pane serviceDetailContent = loader.load();
        mainContent.getChildren().setAll(serviceDetailContent);
    }
}